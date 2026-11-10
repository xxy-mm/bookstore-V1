package org.example.bookstore.dao.impl;

import org.example.bookstore.dao.BookDao;
import org.example.bookstore.dto.BookListDTO;
import org.example.bookstore.dto.PageResult;
import org.example.bookstore.entity.Book;
import org.example.bookstore.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {

    private static List<BookListDTO> findBooksByPage(int pageSize, int pageNum) {
        long offset = (long) pageSize * (pageNum - 1);


        String sql = "select b.id, b.title, b.author, b.cover, b.price, c.name as category_name from books b join categories c on b.category_id = c.id order by b.id limit ? offset ?";
        List<BookListDTO> dtoList = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)

        ) {
            stmt.setInt(1, pageSize);
            stmt.setLong(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BookListDTO dto = new BookListDTO();
                    dto.setId(rs.getLong("id"));
                    dto.setTitle(rs.getString("title"));
                    dto.setAuthor(rs.getString("author"));
                    dto.setCover(rs.getString("cover"));
                    dto.setPrice(rs.getBigDecimal("price"));
                    dto.setCategoryName(rs.getString("category_name"));

                    dtoList.add(dto);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dtoList;
    }

    @Override
    public Optional<Book> findById(long id) {
        String sql = "SELECT id, category_id, title, author, publisher, isbn, cover, price, stock, description, status, create_time, update_time FROM books WHERE id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find book by id: " + id, e);
        }

    }

    @Override
    public PageResult<BookListDTO> findPage(int pageSize, int pageNum) {

        PageResult<BookListDTO> pageResult = new PageResult<>();
        String sql = "select count(*) from books";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            rs.next();

            long totalRecords = rs.getLong(1);
            int totalPages = totalRecords == 0 ? 0 : (int) ((totalRecords - 1) / pageSize + 1);
            int actualPageNum = totalPages == 0 ? 1 : Math.min(pageNum, totalPages);


            pageResult.setPageNum(actualPageNum);
            pageResult.setPageSize(pageSize);
            pageResult.setTotalPages(totalPages);
            pageResult.setTotalRecords(totalRecords);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<BookListDTO> books = findBooksByPage(pageResult.getPageSize(), pageResult.getPageNum());
        pageResult.setList(books);
        return pageResult;
    }

    private static Book mapRow(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setAuthor(rs.getString("author"));
        book.setCategoryId(rs.getLong("category_id"));
        book.setTitle(rs.getString("title"));
        book.setPublisher(rs.getString("publisher"));
        book.setIsbn(rs.getString("isbn"));
        book.setCover(rs.getString("cover"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setStock(rs.getInt("stock"));
        book.setDescription(rs.getString("description"));
        book.setStatus(rs.getInt("status"));
        book.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        book.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
        return book;

    }
}
