package com.cwc.web.ypzj.api.jsonObj;

import java.util.ArrayList;
import java.util.List;

public class ImgUploadResponser {
	private List<ImgUploadResponseUnit> responseList=new ArrayList<>();
	public void setResponseList(List<ImgUploadResponseUnit> list) {
		this.responseList=list;
	}
	public List<ImgUploadResponseUnit> getResponseList(){
		return this.responseList;
	}
	public void addUnit(ImgUploadResponseUnit unit) {
		responseList.add(unit);
	}
//	@Override
//	public String toString(){
//		String ans="";
//		for(ImgUploadResponseUnit each:responseList) {
//			ans+="{ opCode:'"+each.opCode+"',opStatus:'"+each.opStatus+"',imageAddr:'"+each.opStatus+"'}";
//		}
//		return ans;
//		
//	}
}

