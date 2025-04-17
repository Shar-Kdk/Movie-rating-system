package com.movieratingsystem.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*"})
public class AdminAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isAdmin = false;
        if (session != null) {
            Object roleAttribute = session.getAttribute("userRole");
            isAdmin = roleAttribute != null && "ADMIN".equals(roleAttribute.toString());
        }

        if (isAdmin) {
            // User is an admin, proceed with the request
            chain.doFilter(request, response);
        } else {
            // User is not an admin, redirect to access denied or login page
            if (session != null && session.getAttribute("userId") != null) {
                // User is logged in but not an admin
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/access-denied");
            } else {
                // User is not logged in
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            }
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
