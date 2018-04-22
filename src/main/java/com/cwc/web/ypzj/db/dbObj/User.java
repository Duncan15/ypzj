 package com.cwc.web.ypzj.db.dbObj;

public class User 
{
	private Long id;
	private String userName;
	private String account;
	private String passwordMD5;
	public User(Long id,String userName,String account,String passwordMD5)
	{
		this.id=id;
		this.userName=userName;
		this.account=account;
		this.passwordMD5=passwordMD5;
	}
	public Long getId(){return this.id;}
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
