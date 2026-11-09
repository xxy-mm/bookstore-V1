package org.example.bookstore.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static final String Driver;
    private static final String  URL;
    private static final String USERNAME;
    private static final String PASSWORD;


    static {
        Properties properties = new Properties();

        try (InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("Can't find db.properties");
            }
            properties.load(inputStream);
            Driver = properties.getProperty("jdbc.driver");
            URL = properties.getProperty("jdbc.url");
            USERNAME = properties.getProperty("jdbc.username");
            PASSWORD = properties.getProperty("jdbc.password");

            Class.forName(Driver);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try{
                connection.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
