package com.zjh.pkm.util;
/**
 * JDBC工具类，方便JDBC操作
 */

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	//工具类中的方法都是静态方法，静态方法调用不需要创建对象，所以将构造函数私有化
	private DBUtil(){
		
	}
	//静态代码块，在类加载时执行，只执行一次
	static{
		try {
			//读取属性配置文件
			InputStream ins= DBUtil.class.getResourceAsStream("db.properties");
			//FileReader reader = new FileReader("db.properties");
			Properties pro = new Properties();
			pro.load(ins);
			ins.close();
			
			driver = pro.getProperty("driver");
			url = pro.getProperty("url");
			user = pro.getProperty("user");
			password = pro.getProperty("password");
			//1.注册驱动
			Class.forName(driver);
			//Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 2.获取数据库连接对象(后续可改进为读取配置文件中的数据)
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		/*String url = "jdbc:mysql://localhost:3366/parking_management?serverTimezone=GMT%2B8";
		String user = "root";
		String password =  "123";*/
		//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3366/parking_management?serverTimezone=GMT%2B8", "root", "123");*/
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	/**
	 * 关闭资源
	 * @param conn
	 * @param ps
	 * @param rs
	 */
	public static void close(Connection conn,Statement ps,ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ps != null){
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 开启事务
	 * @param conn
	 * @throws SQLException
	 */
	public static void beginTransaction(Connection conn) throws SQLException{
		if(conn != null){
			conn.setAutoCommit(false);	
		}
	}
	
	/**
	 * 提交事务
	 * @param conn
	 * @throws SQLException
	 */
	public static void commitTransaction(Connection conn) throws SQLException{
		if(conn != null){
			conn.commit();	
		}
	}
	
	/**
	 * 回滚事务
	 * @param conn
	 */
	public static void rollbackTransaction(Connection conn){
		if(conn != null){
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	} 
}
