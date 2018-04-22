package com.cwc.web.ypzj.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cwc.web.ypzj.db.dbObj.ArticleContent;

public class ArticleContentMapper extends RowMapper {

	@Override
	public Object map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		long id=resultSet.getLong("id");
		String content=resultSet.getString("content");
		return new ArticleContent(id, content);
	}

}
