package com.zqw.agent_app.service.impl;

import com.zqw.agent_app.mapper.SessionMapper;
import com.zqw.agent_app.model.po.SessionPO;
import com.zqw.agent_app.model.vo.SessionVO;
import com.zqw.agent_app.service.SessionService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@EnableAsync
@Service
public class SessionServiceImpl implements SessionService {

    @Resource
    private SessionMapper sessionMapper;

    @Override
    public List<SessionVO> getSessionByAgentId(Integer agentId) {
        List<SessionPO> sessionPOList = sessionMapper.selectByAgentId(agentId);

        return sessionPOList.stream().map(sessionPO -> {
            SessionVO sessionVO = new SessionVO();
            sessionVO.setSessionId(sessionPO.getSessionId());
            sessionVO.setUserId(sessionPO.getUserId());
            sessionVO.setAgentId(sessionPO.getAgentId());
            sessionVO.setTitle(sessionPO.getTitle());
            sessionVO.setContextSummary(sessionPO.getContextSummary());
            return sessionVO;
        }).collect(Collectors.toList());
    }
}

