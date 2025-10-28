package com.zqw.agent_app.mapper;

import com.zqw.agent_app.model.po.SessionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


@Mapper
public interface SessionMapper {
    // 创建会话
    int createSession(SessionPO sessionPO);

    SessionPO getById(Integer sessionId);

    void updateSummary(Integer sessionId, String newSummary);

    Date getLastSummaryTime(@Param("sessionId") Integer sessionId);

    List<SessionPO> selectByAgentId(Integer agentId);
}
