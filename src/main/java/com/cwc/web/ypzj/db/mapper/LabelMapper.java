package com.cwc.web.ypzj.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cwc.web.ypzj.db.dbObj.Label;

public class LabelMapper extends RowMapper {

	@Override
	public Object map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return new Label(resultSet.getLong("id"), resultSet.getString("name"));
	}

}
