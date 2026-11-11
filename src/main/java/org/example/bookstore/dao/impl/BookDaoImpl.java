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

// handle DB logic
public class BookDaoImpl implements BookDao {

    private static List<BookListDTO> findBooksByPage(int pageSize, int pageNum) {

        String sql = "select b.id, b.title, b.author, b.cover, b.price, c.name as category_name from books b join categories c on b.category_id = c.id order by b.id limit ? offset ?";
        List<BookListDTO> dtoList = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            long offset = (long) pageSize * (pageNum - 1);
            stmt.setInt(1, pageSize);
            stmt.setLong(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dtoList.add(createBookListDTO(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dtoList;
    }

    private static List<BookListDTO> findBooksByPageAndCategory(int pageSize, int pageNum, long categoryId) {

        String sql = "select b.id, b.title, b.author, b.cover, b.price, c.name as category_name from books b join categories c on b.category_id = c.id where b.category_id = ? order by b.id limit ? offset ?";
        List<BookListDTO> dtoList = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            long offset = (long) pageSize * (pageNum - 1);
            stmt.setLong(1, categoryId);
            stmt.setInt(2, pageSize);
            stmt.setLong(3, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dtoList.add(createBookListDTO(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dtoList;
    }

    private static BookListDTO createBookListDTO(ResultSet rs) throws SQLException {

        BookListDTO dto = new BookListDTO();
        dto.setId(rs.getLong("id"));
        dto.setTitle(rs.getString("title"));
        dto.setAuthor(rs.getString("author"));
        dto.setCover(rs.getString("cover"));
        dto.setPrice(rs.getBigDecimal("price"));
        dto.setCategoryName(rs.getString("category_name"));
        return dto;
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

        String sql = "select count(*) from books";
        long count;
        try {
            count = getCount(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PageResult<BookListDTO> pageResult = fillPageInfo(pageSize, pageNum, count);
        List<BookListDTO> books = findBooksByPage(pageResult.getPageSize(), pageResult.getPageNum());
        pageResult.setList(books);
        return pageResult;

    }

    @Override
    public PageResult<BookListDTO> findPage(int pageSize, int pageNum, long categoryId) {

        String countSql = "select count(*) from books where category_id = ?";
        long count;
        try {
            count = getCount(countSql, categoryId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get count:", e);
        }
        PageResult<BookListDTO> pageResult = fillPageInfo(pageSize, pageNum, count);
        List<BookListDTO> bookListDTOS = findBooksByPageAndCategory(pageSize, pageNum, categoryId);
        pageResult.setList(bookListDTOS);
        return pageResult;
    }

    /**
     * get count
     *
     * @param countSql a sql to query count
     * @return the count
     * @throws SQLException if the query failed
     */
    private long getCount(String countSql, Object... args) throws SQLException {

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(countSql)
        ) {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {

                rs.next();
                return rs.getLong(1);
            }
        }
    }

    private <T> PageResult<T> fillPageInfo(int pageSize, int pageNum, long totalCount) {

        int totalPages = totalCount == 0 ? 0 : (int) ((totalCount - 1) / pageSize + 1);
        int actualPageNum = Math.min(Math.max(1, pageNum), totalPages);

        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotalRecords(totalCount);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalPages(totalPages);
        pageResult.setPageNum(actualPageNum);

        return pageResult;
    }
}
