package org.example.bookstore.controller;

import org.example.bookstore.dao.impl.BookDaoImpl;
import org.example.bookstore.entity.Book;
import org.example.bookstore.exception.BookNotFoundException;
import org.example.bookstore.service.BookService;
import org.example.bookstore.service.impl.BookServiceImpl;

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

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;

        }

        long id;

        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (id <= 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        try {
            Book book = bookService.findById(id);

            req.setAttribute("book", book);
            req.getRequestDispatcher("/WEB-INF/view/book/detail.jsp").forward(req, resp);
        } catch (BookNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }


    }
}
