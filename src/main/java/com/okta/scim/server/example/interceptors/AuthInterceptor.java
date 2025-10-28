/**
 * AuthInterceptor.java
 *
 * Intercepts incoming HTTP requests to check for a valid API token.
 */
package com.okta.scim.server.example;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Checks the "Authorization" header for a valid Bearer token.
 * Returns 401 Unauthorized if the token is missing or invalid.
 */
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN = "00SvMqRNdFYjFRh6Cqm80A8lbDQxJdxfvZR8KGZa-J";

    /**
     * Validates the Authorization header before controller execution.
     *
     * @param req     incoming HTTP request
     * @param res     HTTP response
     * @param handler handler object
     * @return true if authorized, false otherwise
     * @throws Exception if an I/O error occurs
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String auth = req.getHeader("Authorization");
        if (auth == null || !auth.equals("Bearer " + TOKEN)) {
            res.setStatus(401);
            res.getWriter().write("Unauthorized");
            return false;
        }
        return true;
    }
}
