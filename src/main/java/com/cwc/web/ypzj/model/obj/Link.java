package com.cwc.web.ypzj.model.obj;

public class Link {
	private Long id;
	private String intro;
	private String link;
	private Long articleId;
	public Link(){}
	public Link(Long id,String intro,String link,Long articleId) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.intro=intro;
		this.link=link;
		this.articleId=articleId;
	}
	public Long getId() {
		return this.id;
	}
	public String getIntro() {
		return this.intro;
	}
	public String getLink() {
		return this.link;
	}
	public Long getArticleId() {
		return this.articleId;
	}
}
