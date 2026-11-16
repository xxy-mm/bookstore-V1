package org.bookstore.service.impl;

import org.bookstore.dao.impl.UserDaoImpl;
import org.bookstore.dto.UserDTO;
import org.bookstore.exception.InvalidCredentialsException;
import org.bookstore.exception.UsernameAlreadyExistsException;
import org.bookstore.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceImplTest {

    private final UserService userService = new UserServiceImpl(new UserDaoImpl());

    @Test
    void register() {

        String username = "xyz" + System.currentTimeMillis();
        String password = "12345678";
        String nickname = "xxyyzz";
        String email = "xyz@gmail.com";
        String phone = "012345";

        long affectedRow = userService.register(username, password, nickname, email, phone);
        assertEquals(1, affectedRow);

    }

    @Test
    void registerFailWithDuplicateUsername() {

        String username = "xxy";
        String password = "12345678";
        String nickname = "xxyyzz";
        String email = "xyz@gmail.com";
        String phone = "012345";

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.register(username, password, nickname,
                email, phone));
    }

    @Test
    void login() {

        UserDTO xxy = userService.login("xxy", "12345678");
        assertEquals("xxy", xxy.getUsername());
    }

    @Test
    void loginFailed() {
        // password does not match
        assertThrows(InvalidCredentialsException.class, () -> userService.login("xxy", "123"));
        // user is not enabled
        assertThrows(InvalidCredentialsException.class, () -> userService.login("asdf", "11111111"));
        // username not exits
        assertThrows(InvalidCredentialsException.class, () -> userService.login("xxy99", "123"));
    }
}