package com.cwc.web.ypzj.model.obj;

import java.util.Date;

public class Brocast {
    private long id;
    private String content;
    private byte deleted;
    /*
    GMT时间
     */
    private Date time;
    public Brocast(){}
    public Brocast(long id, String content, byte deleted, Date time){
        this.id=id;
        this.content=content;
        this.deleted=deleted;
        this.time=time;
    }
    public byte getDeleted() {
        return deleted;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getTime() {
        return time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDeleted(byte deleted) {
        this.deleted = deleted;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
