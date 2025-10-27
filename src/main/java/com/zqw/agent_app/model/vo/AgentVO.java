package com.zqw.agent_app.model.vo;

import lombok.Data;

@Data
public class AgentVO {

    private Long agentId;
    private String agentName;
    private String description;
    private Integer usageCount;
}
