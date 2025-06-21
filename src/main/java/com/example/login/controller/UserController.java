package com.example.login.controller;

import com.example.login.entity.User;
import com.example.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // 允许所有来源的请求
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginUser) {
        Map<String, Object> response = new HashMap<>();
        System.out.println("=== 收到登录请求 ===");
        System.out.println("登录用户名为：" + loginUser.getUsername());
        User user = userRepository.findByUsername(loginUser.getUsername());
        
        if (user != null && user.getPassword().equals(loginUser.getPassword())) {
            response.put("success", true);
            response.put("message", "登录成功");
            response.put("user", user);
        } else {
            response.put("success", false);
            response.put("message", "用户名或密码错误");
        }
        
        return response;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User newUser) {
        System.out.println("=== 收到注册请求 ===");
        System.out.println("用户名：" + newUser.getUsername());
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 测试数据库连接
            System.out.println("测试数据库连接...");
            User testUser = userRepository.findByUsername(newUser.getUsername());
            System.out.println("数据库连接测试结果: " + (testUser != null ? "已存在用户" : "未找到用户"));
            
            if (userRepository.existsByUsername(newUser.getUsername())) {
                System.out.println("用户名已存在");
                response.put("success", false);
                response.put("message", "该用户名已被注册");
                return response;
            }
            
            // 创建新用户，只设置username和password
            User userToSave = new User();
            userToSave.setUsername(newUser.getUsername());
            userToSave.setPassword(newUser.getPassword());
            
            User savedUser = userRepository.save(userToSave);
            System.out.println("保存成功，用户ID: " + savedUser.getUserId());
            
            // 验证数据是否真的保存成功
            User verifyUser = userRepository.findByUsername(newUser.getUsername());
            System.out.println("验证查询结果: " + (verifyUser != null ? "找到用户" : "未找到用户"));
            
            response.put("success", true);
            response.put("message", "注册成功");
            response.put("user", savedUser);
        } catch (Exception e) {
            System.out.println("保存失败: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "注册失败: " + e.getMessage());
        }
        
        return response;
    }
    
    @GetMapping("/user/{username}")
    public Map<String, Object> getUserInfo(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                response.put("success", true);
                response.put("user", user);
            } else {
                response.put("success", false);
                response.put("message", "用户不存在");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
} 