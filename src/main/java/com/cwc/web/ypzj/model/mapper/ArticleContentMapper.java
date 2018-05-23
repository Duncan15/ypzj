package com.cwc.web.ypzj.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cwc.web.ypzj.model.obj.ArticleContent;

public class ArticleContentMapper extends RowMapper {

	@Override
	public ArticleContent map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		if(resultSet==null)return new ArticleContent();
		long id=resultSet.getLong("id");
		String content=resultSet.getString("content");
		return new ArticleContent(id, content);
	}

}
