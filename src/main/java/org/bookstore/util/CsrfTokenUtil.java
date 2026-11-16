package org.bookstore.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class CsrfTokenUtil {

    public static final String CSRF_SESSION_ATTRIBUTE = "csrfToken";
    public static final String CSRF_REQUEST_PARAMETER = "csrfToken";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * add csrf token into session if the session does not have
     *
     * @param req the incoming http servlet request
     */
    public static void addCsrfTokenToSession(HttpServletRequest req) {

        HttpSession session = req.getSession();
        if (session.getAttribute(CSRF_SESSION_ATTRIBUTE) == null) {
            session.setAttribute(CSRF_SESSION_ATTRIBUTE, generateCsrfToken());
        }
    }

    /**
     * generate a csrf token
     *
     * @return the generated csrf token
     */
    public static String generateCsrfToken() {

        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * check whether the csrf token is valid
     *
     * @param req the incoming http servlet request
     * @return true if valid, otherwise false
     */
    public static boolean isValidCsrfToken(HttpServletRequest req) {

        HttpSession session = req.getSession(false);
        if (session == null) {
            return false;
        }

        String tokenFromSession = (String) session.getAttribute(CSRF_SESSION_ATTRIBUTE);
        String tokenFromRequest = req.getParameter(CSRF_REQUEST_PARAMETER);

        if (tokenFromSession == null || tokenFromRequest == null) {
            return false;
        }

        return MessageDigest.isEqual(tokenFromRequest.getBytes(StandardCharsets.UTF_8),
                tokenFromSession.getBytes(StandardCharsets.UTF_8));
    }
}
