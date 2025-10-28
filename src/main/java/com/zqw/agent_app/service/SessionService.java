package com.zqw.agent_app.service;

import com.zqw.agent_app.model.vo.SessionVO;

import java.util.List;

public interface SessionService {


    /**
     * 根据agentId获取会话
     * @param agentId
     * @return
     */
    List<SessionVO> getSessionByAgentId(Integer agentId);
}
