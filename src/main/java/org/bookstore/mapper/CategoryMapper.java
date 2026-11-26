package org.bookstore.mapper;

import org.bookstore.entity.Category;

import java.util.List;

public interface CategoryMapper {

    Category selectById(Long id);

    List<Category> selectAll(Integer status);

    List<Category> selectChildren(Long parentId);
}
