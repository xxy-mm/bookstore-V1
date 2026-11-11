package org.example.bookstore.service.impl;

import org.example.bookstore.dao.CategoryDao;
import org.example.bookstore.entity.Category;
import org.example.bookstore.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> findAll() {
        return this.categoryDao.findAll();
    }

    @Override
    public List<Category> findAllEnabled() {

        return this.categoryDao.findAllEnabled();
    }

    @Override
    public boolean isEnabled(long categoryId) {

        return this.categoryDao.isEnabled(categoryId);
    }
}
