package org.bookstore.service.impl;

import org.bookstore.dao.impl.BookDaoImpl;
import org.bookstore.dto.BookDTO;
import org.bookstore.dto.PageResult;
import org.bookstore.entity.Book;
import org.bookstore.service.BookService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceImplTest {

    private final BookService bookService = new BookServiceImpl(new BookDaoImpl());

    @Test
    void findById() {

        Book book = bookService.findById(1);
        assertNotNull(book);

        book = bookService.findById(99999);
        assertNull(book);
    }

    @Test
    void findByPage() {

        PageResult<BookDTO> pageResult = bookService.findByPage(10, 1, null);
        assertEquals(10, pageResult.getList().size());
        assertEquals(1, pageResult.getPageNum());
        assertEquals(10, pageResult.getPageSize());
        assertEquals(3, pageResult.getTotalPages());
        assertEquals(30, pageResult.getTotalRecords());
    }
}