package com.cwc.web.common.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.cwc.web.common.db.ConnectionPool;
import com.cwc.web.ypzj.db.mapper.RowMapper;


public class DBManager {
	private static ConnectionPool connPool=ConnectionPool.getInstance();
	private Connection conn;
	private PreparedStatement statement;
	private ResultSet resultSet;

	public DBManager() throws SQLException{
		conn=connPool.getConn();
		if(conn==null) {
			throw new SQLException("连接池服务繁忙");
		}
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

	private boolean prepareQuery(String sql,Object... param){
		try{
			statement=conn.prepareStatement(sql);
			dealParam(param);
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
	public List<Object> findAll(RowMapper rowMapper,String sql,Object... param)
	{
		if(prepareQuery(sql,param)){
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
	
	public Object queryObject(RowMapper rowMapper,String sql,Object... param)
	{
		if(prepareQuery(sql,param)){
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

	public Long getNum(String sql,Object... param){
		if(prepareQuery(sql,param)){
			try{
				if(resultSet.first()){
					return resultSet.getLong(1);
				}
			}catch (Exception e){
				e.printStackTrace();
				System.err.println("get num error");
			}
		}
		return null;
	}

	public int update(String sql,Object... param)
	{
		try
		{
			statement=conn.prepareStatement(sql);
			dealParam(param);
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

	/*
	param here should fit the order in the sql
	 */
	private void dealParam(Object... param) throws SQLException {
		for(int i=1;i<=param.length;i++){
			int index=i-1;
			if(param[index] instanceof Long){
				statement.setLong(i,(Long)param[index]);
			}else if(param[index] instanceof String){
				statement.setString(i,(String)param[index]);
			}else if(param[index] instanceof byte[]){
				statement.setBytes(i,(byte[])param[index]);
			}else if(param[index] instanceof Timestamp){
				statement.setTimestamp(i,(Timestamp) param[index]);
			} else if(param[index]==null){
				statement.setNull(i,Types.INTEGER);
			}
		}
	}

	/**
	 *
	 * @param sql
	 * @param param
	 * @return null if errors happen
	 */
	public Long insertAndGetKey(String sql,Object... param) {

		try {
			statement=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			dealParam(param);
			statement.executeUpdate();
			resultSet=statement.getGeneratedKeys();
			if(resultSet.first()) {
				return resultSet.getLong(1);
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
	 * @param param
	 * @return 
	 */
	public Integer insert(String sql,Object... param)
	{
		try
		{
			statement=conn.prepareStatement(sql);
			dealParam(param);
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
