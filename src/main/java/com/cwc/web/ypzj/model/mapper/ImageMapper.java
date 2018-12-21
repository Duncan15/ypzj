package com.cwc.web.ypzj.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cwc.web.ypzj.model.obj.Image;

public class ImageMapper extends RowMapper<Image>{

	@Override
	public Image map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		if(resultSet==null)return new Image();
		String imageName=resultSet.getString("image_name");
		byte[] MD5=resultSet.getBytes("MD5");
		return new Image(imageName, MD5);
	}

}
