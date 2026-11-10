package org.example.bookstore.dao;

import org.example.bookstore.entity.Book;

import java.util.List;
import java.util.Optional;

public class BookDaoTest {
    public static void main(String[] args) {
        BookDao bookDao = new BookDao();

        List<Book> bookList = bookDao.findAll();
        System.out.println(bookList);

        Optional<Book> book = bookDao.findById(1);
        System.out.println(book);
    }
}
