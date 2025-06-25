package com.example.login.mapper;

import com.example.login.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface CommentMapper {

    //发布评论
    @Insert("INSERT INTO comments (article_id, parent_id, content, uid, create_time) " +
            "VALUES (#{articleId}, #{parentId}, #{content}, #{uid}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id" ,keyColumn = "id")
    void publishComment(Comment comment);

}
