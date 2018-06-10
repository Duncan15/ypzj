package com.cwc.web.ypzj.model.obj;

import com.cwc.web.ypzj.common.constant.Type;

import java.util.Date;

public class Comment {
    private Long id;
    private Long hostId;
    private Long senderId;
    private Long receiverId;
    private Long topCommentId;
    private String comment;
    private Long createdTime;
    private Type.MessageType messageType;

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }
    public void setCreatedTime(Date date){
        this.createdTime=date.getTime()/1000;
    }

    public Type.MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(Type.MessageType messageType) {
        this.messageType = messageType;
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getTopCommentId() {
        return topCommentId;
    }

    public void setTopCommentId(Long topCommentId) {
        this.topCommentId = topCommentId;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
