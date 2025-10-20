package com.zqw.agent_app.service;

import com.zqw.agent_app.model.po.AgentPO;

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
}
