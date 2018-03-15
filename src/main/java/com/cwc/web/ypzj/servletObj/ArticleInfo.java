package com.cwc.web.ypzj.servletObj;

import java.util.Date;

public class ArticleInfo {
	private long id;
	private String articleName;
	private Date createdTime;
	private long supportedTime;
	private long topLabelId;
	private String authorId;
	public ArticleInfo(long id,String articleName,Date createdTime,long supportedTime,long topLabelId,String authorId)
	{
		this.id=id;
		this.articleName=articleName;
		this.createdTime=createdTime;
		this.supportedTime=supportedTime;
		this.topLabelId=topLabelId;
		this.authorId=authorId;
	}
	public long getId()
	{
		return this.id;
	}
	public String getArticleName()
	{
		return this.articleName;
	}
	public Date getCreatedTime()
	{
		return this.createdTime;
	}
	public long getSupportedTime()
	{
		return this.supportedTime;
	}
	public long getTopLabelId()
	{
		return this.topLabelId;
	}
	public String getAuthorId()
	{
		return this.authorId;
	}
	
}
