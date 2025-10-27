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
public class SessionPO {

    private Integer sessionId;
    private Integer agentId;
    private Integer userId;
    private String title;
    private String contextSummary;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
