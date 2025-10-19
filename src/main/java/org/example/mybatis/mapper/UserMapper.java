package org.example.mybatis.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.example.mybatis.entity.User;

import java.util.List;

/**
 * 用户表 Mapper 接口
 */
@Mapper
public interface UserMapper {
    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> findAll();

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户
     */
    User findById(Long id);

    /**
     * 插入用户
     *
     * @param user 用户实体
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 更新用户
     *
     * @param user 用户实体
     * @return 影响行数
     */
    int update(User user);

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(Long id);
}
