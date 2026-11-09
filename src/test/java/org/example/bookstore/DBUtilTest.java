package org.example.bookstore;

import org.example.bookstore.util.DBUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtilTest {
    public static void main(String[] args) {
        String sql = "select database(), now()";
        try (Connection connection = DBUtil.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            System.out.println("Database connection successful!");
            System.out.println("Database: "
                    + metaData.getDatabaseProductName());
            System.out.println("Version: "
                    + metaData.getDatabaseProductVersion());

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Database: " + resultSet.getString(1));
                System.out.println("Current time: " + resultSet.getString(2));

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
