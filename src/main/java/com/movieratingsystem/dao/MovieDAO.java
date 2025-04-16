package com.movieratingsystem.dao;

import com.movieratingsystem.model.Movie;
import com.movieratingsystem.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    
    public Movie findById(int id) throws Exception {
        String sql = "SELECT * FROM Movie WHERE Movie_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractMovieFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Movie> findAll() throws Exception {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM Movie";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                movies.add(extractMovieFromResultSet(rs));
            }
        }
        return movies;
    }

    public boolean save(Movie movie) throws Exception {
        String sql = "INSERT INTO Movie (Movie_ID, title, release_date, image, minutes) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movie.getMovieId());
            stmt.setString(2, movie.getTitle());
            stmt.setDate(3, new java.sql.Date(movie.getReleaseDate().getTime()));
            stmt.setString(4, movie.getImage());
            stmt.setInt(5, movie.getMinutes());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Movie movie) throws Exception {
        String sql = "UPDATE Movie SET title = ?, release_date = ?, image = ?, minutes = ? WHERE Movie_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, movie.getTitle());
            stmt.setDate(2, new java.sql.Date(movie.getReleaseDate().getTime()));
            stmt.setString(3, movie.getImage());
            stmt.setInt(4, movie.getMinutes());
            stmt.setInt(5, movie.getMovieId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM Movie WHERE Movie_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Movie> findByTitle(String title) throws Exception {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM Movie WHERE title LIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + title + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movies.add(extractMovieFromResultSet(rs));
                }
            }
        }
        return movies;
    }

    public List<Movie> findByReleaseYear(int year) throws Exception {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM Movie WHERE YEAR(release_date) = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movies.add(extractMovieFromResultSet(rs));
                }
            }
        }
        return movies;
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

    private Movie extractMovieFromResultSet(ResultSet rs) throws SQLException {
        return new Movie(
            rs.getInt("Movie_ID"),
            rs.getString("title"),
            rs.getDate("release_date"),
            rs.getString("image"),
            rs.getInt("minutes")
        );
    }
} 