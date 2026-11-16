package org.bookstore.dao.impl;

import org.bookstore.dao.CategoryDao;
import org.bookstore.entity.Category;
import org.junit.jupiter.api.Test;

import java.util.List;

class CategoryDaoImplTest {

    public CategoryDao categoryDao = new CategoryDaoImpl();

    @Test
    void findById() {

        Category category = this.categoryDao.findById(1);
        assert category.getId() == 1;
    }

    @Test
    void findAll() {

        List<Category> all = this.categoryDao.findAll(null);
        assert !all.isEmpty();

        List<Category> disabledCategories = this.categoryDao.findAll(0);
        List<Category> enabledCategories = this.categoryDao.findAll(1);

        assert all.size() == (disabledCategories.size() + enabledCategories.size());
    }
}