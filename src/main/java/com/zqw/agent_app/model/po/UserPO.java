package com.zqw.agent_app.model.po;

import cn.hutool.core.date.DateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPO {

    private String userName;
    private String email;
    private String password;
    private DateTime createdTime;
    private DateTime updateTime;

}
