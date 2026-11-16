package org.bookstore.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.bookstore.dao.CategoryDao;
import org.bookstore.entity.Category;
import org.bookstore.mapper.CategoryMapper;
import org.bookstore.util.MybatisUtil;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public Category findById(long id) {

        try (SqlSession sqlSession = MybatisUtil.openSession()) {
            CategoryMapper mapper = sqlSession.getMapper(CategoryMapper.class);
            return mapper.findById(id);
        }
    }

    @Override
    public List<Category> findAll(Integer status) {

        try (SqlSession sqlSession = MybatisUtil.openSession()) {
            CategoryMapper mapper = sqlSession.getMapper(CategoryMapper.class);
            return mapper.findAll(status);
        }
    }

}
