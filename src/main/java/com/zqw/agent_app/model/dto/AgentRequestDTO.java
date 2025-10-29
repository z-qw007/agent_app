package com.zqw.agent_app.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AgentRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String agentName;
    private int userId;
    private String description;
    private String prompt;
    private Double temperature;
    private Double topP;
    private int status;
    private int usageCount;

}
