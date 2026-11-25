package org.bookstore.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

public class LoginCheckFilter implements Filter {
    private static final String SESSION_USER_ATTRIBUTE = "loginUser";
    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/", "/login", "/register", "/books", "/book/detail"
    );

    private static final Set<String> PUBLIC_PREFIXES = Set.of(
            "/static/"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        // check path
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = requestURI.substring(contextPath.length());

        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }


        // check login
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute(SESSION_USER_ATTRIBUTE) != null) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    private boolean isPublicPath(String path) {
        if (PUBLIC_PATHS.contains(path)) {
            return true;
        }
        for (String publicPrefix : PUBLIC_PREFIXES) {
            if (path.startsWith(publicPrefix)) {
                return true;
            }
        }

        return false;
    }


}
