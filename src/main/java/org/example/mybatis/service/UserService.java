package org.example.mybatis.service;

import org.example.mybatis.entity.User;
import org.example.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务类
 * 负责处理用户相关的业务逻辑
 */
// @Service: Spring 的服务层注解
// 作用：1. 标识这是一个服务层组件
//      2. Spring 会自动扫描并将其注册为 Bean，可以被其他组件注入使用
@Service
public class UserService {

    // 声明 UserMapper 依赖，使用 final 确保依赖不可变
    private final UserMapper mapper;

    /**
     * 构造器注入依赖
     * 推荐使用构造器注入而不是字段注入，因为：
     * 1. 可以使用 final 关键字，保证依赖不可变
     * 2. 更容易进行单元测试
     * 3. Spring 4.3+ 版本，单个构造器可以省略 @Autowired
     *
     * @param mapper UserMapper 实例
     */
    @Autowired
    public UserService(UserMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    public List<User> findAll() {
        return mapper.findAll();
    }

    /**
     * 根据ID查询指定用户
     *
     * @param id 用户ID
     * @return 用户对象，如果不存在则返回 null
     */
    public User findById(Long id) {
        return mapper.findById(id);
    }

    /**
     * 插入新用户
     * MyBatis 会自动将生成的主键回填到 user.id 中
     *
     * @param user 用户对象
     * @return 受影响的行数（通常为 1 表示成功，0 表示失败）
     */
    public int insert(User user) {
        return mapper.insert(user);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户对象（必须包含 id）
     * @return 受影响的行数（1 表示成功，0 表示用户不存在）
     */
    public int update(User user) {
        return mapper.update(user);
    }

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 受影响的行数（1 表示成功，0 表示用户不存在）
     */
    public int deleteById(Long id) {
        return mapper.deleteById(id);
    }
}
