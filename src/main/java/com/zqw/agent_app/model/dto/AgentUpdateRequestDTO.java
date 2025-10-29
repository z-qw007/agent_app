package com.zqw.agent_app.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AgentUpdateRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int agentId;
    private String agentName;
    private String description;
    private String prompt;
    private Double temperature;
    private Double topP;

}
