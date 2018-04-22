package com.cwc.web.ypzj.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cwc.web.ypzj.db.dbObj.User;

public class UserMapper extends RowMapper {

	@Override
	public Object map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		Long id=resultSet.getLong("id");
		String account=resultSet.getString("account");
		String userName=resultSet.getString("user_name");
		String passwordMD5=resultSet.getString("password_md5");
		User usr=new User(id,userName, account, passwordMD5);
		return usr;
	}

}
