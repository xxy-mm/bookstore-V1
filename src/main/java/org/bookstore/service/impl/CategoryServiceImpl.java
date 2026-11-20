package org.bookstore.service.impl;

import org.bookstore.dao.CategoryDao;
import org.bookstore.entity.Category;
import org.bookstore.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private static final int CATEGORY_STATUS_ENABLED = 1;
    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {

        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> findAll() {

        return this.categoryDao.findAll(null);
    }

    @Override
    public List<Category> findAllEnabled() {

        return this.categoryDao.findAll(CATEGORY_STATUS_ENABLED);
    }

    @Override
    public boolean isEnabled(long categoryId) {

        Category category = this.categoryDao.findById(categoryId);

        return category != null && category.getStatus() == CATEGORY_STATUS_ENABLED;
    }

    @Override
    public List<Category> selectChildren(long parentId) {
        List<Category> categories = this.categoryDao.selectChildren(parentId);
        return categories;
    }
}
