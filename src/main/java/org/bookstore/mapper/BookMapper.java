package org.bookstore.mapper;

import org.bookstore.entity.Book;

import java.util.List;

public interface BookMapper {

    Book findById(long id);

    List<Book> findAll();

    List<Book> findAllByCategory(Long categoryId);
}
