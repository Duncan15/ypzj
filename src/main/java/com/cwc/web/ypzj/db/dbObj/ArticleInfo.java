package com.cwc.web.ypzj.db.dbObj;

import java.util.Date;

public class ArticleInfo {
	private long id;
	private String articleName;
	private Date createdTime;
	private long supportedTime;
	private long topLabelId;
	private Long authorId;
	private String avatarId;
	public ArticleInfo(long id,String articleName,Date createdTime,long supportedTime,long topLabelId,Long authorId,String avatarId)
	{
		this.id=id;
		this.articleName=articleName;
		this.createdTime=createdTime;
		this.supportedTime=supportedTime;
		this.topLabelId=topLabelId;
		this.authorId=authorId;
		this.avatarId=avatarId;
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
	public Long getSupportedTime()
	{
		return this.supportedTime;
	}
	public Long getTopLabelId()
	{
		return this.topLabelId;
	}
	public Long getAuthorId()
	{
		return this.authorId;
	}

	public String getAvatarId() {
		return avatarId;
	}
}
