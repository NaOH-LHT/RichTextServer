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
        System.out.println("登录用户手机号为：" + loginUser.getPhone());
        User user = userRepository.findByPhone(loginUser.getPhone());
        
        if (user != null && user.getPassword().equals(loginUser.getPassword())) {
            response.put("success", true);
            response.put("message", "登录成功");
        } else {
            response.put("success", false);
            response.put("message", "手机号或密码错误");
        }
        
        return response;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User newUser) {
        System.out.println("=== 收到注册请求 ===");
        System.out.println("手机号：" + newUser.getPhone());
        System.out.println("密码：" + newUser.getPassword());
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 测试数据库连接
            System.out.println("测试数据库连接...");
            User testUser = userRepository.findByPhone(newUser.getPhone());
            System.out.println("数据库连接测试结果: " + (testUser != null ? "已存在用户" : "未找到用户"));
            
            if (userRepository.existsByPhone(newUser.getPhone())) {
                System.out.println("手机号已存在");
                response.put("success", false);
                response.put("message", "该手机号已被注册");
                return response;
            }
            
            User savedUser = userRepository.save(newUser);
            System.out.println("保存成功，用户ID: " + savedUser.getId());
            
            // 验证数据是否真的保存成功
            User verifyUser = userRepository.findByPhone(newUser.getPhone());
            System.out.println("验证查询结果: " + (verifyUser != null ? "找到用户" : "未找到用户"));
            
            response.put("success", true);
            response.put("message", "注册成功");
        } catch (Exception e) {
            System.out.println("保存失败: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "注册失败: " + e.getMessage());
        }
        
        return response;
    }
} 