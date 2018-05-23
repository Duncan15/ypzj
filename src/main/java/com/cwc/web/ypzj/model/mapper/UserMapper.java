package com.cwc.web.ypzj.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cwc.web.ypzj.model.obj.User;

public class UserMapper extends RowMapper {

	@Override
	public User map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		if(resultSet==null)return new User();
		Long id=resultSet.getLong("id");
		String account=resultSet.getString("account");
		String userName=resultSet.getString("user_name");
		String passwordMD5=resultSet.getString("password_md5");
		byte status=resultSet.getByte("status");
		Long concernedTime=resultSet.getLong("concerned_time");
		User usr=new User(id,userName, account, passwordMD5,concernedTime);
		usr.setStatus(status);
		return usr;
	}

}