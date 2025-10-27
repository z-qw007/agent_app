package com.zqw.agent_app.mapper;

import com.zqw.agent_app.model.po.SessionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SessionMapper {
    // 创建会话
    int createSession(
            @Param("agentId") Integer agentId,
            @Param("userId") Integer userId,
            @Param("sessionName") String sessionName);

    SessionPO getById(Integer sessionId);

    void updateSummary(Integer sessionId, String newSummary);
}
