package org.bookstore.controller;

import org.bookstore.dao.impl.UserDaoImpl;
import org.bookstore.dto.UserDTO;
import org.bookstore.exception.InvalidCredentialsException;
import org.bookstore.service.UserService;
import org.bookstore.service.impl.UserServiceImpl;
import org.bookstore.util.CsrfTokenUtil;
import org.bookstore.util.RequestParamUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl(new UserDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CsrfTokenUtil.addCsrfTokenToSession(req);

        req.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
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

        if (username == null || password == null) {
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
            session.setAttribute(CsrfTokenUtil.CSRF_SESSION_ATTRIBUTE, CsrfTokenUtil.generateCsrfToken());

            resp.sendRedirect(req.getContextPath() + "/index.jsp");

        } catch (InvalidCredentialsException e) {
            req.setAttribute("errorMsg", "Username or password is incorrect.");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
        }
    }

}
