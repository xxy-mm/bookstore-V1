package org.bookstore.service.impl;

import org.bookstore.dao.BookDao;
import org.bookstore.dto.BookDTO;
import org.bookstore.dto.BookPageQuery;
import org.bookstore.dto.PageResult;
import org.bookstore.service.BookService;

import java.util.List;

// handle business logic
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {

        this.bookDao = bookDao;
    }

    @Override
    public BookDTO findById(long id) {

        return bookDao.findById(id);

    }

    @Override
    public PageResult<BookDTO> findByPage(BookPageQuery query) {

        long count = bookDao.queryCountByCategoryId(query.getCategoryId());
        PageResult<BookDTO> pageResult =
                PageResult.create(query, count);

        List<BookDTO> list = bookDao.findByPage(pageResult.getPageSize(),
                pageResult.getOffset(), query.getCategoryId());
        pageResult.setList(list);
        return pageResult;
    }

}
