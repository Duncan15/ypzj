 package com.cwc.web.ypzj.model.obj;

 import com.cwc.web.ypzj.common.constant.Type;

 public class User
{
	private Long id;
	private String userName;
	private String account;
	private String passwordMD5;
	private Type.UserStatus status;
	private Long concernedTime;
	public User(){}
	public User(Long id,String userName,String account,String passwordMD5,Long concernedTime)
	{
		this.id=id;
		this.userName=userName;
		this.account=account;
		this.passwordMD5=passwordMD5;
		this.status=Type.UserStatus.INVALID;
		this.concernedTime=concernedTime;
	}
	public Long getId(){return this.id;}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName()
	{
		return this.userName;
	}
	public String getAccount()
	{
		return this.account;
	}
	public String getPasswordMD5()
	{
		return this.passwordMD5;
	}
	public void setUserName(String name)
	{
		this.userName=name;
	}
	public void setPasswordMD5(String passwordMD5){
		this.passwordMD5=passwordMD5;
	}

	public byte getStatus() {
		return status.getValue();
	}

	public void setStatus(Type.UserStatus status) {
		this.status = status;
	}

	public Long getConcernedTime() {
		return concernedTime;
	}

	public void setConcernedTime(Long concernedTime) {
		this.concernedTime = concernedTime;
	}
}
