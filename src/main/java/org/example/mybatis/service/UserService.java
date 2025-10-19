package org.example.mybatis.service;

import org.example.mybatis.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    int insert(User user);
    int update(User user);
    int deleteById(Long id);
}
