package com.cwc.web.ypzj.model.pool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

//the attribute in the class with be configed by the listener when context init
public class ConnectionPool{
    private static final String driver="com.mysql.cj.jdbc.Driver";
    //set useSSL=false here can avoid the warning
    public static String connectionString;
    public static String username;
    public static String password;
    public static int poolMaxSize;//连接池最大连接数
    public static int poolMinSize;//连接池最小连接数
    private static int count;
    private static void register() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static ConnectionPool connPool=null;//单例对象
    private ArrayBlockingQueue<Connection> connArray;//实际连接池
    private ReentrantLock lock;
    private ConnectionPool(){
        this.count=this.poolMinSize;
        this.lock=new ReentrantLock();
        this.connArray=new ArrayBlockingQueue<>(this.poolMinSize);
        register();

        for(int i=0;i<this.poolMinSize;i++){
            try{
                Connection tmp=DriverManager.getConnection(connectionString, username, password);
                connArray.put(tmp);
            }catch (Exception e){
                i--;
            }
        }
    }
    public static ConnectionPool getInstance(){
        if(connPool==null) {//克服指令排序问题
            synchronized (ConnectionPool.class) {
                if (connPool == null) {
                    connPool = new ConnectionPool();
                }
            }
        }
        return connPool;
    }
    public Connection getConn(){
        if(connArray.isEmpty()){
            lock.lock();
            if(count<poolMaxSize){
                try{
                    Connection tmp=DriverManager.getConnection(connectionString, username, password);
                    count++;
                    return tmp;
                }catch (SQLException e){
                    e.printStackTrace();
                    return null;
                }
                finally {
                    lock.unlock();
                }
            }
            else{
                lock.unlock();
            }
        }
        try{
            Connection ans= connArray.poll(3, TimeUnit.SECONDS);
            if(ans==null)return null;
            if (ans.isValid(5)){
                return ans;
            }else {
                ans.close();
                register();
                Connection tmp=DriverManager.getConnection(connectionString, username, password);
                return tmp;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static boolean returnConn(Connection conn){
        if(conn==null)return false;
        if(connPool==null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                return false;
            }
        }
        connPool.lock.lock();
        if(connPool.connArray.size()>=poolMinSize){
            try{
                conn.close();

            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                connPool.lock.unlock();
                count--;
                return false;
            }
        }
        try {
            connPool.connArray.put(conn);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("save connection fail");
            try {
                conn.close();
            }catch (SQLException e2){
                e2.printStackTrace();
                System.err.println("close connection fail");
            }
            return false;
        }finally {
            connPool.lock.unlock();
        }
    }

}
