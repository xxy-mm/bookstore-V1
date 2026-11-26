package org.bookstore.service.impl;

import org.bookstore.dto.BookDTO;
import org.bookstore.dto.BookPageQuery;
import org.bookstore.dto.PageResult;
import org.bookstore.mapper.BookMapper;
import org.bookstore.service.BookService;
import org.bookstore.util.MybatisUtil;

import java.util.List;

// handle business logic
public class BookServiceImpl implements BookService {

    @Override
    public BookDTO selectById(Long id) {
        return MybatisUtil.execute(BookMapper.class, bookMapper -> {
            return bookMapper.selectById(id);
        });
    }

    @Override
    public PageResult<BookDTO> selectByPage(BookPageQuery query) {

        return MybatisUtil.execute(BookMapper.class, bookMapper -> {
            long total = bookMapper.selectCountByCategory(query.getCategoryId());
            PageResult<BookDTO> pageResult = PageResult.create(query, total);
            List<BookDTO> bookDTOS = bookMapper.selectPageByCategory(pageResult.getPageSize(), pageResult.getOffset(), pageResult.getTotalRecords());
            pageResult.setList(bookDTOS);
            return pageResult;
        });
    }

}
