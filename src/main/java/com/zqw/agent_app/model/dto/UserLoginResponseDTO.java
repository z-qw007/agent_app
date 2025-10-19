package com.zqw.agent_app.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserLoginResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;
    private int userId;
    private String userName;

}
