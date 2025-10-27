package com.zqw.agent_app.mapper;

import com.zqw.agent_app.model.po.MessageLogPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {

    /**
     * 插入单条消息记录 (用户或AI)。
     */
    int insert(MessageLogPO messageLogPO);

    /**
     * 查询最近的消息历史 (用于构建上下文)。
     * @param sessionId 会话ID
     * @param limit 限制返回的消息条数
     * @return 消息列表，按时间升序返回
     */
    List<MessageLogPO> getRecentMessages(@Param("sessionId") Integer sessionId, @Param("limit") Integer limit);

    /**
     * 统计指定会话中，自上次会话更新时间以来新增的消息数量。
     * (或者更简单：直接统计 message_log 中属于该 session_id 的总消息数)
     * @param sessionId 会话ID
     * @return 消息总数
     */
    Integer countMessagesBySessionId(@Param("sessionId") Integer sessionId);

    List<MessageLogPO> getMessagesSinceLastSummary(@Param("sessionId") Integer sessionId);
}
