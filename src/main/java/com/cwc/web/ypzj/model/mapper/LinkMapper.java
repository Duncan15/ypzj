package com.cwc.web.ypzj.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cwc.web.ypzj.model.obj.Link;

public class LinkMapper extends RowMapper {

	@Override
	public Link map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		if(resultSet==null)return new Link();
		return new Link(resultSet.getLong("id"), resultSet.getString("intro"), resultSet.getString("link"), resultSet.getLong("article_id"));
	}

}
