package com.cwc.web.ypzj.DAO;

import java.util.ArrayList;
import java.util.List;

import com.cwc.web.ypzj.db.DBUtil;
import com.cwc.web.ypzj.db.mapper.LabelMapper;
import com.cwc.web.ypzj.servletObj.Label;

public class LabelRepository {
	public static List<Label> getAllLabels()
	{
		ArrayList<Label> ans=new ArrayList<>();
		String sql="select * from top_label_table";
		List<Object> t=DBUtil.findAll(sql, new LabelMapper());
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
