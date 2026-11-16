package org.bookstore.dao;

import org.bookstore.entity.User;

public interface UserDao {

    /**
     * insert a new user
     *
     * @param user the user to insert
     * @return the number of affected rows
     */
    long insert(User user);

    /**
     * find user by username
     *
     * @param username the username
     * @return an optional of User, empty if not found
     */
    User findByUsername(String username);
}
