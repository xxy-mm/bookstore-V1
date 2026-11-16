package org.bookstore.dao.impl;

import org.bookstore.dao.UserDao;
import org.bookstore.entity.User;
import org.bookstore.exception.DuplicateKeyException;
import org.junit.jupiter.api.Test;

class UserDaoImplTest {

    public UserDao userDao = new UserDaoImpl();

    @Test
    void insert() {

        User user = new User();
        user.setUsername("xyz" + System.currentTimeMillis());
        user.setPassword("12345678");
        user.setNickname("xxyyzz");
        user.setEmail("xyz@gmail.com");
        user.setPhone("012345");
        user.setRole(1);
        user.setStatus(1);

        long affectedRow = userDao.insert(user);
        assert affectedRow == 1;
        assert user.getId() != null;
    }

    @Test
    void insertDuplicateUsername() {

        User user = new User();
        user.setUsername("xxy");
        user.setPassword("12345678");
        user.setNickname("xxyyzz");
        user.setEmail("xyz@gmail.com");
        user.setPhone("012345");
        user.setRole(1);
        user.setStatus(1);

        try {
            long affectedRows = userDao.insert(user);
            assert affectedRows == 0;
        } catch (RuntimeException e) {
            assert e instanceof DuplicateKeyException;
        }

    }

    @Test
    void findByUsername() {

        User xxy = userDao.findByUsername("xxy");
        assert xxy != null;

        xxy = userDao.findByUsername("non exist name");
        assert xxy == null;
    }
}