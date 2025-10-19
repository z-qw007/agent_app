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
public class UserPO {

    private int userId;
    private String userName;
    private String email;
    private String userPassword;
    private DateTime createdTime;
    private DateTime updateTime;

}
