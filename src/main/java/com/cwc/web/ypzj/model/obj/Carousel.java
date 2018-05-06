package com.cwc.web.ypzj.model.obj;

public class Carousel extends Image{
    private Long id;
    public Carousel(){}
    public Carousel(Long id){
        super();
        this.id=id;
    }
    public void setId(long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
}
