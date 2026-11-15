package org.bookstore.service.impl;

import org.bookstore.dao.BookDao;
import org.bookstore.dto.BookListDTO;
import org.bookstore.dto.PageResult;
import org.bookstore.entity.Book;
import org.bookstore.exception.BookNotFoundException;
import org.bookstore.service.BookService;

// handle business logic
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

        return bookDao.findById(id).orElseThrow(() -> new BookNotFoundException(id));

    }

    @Override
    public PageResult<BookListDTO> findByPage(int pageSize, int pageNum, long categoryId) {

        return bookDao.findPage(pageSize, pageNum, categoryId);
    }
}
