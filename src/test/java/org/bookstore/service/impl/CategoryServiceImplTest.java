package org.bookstore.service.impl;

import org.bookstore.dao.impl.CategoryDaoImpl;
import org.bookstore.entity.Category;
import org.bookstore.service.CategoryService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceImplTest {

    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryDaoImpl());

    @Test
    void findAll() {

        List<Category> categories = categoryService.findAll();
        assertEquals(10, categories.size());

    }

    @Test
    void findAllEnabled() {

        List<Category> categories = categoryService.findAllEnabled();
        assertEquals(9, categories.size());
    }

    @Test
    void isEnabled() {

        boolean categoryWithId1Enabled = categoryService.isEnabled(1);
        assertFalse(categoryWithId1Enabled);

        boolean categoryWithId2Enabled = categoryService.isEnabled(2);
        assertTrue(categoryWithId2Enabled);
    }
}