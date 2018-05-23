package com.cwc.web.ypzj.model.DAO;

import com.cwc.web.ypzj.model.pool.DBManager;
import com.cwc.web.ypzj.model.mapper.UserMapper;
import com.cwc.web.ypzj.model.obj.User;

import java.io.IOException;
import java.sql.SQLException;

public class UserRepository {
	private final static String TABLE="user_table";
	enum Arg{
		id,
		account,
		user_name,
		password_md5,
		status,
	}
	public static User getUserByAccount(String account) {
		String sqlString="select * from "+TABLE+" where "+Arg.account.toString()+"=?;";
		DBManager<User> dbManager=null;
		try {
			dbManager=new DBManager();
			return  dbManager.queryObject(new UserMapper(),sqlString,account);//if not exist, here return null
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			if(dbManager!=null)dbManager.close();
		}
	}
	public static User getUserByUserName(String userName){
		String sql="select * from "+TABLE+" where "+Arg.user_name.name()+" = ? ;";
		DBManager<User> dbManager=null;
		try {
			dbManager=new DBManager<>();
			return dbManager.queryObject(new UserMapper(),sql,userName);
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			if(dbManager!=null)dbManager.close();
		}

	}
	public static User getUserById(Long id){
		String sql="select * from "+TABLE+" where "+Arg.id.toString()+" = ?;";
		DBManager<User> dbManager=null;
		try {
			dbManager=new DBManager();
			return dbManager.queryObject(new UserMapper(),sql,id);
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			if(dbManager!=null)dbManager.close();
		}
	}
	public static boolean updateUser(User usr){
		String sqlString="update "+TABLE+" set "+Arg.account.toString()+" = ? ,"+Arg.user_name.toString()+" = ? ,"+Arg.password_md5.toString()+" = ? , "+Arg.status.name()+" = ? "+"where "+Arg.id.toString()+" = ?";
		DBManager dbManager=null;
		int ans=0;
		try {
			dbManager=new DBManager();
			ans=dbManager.update(sqlString,usr.getAccount(),usr.getUserName(),usr.getPasswordMD5(),usr.getStatus(),usr.getId());
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			if(dbManager!=null)dbManager.close();
		}
		if(ans>0)return true;
		return false;
	}
	public static boolean updateUserNameByAccount(String account,String userName) {
		String sqlString="update "+TABLE+" set "+Arg.user_name.toString()+"=? where "+Arg.account.toString()+"=?;";
		DBManager dbManager=null;
		int ans=0;
		try{
			dbManager=new DBManager();
			ans=dbManager.update(sqlString,userName,account);
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			if(dbManager!=null)dbManager.close();
		}
		if(ans>0)return true;
		return false;
	}
	
	/**
	 *
	 * @return null if fail
	 */
	public static User createNewAccount(User usr)
	{
		String sqlString="insert into "+TABLE+"("+Arg.account.toString()+","+Arg.user_name.toString()+","+Arg.password_md5.toString()+","+Arg.status.name()+")"+" values(?,?,?,?);";
		DBManager<User> dbManager=null;
		Long ans=0l;
		try{
			dbManager=new DBManager();
			ans=dbManager.insertAndGetKey(sqlString,usr.getAccount(),usr.getUserName(),usr.getPasswordMD5(),usr.getStatus());
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			if(dbManager!=null)dbManager.close();
		}
		if(ans!=null) {
			usr.setId(ans);
			return usr;
		}
		return null;
	}
}
