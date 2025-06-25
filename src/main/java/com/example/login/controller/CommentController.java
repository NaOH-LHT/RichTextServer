package com.example.login.controller;


import com.example.login.dto.CommentPublishDto;
import com.example.login.entity.Comment;
import com.example.login.result.Result;
import com.example.login.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = "*") // 允许所有来源的请求
public class CommentController {

    private CommentService commentService;

    //发表评论
    @PostMapping("/publish")
    public Result<Comment> publishComment(@RequestBody CommentPublishDto commentPublishDto)
    {
        String loginId = UserContext.getUserId();
        Comment Comment = commentService.publishArticleComment(Integer.valueOf(loginId),commentPublishDto);
        return Result.success(Comment);
    }



}
