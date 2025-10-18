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

    List<AgentPO> getHotModel();
}
