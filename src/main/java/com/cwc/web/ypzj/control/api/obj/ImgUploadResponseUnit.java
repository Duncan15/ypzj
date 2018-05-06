package com.cwc.web.ypzj.control.api.obj;

public class ImgUploadResponseUnit {
	public int opCode;
	public String opStatus;
	public String imageAddr;
	public ImgUploadResponseUnit(int opCode,String opStatus,String imageAddr) {
		// TODO Auto-generated constructor stub
		this.opCode=opCode;
		this.opStatus=opStatus;
		this.imageAddr=imageAddr;
	}
}
