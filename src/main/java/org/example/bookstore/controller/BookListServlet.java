package org.example.bookstore.controller;

import org.example.bookstore.dao.impl.BookDaoImpl;
import org.example.bookstore.dto.BookListDTO;
import org.example.bookstore.dto.PageResult;
import org.example.bookstore.service.BookService;
import org.example.bookstore.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// handle request params
@WebServlet("/books")
public class BookListServlet extends HttpServlet {
    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final int MIN_PAGE_SIZE = 5;
    private static final int MAX_PAGE_SIZE = 50;

    private final BookService bookService = new BookServiceImpl(new BookDaoImpl());

    private static int getPageSize(HttpServletRequest req) {
        int pageSize = parseInt(req.getParameter("pageSize"), DEFAULT_PAGE_SIZE);
        return Math.max(MIN_PAGE_SIZE, Math.min(MAX_PAGE_SIZE, pageSize));
    }

    private static int getPageNum(HttpServletRequest req) {
        return Math.max(1, parseInt(req.getParameter("pageNum"), DEFAULT_PAGE_NUM));
    }

    private static int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        int pageSize = getPageSize(req);
        int pageNum = getPageNum(req);


        PageResult<BookListDTO> pageResult = bookService.findByPage(pageSize, pageNum);
        req.setAttribute("pageResult", pageResult);
        req.getRequestDispatcher("/WEB-INF/view/book/list.jsp").forward(req, resp);

    }

}
