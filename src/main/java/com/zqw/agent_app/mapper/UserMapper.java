package com.zqw.agent_app.mapper;

import com.zqw.agent_app.model.dto.UserLoginResponseDTO;
import com.zqw.agent_app.model.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /**
     * 新增用户数据
     * @param userPO 用户实体类
     * @return 是否新增成功
     */
    int addUser(UserPO userPO);

    /**
     * 根据用户名查询用户信息
     * @param userName 用户名
     * @return 用户登录响应DTO
     */
    UserLoginResponseDTO getUserByUserName(String userName);

    UserLoginResponseDTO getUserByEmail(String userEmail);
}
