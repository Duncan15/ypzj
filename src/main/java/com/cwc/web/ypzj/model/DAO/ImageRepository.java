package com.cwc.web.ypzj.model.DAO;

import com.cwc.web.ypzj.model.pool.DBManager;
import com.cwc.web.ypzj.model.mapper.ImageMapper;
import com.cwc.web.ypzj.model.obj.Image;

import java.sql.SQLException;

public class ImageRepository {
	private static final String TABLE_NAME="image_table";
	public enum Arg{
		image_name,
		article_id,
		md5
	}
	/**	
	 * 
	 * @param MD5
	 * @return null if not exist
	 */
	public static Image findImageNameByMD5(byte[] MD5) {
		String sql="select * from "+TABLE_NAME+" where md5=?;";
		DBManager<Image> dbManager=null;
		try{
			dbManager=new DBManager();
			return dbManager.queryObject(new ImageMapper(),sql,MD5);
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			if (dbManager!=null)dbManager.close();
		}
	}
	public static Image createNewImage(String imageName,byte[] MD5) {
		String sql="insert into "+TABLE_NAME+"("+Arg.image_name.toString()+","+Arg.md5.toString()+")"
				+"values(?,?);";
		DBManager<Image> dbManager=null;
		int ans=0;
		try{
			dbManager=new DBManager();
			ans=dbManager.insert(sql,imageName,MD5);
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			if(dbManager!=null)dbManager.close();
		}
		if(ans>0) {
			return new Image(imageName,  MD5);
		}
		return null;
	}
}
