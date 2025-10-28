package com.zqw.agent_app.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer agentId;
    private Integer sessionId;
    private String message;
    private Integer userId;

}
