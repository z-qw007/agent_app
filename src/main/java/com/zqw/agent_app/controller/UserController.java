package com.zqw.agent_app.controller;

import com.zqw.agent_app.common.Result;
import com.zqw.agent_app.model.dto.*;
import com.zqw.agent_app.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册,表中新增用户
     *
     * @param userRegisterRequestDTO 用户注册信息
     * @return 注册结果
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<Integer> addUser(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
        return userService.addUser(userRegisterRequestDTO);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequestDTO 用户登录信息
     * @return 登录结果
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        return userService.login(userLoginRequestDTO);
    }

    /**
     * 获取用户详情
     * @param userId 用户ID
     * @return 用户详情
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Result<UserInfoDTO> getUserDetail(@RequestParam int userId) {
        return userService.getUserDetail(userId);
    }

    /**
     * 更新用户信息
     * @param userEditRequestDTO 用户更新信息
     * @return 更新结果
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> updateUser(@RequestBody UserEditRequestDTO userEditRequestDTO) {
        return userService.updateUser(userEditRequestDTO);
    }
}