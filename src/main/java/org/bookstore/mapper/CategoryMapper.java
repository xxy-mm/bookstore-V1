package org.bookstore.mapper;

import org.bookstore.entity.Category;

import java.util.List;

public interface CategoryMapper {

    Category findById(long id);
    
    List<Category> findAll(Integer status);
}
