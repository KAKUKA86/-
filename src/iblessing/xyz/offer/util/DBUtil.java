package iblessing.xyz.offer.util;

import java.sql.*;

public class DBUtil {
    private static final String
            DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String
            DB_URL = "jdbc:mysql://localhost:3306/jdbc_offer?serverTimezone=PRC&useUnicode=true&characterEncoding=utf8";
    private static final String
            DB_USER = "root";
    private static final String
            DB_PASSWORD = "";

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }//驱动注册
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return connection;
    }

    public static void closeJDBC(ResultSet resultSet, Statement statement,
                                 Connection connection) {//释放资源
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
