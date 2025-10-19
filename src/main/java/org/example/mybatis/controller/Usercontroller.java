package org.example.mybatis.controller;

import org.example.mybatis.entity.User;
import org.example.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制器类
 * 负责处理与用户相关的 HTTP 请求
 */
// @RestController: 组合注解，等于 @Controller + @ResponseBody
// 作用：1. 标识这是一个 Spring MVC 的控制器类
//      2. 自动将方法返回值序列化为 JSON 格式并写入 HTTP 响应体
@RestController
// @RequestMapping: 请求映射注解，用于定义该控制器处理的请求路径前缀
// 当前未指定 value，表示映射到根路径 "/"
// 建议改为 @RequestMapping("/users") 或 @RequestMapping("/api/users")
@RequestMapping("/api/users")
public class Usercontroller {

    /**
     * userMapper 字段：用户数据访问层接口
     * 类型：UserMapper - MyBatis Mapper 接口，负责与数据库交互
     * 作用：通过该接口调用 SQL 操作（增删改查）
     */
    // @Autowired: Spring 依赖注入注解
    // 作用：自动从 Spring 容器中查找 UserMapper 类型的 Bean 并注入到该字段
    //      Spring 会自动创建 UserMapper 接口的代理实现类
    // 注意：从 Spring 4.3 开始，如果类只有一个构造函数，@Autowired 可以省略
    //      建议使用构造器注入代替字段注入（更易测试，避免空指针）
    @Autowired
    private UserMapper userMapper;

    /**
     * findAll 方法：查询所有用户
     *
     * @return List<User> - 用户列表，包含所有用户信息
     *         如果数据库中没有用户，返回空列表（不是 null）
     */
    // @GetMapping: GET 请求映射注解，是 @RequestMapping(method = RequestMethod.GET) 的简写
    // 当前未指定 value，表示映射到类级别的路径
    // 完整请求路径：GET http://localhost:8080/ (假设端口是 8080)
    // 建议改为 @GetMapping("/list") 或 @GetMapping("")，使路径更清晰
    // 作用：当收到 GET 请求时，Spring MVC 会调用此方法
    @GetMapping
    public List<User> findAll() {
        // 调用 UserMapper 的 findAll() 方法查询数据库
        // MyBatis 会执行 UserMapper.xml 中 id="findAll" 的 SQL 语句
        // 返回的 List<User> 会被 @RestController 自动转换为 JSON 格式
        return userMapper.findAll();
    }
    //根据id查询用户
    @GetMapping("/{id}")
    // 喵呜~ 主人，这里需要添加 @PathVariable 注解哦！
    // 为什么呢？因为在 @GetMapping("/{id}") 中，{id} 是一个路径变量。
    // Spring MVC 需要知道如何把 URL 路径中的这个 {id} 值，映射到我们方法参数的 `id` 上。
    // 如果不加 @PathVariable，Spring 就不知道这个 `id` 参数是从哪里来的，
    // 它会尝试把它当作一个请求参数（例如 ?id=1），而不是路径的一部分。
    // 这样就会导致找不到对应的参数，或者出现您提到的“应为标识符或类型”这样的错误提示，
    // 因为它无法正确解析这个参数的来源和类型。
    // 所以，加上 @PathVariable Long id，就明确告诉 Spring：“嘿，这个 `id` 参数就是从 URL 路径中的 `{id}` 占位符那里取值的！”
    // 这样，当您访问 /api/users/123 时，123 就会被正确地赋值给 `id` 参数啦！
    // 是不是很神奇呢，喵~
    public User findById(@PathVariable Long id) {
        return userMapper.findById(id);
    }


}
