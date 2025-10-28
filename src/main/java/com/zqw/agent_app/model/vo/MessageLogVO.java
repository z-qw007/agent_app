package com.zqw.agent_app.model.vo;

import lombok.Data;

@Data
public class MessageLogVO {
    private Integer sessionId;
    private String content;
    private String role;
}
