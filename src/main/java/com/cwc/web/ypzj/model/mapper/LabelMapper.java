package com.cwc.web.ypzj.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cwc.web.ypzj.model.obj.Label;

public class LabelMapper extends RowMapper {

	@Override
	public Label map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		if(resultSet==null)return new Label();
		return new Label(resultSet.getLong("id"), resultSet.getString("name"));
	}

}
