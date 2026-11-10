package org.example.bookstore.dao;

import org.example.bookstore.dao.impl.BookDaoImpl;
import org.example.bookstore.entity.Book;

import java.util.Optional;

public class BookDaoTest {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();


        Optional<Book> book = bookDao.findById(1);
        System.out.println(book);
    }
}
