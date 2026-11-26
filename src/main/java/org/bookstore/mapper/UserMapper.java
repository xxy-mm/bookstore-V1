package org.bookstore.mapper;

import org.bookstore.entity.User;

public interface UserMapper {

    User findByUsername(String username);


    int insertUser(User user);
}
