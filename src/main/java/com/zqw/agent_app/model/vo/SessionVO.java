package com.zqw.agent_app.model.vo;

import lombok.Data;

@Data
public class SessionVO {

    private Integer sessionId;
    private Integer agentId;
    private Integer userId;
    private String title;
    private String contextSummary;

}
