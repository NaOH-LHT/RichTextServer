package com.example.login.controller;


import com.example.login.pojo.vo.CommentVo;
import com.example.login.pojo.vo.PageBean;
import com.example.login.pojo.dto.CommentPublishDto;
import com.example.login.pojo.entity.Comment;
import com.example.login.result.Result;
import com.example.login.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = "*") // 允许所有来源的请求
public class CommentController {

    @Autowired
    private CommentService commentService;

    //发表评论
    @PostMapping("/publish")
    public Result<Comment> publishComment(@RequestBody CommentPublishDto commentPublishDto)
    {
        // 输出前端传来的数据
        System.out.println(commentPublishDto.toString());
        Comment comment = commentService.publishTextComment(commentPublishDto);
        // 输出后端返回的数据
        System.out.println(comment.toString());
        return Result.success(comment);
    }

    // 获取评论
    @GetMapping("/commentLists")
    public Result<PageBean<CommentVo>> getComment(@RequestParam Integer textId,
                                                  @RequestParam(defaultValue = "1")Integer page,
                                                  @RequestParam(defaultValue = "5")Integer pageSize)
    {
        System.out.println("发送评论请求成功，请求文档"+textId);
        PageBean<CommentVo> comments = commentService.getCommentsByTextId(textId, page, pageSize);
        System.out.println("获取评论成功"+comments.toString());
        return Result.success(comments);
    }

}
