package com.cwc.web.ypzj.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class RowMapper {
	public abstract Object map(ResultSet resultSet)throws SQLException;
}
