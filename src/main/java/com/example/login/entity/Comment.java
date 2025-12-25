package com.example.login.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="comments")
public class Comment {
    @Id
    private Integer id; // 评论的ID
    private Integer textId; // 文档ID
    private Integer parentId; // 父评论ID
    private Integer uid; // 当前用户ID
    @Column(length = 500)
    private String content; // 评论内容
    private Timestamp createTime; // 评论时间
}
