package org.example.bookstore.exception;

public class InvalidRequestParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidRequestParameterException(String message) {

        super(message);
    }

    public InvalidRequestParameterException(String message, Throwable cause) {

        super(message, cause);
    }

}
