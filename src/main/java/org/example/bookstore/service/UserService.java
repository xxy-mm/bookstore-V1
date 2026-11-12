package org.example.bookstore.service;

import org.example.bookstore.dto.UserDTO;

public interface UserService {

    /**
     * register a new user
     *
     * @param username the username of user
     * @param password the raw password of the user
     * @param nickname the nickname of the user
     * @param email    the email of the user
     * @param phone    the phone of the user
     * @return the generated id of the user
     */
    long register(String username, String password, String nickname, String email, String phone);

    UserDTO login(String username, String password);
}
