package org.bookstore.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bookstore.dao.impl.BookDaoImpl;
import org.bookstore.dao.impl.CategoryDaoImpl;
import org.bookstore.dto.BookDTO;
import org.bookstore.dto.BookPageQuery;
import org.bookstore.dto.PageResult;
import org.bookstore.entity.Category;
import org.bookstore.exception.InvalidRequestParameterException;
import org.bookstore.service.BookService;
import org.bookstore.service.CategoryService;
import org.bookstore.service.impl.BookServiceImpl;
import org.bookstore.service.impl.CategoryServiceImpl;
import org.bookstore.util.RequestParamUtil;

import java.io.IOException;
import java.util.List;

// handle request params
@WebServlet("/books")
public class BookListServlet extends HttpServlet {

    private final BookService bookService = new BookServiceImpl(new BookDaoImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer pageSize;
        Integer pageNum;
        Long categoryId;
        try {
            pageSize = RequestParamUtil.getInteger(req.getParameter("pageSize"));
            pageNum = RequestParamUtil.getInteger(req.getParameter("pageNum"));
            categoryId = RequestParamUtil.getLong(req.getParameter("category"));
        } catch (InvalidRequestParameterException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        BookPageQuery bpq = new BookPageQuery();
        bpq.setCategoryId(categoryId);
        bpq.setPageNum(pageNum);
        bpq.setPageSize(pageSize);

        List<Category> categories = categoryService.findAllEnabled();
        PageResult<BookDTO> pageResult = bookService.findByPage(bpq);
        req.setAttribute("pageResult", pageResult);
        req.setAttribute("categories", categories);
        req.setAttribute("categoryId", categoryId);
        req.getRequestDispatcher("/WEB-INF/view/book/list.jsp").forward(req, resp);

    }

}