package com.cwc.web.ypzj.model.obj;

public class Label {
	private long id;
	private String name;
	public Label(long id,String name)
	{
		this.id=id;
		this.name=name;
	}
	public long getId()
	{
		return this.id;
	}
	public String getName()
	{
		return this.name;
	}
}
