package org.bookstore.service.impl;

import org.bookstore.dao.BookDao;
import org.bookstore.dto.BookDTO;
import org.bookstore.dto.PageResult;
import org.bookstore.entity.Book;
import org.bookstore.service.BookService;
import org.bookstore.util.PageUtil;

import java.util.List;

// handle business logic
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {

        this.bookDao = bookDao;
    }

    @Override
    public Book findById(long id) {

        return bookDao.findById(id);

    }

    @Override
    public PageResult<BookDTO> findByPage(long pageSize, long pageNum, Long categoryId) {

        long count = bookDao.queryCountByCategoryId(categoryId);
        PageResult<BookDTO> pageResult = PageUtil.getPageResult(pageSize, pageNum, count);
        long offset = pageResult.getPageSize() * (pageResult.getPageNum() - 1);
        List<BookDTO> list = bookDao.findByPage(pageSize, offset, categoryId);
        pageResult.setList(list);
        return pageResult;
    }

}
