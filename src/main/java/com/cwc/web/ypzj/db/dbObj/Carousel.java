package com.cwc.web.ypzj.db.dbObj;

public class Carousel {
    private Long id;
    private Image img;
    public Carousel(Long id,Image img){
        this.id=id;
        this.img=img;
    }
    public void setId(long id){
        this.id=id;
    }
    public void setImage(Image img){
        this.img=img;
    }
    public Long getId(){
        return this.id;
    }

    public Image getImg() {
        return img;
    }
}
