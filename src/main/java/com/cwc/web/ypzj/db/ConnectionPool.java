package com.cwc.web.ypzj.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final String driver="com.mysql.cj.jdbc.Driver";
    //set useSSL=false here can avoid the warning
    private static final String connectionString="jdbc:mysql://127.0.0.1:3306/ypzj?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String username="root";
    private static final String password="1215287416";
    private static int poolMaxSize;
    private static int poolMinSize;
    private static int count;
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static ConnectionPool connPool;//单例对象
    private ArrayBlockingQueue<Connection> connArray;//实际连接池
    private ReentrantLock lock;
    private ConnectionPool(){
        this.poolMaxSize=20;//连接池最大连接数
        this.poolMinSize=5;//连接池最小连接数
        this.count=this.poolMinSize;
        this.lock=new ReentrantLock();
        this.connArray=new ArrayBlockingQueue<>(this.poolMinSize);
        for(int i=0;i<this.poolMinSize;i++){
            try(Connection tmp=DriverManager.getConnection(connectionString, username, password);){
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
                try(Connection tmp=DriverManager.getConnection(connectionString, username, password);){
                    count++;
                    return tmp;
                }catch (SQLException e){
                    e.printStackTrace();
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
            return connArray.poll(3, TimeUnit.SECONDS);
        }catch (InterruptedException e){
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
