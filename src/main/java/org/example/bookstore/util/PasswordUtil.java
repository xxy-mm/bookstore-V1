package org.example.bookstore.util;

import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;

public final class PasswordUtil {

    private static final int PASSWORD_MAX_BYTES = 72;
    private static final int LOG_ROUNDS = 10;

    private PasswordUtil(){

        throw new IllegalStateException("PasswordUtil is a util class");
    }

    public static String hashPassword(String password) {

        validatePassword(password);

        return BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS));
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null){
            return false;
        }
        if (getPasswordBytes(plainPassword) > PASSWORD_MAX_BYTES) {
            return false;
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    private static void validatePassword(String password) {

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password mustn't be empty");
        }

        if (getPasswordBytes(password) > PASSWORD_MAX_BYTES) {
            throw new IllegalArgumentException("Password mustn't exceed 72 bytes");
        }

    }

    private static int getPasswordBytes(String password) {

        return password.getBytes(StandardCharsets.UTF_8).length;
    }
}
