package org.bookstore.service.impl;

import org.bookstore.entity.Category;
import org.bookstore.mapper.CategoryMapper;
import org.bookstore.service.CategoryService;
import org.bookstore.util.MybatisUtil;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private static final Integer CATEGORY_STATUS_ENABLED = 1;

    @Override
    public List<Category> selectAll() {
        return MybatisUtil.execute(CategoryMapper.class, mapper -> {
            return mapper.selectAll(null);
        });
    }

    @Override
    public List<Category> selectAllEnabled() {
        return MybatisUtil.execute(CategoryMapper.class, mapper -> {
            return mapper.selectAll(CATEGORY_STATUS_ENABLED);
        });
    }


    @Override
    public boolean isEnabled(Long categoryId) {
        return MybatisUtil.execute(CategoryMapper.class, mapper -> {
            Category category = mapper.selectById(categoryId);
            return category != null && category.getStatus().equals(CATEGORY_STATUS_ENABLED);
        });

    }
}
