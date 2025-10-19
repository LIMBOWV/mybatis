package org.example.mybatis.controller;

import org.example.mybatis.entity.User;
import org.example.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * 用户控制器类
 * 负责处理与用户相关的 HTTP 请求
 * RESTful API 风格
 */
// @RestController: 组合注解，等于 @Controller + @ResponseBody
// 作用：1. 标识这是一个 Spring MVC 的控制器类
//      2. 自动将方法返回值序列化为 JSON 格式并写入 HTTP 响应体
@RestController
// @RequestMapping: 请求映射注解，定义该控制器处理的请求路径前缀
// 所有方法的路径都会在 "/api/users" 之后
@RequestMapping("/api/users")
public class UserController {

    // 声明 UserService 依赖，使用 final 确保依赖不可变
    private final UserService userService;

    /**
     * 构造器注入 UserService 依赖
     *
     * @param userService 用户服务实例
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询所有用户
     * GET /api/users
     *
     * @return 用户列表
     */
    // @GetMapping: 处理 HTTP GET 请求，相当于 @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 根据ID查询用户
     * GET /api/users/{id}
     *
     * @param id 用户ID（从URL路径中获取）
     * @return ResponseEntity，包含用户信息（200 OK）或 404 Not Found
     */
    // @PathVariable: 从URL路径中提取变量值，{id} 会被绑定到参数 id
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            // 找到用户，返回 200 OK 和用户数据
            return ResponseEntity.ok(user);
        } else {
            // 用户不存在，返回 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 插入新用户
     * POST /api/users
     *
     * @param user 用户对象（从请求体中获取 JSON 数据）
     * @return ResponseEntity，包含创建的用户信息和 Location 头（201 Created）或 500 错误
     */
    // @PostMapping: 处理 HTTP POST 请求，通常用于创建资源
    // @RequestBody: 将请求体中的 JSON 数据自动转换为 User 对象
    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User user) {
        // 调用服务层插入用户，返回受影响的行数
        int rows = userService.insert(user);
        if (rows > 0) {
            // 插入成功（MyBatis 已将生成的主键回填到 user.id）
            // 构建 Location URI: /api/users/{新用户的id}
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(user.getId())
                    .toUri();
            // 返回 201 Created，包含 Location 头和用户数据
            return ResponseEntity.created(location).body(user);
        } else {
            // 插入失败，返回 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 更新用户信息
     * PUT /api/users/{id}
     *
     * @param id 用户ID（从URL路径中获取）
     * @param user 用户对象（从请求体中获取 JSON 数据）
     * @return ResponseEntity，包含更新后的用户信息（200 OK）或 404 Not Found
     */
    // @PutMapping: 处理 HTTP PUT 请求，通常用于更新整个资源
    // 注意：PUT 请求通常不需要在路径中指定 /{id}，但为了 RESTful 规范，建议加上
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        // 确保更新的是路径中指定的用户
        user.setId(id);
        // 调用服务层更新用户，返回受影响的行数
        int rows = userService.update(user);
        if (rows > 0) {
            // 更新成功，返回 200 OK 和用户数据
            return ResponseEntity.ok(user);
        } else {
            // 用户不存在，返回 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据ID删除用户
     * DELETE /api/users/{id}
     *
     * @param id 用户ID（从URL路径中获取）
     * @return ResponseEntity，204 No Content（删除成功）或 404 Not Found
     */
    // @DeleteMapping: 处理 HTTP DELETE 请求，用于删除资源
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        // 调用服务层删除用户，返回受影响的行数
        int rows = userService.deleteById(id);
        if (rows > 0) {
            // 删除成功，返回 204 No Content（表示成功但无返回内容）
            return ResponseEntity.noContent().build();
        } else {
            // 用户不存在，返回 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
}
