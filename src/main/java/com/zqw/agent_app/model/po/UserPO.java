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
public class UserPO {

    private int userId;
    private String userName;
    private String email;
    private String userPassword;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private Long modelCount;

}
