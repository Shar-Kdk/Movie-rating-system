package com.movieratingsystem.dao;

import com.movieratingsystem.model.Review;
import com.movieratingsystem.model.User;
import com.movieratingsystem.model.Movie;
import com.movieratingsystem.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ReviewDAO {
    
    public Review findById(int id) throws Exception {
        String sql = "SELECT r.*, u.*, m.* FROM Review r " +
                    "LEFT JOIN User u ON r.User_ID = u.User_ID " +
                    "LEFT JOIN Movie m ON r.Movie_ID = m.Movie_ID " +
                    "WHERE r.Review_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractReviewFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Review> findAll() throws Exception {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.*, m.* FROM Review r " +
                    "LEFT JOIN User u ON r.User_ID = u.User_ID " +
                    "LEFT JOIN Movie m ON r.Movie_ID = m.Movie_ID";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reviews.add(extractReviewFromResultSet(rs));
            }
        }
        return reviews;
    }

    public boolean save(Review review) throws Exception {
        String sql = "INSERT INTO Review (Review_ID, comment, rev_date, User_ID, Movie_ID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, review.getReviewId());
            stmt.setString(2, review.getComment());
            stmt.setDate(3, new java.sql.Date(review.getReviewDate().getTime()));
            if (review.getUser() != null) {
                stmt.setInt(4, review.getUser().getUserId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            if (review.getMovie() != null) {
                stmt.setInt(5, review.getMovie().getMovieId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Review review) throws Exception {
        String sql = "UPDATE Review SET comment = ?, rev_date = ?, User_ID = ?, Movie_ID = ? WHERE Review_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, review.getComment());
            stmt.setDate(2, new java.sql.Date(review.getReviewDate().getTime()));
            if (review.getUser() != null) {
                stmt.setInt(3, review.getUser().getUserId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            if (review.getMovie() != null) {
                stmt.setInt(4, review.getMovie().getMovieId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setInt(5, review.getReviewId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM Review WHERE Review_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Review> findByMovieId(int movieId) throws Exception {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.*, m.* FROM Review r " +
                    "LEFT JOIN User u ON r.User_ID = u.User_ID " +
                    "LEFT JOIN Movie m ON r.Movie_ID = m.Movie_ID " +
                    "WHERE r.Movie_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(extractReviewFromResultSet(rs));
                }
            }
        }
        return reviews;
    }

    public List<Review> findByUserId(int userId) throws Exception {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.*, m.* FROM Review r " +
                    "LEFT JOIN User u ON r.User_ID = u.User_ID " +
                    "LEFT JOIN Movie m ON r.Movie_ID = m.Movie_ID " +
                    "WHERE r.User_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(extractReviewFromResultSet(rs));
                }
            }
        }
        return reviews;
    }

    public List<Review> findByDate(Date date) throws Exception {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.*, m.* FROM Review r " +
                    "LEFT JOIN User u ON r.User_ID = u.User_ID " +
                    "LEFT JOIN Movie m ON r.Movie_ID = m.Movie_ID " +
                    "WHERE DATE(r.rev_date) = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(date.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(extractReviewFromResultSet(rs));
                }
            }
        }
        return reviews;
    }

    public List<Review> findRecentReviews(int limit) throws Exception {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.*, m.* FROM Review r " +
                    "LEFT JOIN User u ON r.User_ID = u.User_ID " +
                    "LEFT JOIN Movie m ON r.Movie_ID = m.Movie_ID " +
                    "ORDER BY r.rev_date DESC LIMIT ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(extractReviewFromResultSet(rs));
                }
            }
        }
        return reviews;
    }

    public int getReviewCount(int movieId) throws Exception {
        String sql = "SELECT COUNT(*) as count FROM Review WHERE Movie_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        }
        return 0;
    }

    private Review extractReviewFromResultSet(ResultSet rs) throws SQLException {
        User user = new User(
            rs.getInt("User_ID"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("role"),
            rs.getString("profilePic"),
            rs.getString("bio")
        );

        Movie movie = new Movie(
            rs.getInt("Movie_ID"),
            rs.getString("title"),
            rs.getDate("release_date"),
            rs.getString("image"),
            rs.getInt("minutes")
        );

        return new Review(
            rs.getInt("Review_ID"),
            rs.getString("comment"),
            rs.getDate("rev_date"),
            user,
            movie
        );
    }
} 