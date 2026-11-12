package org.example.bookstore.dao.impl;

import org.example.bookstore.dao.UserDao;
import org.example.bookstore.entity.User;
import org.example.bookstore.exception.DuplicateKeyException;
import org.example.bookstore.util.DBUtil;

import java.sql.*;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean existsByUsername(String username) {

        String sql = "SELECT 1 FROM users WHERE username = ? LIMIT 1";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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

        String sql =
                "INSERT INTO users (username, password, nickname, email, phone, role, status) " + "VALUES (?,?," +
                        "?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
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
            if (e.getErrorCode() == 1062) {
                throw new DuplicateKeyException("Duplicate key when inserting user", e);
            }
            throw new RuntimeException("Failed to insert user", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {

        String sql =
                "SELECT id, username, password, nickname, email, phone, role, status, create_time, update_time " +
                        "FROM " + "users WHERE username=? limit 1";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement stmt =
                connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find by username: " + username, e);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {

        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setNickname(rs.getString("nickname"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getInt("role"));
        user.setStatus(rs.getInt("status"));
        user.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        user.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());

        return user;
    }
}
