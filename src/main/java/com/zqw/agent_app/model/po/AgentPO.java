package com.zqw.agent_app.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentPO {

    private int agentId;
    private String agentName;
    private int userId;
    private String description;
    private String prompt;
    private String configJson;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private int usageCount;
}
