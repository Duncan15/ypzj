package com.cwc.web.ypzj.api.jsonObj;

public class LinkResponser {
	private Integer opCode;
	private String opStatus;
	private Long  rowId;
	public LinkResponser(Integer opCode,String opStatus,Long rowId) {
		// TODO Auto-generated constructor stub
		this.opCode=opCode;
		this.opStatus=opStatus;
		this.rowId=rowId;
	}
	public Integer getOpCode() {
		return this.opCode;
	}
	public String getOpStatus() {
		return this.opStatus;
	}
	public Long getRowId() {
		return this.rowId;
	}
}
