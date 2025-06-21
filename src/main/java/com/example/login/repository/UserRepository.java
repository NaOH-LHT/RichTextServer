package com.example.login.repository;

import com.example.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名查询用户
    User findByUsername(String username);
    
    // 检查用户名是否存在
    boolean existsByUsername(String username);
} 