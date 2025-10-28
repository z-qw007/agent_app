package com.zqw.agent_app.service;

import com.zqw.agent_app.model.vo.MessageLogVO;

import java.util.List;

public interface MessageService {


    /**
     * 根据会话ID获取消息列表
     * @param sessionId 会话ID
     * @return 消息列表
     */
    List<MessageLogVO> getMessageList(Integer sessionId);
}
