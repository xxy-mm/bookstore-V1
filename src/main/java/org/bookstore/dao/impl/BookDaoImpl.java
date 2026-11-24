package org.bookstore.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.bookstore.dao.BookDao;
import org.bookstore.dto.BookDTO;
import org.bookstore.entity.Category;
import org.bookstore.mapper.BookMapper;
import org.bookstore.mapper.CategoryMapper;
import org.bookstore.util.MybatisUtil;

import java.util.ArrayList;
import java.util.List;

// handle DB logic
public class BookDaoImpl implements BookDao {

    @Override
    public BookDTO findById(long id) {

        try (SqlSession sqlSession = MybatisUtil.openSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            return bookMapper.findById(id);
        }
    }

    @Override
    public List<BookDTO> findByPage(long limit, long offset, Long categoryId) {

        try (SqlSession sqlSession = MybatisUtil.openSession()) {
            List<Long> categoryIds = new ArrayList<>();
            if (categoryId != null) {
                CategoryMapper categoryMapper = sqlSession.getMapper(CategoryMapper.class);
                findChildCategories(categoryId, categoryIds, categoryMapper);
            }

            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            return bookMapper.findAllByCategory(limit, offset, categoryIds);
        }

    }

    private void findChildCategories(long categoryId, List<Long> leafIds, CategoryMapper mapper) {

        List<Category> categories = mapper.selectChildren(categoryId);
        if (categories.isEmpty()) {
            leafIds.add(categoryId);
        } else {
            categories.forEach(category -> findChildCategories(category.getId(), leafIds, mapper));
        }
    }

    @Override
    public long queryCountByCategoryId(Long categoryId) {

        try (SqlSession sqlSession = MybatisUtil.openSession()) {
            List<Long> categoryIds = new ArrayList<>();
            if (categoryId != null) {
                CategoryMapper categoryMapper = sqlSession.getMapper(CategoryMapper.class);
                findChildCategories(categoryId, categoryIds, categoryMapper);
            }
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            return bookMapper.queryCountByCategoryId(categoryIds);
        }
    }

}
