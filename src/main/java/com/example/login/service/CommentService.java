package com.example.login.service;

import com.example.login.pojo.vo.CommentVo;
import com.example.login.pojo.vo.PageBean;
import com.example.login.pojo.dto.CommentPublishDto;
import com.example.login.pojo.entity.Comment;

public interface CommentService {

    // 发布评论
    Comment publishTextComment(CommentPublishDto commentPublishDto);

    // 获取评论
    PageBean<CommentVo> getCommentsByTextId(Integer id, Integer page, Integer pageSize);

}
