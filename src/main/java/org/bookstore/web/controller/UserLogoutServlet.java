package org.bookstore.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.bookstore.util.CsrfTokenUtil;

import java.io.IOException;


@WebServlet("/logout")
public class UserLogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (!CsrfTokenUtil.isValidCsrfToken(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
