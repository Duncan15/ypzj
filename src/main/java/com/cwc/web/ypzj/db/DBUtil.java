package com.cwc.web.ypzj.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cwc.web.ypzj.DAO.ArticleRepository.Arg;
import com.cwc.web.ypzj.db.mapper.RowMapper;


public class DBUtil {
	private static String driver="com.mysql.cj.jdbc.Driver";
	//set useSSL=false here can avoid the warning
	private static String connectionString="jdbc:mysql://127.0.0.1:3306/ypzj?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static String username="root";
	private static String password="123456";
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static List<Object> findAll(String sql,RowMapper rowMapper)
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try
		{
			ArrayList<Object> ans=new ArrayList<>();
			connection=getConnection();
			preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
			{
				ans.add(rowMapper.map(resultSet));
			}
			return ans;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("error in find all");
			return null;
		}
		finally {
			clear(null,preparedStatement,connection);
		}
	}
	
	public static Object queryObject(String sql,byte[] bin,RowMapper rowMapper)
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		try
		{
			connection=getConnection();
			preparedStatement=connection.prepareStatement(sql);
			if(bin!=null) {
				preparedStatement.setBytes(1, bin);
			}
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.first())
			{
				return rowMapper.map(resultSet);
			}
			return null;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("query error");
			return null;
		}
		finally {
			clear(null,preparedStatement,connection);
		}
	}
	public static List<Object> findByArgAOrderByArgB(String table,String argA,String valueA,String argB,RowMapper rowMapper)
	{
		String sql="select * from "+table+" where "+argA+" = \""+valueA+"\" order by "+argB+" desc;";
		List<Object> t=findAll(sql, rowMapper);
		return t;
	}
	public static int update(String sql)
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		try
		{
			connection=getConnection();
			preparedStatement=connection.prepareStatement(sql);
			return preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("update error");
			return 0;
		}
		finally {
			clear(null, preparedStatement, connection);
		}
	}
	
	public static Long insertAndGetKey(String sql,byte[] bin) {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try {
			connection=getConnection();
			preparedStatement=connection.prepareStatement(sql);
			if(bin!=null) {
				preparedStatement.setBytes(1, bin);
			}
			preparedStatement.executeUpdate();
			resultSet=preparedStatement.getGeneratedKeys();
			if(resultSet.first()) {
				return resultSet.getLong("id");
			}
			return null;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("update error");
			return null;
		}finally {
			clear(resultSet, preparedStatement, connection);
		}
	}
	/**
	 * 
	 * @param sql
	 * @param bin
	 * @return 
	 */
	public static int insert(String sql,byte[] bin)
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		try
		{
			connection=getConnection();
			preparedStatement=connection.prepareStatement(sql);
			if(bin!=null) {
				preparedStatement.setBytes(1, bin);
			}
			return preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("insert error");
			return 0;
		}
		finally {
			clear(null,preparedStatement,connection);
		}
	}
	private static Connection getConnection()throws Exception
	{
		return DriverManager.getConnection(connectionString, username, password);
	}
	private static void clear(ResultSet resultset,Statement statement,Connection connection)
	{
		try
		{
			if(resultset!=null)
			{
				resultset.close();
			}
			if(statement!=null)
			{
				statement.close();
			}
			if(connection!=null)
			{
				connection.close();
			}
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("close error");
		}
	}
	
}
