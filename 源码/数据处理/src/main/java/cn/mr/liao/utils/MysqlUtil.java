package cn.mr.liao.utils;
import	java.sql.PreparedStatement;
import	java.sql.ResultSet;
import	java.util.HashMap;
import	java.sql.Connection;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import	java.util.Properties;

/**
 * 从mysql数据库中取数据
 */
public class MysqlUtil {

    private static String USERNAME;
    private static String PASSWORD;
    private static String URL;

    static {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream("mysql.properties");
            prop.load(in);
            USERNAME = prop.getProperty("username");
            PASSWORD = prop.getProperty("password");
            URL = prop.getProperty("url");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取wifi探针对应的id
     * @return
     */
    public static HashMap<String, String> getWidId() {
        HashMap<String, String> map = new HashMap<> ();
        Connection conn = getConnection();
        ResultSet rs = null;
        String sql = "select * from wifi_info";
        PreparedStatement ps = null;
        int count = 0;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                String wid = rs.getString("wid");
                String id = rs.getString("id");
                map.put(wid, id);
                count++;
            }
            map.put("count", String.valueOf(count));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void close(AutoCloseable ...args) {
        for (AutoCloseable item :
                args) {
            if (item != null){
                try {
                    item.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                item = null;
            }
        }
    }

    public static void main(String[] args) {
        Connection connection = MysqlUtil.getConnection();

    }
}





































