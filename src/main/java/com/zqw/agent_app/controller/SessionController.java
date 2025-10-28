package com.zqw.agent_app.controller;

import com.zqw.agent_app.common.Result;
import com.zqw.agent_app.model.vo.SessionVO;
import com.zqw.agent_app.service.SessionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {
    @Resource
    private SessionService sessionService;

    /**
     * 获取所有历史会话记录
     * @return 历史会话记录
     */
    @RequestMapping(value = "/get_session_by_agent_id", method = RequestMethod.GET)
    public Result<List<SessionVO>> getSessionByAgentId(@RequestParam Integer agentId) {
        List<SessionVO> sessionVOList = sessionService.getSessionByAgentId(agentId);
        return Result.success(sessionVOList);
    }


}
