package com.example.login.service.impl;

import com.example.login.pojo.vo.CommentVo;
import com.example.login.pojo.vo.PageBean;
import com.example.login.pojo.dto.CommentPublishDto;
import com.example.login.pojo.entity.Comment;
import com.example.login.mapper.CommentMapper;
import com.example.login.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentServiceimpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Comment publishTextComment(CommentPublishDto commentPublishDto) {
        System.out.println("成功传入Service");
        Comment comment = new Comment();
        comment.setUid(commentPublishDto.getUid());
        comment.setParentId(commentPublishDto.getParentId());
        comment.setTextId(commentPublishDto.getTextId());
        comment.setContent(commentPublishDto.getContent());
        comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        commentMapper.publishComment(comment);
        System.out.println("数据库写入成功");
        return comment;
    }

    @Override
    public PageBean<CommentVo> getCommentsByTextId(Integer textId, Integer page, Integer pageSize){
        //获取评论总数
        Integer CommentTotalNum = commentMapper.getCommentCountByTextId(textId);
        System.out.println("成功获取评论总数："+CommentTotalNum);

        // 获取父评论
        List<CommentVo> parentComments = commentMapper.getParentComments(textId, (page - 1) * pageSize, pageSize);
        System.out.println("成功获取父评论列表");

        // 获取每条父评论的子评论
        for (CommentVo parent : parentComments) {
            Integer count = commentMapper.getReplyCountByParentId(parent.getId());
            List<CommentVo> replylist = commentMapper.getRepliesByParentId(parent.getId());
            parent.setReply(new PageBean<CommentVo>(count,replylist)); // 设置子评论
        }
        System.out.println("成功获取每条父评论的子评论");

        return new PageBean<CommentVo>(CommentTotalNum,parentComments);
    }

}
