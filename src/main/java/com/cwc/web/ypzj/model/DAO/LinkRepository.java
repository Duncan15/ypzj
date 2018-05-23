package com.cwc.web.ypzj.model.DAO;

import com.cwc.web.ypzj.model.pool.DBManager;
import com.cwc.web.ypzj.model.mapper.LinkMapper;
import com.cwc.web.ypzj.model.obj.Link;

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
				+"values(?,?);";
		String searchSql="select * from "+TABLE_NAME+" where "+Arg.id.toString()+"=?;";
		DBManager<Link> dbManager=null;
		try{
			dbManager=new DBManager();
			Long linkId=dbManager.insertAndGetKey(sql,intro,link);
			if(linkId!=null) {
				Link linkObj=dbManager.queryObject(new LinkMapper(),searchSql, linkId);
				return linkObj;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			if(dbManager!=null)dbManager.close();
		}
		return null;
	}
}
