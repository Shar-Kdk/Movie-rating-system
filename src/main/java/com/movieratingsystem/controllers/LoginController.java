    package com.movieratingsystem.controllers;

    import com.movieratingsystem.models.UserModel;
    import com.movieratingsystem.service.UserService;
    import java.io.IOException;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.*;

    @WebServlet(name = "LoginController", urlPatterns = {"/login", "/login/togglePassword"})
    public class LoginController extends HttpServlet {

        private UserService userService;

        @Override
        public void init() throws ServletException {
            super.init();
            userService = new UserService();
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String path = request.getServletPath();

            // Handle password toggle request
            if ("/login/togglePassword".equals(path)) {
                // Get current state from session
                HttpSession session = request.getSession();
                Boolean passwordVisible = (Boolean) session.getAttribute("passwordVisible");

                // Toggle state
                passwordVisible = (passwordVisible == null || !passwordVisible);
                session.setAttribute("passwordVisible", passwordVisible);

                // Redirect back to login page
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Normal login page request
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
            String remember = request.getParameter("remember");

            UserModel user = userService.authenticateUser(email, password);

            if (user != null) {
                // Create session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Handle "Remember me" functionality
                if (remember != null) {
                    // Create cookie to store user email
                    Cookie userCookie = new Cookie("userEmail", user.getEmail());
                    userCookie.setMaxAge(60 * 60 * 24 * 7); // 1 week
                    userCookie.setPath("/"); // Set path explicitly to match deletion
                    response.addCookie(userCookie);
                }

                response.sendRedirect(request.getContextPath() + "/");
            } else {
                // Pass back the email for convenience
                request.setAttribute("email", email);
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        }
    }