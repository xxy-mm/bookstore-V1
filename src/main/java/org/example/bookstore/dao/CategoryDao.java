package org.example.bookstore.dao;

import org.example.bookstore.entity.Category;
import org.example.bookstore.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDao {


    private Category mapRow(ResultSet rs) throws SQLException {

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

    public List<Category> findAll() {
        String sql = "select id, parent_id, name, sort, status, create_time, update_time from categories " +
                "order by sort";
        ArrayList<Category> categories = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Category category = this.mapRow(resultSet);
                categories.add(category);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    public Optional<Category> findById(long id) {
        String sql = "select id, parent_id, name, sort, status, create_time, update_time from categories where id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
        ) {

            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
