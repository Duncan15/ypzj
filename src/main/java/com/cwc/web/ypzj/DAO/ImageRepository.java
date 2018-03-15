package com.cwc.web.ypzj.DAO;

import com.cwc.web.ypzj.db.DBUtil;
import com.cwc.web.ypzj.db.mapper.ImageMapper;
import com.cwc.web.ypzj.servletObj.Image;

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
		return (Image) DBUtil.queryObject(sql, MD5, new ImageMapper());
	}
	public static Image createNewImage(String imageName,byte[] MD5) {
		String sql="insert into "+TABLE_NAME+"("+Arg.image_name.toString()+","+Arg.md5.toString()+")"
				+"values(\""+imageName+"\",?);";
		int ans=DBUtil.insert(sql, MD5);
		if(ans>0) {
			return new Image(imageName, null, MD5);
		}
		return null;
	}
}
