package com.zqw.agent_app.service;

import com.zqw.agent_app.common.Result;
import com.zqw.agent_app.model.dto.UserLoginRequestDTO;
import com.zqw.agent_app.model.dto.UserLoginResponseDTO;
import com.zqw.agent_app.model.dto.UserRegisterRequestDTO;

public interface UserService {

    /**
     * 用户注册,表中新增用户
     * @param userRegisterRequestDTO 用户注册实体
     * @return 注册结果
     */
     Result<Integer> addUser(UserRegisterRequestDTO userRegisterRequestDTO);

    /**
     * 用户登录
     * @param userLoginRequestDTO 用户登录实体
     * @return 登录结果
     */
    Result<UserLoginResponseDTO> login(UserLoginRequestDTO userLoginRequestDTO);
}
