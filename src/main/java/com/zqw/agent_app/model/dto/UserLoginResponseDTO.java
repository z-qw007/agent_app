package com.zqw.agent_app.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private String email;
    private String password;
}
