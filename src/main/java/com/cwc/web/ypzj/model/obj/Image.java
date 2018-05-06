package com.cwc.web.ypzj.model.obj;

public class Image {
	private String imageName;
	private byte[] MD5;
	public Image(){ }
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

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setMD5(byte[] MD5) {
		this.MD5 = MD5;
	}
}
