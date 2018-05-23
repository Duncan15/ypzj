package com.cwc.web.ypzj.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class RowMapper<E> {
	public abstract E map(ResultSet resultSet)throws SQLException;
}
