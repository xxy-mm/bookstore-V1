package org.example.bookstore.dao;

import org.example.bookstore.entity.User;

import java.util.Optional;

public interface UserDao {

    /**
     * Check whether a user exists with the specified username
     *
     * @param username the username to check
     * @return true if the username already exists, otherwise false
     */
    boolean existsByUsername(String username);

    /**
     * insert a new user
     *
     * @param user the user to insert
     * @return the generated user id
     */
    long insert(User user);

    /**
     * find user by username
     *
     * @param username the username
     * @return an optional of User, empty if not found
     */
    Optional<User> findByUsername(String username);
}
