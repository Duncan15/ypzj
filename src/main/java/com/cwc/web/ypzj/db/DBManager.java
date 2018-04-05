package com.cwc.web.ypzj.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.cwc.web.ypzj.db.mapper.RowMapper;


public class DBManager {
	private static ConnectionPool connPool=ConnectionPool.getInstance();
	private Connection conn;
	private PreparedStatement statement;
	private ResultSet resultSet;

	public DBManager() throws SQLException{

		conn=connPool.getConn();
		if(conn==null)throw new SQLException("连接池服务繁忙");

	}
	public boolean supportTransaction(boolean supported){
		try{
			this.conn.setAutoCommit(!supported);
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean commit(){
		try{
			this.conn.commit();
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		finally {
			supportTransaction(false);
		}
		return true;
	}
	public boolean rollback(){
		try {
			this.conn.rollback();
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		finally {
			supportTransaction(false);
		}
		return true;
	}

	private boolean prepareQuery(String sql,byte[] bin){
		try{
			statement=conn.prepareStatement(sql);
			if(bin!=null){
				statement.setBytes(1,bin);
			}
			resultSet=statement.executeQuery();
		}catch (SQLException e){
			e.printStackTrace();
			System.err.println("error in prepare");
			return false;
		}
		return true;
	}
	private void clear(){
		try{
			if(resultSet!=null)resultSet.close();
		}catch (SQLException e){
			e.printStackTrace();
			System.err.println("can't close resultSet");
		}
		try{
			if(statement!=null)statement.close();
		}catch (SQLException e){
			e.printStackTrace();
			System.err.println("can't close statement");
		}
	}
	public List<Object> findAll(String sql,byte[] bin,RowMapper rowMapper)
	{
		if(prepareQuery(sql,bin)){
			ArrayList<Object> ans=new ArrayList<>();
			try
			{
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
			}finally {
				clear();
			}

		}
		return null;
	}
	
	public Object queryObject(String sql,byte[] bin,RowMapper rowMapper)
	{
		if(prepareQuery(sql,bin)){
			ArrayList<Object> ans=new ArrayList<>();
			try
			{
				if(resultSet.first())
				{
					return rowMapper.map(resultSet);
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.err.println("query error");
			}finally {
				clear();
			}
		}
		return null;
	}
	public  List<Object> findByArgAOrderByArgB(String table,String argA,String valueA,String argB,RowMapper rowMapper)
	{
		String sql="select * from "+table+" where "+argA+" = \""+valueA+"\" order by "+argB+" desc;";
		List<Object> t=findAll(sql, null,rowMapper);
		return t;
	}
	public int update(String sql)
	{
		try
		{
			statement=conn.prepareStatement(sql);
			return statement.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("update error");
			return 0;
		}
		finally {
			clear();
		}
	}

	/**
	 *
	 * @param sql
	 * @param bin
	 * @return null if errors happen
	 */
	public Long insertAndGetKey(String sql,byte[] bin) {

		try {
			statement=conn.prepareStatement(sql);
			if(bin!=null){
				statement.setBytes(1,bin);
			}
			resultSet=statement.getGeneratedKeys();
			if(resultSet.first()) {
				return resultSet.getLong("id");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("update error");
		}finally {
			clear();
		}
		return null;
	}
	/**
	 * 
	 * @param sql
	 * @param bin
	 * @return 
	 */
	public Integer insert(String sql,byte[] bin)
	{
		try
		{
			statement=conn.prepareStatement(sql);
			if(bin!=null) {
				statement.setBytes(1, bin);
			}
			return statement.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("insert error");
			return null;
		}
		finally {
			clear();
		}
	}
	public void close()
	{
		if(conn!=null)
		{
			ConnectionPool.returnConn(conn);
		}
	}

}
