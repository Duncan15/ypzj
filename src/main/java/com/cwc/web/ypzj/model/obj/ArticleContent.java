package com.cwc.web.ypzj.model.obj;

public class ArticleContent {
	private long id;
	private String content;
	public ArticleContent(){}
	public ArticleContent(long id,String content) 
	{
		// TODO Auto-generated constructor stub
		this.id=id;
		this.content=content;
	}
	public long getId()
	{
		return this.id;
	}
	public String getContent()
	{
		return this.content;
	}
}
