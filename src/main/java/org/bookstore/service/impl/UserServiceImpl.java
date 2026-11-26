package org.bookstore.service.impl;

import org.apache.ibatis.exceptions.PersistenceException;
import org.bookstore.dto.UserDTO;
import org.bookstore.entity.User;
import org.bookstore.exception.InvalidCredentialsException;
import org.bookstore.exception.UsernameAlreadyExistsException;
import org.bookstore.mapper.UserMapper;
import org.bookstore.service.UserService;
import org.bookstore.util.MybatisUtil;
import org.bookstore.util.PasswordUtil;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    public static final int USER_STATUS_ACTIVE = 1;
    private static final int DEFAULT_ROLE = 1;
    private static final int DEFAULT_STATUS = 1;
    private static final int USERNAME_MAX_LENGTH = 50;
    private static final int OPTIONAL_MAX_LENGTH = 50;


    @Override
    public int register(String username, String password, String nickname, String email, String phone) {

        String usernameValidated = validateUsername(username);
        String passwordHashed = PasswordUtil.hashPassword(password);
        String nicknameValidated = validateOptional(nickname, "nickname");
        String emailValidated = validateOptional(email, "email");
        String phoneValidated = validateOptional(phone, "phone");

        User user = new User();
        user.setUsername(usernameValidated);
        user.setPassword(passwordHashed);
        user.setNickname(nicknameValidated);
        user.setEmail(emailValidated);
        user.setPhone(phoneValidated);
        user.setRole(DEFAULT_ROLE);
        user.setStatus(DEFAULT_STATUS);
        return MybatisUtil.execute(UserMapper.class, userMapper -> {
            // first check duplication, to check the username
            boolean exists = userMapper.findByUsername(user.getUsername()) != null;
            if (exists) {
                throw new UsernameAlreadyExistsException(user.getUsername());
            }
            try {
                // even we checked above,the username still maybe inserted just before the following code executes.
                // so we may still encounter a duplicate key exception.
                return userMapper.insertUser(user);
            } catch (PersistenceException e) {
                Throwable cause = e.getCause();
                if (cause instanceof SQLException && ((SQLException) cause).getErrorCode() == 1062) {
                    throw new UsernameAlreadyExistsException(user.getUsername());
                }
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    public UserDTO login(String username, String password) {
        return MybatisUtil.execute(UserMapper.class, userMapper -> {
            User user = userMapper.findByUsername(username);
            if (user == null || user.getStatus() != USER_STATUS_ACTIVE || !PasswordUtil.verifyPassword(password,
                    user.getPassword())) {
                throw new InvalidCredentialsException("Invalid username and password");
            }

            UserDTO userDto = new UserDTO();
            userDto.setUsername(user.getUsername());
            userDto.setId(user.getId());
            userDto.setNickname(user.getNickname());
            userDto.setRole(user.getRole());
            return userDto;
        });

    }

    private String validateUsername(String username) {

        if (username.length() > USERNAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Username must not exceeds " + USERNAME_MAX_LENGTH + "characters");
        }
        return username;
    }

    private String validateOptional(String value, String fieldName) {

        if (value != null && value.length() > OPTIONAL_MAX_LENGTH) {
            throw new IllegalArgumentException(fieldName + " must not exceeds " + OPTIONAL_MAX_LENGTH + "characters.");
        }
        return value;
    }
}
