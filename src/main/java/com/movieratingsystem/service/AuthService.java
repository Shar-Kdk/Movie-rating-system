package com.movieratingsystem.service;

import com.movieratingsystem.dao.UserDAO;
import com.movieratingsystem.model.User;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.http.HttpSession;
import java.util.regex.Pattern;

public class AuthService {
    private final UserDAO userDAO;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User login(String email, String password, HttpSession session) throws Exception {
        if (!isValidEmail(email)) {
            return null;
        }

        User user = userDAO.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            // Set session attributes
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userRole", user.getRole());
            return user;
        }
        return null;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public boolean register(User user) throws Exception {
        // Validate email and password
        if (!isValidEmail(user.getEmail()) || !isValidPassword(user.getPassword())) {
            return false;
        }

        if (userDAO.findByEmail(user.getEmail()) != null) {
            return false; // User already exists
        }
        
        // Hash the password before saving
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPassword);
        
        return userDAO.save(user);
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword) throws Exception {
        if (!isValidPassword(newPassword)) {
            return false;
        }

        User user = userDAO.findById(userId);
        if (user != null && BCrypt.checkpw(oldPassword, user.getPassword())) {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
            user.setPassword(hashedPassword);
            return userDAO.update(user);
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password != null && 
               password.length() >= MIN_PASSWORD_LENGTH &&
               password.matches(".*[A-Z].*") &&    // At least one uppercase letter
               password.matches(".*[a-z].*") &&    // At least one lowercase letter
               password.matches(".*\\d.*") &&      // At least one digit
               password.matches(".*[!@#$%^&*].*"); // At least one special character
    }

    public boolean isAuthenticated(HttpSession session) {
        return session != null && session.getAttribute("userId") != null;
    }

    public boolean isAdmin(HttpSession session) {
        return isAuthenticated(session) && 
               "ADMIN".equals(session.getAttribute("userRole"));
    }
}
