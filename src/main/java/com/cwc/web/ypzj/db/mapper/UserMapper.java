package com.cwc.web.ypzj.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cwc.web.ypzj.servletObj.User;

public class UserMapper extends RowMapper {

	@Override
	public Object map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		String account=resultSet.getString("account");
		String userName=resultSet.getString("user_name");
		String passwordMD5=resultSet.getString("password_md5");
		User usr=new User(userName, account, passwordMD5);
		return usr;
	}

}
