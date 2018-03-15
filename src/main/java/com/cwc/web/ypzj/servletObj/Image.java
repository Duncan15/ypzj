package com.cwc.web.ypzj.servletObj;

public class Image {
	private String imageName;
	private Long articleId;
	private byte[] MD5;
	public Image(String imageName,Long articleId,byte[] MD5) {
		// TODO Auto-generated constructor stub
		this.imageName=imageName;
		this.articleId=articleId;
		this.MD5=MD5;
	}
	public String getImageName() {
		return this.imageName;
	}
	public Long getArticleId() {
		return this.articleId;
	}
	public byte[] getMD5() {
		return this.MD5;
	}
}
