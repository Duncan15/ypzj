package com.cwc.web.ypzj.model.obj;

import java.util.Date;

public class Recommendation {
    private long id;
    private long authorId;
    private long articleId;
    private long time;

    public long getAuthorId() {
        return authorId;
    }

    public long getId() {
        return id;
    }

    public long getArticleId() {
        return articleId;
    }

    public long getTime() {
        return time;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTime(long time) {
        this.time = time;
    }
    public void setTime(Date date){
        this.time=date.getTime()/1000;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}
