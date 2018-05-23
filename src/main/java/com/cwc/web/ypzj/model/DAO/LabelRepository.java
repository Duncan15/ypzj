package com.cwc.web.ypzj.model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cwc.web.ypzj.model.pool.DBManager;
import com.cwc.web.ypzj.model.mapper.LabelMapper;
import com.cwc.web.ypzj.model.obj.Label;

public class LabelRepository {
	public static List<Label> getAllLabels()
	{
		String sql="select * from top_label_table";
		DBManager<Label> dbManager=null;
		List<Label> t=null;
		try{
			dbManager=new DBManager();
			t=dbManager.findAll(new LabelMapper(),sql);
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			if(dbManager!=null)dbManager.close();
		}

		return t;
	}

}
