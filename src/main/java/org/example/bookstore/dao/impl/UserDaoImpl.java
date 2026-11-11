package org.example.bookstore.dao.impl;

import org.example.bookstore.dao.UserDao;
import org.example.bookstore.entity.User;
import org.example.bookstore.util.DBUtil;

import java.sql.*;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean existsByUsername(String username) {

        String sql = "SELECT 1 FROM users WHERE username = ? LIMIT 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by username", e);
        }

    }

    @Override
    public long insert(User user) {

        String sql = "INSERT INTO users (username, password, nickname, email, phone, role, status) " +
                "VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getNickname());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.setInt(6, user.getRole());
            stmt.setInt(7, user.getStatus());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Failed to insert user, affected rows: " + affectedRows);
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new SQLException("Insert user succeeded but no generated key wa returned");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert user", e);
        }
    }
}
