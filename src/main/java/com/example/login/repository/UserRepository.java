package com.example.login.repository;

import com.example.login.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 根据手机号查询用户
    User findByPhone(String phone);
    
    // 检查手机号是否存在
    boolean existsByPhone(String phone);
} 