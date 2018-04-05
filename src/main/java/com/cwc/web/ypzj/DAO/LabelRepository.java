package com.cwc.web.ypzj.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cwc.web.ypzj.db.DBManager;
import com.cwc.web.ypzj.db.mapper.LabelMapper;
import com.cwc.web.ypzj.servletObj.Label;

public class LabelRepository {
	public static List<Label> getAllLabels()
	{
		ArrayList<Label> ans=new ArrayList<>();
		String sql="select * from top_label_table";
		DBManager dbManager;
		List<Object> t=null;
		try{
			dbManager=new DBManager();
			t=dbManager.findAll(sql, null,new LabelMapper());
			System.err.println(t);
		}catch (SQLException e){
			e.printStackTrace();
		}
		if(t!=null)
		{
			for(Object each:t)
			{
				ans.add((Label) each);
			}
		}
		return ans;
	}
	public static void main(String[] args)
	{
		for(Label each:LabelRepository.getAllLabels())
		{
			System.out.println(each.getId()+"\t"+each.getName());
		}
	}
}
