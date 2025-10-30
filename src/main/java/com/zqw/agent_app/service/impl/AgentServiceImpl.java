package com.zqw.agent_app.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqw.agent_app.mapper.MessageMapper;
import com.zqw.agent_app.model.dto.AgentRequestDTO;
import com.zqw.agent_app.model.dto.AgentUpdateRequestDTO;
import com.zqw.agent_app.model.dto.ChatResponseDTO;
import com.zqw.agent_app.model.po.MessageLogPO;
import com.zqw.agent_app.model.vo.AgentVO;
import io.micrometer.common.util.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import com.zqw.agent_app.common.ResultCode;
import com.zqw.agent_app.exception.BusinessException;
import com.zqw.agent_app.mapper.AgentMapper;
import com.zqw.agent_app.mapper.SessionMapper;
import com.zqw.agent_app.model.po.AgentPO;
import com.zqw.agent_app.model.po.SessionPO;
import com.zqw.agent_app.service.AgentService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@EnableAsync
@Service
public class AgentServiceImpl implements AgentService {

    @Resource
    private ChatClient chatClient;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private AgentMapper agentMapper;

    @Resource
    private SessionMapper sessionMapper;

    @Resource
    private MessageMapper messageMapper;

    @Override
    public int getAllAgentNum(){
        return agentMapper.getAllAgentNum();
    }

    @Override
    public int getLastWeekAgentNum() {
        return agentMapper.getLastWeekAgentNum();
    }

    @Override
    public List<AgentPO> getHotModel() {
        return agentMapper.getHotModel();
    }

    @Override
    public ChatResponseDTO chat(Integer agentId, Integer sessionId, Integer userId, String userMessage) {
        // 数据库获取agent相关信息
        AgentPO agentPO = agentMapper.getAgentById(agentId);
        if (agentPO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "agent不存在");
        }

        // 确定会话ID (如果传入的sessionId为null，需要创建新的 chat_session 记录并返回新ID)
        if (sessionId == null || sessionId == 0) {
            SessionPO sessionPO = new SessionPO().builder()
                    .agentId(agentId)
                    .userId(userId)
                    .title("新对话")
                    .build();
            sessionMapper.createSession(sessionPO);
            sessionId = sessionPO.getSessionId();
        }

        // 分布式锁，防止同一个用户在同一个会话中同时进行多次请求
        String lockKey = "lock:chat:session:" + sessionId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 设置等待时间（如10秒）和租约时间（如30秒），防止死锁
            boolean locked = lock.tryLock(10, 30, TimeUnit.SECONDS);

            if (!locked) {
                // 如果获取锁失败，表示该会话已有其他请求在处理
                throw new BusinessException(ResultCode.BUSINESS_ERROR, "该会话已有其他请求在处理，请稍后再试");
            }

            // 构建消息列表 (上下文核心逻辑)
            List<Message> messages = new ArrayList<>();

            // 添加Agent的系统提示词
            messages.add(new SystemMessage(agentPO.getPrompt()));

            // 添加历史摘要
            SessionPO sessionPO = sessionMapper.getById(sessionId);
            if (sessionPO != null && !StringUtils.isEmpty(sessionPO.getContextSummary())) {
                messages.add(new AssistantMessage(sessionPO.getContextSummary()));
            }

            // 添加最近10条消息
            List<MessageLogPO> recentHistory = messageMapper.getRecentMessages(sessionId, 10);
            recentHistory.forEach(log -> {
                if ("USER".equals(log.getRole())) {
                    messages.add(new UserMessage(log.getContent()));
                } else if ("AI".equals(log.getRole())) {
                    messages.add(new AssistantMessage(log.getContent()));
                }
            });

            // 添加当前用户信息
            messages.add(new UserMessage(userMessage));

            Map<String, Object> configMap = null;
            try {
                configMap = objectMapper.readValue(agentPO.getConfigJson(), new TypeReference<Map<String, Object>>() {});
            } catch (JsonProcessingException e) {
                throw new BusinessException(ResultCode.FAILED, "解析配置失败");
            }

            // 设置模型参数
            DashScopeChatOptions options = DashScopeChatOptions.builder()
                    .withTemperature(getFloatValueFromConfig(configMap, "temperature", 0.70d))
                    .withTopP(getFloatValueFromConfig(configMap, "top_p", 0.80d))
                    .build();

            // 调用 AI 客户端
            ChatResponse response = chatClient.prompt()
                    .messages(messages)
                    .options(options)
                    .call()
                    .chatResponse();

            // 获取响应
            String aiResponse = response.getResult().getOutput().getText();

            // 存储消息记录
            // 用户消息
            MessageLogPO userMessageLog = MessageLogPO.builder()
                    .sessionId(sessionId)
                    .role("USER")
                    .content(userMessage)
                    .build();

            // AI 消息
            MessageLogPO aiMessageLog = MessageLogPO.builder()
                    .sessionId(sessionId)
                    .role("AI")
                    .content(aiResponse)
                    .build();

            // 插入库表
            messageMapper.insert(userMessageLog);
            messageMapper.insert(aiMessageLog);


            Date lastSummaryTime = sessionMapper.getLastSummaryTime(sessionId);
            final int SUMMARY_THRESHOLD = 10; // 定义阈值
            Integer newMessageCount = messageMapper.countMessagesSinceLastSummary(sessionId, lastSummaryTime);

            if (newMessageCount != null && newMessageCount >= SUMMARY_THRESHOLD) {
                summarizeSession(sessionId);
            }

            // 构建返回响应类
            ChatResponseDTO chatResponseDTO = new ChatResponseDTO();
            chatResponseDTO.setAiContent(aiResponse);
            chatResponseDTO.setSessionId(sessionId);

            return chatResponseDTO;

        } catch (InterruptedException e) {
            // 处理线程中断异常
            Thread.currentThread().interrupt();
            throw new BusinessException(ResultCode.FAILED, "系统错误，处理线程中断");
        } finally {
            // 确保锁一定会被释放，且只释放当前线程持有的锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }

    }

    @Override
    @Async
    public void summarizeSession(Integer sessionId) {
        // 1. 获取需要摘要的消息
        Date lastSummaryTime = sessionMapper.getLastSummaryTime(sessionId);
        List<MessageLogPO> messagesToSummarize = messageMapper.getMessagesSinceLastSummaryByTime(sessionId, lastSummaryTime);
        if (messagesToSummarize.isEmpty()) return;

        // 2. 构建摘要提示
        String summaryPrompt = "请将以下对话历史和现有的摘要进行合并，生成一个新的、精简的摘要，以便后续对话使用。现有摘要为：\""
                + sessionMapper.getById(sessionId).getContextSummary() + "\"。请只返回摘要内容。";

        // 3. 构建发送给 AI 的消息列表
        List<Message> summaryMessages = new ArrayList<>();
        summaryMessages.add(new SystemMessage(summaryPrompt));
        // 将要摘要的旧消息内容作为 UserMessage 附加
        messagesToSummarize.forEach(log -> {
            summaryMessages.add(new UserMessage(log.getRole() + ": " + log.getContent()));
        });

        // 4. 调用 AI 客户端
        ChatResponse summaryResponse = chatClient.prompt()
                .messages(summaryMessages)
                .call()
                .chatResponse();
        String newSummary = summaryResponse.getResult().getOutput().getText();

        // 5. 更新 chat_session 表
        sessionMapper.updateSummary(sessionId, newSummary);
    }

    @Override
    public List<AgentVO> fetchModel() {
        List<AgentPO> agentPOS = agentMapper.fetchModel();
        return getAgentVOList(agentPOS);
    }

    /**
     * 新增模型
     * @param agentAddDTO 新增模型请求类
     * @return 是否添加成功
     */
    @Override
    public int addAgent(AgentRequestDTO agentAddDTO) {
        double temperature = agentAddDTO.getTemperature();
        double topP = agentAddDTO.getTopP();

        String configJson = String.format("{\"temperature\": %.1f, \"top_p\": %.1f}", temperature, topP);

        AgentPO agentPO = AgentPO.builder()
                .agentName(agentAddDTO.getAgentName())
                .userId(agentAddDTO.getUserId())
                .description(agentAddDTO.getDescription())
                .prompt(agentAddDTO.getPrompt())
                .configJson(configJson)
                .createdTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        agentMapper.insertAgent(agentPO);

        return agentPO.getAgentId();
    }

    @Override
    public List<AgentVO> selectByKeyword(String keyword) {
        List<AgentPO> agentPOS = agentMapper.selectByKeyword(keyword);
        return getAgentVOList(agentPOS);
    }

    @Override
    public boolean addUsageCount(int agentId) {
        AgentPO agentPO = agentMapper.getAgentById(agentId);
        if (agentPO == null) {
            return false;
        }

        agentPO.setUsageCount(agentPO.getUsageCount() + 1);
        agentMapper.updateUsageCountById(agentPO);
        return true;
    }

    @Override
    public List<AgentVO> fetchModelByUserId(int userId) {
        List<AgentPO> agentPOS = agentMapper.fetchModelByUserId(userId);
        return getAgentVOList(agentPOS);
    }

    @Override
    public boolean deleteAgent(int agentId) {
        AgentPO agent = agentMapper.getAgentById(agentId);
        agent.setStatus(0);
        agent.setUpdateTime(LocalDateTime.now());
        agentMapper.deleteAgent(agent);
        return true;
    }

    @Override
    public boolean updateAgent(AgentUpdateRequestDTO agentUpdateRequestDTO) {
        AgentPO agentPO = agentMapper.getAgentById(agentUpdateRequestDTO.getAgentId());
        agentPO.setAgentName(agentUpdateRequestDTO.getAgentName());
        agentPO.setDescription(agentUpdateRequestDTO.getDescription());
        agentPO.setPrompt(agentUpdateRequestDTO.getPrompt());

        Double topP = agentUpdateRequestDTO.getTopP();
        Double temperature = agentUpdateRequestDTO.getTemperature();

        String configJson = String.format("{\"temperature\": %.1f, \"top_p\": %.1f}", temperature, topP);
        agentPO.setConfigJson(configJson);
        agentPO.setUpdateTime(LocalDateTime.now());
        agentMapper.updateAgent(agentPO);
        return true;
    }

    /**
     * 将agentPO列表转换为agentVO列表
     * @param agentPOS agentPO列表
     * @return agentVO列表
     */
    private List<AgentVO> getAgentVOList(List<AgentPO> agentPOS) {
        return agentPOS.stream().map(agentPO -> {
            AgentVO agentVO = new AgentVO();
            agentVO.setAgentId(agentPO.getAgentId());
            agentVO.setAgentName(agentPO.getAgentName());
            agentVO.setDescription(agentPO.getDescription());
            agentVO.setUsageCount(agentPO.getUsageCount());
            return agentVO;
        }).collect(Collectors.toList());
    }

    /**
     * 安全地从配置Map中提取浮点数。
     * 处理 Jackson 默认解析为 Double 的情况，并转换为 Float。
     */
    private double getFloatValueFromConfig(Map<String, Object> map, String key, double defaultValue) {
        Object value = map.get(key);
        if (value == null) {
            return defaultValue;
        }

        if (value instanceof Number) {
            Number number = (Number) value;
            // 直接使用 floatValue()，精度不会丢失（0.75 Double 转 Float 仍是 0.75）
            return number.floatValue();
        }

        // 如果值不是数字类型，则返回默认值
        return defaultValue;
    }
}

