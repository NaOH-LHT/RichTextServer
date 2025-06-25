package com.example.login.dto;

import lombok.Data;

@Data
public class CommentPublishDto {
    private Integer resourceId; //
    private Integer parentId; // 父评论ID
    private String content; // 评论内容
}
