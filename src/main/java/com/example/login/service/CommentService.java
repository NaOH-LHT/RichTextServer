package com.example.login.service;


import com.example.login.entity.Comment;
import com.example.login.entity.dto.CommentPublishDTO;
import com.example.login.entity.vo.CommentVO;
import com.example.login.entity.vo.PageBean;

public interface CommentService {

    // 发布评论
    Comment publishTextComment(CommentPublishDTO commentPublishDto);

    // 获取评论
    PageBean<CommentVO> getCommentsByTextId(Integer id, Integer page, Integer pageSize);

}
