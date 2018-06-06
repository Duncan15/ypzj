package com.cwc.web.ypzj.model.obj;

public class FriendLink {
    private Long id;
    private Link link;
    public FriendLink(){
    }
    public FriendLink(Long id,Link link){
        this.id=id;
        this.link=link;
    }

    public Long getId() {
        return id;
    }

    public Link getLink() {
        return link;
    }

}
