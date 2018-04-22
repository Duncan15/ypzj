package com.cwc.web.ypzj.db.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cwc.web.common.db.DBManager;
import com.cwc.web.ypzj.db.mapper.LabelMapper;
import com.cwc.web.ypzj.db.dbObj.Label;

public class LabelRepository {
	public static List<Label> getAllLabels()
	{
		ArrayList<Label> ans=new ArrayList<>();
		String sql="select * from top_label_table";
		DBManager dbManager=null;
		List<Object> t=null;
		try{
			dbManager=new DBManager();
			t=dbManager.findAll(new LabelMapper(),sql);
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			if(dbManager!=null)dbManager.close();
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

}
