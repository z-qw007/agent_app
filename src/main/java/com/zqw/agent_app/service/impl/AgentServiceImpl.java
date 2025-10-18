package com.zqw.agent_app.service.impl;

import com.zqw.agent_app.mapper.AgentMapper;
import com.zqw.agent_app.model.po.AgentPO;
import com.zqw.agent_app.service.AgentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    @Resource
    private AgentMapper agentMapper;

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
}

