package com.movieratingsystem.controllers;

import com.movieratingsystem.models.UserModel;
import com.movieratingsystem.service.UserService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Forward to login page
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserModel user = userService.authenticateUser(email, password);

        if (user != null) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Create cookie to store user email (or ID)
            Cookie userCookie = new Cookie("userEmail", user.getEmail());
            userCookie.setMaxAge(60 * 60 * 24 * 7); // 1 week
            response.addCookie(userCookie);

            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

}