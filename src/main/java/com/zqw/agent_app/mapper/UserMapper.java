package com.zqw.agent_app.mapper;

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
    UserPO getUserByUserName(String userName);

    /**
     * 根据邮箱查询用户信息
     * @param userEmail 用户邮箱
     * @return 用户登录响应DTO
     */
    UserPO getUserByEmail(String userEmail);

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户ID
     * @return 用户登录响应DTO
     */
    UserPO getUserById(int userId);

    /**
     * 更新用户信息
     * @param newUserPO 用户实体
     * @return 更新结果
     */
    int updateUser(UserPO newUserPO);
}
