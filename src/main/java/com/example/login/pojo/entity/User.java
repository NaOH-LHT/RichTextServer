package com.example.login.pojo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 用户ID
    private String username; // 用户昵称
    private String avatar; // 用户头像
    private String phone; // 用户手机号
    private String password; // 用户密码
} 