package com.zqw.agent_app.service.impl;

import cn.hutool.core.date.DateTime;
import com.zqw.agent_app.common.Result;
import com.zqw.agent_app.common.ResultCode;
import com.zqw.agent_app.exception.BusinessException;
import com.zqw.agent_app.mapper.UserMapper;
import com.zqw.agent_app.model.dto.*;
import com.zqw.agent_app.model.po.UserPO;
import com.zqw.agent_app.service.UserService;
import com.zqw.agent_app.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        UserPO userPO = userMapper.getUserByUserName(userName);
        if (userPO != null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户名已存在");
        }

        String userEmail = userRegisterRequestDTO.getUserEmail();
        // 校验邮箱格式问题
        if (!userEmail.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "邮箱格式错误");
        }

        // 校验邮箱是否重复注册
        userPO = userMapper.getUserByEmail(userEmail);
        if (userPO != null) {
            throw new BusinessException(ResultCode.EMAIL_ALREADY_EXISTS, "邮箱已存在");
        }

        // 密码加密
        String encodePassword = passwordEncoder.encode(userRegisterRequestDTO.getUserPassword());

        UserPO newUserPO = new UserPO();
        newUserPO = UserPO.builder()
                .userName(userName)
                .email(userEmail)
                .userPassword(encodePassword)
                .createdTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        // DAO层操作
        int result = userMapper.addUser(newUserPO);

        return Result.success(result);
    }

    @Override
    public Result<UserLoginResponseDTO> login(UserLoginRequestDTO userLoginRequestDTO) {

        UserPO userPO = userMapper.getUserByUserName(userLoginRequestDTO.getUserName());
        if (userPO == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户名不存在");
        }

        String password = userLoginRequestDTO.getUserPassword();
        if (password == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "密码不能为空");
        }
        // 校验密码
        if (!passwordEncoder.matches(password, userPO.getUserPassword())) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "密码错误");
        }

        // 生成token
        String token = JwtUtil.generateToken(
                userPO.getUserId(),
                userPO.getUserName()
        );

        // 构建返回DTO
        UserLoginResponseDTO userLoginResponseDTO = UserLoginResponseDTO.builder()
                .token(token)
                .userId(userPO.getUserId())
                .userName(userPO.getUserName())
                .build();

        // 校验通过,登录成功,返回包含token的Result
        return Result.success(userLoginResponseDTO);
    }

    @Override
    public Result<UserInfoDTO> getUserDetail(int userId) {
        // 校验参数
        if (userId <= 0) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户ID不能为空");
        }

        // DAO层操作
        UserPO userPO = userMapper.getUserById(userId);
        if (userPO == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户不存在");
        }
        // 构建返回DTO
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .userId(userPO.getUserId())
                .userName(userPO.getUserName())
                .email(userPO.getEmail())
                .modelCount(userPO.getModelCount())
                .createTime(userPO.getCreatedTime())
                .updateTime(userPO.getUpdateTime())
                .build();

        return Result.success(userInfoDTO);
    }

    @Override
    public Result<Integer> updateUser(UserEditRequestDTO userEditRequestDTO) {
        // 校验参数
        if (userEditRequestDTO == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户信息不能为空");
        }

        int userId = userEditRequestDTO.getUserId();
        // 校验用户是否存在
        UserPO userPO = userMapper.getUserById(userId);
        if (userPO == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户不存在");
        }

        // 构造入库类
        UserPO newUserPO = UserPO.builder()
                .userId(userId)
                .userName(userEditRequestDTO.getUserName())
                .email(userEditRequestDTO.getEmail())
                .build();

        // DAO层操作
        int result = userMapper.updateUser(newUserPO);

        return Result.success(result);
    }
}
