package com.cwc.web.ypzj.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cwc.web.ypzj.db.dbObj.Link;

public class LinkMapper extends RowMapper {

	@Override
	public Object map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return new Link(resultSet.getLong("id"), resultSet.getString("intro"), resultSet.getString("link"), resultSet.getLong("article_id"));
	}

}
