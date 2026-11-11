package org.example.bookstore.service;


import org.example.bookstore.entity.Category;

import java.util.List;

/**
 * book category service
 */
public interface CategoryService {
    /**
     * find all categories
     * @return {@link List<Category>} all categories, or empty list if none exist
     */
    List<Category> findAll();

    /**
     * find all enabled categories
     * @return {@link List<Category>} all enabled categories, or empty list if none exist
     */
    List<Category> findAllEnabled();

    /**
     * check weather the category is enabled or not
     * @param categoryId the id of the category
     * @return true if enabled, false if not.
     */
    boolean isEnabled(long categoryId);
}
