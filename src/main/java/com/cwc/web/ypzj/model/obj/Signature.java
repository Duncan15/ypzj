package com.cwc.web.ypzj.model.obj;

import java.util.Date;

public class Signature {
    private long id;
    private String content;
    private long createdTime;
    private long authorId;

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
    public void setCreatedTime(Date date){
        this.createdTime=date.getTime()/1000;
    }
    public String getContent() {
        return content;
    }

    public long getId() {
        return id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public long getCreatedTime() {
        return createdTime;
    }
}
