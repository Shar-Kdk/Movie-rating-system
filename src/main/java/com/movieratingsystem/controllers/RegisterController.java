package com.movieratingsystem.controllers;

import com.movieratingsystem.models.UserModel;
import com.movieratingsystem.service.UserService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;

@WebServlet(name = "RegisterController", urlPatterns = {"/register", "/register/togglePassword"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5,   // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class RegisterController extends HttpServlet {

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
        if ("/register/togglePassword".equals(path)) {
            togglePasswordVisibility(request, response);
            return;
        }

        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Forward to register page
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    /**
     * Handles the password visibility toggle functionality
     */
    private void togglePasswordVisibility(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String passwordField = request.getParameter("field");

        if (passwordField != null) {
            if ("password".equals(passwordField)) {
                Boolean passwordVisible = (Boolean) session.getAttribute("passwordVisible");
                passwordVisible = (passwordVisible == null || !passwordVisible);
                session.setAttribute("passwordVisible", passwordVisible);
            } else if ("confirmPassword".equals(passwordField)) {
                Boolean confirmPasswordVisible = (Boolean) session.getAttribute("confirmPasswordVisible");
                confirmPasswordVisible = (confirmPasswordVisible == null || !confirmPasswordVisible);
                session.setAttribute("confirmPasswordVisible", confirmPasswordVisible);
            }
        }

        // Preserve form data in session
        preserveFormData(request);

        // Redirect back to register page
        response.sendRedirect(request.getContextPath() + "/register");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        HttpSession session = request.getSession();

        try {
            // Process file upload first
            Part filePart = null;
            try {
                filePart = request.getPart("profileImage");
            } catch (Exception e) {
                // Handle the case where the file part might be missing or invalid
                request.setAttribute("error", "Error processing the uploaded file: " + e.getMessage());
                preserveFormData(request);
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }

            // Process the file if it exists and has content
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getSubmittedFileName(filePart);
                if (fileName != null && !fileName.isEmpty()) {
                    session.setAttribute("uploadedFileName", fileName);
                }
            }

            // Validate input
            if (name == null || name.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    password == null || password.trim().isEmpty() ||
                    confirmPassword == null || confirmPassword.trim().isEmpty()) {

                // Preserve form data
                preserveFormData(request);

                request.setAttribute("error", "All fields are required");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }

            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                // Preserve form data
                preserveFormData(request);

                request.setAttribute("error", "Passwords do not match");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }

            // Create user object
            UserModel user = new UserModel();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            // Handle profile image upload
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream fileContent = filePart.getInputStream()) {
                    byte[] imageBytes = new byte[(int) filePart.getSize()];
                    fileContent.read(imageBytes);
                    user.setImage(imageBytes);
                }
            }

            // Register user
            boolean success = userService.registerUser(user);

            if (success) {
                // Clear form data from session
                clearSessionFormData(session);

                // Authenticate and create session
                UserModel authenticatedUser = userService.authenticateUser(email, password);
                if (authenticatedUser != null) {
                    session.setAttribute("user", authenticatedUser);
                }

                // Redirect to home page
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                // Preserve form data
                preserveFormData(request);

                request.setAttribute("error", "Registration failed. Email may already be in use.");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Log the exception and show a user-friendly error
            e.printStackTrace();
            preserveFormData(request);
            request.setAttribute("error", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }

    /**
     * Clears form data from the session
     */
    private void clearSessionFormData(HttpSession session) {
        session.removeAttribute("formName");
        session.removeAttribute("formEmail");
        session.removeAttribute("uploadedFileName");
        session.removeAttribute("passwordVisible");
        session.removeAttribute("confirmPasswordVisible");
    }

    /**
     * Helper method to preserve form data between requests
     */
    private void preserveFormData(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // Save form data in session
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (name != null && !name.trim().isEmpty()) {
            session.setAttribute("formName", name);
        }

        if (email != null && !email.trim().isEmpty()) {
            session.setAttribute("formEmail", email);
        }
    }

    /**
     * Helper method to get the submitted filename with improved reliability
     */
    private String getSubmittedFileName(Part part) {
        // Check for null part
        if (part == null) {
            return "";
        }

        // Try to get the file name from the content-disposition header
        String contentDisp = part.getHeader("content-disposition");
        if (contentDisp != null) {
            String[] items = contentDisp.split(";");
            for (String item : items) {
                if (item.trim().startsWith("filename")) {
                    // Extract only the actual file name without quotes
                    String fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);

                    // For IE which may include the full path
                    int lastSeparator = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
                    if (lastSeparator > -1) {
                        fileName = fileName.substring(lastSeparator + 1);
                    }

                    return fileName;
                }
            }
        }

        // If we can't find the filename, return an empty string
        return "";
    }
}