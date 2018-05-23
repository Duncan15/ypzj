package com.cwc.web.ypzj.model.obj;

import java.io.Serializable;

public class Label implements Serializable{
	private long id;
	private String name;
	public Label(){}
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
