package org.bookstore.controller;

import org.bookstore.dao.impl.UserDaoImpl;
import org.bookstore.exception.UsernameAlreadyExistsException;
import org.bookstore.service.UserService;
import org.bookstore.service.impl.UserServiceImpl;
import org.bookstore.util.CsrfTokenUtil;
import org.bookstore.util.PasswordUtil;
import org.bookstore.util.RequestParamUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/register")
public class UserRegisterServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl(new UserDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CsrfTokenUtil.addCsrfTokenToSession(req);
        req.getRequestDispatcher("/WEB-INF/view/user/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        if (!CsrfTokenUtil.isValidCsrfToken(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String username = RequestParamUtil.trimToNull(req.getParameter("username"));
        String password = RequestParamUtil.trimToNull(req.getParameter("password"));
        String confirmPassword = RequestParamUtil.trimToNull(req.getParameter("confirmPassword"));
        String nickname = RequestParamUtil.trimToNull(req.getParameter("nickname"));
        String email = RequestParamUtil.trimToNull(req.getParameter("email"));
        String phone = RequestParamUtil.trimToNull(req.getParameter("phone"));

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
