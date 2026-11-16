package org.bookstore.dao.impl;

import org.bookstore.dao.BookDao;
import org.bookstore.dto.BookDTO;
import org.bookstore.entity.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookDaoImplTest {

    public final BookDao bookDao = new BookDaoImpl();

    @Test
    void findById() {

        Book book = this.bookDao.findById(1);
        assertEquals(1, book.getId());
    }

    @Test
    void findByPage() {

        List<BookDTO> books = this.bookDao.findByPage(5, 0, null);
        assertEquals(5, books.size());

        List<BookDTO> byPage = this.bookDao.findByPage(5, 0, 1L);
        assert byPage.isEmpty();

        List<BookDTO> byPage1 = this.bookDao.findByPage(5, 0, 6L);
        assert !byPage1.isEmpty();
    }

    @Test
    void queryCountByCategoryId() {

        long count = this.bookDao.queryCountByCategoryId(null);
        assertEquals(30, count);

        count = this.bookDao.queryCountByCategoryId(6L);
        assert count < 30;
    }
}