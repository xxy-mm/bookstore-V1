package org.example.bookstore.controller;

import org.example.bookstore.dao.impl.UserDaoImpl;
import org.example.bookstore.dto.UserDTO;
import org.example.bookstore.exception.InvalidCredentialsException;
import org.example.bookstore.service.UserService;
import org.example.bookstore.service.impl.UserServiceImpl;

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

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {

    private static final String CSRF_SESSION_ATTRIBUTE = "csrfToken";
    private static final String CSRF_REQUEST_PARAMETER = "csrfToken";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final UserService userService = new UserServiceImpl(new UserDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute(CSRF_SESSION_ATTRIBUTE) == null) {
            session.setAttribute(CSRF_SESSION_ATTRIBUTE, generateCsrfToken());
        }

        req.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        if (!isValidCsrfToken(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            req.setAttribute("errorMsg", "Username or password is incorrect.");
            req.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
            return;
        }

        try {
            UserDTO user = userService.login(username, password);
            // prevent fixed session attack
            req.changeSessionId();

            HttpSession session = req.getSession(false);
            session.setAttribute("user", user);
            session.setAttribute(CSRF_SESSION_ATTRIBUTE, generateCsrfToken());

            resp.sendRedirect(req.getContextPath() + "/index.jsp");

        } catch (InvalidCredentialsException e) {
            req.setAttribute("errorMsg", "Username or password is incorrect.");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
        }
    }

    private String generateCsrfToken() {

        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private boolean isValidCsrfToken(HttpServletRequest req) {

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
