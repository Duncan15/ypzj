package com.cwc.web.ypzj.model.mapper;

import com.cwc.web.ypzj.model.obj.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper extends RowMapper {
    @Override
    public Comment map(ResultSet resultSet) throws SQLException {
        Comment comment =new Comment();
        if(resultSet==null)return comment;
        comment.setComment(resultSet.getString("comment"));
        comment.setCreatedTime(resultSet.getLong("created_time"));
        comment.setHostId(resultSet.getLong("host_id"));
        comment.setId(resultSet.getLong("id"));
        comment.setSenderId(resultSet.getLong("sender_id"));
        comment.setReceiverId(resultSet.getLong("receiver_id"));
        comment.setTopCommentId(resultSet.getLong("top_comment_id"));
        comment.setMessageType(resultSet.getByte("message_type"));
        return  comment;
    }
}
