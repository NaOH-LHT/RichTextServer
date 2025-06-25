package com.example.login.repository;

import com.example.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin(origins = "*")
public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名查询用户
    User findByUsername(String username);
    
    // 检查用户名是否存在
    boolean existsByUsername(String username);

    //根据用户名查询用户id
    User findIdByUsername(String username);

    // 根据昵称查询用户
    User findByNickname(String nickname);
} 