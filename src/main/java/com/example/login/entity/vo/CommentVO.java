package com.example.login.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
class User{
    private String username; // 用户名
    private String avatar; // 用户头像
}

@Data
public class CommentVO {
    private Integer id; // 评论ID
    private Integer parentId; // 父评论ID
    private Integer uid; // 用户ID
    private User user; // 当前用户
    private String content; // 评论内容
    private LocalDateTime createTime; //  评论时间
    private PageBean<CommentVO> reply; // 子评论列表
}
