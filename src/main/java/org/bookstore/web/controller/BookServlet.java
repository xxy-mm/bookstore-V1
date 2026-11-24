package org.bookstore.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bookstore.dao.impl.BookDaoImpl;
import org.bookstore.entity.Book;
import org.bookstore.service.BookService;
import org.bookstore.service.impl.BookServiceImpl;
import org.bookstore.util.RequestParamUtil;

import java.io.IOException;

@WebServlet("/book/detail")
public class BookServlet extends HttpServlet {

    private final BookService bookService = new BookServiceImpl(new BookDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = RequestParamUtil.getLong(req.getParameter("id"));

        if (id == null || id <= 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Book book = bookService.findById(id);
        if (book == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setAttribute("book", book);
        req.getRequestDispatcher("/WEB-INF/view/book/detail.jsp").forward(req, resp);

    }

}
