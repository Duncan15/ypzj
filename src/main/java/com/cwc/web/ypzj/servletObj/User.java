 package com.cwc.web.ypzj.servletObj;

public class User 
{
	private String userName;
	private String account;
	private String passwordMD5;
	public User(String userName,String account,String passwordMD5)
	{
		this.userName=userName;
		this.account=account;
		this.passwordMD5=passwordMD5;
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
}
