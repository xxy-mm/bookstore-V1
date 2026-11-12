package org.example.bookstore.service.impl;

import org.example.bookstore.dao.UserDao;
import org.example.bookstore.dto.UserDTO;
import org.example.bookstore.entity.User;
import org.example.bookstore.exception.DuplicateKeyException;
import org.example.bookstore.exception.InvalidCredentialsException;
import org.example.bookstore.exception.UsernameAlreadyExistsException;
import org.example.bookstore.service.UserService;
import org.example.bookstore.util.PasswordUtil;

public class UserServiceImpl implements UserService {

    public static final int USER_STATUS_ACTIVE = 1;
    private static final int DEFAULT_ROLE = 1;
    private static final int DEFAULT_STATUS = 1;
    private static final int USERNAME_MAX_LENGTH = 50;
    private static final int OPTIONAL_MAX_LENGTH = 50;
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {

        this.userDao = userDao;
    }

    @Override
    public long register(String username, String password, String nickname, String email, String phone) {

        String usernameValidated = normalizeAndValidateUsername(username);
        String passwordHashed = PasswordUtil.hashPassword(password);
        String nicknameValidated = normalizeAndValidateOptional(nickname, OPTIONAL_MAX_LENGTH, "nickname");
        String emailValidated = normalizeAndValidateOptional(email, OPTIONAL_MAX_LENGTH, "email");
        String phoneValidated = normalizeAndValidateOptional(phone, OPTIONAL_MAX_LENGTH, "phone");

        User user = new User();
        user.setUsername(usernameValidated);
        user.setPassword(passwordHashed);
        user.setNickname(nicknameValidated);
        user.setEmail(emailValidated);
        user.setPhone(phoneValidated);
        user.setRole(DEFAULT_ROLE);
        user.setStatus(DEFAULT_STATUS);

        boolean exists = userDao.existsByUsername(user.getUsername());
        if (exists) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
        try {
            return this.userDao.insert(user);
        } catch (DuplicateKeyException e) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
    }

    @Override
    public UserDTO login(String username, String password) {

        User user =
                this.userDao.findByUsername(username).orElseThrow(() -> new InvalidCredentialsException("User " + 
                        "not found."));
        if (user.getStatus() != USER_STATUS_ACTIVE) {
            throw new InvalidCredentialsException("The user is disabled.");
        }
        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            throw new InvalidCredentialsException("Password does not match");
        }

        UserDTO userDto = new UserDTO();
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setNickname(user.getNickname());
        userDto.setRole(user.getRole());
        return userDto;
    }

    private String normalizeAndValidateUsername(String username) {

        if (username == null) {
            throw new IllegalArgumentException("Username must not be null");
        }
        username = username.trim();
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }
        if (username.length() > USERNAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Username must not exceeds " + USERNAME_MAX_LENGTH + "characters");
        }
        return username;
    }

    private String normalizeAndValidateOptional(String value, int maxLength, String fieldName) {

        if (value == null) {
            return null;
        }
        value = value.trim();
        if (value.isEmpty()) {
            return null;
        }
        if (value.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " must not exceeds " + maxLength + "characters.");
        }
        return value;
    }
}
