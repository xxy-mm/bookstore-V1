package org.example.bookstore.exception;

public class DuplicateKeyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateKeyException(String message, Throwable cause) {

        super(message, cause);
    }
}
