package com.zqw.agent_app.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer sessionId;
    private String aiContent;

}
