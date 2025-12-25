package com.example.login.service.impl;

import com.example.login.entity.Comment;
import com.example.login.entity.dto.CommentPublishDTO;
import com.example.login.entity.vo.CommentVO;
import com.example.login.entity.vo.PageBean;
import com.example.login.mapper.CommentRepository;
import com.example.login.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentServiceimpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment publishTextComment(CommentPublishDTO commentPublishDto) {
        System.out.println("成功传入Service");
        Comment comment = new Comment();
        comment.setUid(commentPublishDto.getUid());
        comment.setParentId(commentPublishDto.getParentId());
        comment.setTextId(commentPublishDto.getTextId());
        comment.setContent(commentPublishDto.getContent());
        comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        commentRepository.publishComment(comment);
        System.out.println("数据库写入成功");
        return comment;
    }

    @Override
    public PageBean<CommentVO> getCommentsByTextId(Integer textId, Integer page, Integer pageSize){
        System.out.println("成功进入!");
        // 获取父评论
        List<CommentVO> parentComments = commentRepository.getParentComments(textId, (page - 1) * pageSize, pageSize);
        System.out.println("成功获取父评论列表");

        //获取评论总数
        Integer CommentTotalNum = parentComments.size();

        // 获取每条父评论的子评论
        for (CommentVO parent : parentComments) {
            Integer count = commentRepository.getReplyCountByParentId(parent.getId());
            List<CommentVO> replylist = commentRepository.getRepliesByParentId(parent.getId());
            parent.setReply(new PageBean<CommentVO>(count,replylist)); // 设置子评论
        }
        System.out.println("成功获取每条父评论的子评论");

        return new PageBean<CommentVO>(CommentTotalNum,parentComments);
    }

}
