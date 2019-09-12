package com.zjh.pkm.util;
/**
 * JDBC�����࣬����JDBC����
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
	//�������еķ������Ǿ�̬��������̬�������ò���Ҫ�����������Խ����캯��˽�л�
	private DBUtil(){
		
	}
	//��̬����飬�������ʱִ�У�ִֻ��һ��
	static{
		try {
			//��ȡ���������ļ�
			InputStream ins= DBUtil.class.getResourceAsStream("db.properties");
			//FileReader reader = new FileReader("db.properties");
			Properties pro = new Properties();
			pro.load(ins);
			ins.close();
			
			driver = pro.getProperty("driver");
			url = pro.getProperty("url");
			user = pro.getProperty("user");
			password = pro.getProperty("password");
			//1.ע������
			Class.forName(driver);
			//Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 2.��ȡ���ݿ����Ӷ���(�����ɸĽ�Ϊ��ȡ�����ļ��е�����)
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
	 * �ر���Դ
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
	 * ��������
	 * @param conn
	 * @throws SQLException
	 */
	public static void beginTransaction(Connection conn) throws SQLException{
		if(conn != null){
			conn.setAutoCommit(false);	
		}
	}
	
	/**
	 * �ύ����
	 * @param conn
	 * @throws SQLException
	 */
	public static void commitTransaction(Connection conn) throws SQLException{
		if(conn != null){
			conn.commit();	
		}
	}
	
	/**
	 * �ع�����
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
