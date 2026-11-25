package org.bookstore.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.bookstore.dao.impl.UserDaoImpl;
import org.bookstore.dto.UserDTO;
import org.bookstore.exception.InvalidCredentialsException;
import org.bookstore.service.UserService;
import org.bookstore.service.impl.UserServiceImpl;
import org.bookstore.util.CsrfTokenUtil;
import org.bookstore.util.RequestParamUtil;

import java.io.IOException;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl(new UserDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CsrfTokenUtil.addCsrfTokenToSession(req);

        req.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!CsrfTokenUtil.isValidCsrfToken(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String username = RequestParamUtil.trimToNull(req.getParameter("username"));
        String password = RequestParamUtil.trimToNull(req.getParameter("password"));

        if (username == null || password == null) {
            req.setAttribute("errorMsg", "Invalid username or password.");
            req.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
            return;
        }

        try {
            UserDTO user = userService.login(username, password);
            // prevent fixed session attack
            req.changeSessionId();

            HttpSession session = req.getSession(false);
            session.setAttribute("loginUser", user);
            session.setAttribute(CsrfTokenUtil.CSRF_SESSION_ATTRIBUTE, CsrfTokenUtil.generateCsrfToken());

            resp.sendRedirect(req.getContextPath() + "/index.jsp");

        } catch (InvalidCredentialsException e) {
            req.setAttribute("errorMsg", "Invalid username or password.");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
        }
    }

}
