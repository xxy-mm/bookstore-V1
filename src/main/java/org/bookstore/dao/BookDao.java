package org.bookstore.dao;

import org.bookstore.dto.BookDTO;
import org.bookstore.entity.Book;

import java.util.List;

public interface BookDao {

    /**
     * find a book by its id
     *
     * @param id the book's id
     * @return the book if found otherwise null
     */
    Book findById(long id);

    List<BookDTO> findByPage(long limit, long offset, Long categoryId);

    /**
     * get the total count of books in the specified category id
     * if category id is null, get the total count of all books, regardless of their category.
     *
     * @param categoryId the book's category id or null
     * @return the total count
     */
    long queryCountByCategoryId(Long categoryId);
    
}
