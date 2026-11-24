package org.bookstore.service;

import org.bookstore.dto.BookDTO;
import org.bookstore.dto.BookPageQuery;
import org.bookstore.dto.PageResult;

public interface BookService {

    /**
     * find book by its id
     *
     * @param id the id of the book
     * @return the book
     */
    BookDTO findById(long id);

    PageResult<BookDTO> findByPage(BookPageQuery query);

}
