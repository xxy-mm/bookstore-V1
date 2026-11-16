package org.bookstore.service;

import org.bookstore.dto.BookDTO;
import org.bookstore.dto.PageResult;
import org.bookstore.entity.Book;

public interface BookService {

    /**
     * find book by its id
     *
     * @param id the id of the book
     * @return the book
     */
    Book findById(long id);

    PageResult<BookDTO> findByPage(long pageSize, long pageNum, Long categoryId);
}
