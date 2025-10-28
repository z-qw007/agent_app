package com.zqw.agent_app.service.impl;

import com.zqw.agent_app.mapper.MessageMapper;
import com.zqw.agent_app.model.po.MessageLogPO;
import com.zqw.agent_app.model.vo.MessageLogVO;
import com.zqw.agent_app.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@EnableAsync
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper messageMapper;


    @Override
    public List<MessageLogVO> getMessageList(Integer sessionId) {
        List<MessageLogPO> messageLogPOList = messageMapper.getMessageBySessionId(sessionId);

        return messageLogPOList.stream().map(messageLogPO -> {
            MessageLogVO messageLogVO = new MessageLogVO();
            messageLogVO.setContent(messageLogPO.getContent());
            messageLogVO.setRole(messageLogPO.getRole());
            messageLogVO.setSessionId(messageLogPO.getSessionId());
            return messageLogVO;
        }).collect(Collectors.toList());
    }

}

