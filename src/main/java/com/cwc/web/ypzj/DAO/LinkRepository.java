package com.cwc.web.ypzj.DAO;

import com.cwc.web.ypzj.db.DBManager;
import com.cwc.web.ypzj.db.mapper.LinkMapper;
import com.cwc.web.ypzj.servletObj.Link;

import java.sql.SQLException;

public class LinkRepository {
	private final static String TABLE_NAME="link_table";
	public enum Arg{
		id,
		intro,
		link,
		article_id
	}
	/**
	 * 
	 * @param intro
	 * @param link
	 * @return null if fail
	 */
	public static Link createLink(String intro,String link) {
		String sql="insert into "+TABLE_NAME+"("+Arg.intro.toString()+","+Arg.link.toString()+")"
				+"values(\""+intro+"\",\""+link+"\");";
		String searchSql="select * from "+TABLE_NAME+" where "+Arg.intro.toString()+"=\""+intro+"\" and "
				+Arg.link.toString()+"=\""+link+"\";";
		DBManager dbManager;
		int ans=0;
		try{
			dbManager=new DBManager();
			ans=dbManager.insert(sql,null);
			if(ans>0) {
				Link linkObj=(Link) dbManager.queryObject(searchSql, null, new LinkMapper());
				return linkObj;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
