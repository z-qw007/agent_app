package com.zqw.agent_app.service.impl;

import cn.hutool.core.date.DateTime;
import com.zqw.agent_app.common.Result;
import com.zqw.agent_app.common.ResultCode;
import com.zqw.agent_app.exception.BusinessException;
import com.zqw.agent_app.mapper.UserMapper;
import com.zqw.agent_app.model.dto.UserLoginRequestDTO;
import com.zqw.agent_app.model.dto.UserLoginResponseDTO;
import com.zqw.agent_app.model.dto.UserRegisterRequestDTO;
import com.zqw.agent_app.model.po.UserPO;
import com.zqw.agent_app.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public Result<Integer> addUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        // 校验参数
        if (userRegisterRequestDTO == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户信息不能为空");
        }

        String userName = userRegisterRequestDTO.getUserName();
        // 用户名不可重复
        UserLoginResponseDTO userLoginResponseDTO = userMapper.getUserByUserName(userName);
        if (userLoginResponseDTO != null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户名已存在");
        }

        String userEmail = userRegisterRequestDTO.getUserEmail();
        // 校验邮箱格式问题
        if (!userEmail.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "邮箱格式错误");
        }

        // 校验邮箱是否重复注册
        userLoginResponseDTO = userMapper.getUserByEmail(userEmail);
        if (userLoginResponseDTO != null) {
            throw new BusinessException(ResultCode.EMAIL_ALREADY_EXISTS, "邮箱已存在");
        }

        // 密码加密
        String encodePassword = passwordEncoder.encode(userRegisterRequestDTO.getUserPassword());

        // 构建PO
        UserPO userPO = UserPO.builder()
                .userName(userName)
                .email(userEmail)
                .password(encodePassword)
                .createdTime(new DateTime())
                .updateTime(new DateTime())
                .build();

        // DAO层操作
        int result = userMapper.addUser(userPO);

        return Result.success(result);
    }

    @Override
    public Result<Boolean> login(UserLoginRequestDTO userLoginRequestDTO) {

        UserLoginResponseDTO userLoginResponseDTO = userMapper.getUserByUserName(userLoginRequestDTO.getUserName());
        if (userLoginResponseDTO == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户名不存在");
        }

        String password = userLoginRequestDTO.getPassword();
        // 校验密码
        if (!passwordEncoder.matches(password, userLoginResponseDTO.getPassword())) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "密码错误");
        }

        // 校验通过,登录成功
        return Result.success(true);
    }
}
