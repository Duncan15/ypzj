package com.cwc.web.ypzj.db.DAO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.cwc.web.common.db.DBManager;
import com.cwc.web.ypzj.db.mapper.ArticleContentMapper;
import com.cwc.web.ypzj.db.mapper.ArticleInfoMapper;
import com.cwc.web.ypzj.db.dbObj.ArticleContent;
import com.cwc.web.ypzj.db.dbObj.ArticleInfo;

public class ArticleRepository {
	private final static String INFO_TABLE="article_info_table";
	private final static String CONTENT_TABLE="article_content_table";
	public enum Arg{
		id,
		article_name,
		created_time,
		supported_time,
		top_label_id,
		author_id,
		content,
		avatar_id
	}
	private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/*
	 * add article
	 */
	public static Long addArticle(String articleName,Long labelId,Long authorId,String content,String avatarId) {
		DBManager dbManager;
		try{
			dbManager=new DBManager();
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		try{
			dbManager.supportTransaction(true);
			String sql="insert into "+INFO_TABLE+"("+Arg.article_name.toString()+","+Arg.top_label_id.toString()+","+Arg.author_id.toString()+","+Arg.created_time.toString()+","+Arg.avatar_id.toString()+")values(?,?,?,?,?);";

			Long articleId=dbManager.insertAndGetKey(sql,articleName,labelId,authorId,simpleDateFormat.format(new java.util.Date()),avatarId);
			System.err.println(new java.util.Date().toString());
			if(articleId!=null){
				sql="insert into "+CONTENT_TABLE+"("+Arg.id.toString()+","+Arg.content.toString()+")values(?,?);";
				if(dbManager.insert(sql,articleId,content)!=null){
					dbManager.commit();
					return articleId;
				}
			}
			dbManager.rollback();
			return null;
		}
		finally {
			dbManager.close();
		}
	}
	
	public static ArticleInfo getArticleInfoById(Long id)//return null when haven't this article
	{
		String sql="select * from article_info_table where id=?;";
		DBManager dbManager=null;
		try{
			dbManager=new DBManager();
			return (ArticleInfo)dbManager.queryObject(new ArticleInfoMapper(),sql,id);
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			if(dbManager!=null)dbManager.close();
		}
	}
	public static ArticleContent getArticleContentByArticleId(Long id)//return null when haven't this article
	{
		String sql="select * from article_content_table where id=?;";
		DBManager dbManager=null;
		try {
			dbManager=new DBManager();
			return (ArticleContent)dbManager.queryObject(new ArticleContentMapper(),sql,id);
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			if(dbManager!=null)dbManager.close();
		}
	}

	public static List<ArticleInfo> findByLabelIdOrderByCreatedTime(long labelId,int requireNum) {
		 List<ArticleInfo> t=findByArgAOrderByArgBLimitStartAndLen(Arg.top_label_id.toString(),labelId,Arg.created_time.toString(),0,requireNum);
		 return t;
	}

	
	public static List<ArticleInfo> findByLabelIdOrderBySupportedTime(long labelId,int requireNum){
		List<ArticleInfo> t=findByArgAOrderByArgBLimitStartAndLen(Arg.top_label_id.toString(),labelId,Arg.supported_time.toString(),0,requireNum);
		return t;
	}

	public static List<ArticleInfo> findByAuthorIdOrderByCreatedTime(Long authorId,int requireNum) {
		List<ArticleInfo> t=findByArgAOrderByArgBLimitStartAndLen(Arg.author_id.toString(),authorId,Arg.created_time.toString(),0,requireNum);
		return t;
	}


	public static List<ArticleInfo> findByAuthorIdOrderBySupportedTime(Long authorId,int requireNum){
		List<ArticleInfo> t=findByArgAOrderByArgBLimitStartAndLen(Arg.author_id.toString(),authorId,Arg.supported_time.toString(),0,requireNum);
		return t;
	}

	public static List<ArticleInfo> findByAuthorIdLimitStartAndNum(Long authorId,int start,int end){
		List<ArticleInfo> t=findByArgAOrderByArgBLimitStartAndLen(Arg.author_id.toString(),authorId,null,start,end);
		return t;
	}
	public static Long getCountByAuthorId(Long authorId){
		DBManager dbManager=null;
		String sql="select count(*) from "+INFO_TABLE;
		String cond="";
		if(authorId!=null){
			cond+=" where "+Arg.author_id.toString()+" = ? ";
		}
		sql+=cond;
		try{
			dbManager=new DBManager();
			return dbManager.getNum(sql,authorId);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}finally {
			dbManager.close();
		}
	}

	private static List<ArticleInfo> findByArgAOrderByArgBLimitStartAndLen(String argA,Object valueA,String argB,int start,int len){
		DBManager dbManager=null;
		String sql="select * from "+INFO_TABLE;
		String cond=" where "+argA+" = ? ";
		sql+=cond;
		if(argB!=null){
			String order=" order by "+argB+" desc ";
			sql+=order;
		}
		sql+=" limit "+start+","+len+";";
		try{
			dbManager=new DBManager();
			return transfer(dbManager.findAll(new ArticleInfoMapper(),sql,valueA));
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			dbManager.close();
		}
	}
	private static List<ArticleInfo> transfer(List<Object> t) {
		ArrayList<ArticleInfo> ans=null;
		if(t!=null) {
			ans=new ArrayList<>(t.size());
			for(Object o:t) {
				ans.add((ArticleInfo)o);
			}
		}
		return ans;
	}

}
