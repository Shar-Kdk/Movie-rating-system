package com.movieratingsystem.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.movieratingsystem.util.DatabaseUtil;

public class ActivityLogService {
    private static final String INSERT_LOG = 
        "INSERT INTO activity_log (user_id, activity_type, description, timestamp) VALUES (?, ?, ?, ?)";

    public void logActivity(Integer userId, String activityType, String description) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_LOG)) {
            
            stmt.setObject(1, userId);  // Can be null for anonymous users
            stmt.setString(2, activityType);
            stmt.setString(3, description);
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            
            stmt.executeUpdate();
        } catch (Exception e) {
            // Log the error but don't throw it to prevent disrupting the main application flow
            System.err.println("Failed to log activity: " + e.getMessage());
        }
    }

    // Convenience methods for common activities
    public void logLogin(int userId, String email) {
        logActivity(userId, "LOGIN", "User logged in: " + email);
    }

    public void logLogout(int userId) {
        logActivity(userId, "LOGOUT", "User logged out");
    }

    public void logRegistration(int userId, String email) {
        logActivity(userId, "REGISTRATION", "New user registered: " + email);
    }

    public void logMovieRating(int userId, int movieId, double rating) {
        logActivity(userId, "RATING", 
            String.format("User rated movie (ID: %d) with %.1f stars", movieId, rating));
    }

    public void logMovieReview(int userId, int movieId) {
        logActivity(userId, "REVIEW", "User reviewed movie (ID: " + movieId + ")");
    }

    public void logWatchlistAdd(int userId, int movieId) {
        logActivity(userId, "WATCHLIST_ADD", "User added movie (ID: " + movieId + ") to watchlist");
    }

    public void logWatchlistRemove(int userId, int movieId) {
        logActivity(userId, "WATCHLIST_REMOVE", "User removed movie (ID: " + movieId + ") from watchlist");
    }

    public void logError(Integer userId, String errorType, String errorMessage) {
        logActivity(userId, "ERROR", errorType + ": " + errorMessage);
    }
}
