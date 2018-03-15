package com.cwc.web.ypzj.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cwc.web.ypzj.servletObj.Image;

public class ImageMapper extends RowMapper {

	@Override
	public Object map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		String imageName=resultSet.getString("image_name");
		long articleId=resultSet.getLong("article_id");
		byte[] MD5=resultSet.getBytes("MD5");
		return new Image(imageName, articleId, MD5);
	}

}
