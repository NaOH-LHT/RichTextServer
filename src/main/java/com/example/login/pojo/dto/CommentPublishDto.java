package com.example.login.pojo.dto;

import lombok.Data;

@Data
public class CommentPublishDto {
    private Integer textId; // 文档ID
    private Integer uid; // 用户ID
    private Integer parentId; // 父评论ID
    private String content; // 评论内容
}
