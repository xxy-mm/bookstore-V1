package org.example.bookstore.exception;

public class BookNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    public BookNotFoundException(long id) {
        super("Book not found, id: " + id);
    }
}
