package com.cwc.web.ypzj.model.obj;


import java.util.Date;

public class ArticleInfo {
	private long id;
	private String articleName;
	private long createdTime;
	private long supportedTime;
	private long topLabelId;
	private Long authorId;
	private String avatarId;
	private Byte status;
	public ArticleInfo(){}
	public ArticleInfo(long id,String articleName,long createdTime,long supportedTime,long topLabelId,Long authorId,String avatarId,Byte status)
	{
		this.id=id;
		this.articleName=articleName;
		this.createdTime=createdTime;
		this.supportedTime=supportedTime;
		this.topLabelId=topLabelId;
		this.authorId=authorId;
		this.avatarId=avatarId;
		this.status=status;
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
		return new Date(this.createdTime*1000);
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

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getStatus() {
		return status;
	}
}
