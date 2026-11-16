package org.bookstore.controller;

import org.bookstore.dao.impl.BookDaoImpl;
import org.bookstore.entity.Book;
import org.bookstore.service.BookService;
import org.bookstore.service.impl.BookServiceImpl;
import org.bookstore.util.RequestParamUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/book/detail")
public class BookServlet extends HttpServlet {

    private final BookService bookService = new BookServiceImpl(new BookDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = RequestParamUtil.getLongValue(req.getParameter("id"));

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
