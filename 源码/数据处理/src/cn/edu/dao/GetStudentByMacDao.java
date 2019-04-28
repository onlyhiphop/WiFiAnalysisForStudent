package cn.edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import cn.edu.utils.MySqlUtil;


/**
 * 
* @Description: 
*	从数据库中取出 mac地址对应的学号
*
* @version: v1.0.0
* @author: liao
* @date: 2019年1月23日 上午9:51:56 
 */
public class GetStudentByMacDao {
	

	public static HashMap<String, String> getMacStudent(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		Connection conn = MySqlUtil.getConnection();
		ResultSet rs = null;
		String sql = "SELECT * FROM id_mac";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				String mac = rs.getString("mac");
				String id = rs.getString("id");
				map.put(mac, id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			MySqlUtil.Close(conn, ps, rs);
		}
		return map;
	}
	
}
