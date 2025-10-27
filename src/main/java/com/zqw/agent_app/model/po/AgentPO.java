package com.zqw.agent_app.model.po;

import cn.hutool.core.date.DateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private DateTime createdTime;
    private DateTime updateTime;
    private int usageCount;
}
