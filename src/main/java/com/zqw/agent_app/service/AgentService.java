package com.zqw.agent_app.service;

import com.zqw.agent_app.model.dto.AgentRequestDTO;
import com.zqw.agent_app.model.dto.AgentUpdateRequestDTO;
import com.zqw.agent_app.model.dto.ChatResponseDTO;
import com.zqw.agent_app.model.po.AgentPO;
import com.zqw.agent_app.model.vo.AgentVO;

import java.util.List;

public interface AgentService {
    /**
     * 获取所有智能体数量
     * @return 智能体数量
     */
    int getAllAgentNum();

    /**
     * 获取本周创建智能体数量
     * @return 本周智能体数量
     */
    int getLastWeekAgentNum();

    /**
     * 获取热门模型
     * @return 热门模型列表
     */
    List<AgentPO> getHotModel();

    /**
     * 处理用户与智能体的聊天请求，并更新会话状态。
     * @param agentId 智能体ID
     * @param sessionId 当前会话ID (如果为 null 或 0，则创建新会话)
     * @param userId 当前用户ID
     * @param userMessage 用户发送的消息内容
     * @return 智能体的回复内容
     */
    ChatResponseDTO chat(Integer agentId, Integer sessionId, Integer userId, String userMessage);

    /**
     * 异步或定时触发：对当前会话进行摘要压缩。
     */
    void summarizeSession(Integer sessionId);

    /**
     * 获取智能体模型
     * @return 智能体模型列表
     */
    List<AgentVO> fetchModel();

    /**
     * 添加智能体
     * @param agentAddDTO 智能体信息
     * @return 是否添加成功
     */
    int addAgent(AgentRequestDTO agentAddDTO);

    List<AgentVO> selectByKeyword(String keyword);

    boolean addUsageCount(int agentId);

    List<AgentVO> fetchModelByUserId(int userId);

    boolean deleteAgent(int agentId);

    boolean updateAgent(AgentUpdateRequestDTO agentUpdateRequestDTO);
}
