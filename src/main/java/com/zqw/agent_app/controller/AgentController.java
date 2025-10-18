package com.zqw.agent_app.controller;

import com.zqw.agent_app.common.Result;
import com.zqw.agent_app.model.po.AgentPO;
import com.zqw.agent_app.service.AgentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agent")
public class AgentController {
    @Resource
    private AgentService agentService;

    /**
     * 获取所有智能体数量
     * @return 智能体数量
     */
    @RequestMapping(value = "/allNum", method = RequestMethod.GET)
    public Result<Integer> getAllAgentNum() {
        int num = agentService.getAllAgentNum();
        return Result.success(num);
    }

    /**
     * 获取上一周创建智能体数量
     * @return 上一周智能体数量
     */
    @RequestMapping(value = "/lastWeekNum", method = RequestMethod.GET)
    public Result<Integer> getLastWeekAgentNum() {
        int num = agentService.getLastWeekAgentNum();
        return Result.success(num);
    }

    /**
     * 获取热门模型，根据点击次数排序
     * @return 热门模型列表
     */
    @RequestMapping(value = "/hotModel", method = RequestMethod.GET)
    public Result<List<AgentPO>> getHotModel() {
        List<AgentPO> hotModelList = agentService.getHotModel();
        return Result.success(hotModelList);
    }
}
