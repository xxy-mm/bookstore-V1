package org.bookstore.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.bookstore.dao.BookDao;
import org.bookstore.dto.BookDTO;
import org.bookstore.entity.Book;
import org.bookstore.mapper.BookMapper;
import org.bookstore.util.MybatisUtil;

import java.util.List;

// handle DB logic
public class BookDaoImpl implements BookDao {

    @Override
    public Book findById(long id) {

        try (SqlSession sqlSession = MybatisUtil.openSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            return bookMapper.findById(id);
        }
    }

    @Override
    public List<BookDTO> findByPage(long limit, long offset, Long categoryId) {

        try (SqlSession sqlSession = MybatisUtil.openSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            return bookMapper.findAllByCategory(limit, offset, categoryId);
        }

    }

    @Override
    public long queryCountByCategoryId(Long categoryId) {

        try (SqlSession sqlSession = MybatisUtil.openSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            return bookMapper.queryCountByCategoryId(categoryId);
        }
    }

}
