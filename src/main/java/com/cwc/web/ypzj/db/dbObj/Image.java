package com.cwc.web.ypzj.db.dbObj;

public class Image {
	private String imageName;
	private byte[] MD5;
	public Image(String imageName,byte[] MD5) {
		// TODO Auto-generated constructor stub
		this.imageName=imageName;
		this.MD5=MD5;
	}
	public String getImageName() {
		return this.imageName;
	}
	public byte[] getMD5() {
		return this.MD5;
	}
}
