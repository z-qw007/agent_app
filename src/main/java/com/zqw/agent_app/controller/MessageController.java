package com.zqw.agent_app.controller;

import com.zqw.agent_app.common.Result;
import com.zqw.agent_app.model.vo.MessageLogVO;
import com.zqw.agent_app.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 获取消息列表
     * @param sessionId 会话ID
     * @return 消息列表
     */
    @RequestMapping( value = "/message_list", method = RequestMethod.GET)
    public Result<List<MessageLogVO>> getMessageList(@RequestParam Integer sessionId) {
        List<MessageLogVO> messageVOList = messageService.getMessageList(sessionId);
        return Result.success(messageVOList);
    }

}
