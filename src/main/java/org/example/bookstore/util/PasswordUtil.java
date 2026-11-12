package org.example.bookstore.util;

import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;

public final class PasswordUtil {

    private static final int PASSWORD_MAX_BYTES = 72;
    private static final int LOG_ROUNDS = 10;
    private static final int PASSWORD_MIN_LENGTH = 8;

    private PasswordUtil() {

    }

    public static String hashPassword(String password) {

        password = normalizePassword(password);
        validatePassword(password);

        return BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS));
    }

    public static boolean verifyPassword(String password, String hashedPassword) {

        password = normalizePassword(password);

        if (password == null || hashedPassword == null) {
            return false;
        }

        if (getPasswordBytes(password) > PASSWORD_MAX_BYTES) {
            return false;
        }
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static boolean passwordsMatch(String password, String confirmPassword) {

        password = normalizePassword(password);
        confirmPassword = normalizePassword(confirmPassword);

        return password != null && !password.isEmpty() && password.equals(confirmPassword);
    }

    private static void validatePassword(String password) {

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password must not be empty");
        }

        if (password.codePointCount(0, password.length()) < PASSWORD_MIN_LENGTH) {
            throw new IllegalArgumentException("Password must contain at least " + PASSWORD_MIN_LENGTH + "characters.");
        }

        if (getPasswordBytes(password) > PASSWORD_MAX_BYTES) {
            throw new IllegalArgumentException("Password must not exceed 72 bytes");
        }

    }

    private static int getPasswordBytes(String password) {

        return password.getBytes(StandardCharsets.UTF_8).length;
    }

    private static String normalizePassword(String password) {

        if (password == null) {
            return null;
        }
        return password.trim();
    }
}
