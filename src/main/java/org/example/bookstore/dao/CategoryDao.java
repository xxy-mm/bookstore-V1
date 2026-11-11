package org.example.bookstore.dao;

import org.example.bookstore.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    /**
     * find the category specified by the id
     *
     * @param id the category's id
     * @return an Optional contains the category if found otherwise empty
     */
    Optional<Category> findById(long id);

    /**
     * find all categories
     * @return {@link List<Category>} all categories, or an empty list if none exist
     */
    List<Category> findAll();

    /**
     * find all enabled categories
     * @return {@link List<Category>} all enabled categories, or an empty list if none exist
     */
    List<Category> findAllEnabled();

    /**
     * check weather the category is enabled or not
     * @param categoryId the id of the category
     * @return true if enabled, otherwise false
     */
    boolean isEnabled(long categoryId);
}
