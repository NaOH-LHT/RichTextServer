package com.example.login.mapper;

import com.example.login.pojo.vo.CommentVo;
import com.example.login.pojo.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    //发布评论
    @Insert("INSERT INTO comments (text_id, parent_id, content, uid, create_time) " +
            "VALUES (#{textId}, #{parentId}, #{content}, #{uid}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id" ,keyColumn = "id")
    void publishComment(Comment comment);

    //获取评论总数
    @Select("select comment_count from texts where text_id = #{textId}")
    Integer getCommentCountByTextId(Integer textId);

    // 获取父评论
    List<CommentVo> getParentComments(@Param("textId")Integer textId, @Param("page")Integer page, @Param("pageSize")Integer pageSize);

    // 获取父评论的所有子评论的数量
    @Select("select count(*) from comments where parent_id = #{parentId}")
    Integer getReplyCountByParentId(Integer parentId);

    // 获取父评论的所有子评论，这里由于parentId的唯一性，所以不需要传递textId
    List<CommentVo> getRepliesByParentId(@Param("parentId")Integer parentId);


}
