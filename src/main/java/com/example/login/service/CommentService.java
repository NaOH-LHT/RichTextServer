package com.example.login.service;

import com.example.login.dto.CommentPublishDto;
import com.example.login.entity.Comment;

public interface CommentService {

    //发布评论
    Comment publishArticleComment(Integer loginId, CommentPublishDto commentPublishDto);

}
