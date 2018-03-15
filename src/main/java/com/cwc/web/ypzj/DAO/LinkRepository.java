package com.cwc.web.ypzj.DAO;

import com.cwc.web.ypzj.db.DBUtil;
import com.cwc.web.ypzj.db.mapper.LinkMapper;
import com.cwc.web.ypzj.servletObj.Link;

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
		int ans=DBUtil.insert(sql, null);
		if(ans>0) {
			Link linkObj=(Link) DBUtil.queryObject(searchSql, null, new LinkMapper());
			return linkObj;
		}
		return null;
	}
}
