package org.example.bookstore.dao;

import org.example.bookstore.entity.Category;

import java.util.Optional;

public interface CategoryDao {

    /**
     * find the category specified by the id
     *
     * @param id the category's id
     * @return an Optional contains the category if found otherwise empty
     */
    Optional<Category> findById(long id);
}
