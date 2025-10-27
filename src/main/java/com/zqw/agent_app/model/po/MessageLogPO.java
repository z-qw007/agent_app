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
public class MessageLogPO {

    private Integer messageId;
    private Integer sessionId;
    private String role;
    private String content;
    private LocalDateTime createTime;

}
