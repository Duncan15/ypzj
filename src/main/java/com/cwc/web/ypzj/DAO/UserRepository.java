package com.cwc.web.ypzj.DAO;

import com.cwc.web.ypzj.db.DBUtil;
import com.cwc.web.ypzj.db.mapper.UserMapper;
import com.cwc.web.ypzj.servletObj.User;

public class UserRepository {
	public static User getUserByAccount(String account)
	{
		String sqlString="select * from user_table where account='"+account+"'";
		return (User) DBUtil.queryObject(sqlString, null,new UserMapper());//if not exist, here return null
	}
	public static boolean updateUserNameByAccount(String account,String userName)
	{
		String sqlString="update user_table set user_name="+userName+"where account="+account;
		int ans=DBUtil.update(sqlString);
		if(ans>0)return true;
		return false;
	}
	
	/**
	 * 
	 * @param account
	 * @param userName
	 * @param passwordMD5
	 * @return null if fail
	 */
	public static User createNewAccount(String account,String userName,String passwordMD5)
	{
		String sqlString="insert into user_table values('"+account+"','"+userName+"','"+passwordMD5+"');";
		int ans=DBUtil.insert(sqlString,null);
		if(ans>0) {
			return new User(userName, account, passwordMD5);
		}
		return null;
	}
}
