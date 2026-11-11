package org.example.bookstore.service;

import org.example.bookstore.dto.BookListDTO;
import org.example.bookstore.dto.PageResult;
import org.example.bookstore.entity.Book;

public interface BookService {

    /**
     * find books by page
     *
     * @param pageSize the page size
     * @param pageNum  the page number
     * @return {@link PageResult} contains the books and related page information
     */
    PageResult<BookListDTO> findByPage(int pageSize, int pageNum);

    /**
     * find book by its id
     *
     * @param id the id of the book
     * @return the book
     * @throws org.example.bookstore.exception.BookNotFoundException if the book doesn't exist
     */
    Book findById(long id);

    PageResult<BookListDTO> findByPage(int pageSize, int pageNum, long categoryId);
}
