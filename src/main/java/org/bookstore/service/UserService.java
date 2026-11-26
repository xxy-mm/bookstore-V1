package org.bookstore.service;

import org.bookstore.dto.UserDTO;

public interface UserService {

    /**
     * register a new user
     *
     * @param username the username of user
     * @param password the raw password of the user
     * @param nickname the nickname of the user
     * @param email    the email of the user
     * @param phone    the phone of the user
     * @return the affected row of this action
     */
    int register(String username, String password, String nickname, String email, String phone);

    UserDTO login(String username, String password);
}
