package org.example.bookstore.service.impl;

import org.example.bookstore.dao.BookDao;
import org.example.bookstore.dto.BookListDTO;
import org.example.bookstore.dto.PageResult;
import org.example.bookstore.entity.Book;
import org.example.bookstore.exception.BookNotFoundException;
import org.example.bookstore.service.BookService;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public PageResult<BookListDTO> findByPage(int pageSize, int pageNum) {
        return bookDao.findPage(pageSize, pageNum);
    }

    @Override
    public Book findById(long id) {

        return bookDao.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

    }
}
