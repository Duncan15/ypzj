package com.cwc.web.ypzj.model.obj;

import java.util.Date;

public class AttentionRelation {
    private Long id;
    private Long startPoint;
    private Long endPoint;
    private Long time;
    public AttentionRelation(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTime(Long time) {
        this.time = time;
    }
    public void setTime(Date time){
        this.time=time.getTime()/1000;
    }

    public Long getTime() {
        return time;
    }

    public Long getEndPoint() {
        return endPoint;
    }

    public Long getStartPoint() {
        return startPoint;
    }

    public void setEndPoint(Long endPoint) {
        this.endPoint = endPoint;
    }

    public void setStartPoint(Long startPoint) {
        this.startPoint = startPoint;
    }
}
