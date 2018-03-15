package com.cwc.web.ypzj.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.cwc.web.ypzj.servletObj.ArticleInfo;

public class ArticleInfoMapper extends RowMapper {

	@Override
	public Object map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		long id=resultSet.getLong("id");
		String articleName=resultSet.getString("article_name");
		Date createdTime=new Date(resultSet.getDate("created_time").getTime());
		long supportedTime=resultSet.getLong("supported_time");
		long topLabelId=resultSet.getLong("top_label_id");
		String authorId=resultSet.getString("author_id");
		return new ArticleInfo(id, articleName, createdTime, supportedTime, topLabelId, authorId);
	}

}
