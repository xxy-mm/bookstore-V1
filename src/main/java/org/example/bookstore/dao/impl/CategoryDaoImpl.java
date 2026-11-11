package org.example.bookstore.dao.impl;

import org.example.bookstore.dao.CategoryDao;
import org.example.bookstore.entity.Category;
import org.example.bookstore.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl implements CategoryDao {

    private static Category mapRow(ResultSet rs) throws SQLException {

        Category category = new Category();
        category.setId(rs.getLong("id"));
        category.setParentId(rs.getLong("parent_id"));
        category.setName(rs.getString("name"));
        category.setSort(rs.getInt("sort"));
        category.setStatus(rs.getInt("status"));
        category.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        category.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());

        return category;
    }

    @Override
    public Optional<Category> findById(long id) {

        String sql = "select id, parent_id, name, sort, status, create_time, update_time from categories where id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find category by id: " + id, e);
        }
    }

    @Override
    public List<Category> findAll() {

        String sql = "SELECT id, parent_id,name,sort,status, " +
                "create_time, update_time " +
                "FROM categories ORDER BY sort, id;";
        return getCategories(sql);
    }

    @Override
    public List<Category> findAllEnabled() {

        String sql = "SELECT id, parent_id,name,sort,status, " +
                "create_time, update_time " +
                "FROM categories WHERE status=1 ORDER BY sort, id;";
        return getCategories(sql);
    }

    @Override
    public boolean isEnabled(long categoryId) {

        String sql = "select status from categories where id = ?";

        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setLong(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt("status") == 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check status for id: " + categoryId, e);
        }

    }

    private List<Category> getCategories(String sql) {

        List<Category> categories = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Category category = mapRow(rs);
                categories.add(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all categories.", e);
        }

        return categories;
    }

}
