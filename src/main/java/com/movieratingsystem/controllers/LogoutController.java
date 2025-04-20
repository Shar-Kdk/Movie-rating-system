package com.movieratingsystem.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LogoutController", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidate session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Delete the userEmail cookie
        Cookie userCookie = new Cookie("userEmail", "");
        userCookie.setMaxAge(0); // Deletes the cookie
        userCookie.setPath("/"); // Important: matches the path where the cookie was originally set
        response.addCookie(userCookie);

        // Redirect to home page
        response.sendRedirect(request.getContextPath() + "/");
    }
}
