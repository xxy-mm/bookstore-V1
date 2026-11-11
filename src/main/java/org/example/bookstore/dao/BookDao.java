package org.example.bookstore.dao;

import org.example.bookstore.dto.BookListDTO;
import org.example.bookstore.dto.PageResult;
import org.example.bookstore.entity.Book;

import java.util.Optional;

public interface BookDao {
    /**
     * find the book specified by the provided id
     *
     * @param id the book's id
     * @return an optional contains the book if found otherwise empty
     */
    Optional<Book> findById(long id);

    /**
     * find books by page
     *
     * @param pageSize the number of records expected to get
     * @param pageNum  the page number
     * @return a PageResult object contains the records and page related information
     */
    PageResult<BookListDTO> findPage(int pageSize, int pageNum);

    PageResult<BookListDTO> findPage(int pageSize, int pageNum, long categoryId);
}
