package org.bookstore.controller;

import org.bookstore.dao.impl.BookDaoImpl;
import org.bookstore.dao.impl.CategoryDaoImpl;
import org.bookstore.dto.BookDTO;
import org.bookstore.dto.PageResult;
import org.bookstore.entity.Category;
import org.bookstore.service.BookService;
import org.bookstore.service.CategoryService;
import org.bookstore.service.impl.BookServiceImpl;
import org.bookstore.service.impl.CategoryServiceImpl;
import org.bookstore.util.RequestParamUtil;

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

    private static final long DEFAULT_PAGE_NUM = 1;
    private static final long DEFAULT_PAGE_SIZE = 5;
    private static final long MIN_PAGE_SIZE = 5;
    private static final long MAX_PAGE_SIZE = 50;

    private final BookService bookService = new BookServiceImpl(new BookDaoImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryDaoImpl());

    private static long getPageSize(HttpServletRequest req) {

        Long pageSize = RequestParamUtil.getLongValue(req.getParameter("pageSize"));
        long pageSizeValue = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        return Math.max(MIN_PAGE_SIZE, Math.min(MAX_PAGE_SIZE, pageSizeValue));
    }

    private static long getPageNum(HttpServletRequest req) {

        Long pageNum = RequestParamUtil.getLongValue(req.getParameter("pageNum"));
        long pageNumValue = pageNum == null ? DEFAULT_PAGE_NUM : pageNum;
        return Math.max(1, pageNumValue);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long pageSize = getPageSize(req);
        long pageNum = getPageNum(req);
        Long categoryId = RequestParamUtil.getLongValue(req.getParameter("category"));

        if (categoryId != null && (categoryId <= 0 || !categoryService.isEnabled(categoryId))) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<Category> categories = categoryService.findAllEnabled();
        PageResult<BookDTO> pageResult = bookService.findByPage(pageSize, pageNum, categoryId);
        req.setAttribute("pageResult", pageResult);
        req.setAttribute("categories", categories);
        req.setAttribute("categoryId", categoryId);
        req.getRequestDispatcher("/WEB-INF/view/book/list.jsp").forward(req, resp);

    }
}