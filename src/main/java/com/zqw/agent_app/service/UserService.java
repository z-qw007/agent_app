package com.zqw.agent_app.service;

import com.zqw.agent_app.common.Result;
import com.zqw.agent_app.model.dto.*;

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

    /**
     * 获取用户详情
     * @param userId 用户id
     * @return 用户详情
     */
    Result<UserInfoDTO> getUserDetail(int userId);

    /**
     * 修改用户信息
     * @param userEditRequestDTO 用户更新实体
     * @return 更新结果
     */
    Result<Integer> updateUser(UserEditRequestDTO userEditRequestDTO);
}
