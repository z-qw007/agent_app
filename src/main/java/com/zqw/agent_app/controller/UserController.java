package com.zqw.agent_app.controller;

import com.zqw.agent_app.common.Result;
import com.zqw.agent_app.model.dto.UserLoginRequestDTO;
import com.zqw.agent_app.model.dto.UserLoginResponseDTO;
import com.zqw.agent_app.model.dto.UserRegisterRequestDTO;
import com.zqw.agent_app.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}