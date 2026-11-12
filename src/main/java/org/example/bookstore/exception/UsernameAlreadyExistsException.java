package org.example.bookstore.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyExistsException(String username) {

        super("Username already exists: " + username);
    }
}
