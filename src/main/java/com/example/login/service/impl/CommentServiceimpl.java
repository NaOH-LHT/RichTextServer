package com.example.login.service.impl;

import com.example.login.dto.CommentPublishDto;
import com.example.login.entity.Comment;
import com.example.login.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

public class CommentServiceimpl {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Comment publishArticleComment(Integer loginId, CommentPublishDto commentPublishDto) {
        Comment comment = new Comment();
        comment.setParentId(commentPublishDto.getParentId());
        comment.setUid(loginId);
        comment.setContent(commentPublishDto.getContent());
        comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        commentMapper.publishComment(comment);
        return comment;
    }

}
