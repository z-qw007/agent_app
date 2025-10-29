package com.zqw.agent_app.controller;

import com.zqw.agent_app.common.Result;
import com.zqw.agent_app.model.dto.AgentRequestDTO;
import com.zqw.agent_app.model.dto.ChatRequestDTO;
import com.zqw.agent_app.model.dto.ChatResponseDTO;
import com.zqw.agent_app.model.po.AgentPO;
import com.zqw.agent_app.model.vo.AgentVO;
import com.zqw.agent_app.service.AgentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agent")
public class AgentController {
    @Resource
    private AgentService agentService;

    /**
     * 获取所有智能体数量
     *
     * @return 智能体数量
     */
    @RequestMapping(value = "/allNum", method = RequestMethod.GET)
    public Result<Integer> getAllAgentNum() {
        int num = agentService.getAllAgentNum();
        return Result.success(num);
    }

    /**
     * 模型展示功能
     *
     * @return
     */
    @RequestMapping(value = "/fetchModel", method = RequestMethod.GET)
    public Result<List<AgentVO>> fetchModel() {
        List<AgentVO> agentList = agentService.fetchModel();
        return Result.success(agentList);
    }


    /**
     * 获取上一周创建智能体数量
     *
     * @return 上一周智能体数量
     */
    @RequestMapping(value = "/lastWeekNum", method = RequestMethod.GET)
    public Result<Integer> getLastWeekAgentNum() {
        int num = agentService.getLastWeekAgentNum();
        return Result.success(num);
    }

    /**
     * 获取热门模型，根据点击次数排序
     *
     * @return 热门模型列表
     */
    @RequestMapping(value = "/hotModel", method = RequestMethod.GET)
    public Result<List<AgentPO>> getHotModel() {
        List<AgentPO> hotModelList = agentService.getHotModel();
        return Result.success(hotModelList);
    }

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public Result<ChatResponseDTO> chat(@RequestBody ChatRequestDTO chatRequestDTO) {
        try {
            // 核心业务逻辑调用
            ChatResponseDTO aiResponse = agentService.chat(
                    chatRequestDTO.getAgentId(),
                    chatRequestDTO.getSessionId(),
                    chatRequestDTO.getUserId(),
                    chatRequestDTO.getMessage()
            );

            // 返回成功结果
            return Result.success(aiResponse);

        } catch (Exception e) {
            // 返回业务异常或通用错误信息
            return Result.failed("AI服务处理失败：" + e.getMessage());
        }
    }

    @RequestMapping(value = "/add_agent", method = RequestMethod.POST)
    public Result<Integer> addAgent(@RequestBody AgentRequestDTO agentAddDTO) {
        int agentId = agentService.addAgent(agentAddDTO);
        return Result.success(agentId);
    }

    @RequestMapping(value = "/select_by_keyword", method = RequestMethod.GET)
    public Result<List<AgentVO>> selectByKeyword(@RequestParam String keyword) {
        List<AgentVO> agentVOList = agentService.selectByKeyword(keyword);
        return Result.success(agentVOList);
    }

    @RequestMapping(value = "/add_usage_count", method = RequestMethod.POST)
    public Result<Boolean> addUsageCount(@RequestParam int agentId) {
        boolean isSuccess = agentService.addUsageCount(agentId);
        return Result.success(isSuccess);
    }

}
