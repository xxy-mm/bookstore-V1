package org.example.bookstore.controller;

import org.example.bookstore.dao.impl.BookDaoImpl;
import org.example.bookstore.dao.impl.CategoryDaoImpl;
import org.example.bookstore.dto.BookListDTO;
import org.example.bookstore.dto.PageResult;
import org.example.bookstore.entity.Category;
import org.example.bookstore.service.BookService;
import org.example.bookstore.service.CategoryService;
import org.example.bookstore.service.impl.BookServiceImpl;
import org.example.bookstore.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// handle request params
@WebServlet("/books")
public class BookListServlet extends HttpServlet {
    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final int MIN_PAGE_SIZE = 5;
    private static final int MAX_PAGE_SIZE = 50;

    private final BookService bookService = new BookServiceImpl(new BookDaoImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryDaoImpl());

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

    private Long getCategoryId(HttpServletRequest req) {

        String categoryParam = req.getParameter("category");

        if (categoryParam == null || categoryParam.trim().isEmpty()) {
            return null;
        }

        try {
            long categoryId = Long.parseLong(categoryParam);
            if (categoryId <= 0) {
                throw new IllegalArgumentException("Invalid category id");
            }
            return categoryId;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid category id.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int pageSize = getPageSize(req);
        int pageNum = getPageNum(req);
        Long categoryId;

        try {
            categoryId = getCategoryId(req);
        } catch (IllegalArgumentException e) {
            // invalid category id
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (categoryId != null && !categoryService.isEnabled(categoryId)) {
            // the category is disabled or not exist
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<Category> categories = categoryService.findAllEnabled();
        PageResult<BookListDTO> pageResult = categoryId == null ? bookService.findByPage(pageSize, pageNum) :
                bookService.findByPage(pageSize, pageNum, categoryId);
        req.setAttribute("pageResult", pageResult);
        req.setAttribute("categories", categories);
        req.setAttribute("categoryId", categoryId);
        req.getRequestDispatcher("/WEB-INF/view/book/list.jsp").forward(req, resp);

    }

}
