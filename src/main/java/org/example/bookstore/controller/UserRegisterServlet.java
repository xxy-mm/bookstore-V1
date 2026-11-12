package org.example.bookstore.controller;

import org.example.bookstore.dao.impl.UserDaoImpl;
import org.example.bookstore.exception.UsernameAlreadyExistsException;
import org.example.bookstore.service.UserService;
import org.example.bookstore.service.impl.UserServiceImpl;
import org.example.bookstore.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

@WebServlet("/user/register")
public class UserRegisterServlet extends HttpServlet {

    private static final String CSRF_TOKEN_ATTRIBUTE = "csrfToken";
    private static final String CSRF_TOKEN_PARAMETER = "csrfToken";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final UserService userService = new UserServiceImpl(new UserDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // When user visit the page, we create a session and csrfToken.
        HttpSession session = req.getSession();
        if (session.getAttribute(CSRF_TOKEN_ATTRIBUTE) == null) {
            session.setAttribute(CSRF_TOKEN_ATTRIBUTE, generateCsrfToken());
        }
        req.getRequestDispatcher("/WEB-INF/view/user/register.jsp").forward(req, resp);
    }

    private String generateCsrfToken() {

        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        // check csrf token
        if (!isValidCsrfToken(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String nickname = req.getParameter("nickname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        try {
            if (!PasswordUtil.passwordsMatch(password, confirmPassword)) {
                throw new IllegalArgumentException("Password does not match");
            }

            userService.register(username, password, nickname, email, phone);
            resp.sendRedirect(resp.encodeRedirectURL("/login.jsp"));
        } catch (UsernameAlreadyExistsException | IllegalArgumentException e) {
            forwardWithError(req, resp, e.getMessage(), username, nickname, email, phone);
        }
    }

    private boolean isValidCsrfToken(HttpServletRequest req) {

        HttpSession session = req.getSession();
        if (session == null) {
            return false;
        }

        String csrfTokenFromSession = (String) session.getAttribute(CSRF_TOKEN_ATTRIBUTE);
        String csrfTokenFromReqParam = req.getParameter(CSRF_TOKEN_PARAMETER);

        if (csrfTokenFromSession == null || csrfTokenFromReqParam == null) {
            return false;
        }

        return MessageDigest.isEqual(csrfTokenFromSession.getBytes(StandardCharsets.UTF_8),
                csrfTokenFromReqParam.getBytes(StandardCharsets.UTF_8));

    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String errorMsg, String username,
                                  String nickname, String email, String phone) throws ServletException, IOException {

        req.setAttribute("errorMsg", errorMsg);
        req.setAttribute("username", username);
        req.setAttribute("nickname", nickname);
        req.setAttribute("email", email);
        req.setAttribute("phone", phone);

        req.getRequestDispatcher("/WEB-INF/view/user/register.jsp").forward(req, resp);
    }
}
