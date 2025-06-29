package com.example.login.entity.dto;

import lombok.Data;

@Data
public class CommentPublishDTO {
    private Integer textId; // 文档ID
    private Integer uid; // 用户ID
    private Integer parentId; // 父评论ID
    private String content; // 评论内容
}
