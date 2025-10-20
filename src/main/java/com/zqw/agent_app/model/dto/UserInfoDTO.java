package com.zqw.agent_app.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class UserInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userId;
    private String userName;
    private String email;
    private Long modelCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
