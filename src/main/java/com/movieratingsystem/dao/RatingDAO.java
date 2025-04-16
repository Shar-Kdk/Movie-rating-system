package com.movieratingsystem.dao;

import com.movieratingsystem.model.Rating;
import com.movieratingsystem.model.Movie;
import com.movieratingsystem.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO {
    
    public Rating findById(int id) throws Exception {
        String sql = "SELECT r.*, m.* FROM Rating r " +
                    "LEFT JOIN Movie m ON r.Movie_ID = m.Movie_ID " +
                    "WHERE r.Rating_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractRatingFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Rating> findAll() throws Exception {
        List<Rating> ratings = new ArrayList<>();
        String sql = "SELECT r.*, m.* FROM Rating r " +
                    "LEFT JOIN Movie m ON r.Movie_ID = m.Movie_ID";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ratings.add(extractRatingFromResultSet(rs));
            }
        }
        return ratings;
    }

    public boolean save(Rating rating) throws Exception {
        String sql = "INSERT INTO Rating (Rating_ID, score, rating_date, Movie_ID) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rating.getRatingId());
            stmt.setDouble(2, rating.getScore());
            stmt.setDate(3, new java.sql.Date(rating.getRatingDate().getTime()));
            if (rating.getMovie() != null) {
                stmt.setInt(4, rating.getMovie().getMovieId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Rating rating) throws Exception {
        String sql = "UPDATE Rating SET score = ?, rating_date = ?, Movie_ID = ? WHERE Rating_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, rating.getScore());
            stmt.setDate(2, new java.sql.Date(rating.getRatingDate().getTime()));
            if (rating.getMovie() != null) {
                stmt.setInt(3, rating.getMovie().getMovieId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setInt(4, rating.getRatingId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM Rating WHERE Rating_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Rating> findByMovieId(int movieId) throws Exception {
        List<Rating> ratings = new ArrayList<>();
        String sql = "SELECT r.*, m.* FROM Rating r " +
                    "LEFT JOIN Movie m ON r.Movie_ID = m.Movie_ID " +
                    "WHERE r.Movie_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ratings.add(extractRatingFromResultSet(rs));
                }
            }
        }
        return ratings;
    }

    public double getAverageRating(int movieId) throws Exception {
        String sql = "SELECT AVG(score) as avg_rating FROM Rating WHERE Movie_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_rating");
                }
            }
        }
        return 0.0;
    }

    public int getRatingCount(int movieId) throws Exception {
        String sql = "SELECT COUNT(*) as count FROM Rating WHERE Movie_ID = ?";
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

    public List<Rating> findByScore(double score) throws Exception {
        List<Rating> ratings = new ArrayList<>();
        String sql = "SELECT r.*, m.* FROM Rating r " +
                    "LEFT JOIN Movie m ON r.Movie_ID = m.Movie_ID " +
                    "WHERE r.score = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, score);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ratings.add(extractRatingFromResultSet(rs));
                }
            }
        }
        return ratings;
    }

    public List<Rating> findTopRatedMovies(int limit) throws Exception {
        List<Rating> ratings = new ArrayList<>();
        String sql = "SELECT r.*, m.*, AVG(r.score) as avg_score " +
                    "FROM Rating r " +
                    "LEFT JOIN Movie m ON r.Movie_ID = m.Movie_ID " +
                    "GROUP BY r.Movie_ID " +
                    "ORDER BY avg_score DESC " +
                    "LIMIT ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ratings.add(extractRatingFromResultSet(rs));
                }
            }
        }
        return ratings;
    }

    private Rating extractRatingFromResultSet(ResultSet rs) throws SQLException {
        Movie movie = null;
        if (rs.getObject("Movie_ID") != null) {
            movie = new Movie(
                rs.getInt("Movie_ID"),
                rs.getString("title"),
                rs.getDate("release_date"),
                rs.getString("image"),
                rs.getInt("minutes")
            );
        }
        
        return new Rating(
            rs.getInt("Rating_ID"),
            rs.getDouble("score"),
            rs.getDate("rating_date"),
            movie
        );
    }
} 