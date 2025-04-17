package com.movieratingsystem.dao;

import com.movieratingsystem.model.ActivityLog;
import com.movieratingsystem.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogDAO {
    
    public boolean save(ActivityLog log) throws Exception {
        String sql = "INSERT INTO activity_log (user_id, activity_type, description, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setObject(1, log.getUserId());  // setObject to handle null
            stmt.setString(2, log.getActivityType());
            stmt.setString(3, log.getDescription());
            stmt.setTimestamp(4, log.getTimestamp());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    log.setLogId(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    public ActivityLog findById(int logId) throws Exception {
        String sql = "SELECT * FROM activity_log WHERE log_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, logId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<ActivityLog> findByUserId(int userId) throws Exception {
        List<ActivityLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM activity_log WHERE user_id = ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(extractFromResultSet(rs));
                }
            }
        }
        return logs;
    }

    public List<ActivityLog> findByType(String activityType) throws Exception {
        List<ActivityLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM activity_log WHERE activity_type = ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, activityType);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(extractFromResultSet(rs));
                }
            }
        }
        return logs;
    }

    public List<ActivityLog> findRecent(int limit) throws Exception {
        List<ActivityLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM activity_log ORDER BY timestamp DESC LIMIT ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(extractFromResultSet(rs));
                }
            }
        }
        return logs;
    }

    public boolean delete(int logId) throws Exception {
        String sql = "DELETE FROM activity_log WHERE log_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, logId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteOlderThan(Timestamp timestamp) throws Exception {
        String sql = "DELETE FROM activity_log WHERE timestamp < ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, timestamp);
            return stmt.executeUpdate() > 0;
        }
    }

    private ActivityLog extractFromResultSet(ResultSet rs) throws SQLException {
        return new ActivityLog(
            rs.getInt("log_id"),
            rs.getObject("user_id", Integer.class),  // Handle null values
            rs.getString("activity_type"),
            rs.getString("description"),
            rs.getTimestamp("timestamp")
        );
    }
} 