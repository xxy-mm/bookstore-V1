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

@WebServlet("/books")
public class BookListServlet extends HttpServlet {

    private final BookService bookService = new BookServiceImpl(new BookDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        int pageSize;
        int pageNum;
        int maxPageSize = 50;
        int minPageSize = 5;
        try {
            pageSize = Integer.parseInt(req.getParameter("pageSize"));
        } catch (NumberFormatException e) {
            pageSize = minPageSize;
        }

        pageSize = Math.min(maxPageSize, Math.max(pageSize, minPageSize));

        try {
            pageNum = Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException e) {

            pageNum = 1;
        }


        PageResult<BookListDTO> pageResult = bookService.findByPage(pageSize, pageNum);
        req.setAttribute("pageResult", pageResult);
        req.getRequestDispatcher("/WEB-INF/view/book/list.jsp").forward(req, resp);

    }

}
