package cn.edu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
* @Description: 
*	连接mysql工具类
* @version: v1.0.0
* @author: liao
* @date: 2019年1月23日 下午4:45:17 
 */

public class MySqlUtil {
	private static String USERNAME;
	private static String PASSWORD;
	private static String URL;
	
	static{
		//取出properties文件中的值
		Properties pp = new Properties();
		InputStream in = null;
		try {
			in = MySqlUtil.class.getResourceAsStream("mysql.properties");
			pp.load(in);
			USERNAME = pp.getProperty("username");
			PASSWORD = pp.getProperty("password");
			URL = pp.getProperty("url");
			
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Connection getConnection(){
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void Close(Connection conn, PreparedStatement ps, ResultSet rs){
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = null;
		}
		
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ps = null;
		}
		
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs = null;
		}
	}
	
}
