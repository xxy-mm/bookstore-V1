package org.bookstore.dao;

import org.bookstore.entity.Category;

import java.util.List;

public interface CategoryDao {

    /**
     * find the category specified by the id
     *
     * @param id the category's id
     * @return the category or null
     */
    Category findById(long id);

    /**
     * find all categories
     *
     * @param status the status of the category, 1 is enabled, 0 is disabled
     *               if null is provided, return all
     *               categories regardless of their status.
     * @return {@link List<Category>} all categories, or an empty list if none exist
     */
    List<Category> findAll(Integer status);

    List<Category> selectChildren(long parentId);
}
