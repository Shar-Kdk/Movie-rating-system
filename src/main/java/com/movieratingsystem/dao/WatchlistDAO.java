package com.movieratingsystem.dao;

import com.movieratingsystem.model.Watchlist;
import com.movieratingsystem.model.Movie;
import com.movieratingsystem.model.User;
import com.movieratingsystem.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WatchlistDAO {
    
    public Watchlist findById(int id) throws Exception {
        String sql = "SELECT w.*, m.*, u.* FROM Watchlist w " +
                    "LEFT JOIN Movie m ON w.Movie_ID = m.Movie_ID " +
                    "LEFT JOIN User u ON w.User_ID = u.User_ID " +
                    "WHERE w.Watch_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractWatchlistFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Watchlist> findAll() throws Exception {
        List<Watchlist> watchlists = new ArrayList<>();
        String sql = "SELECT w.*, m.*, u.* FROM Watchlist w " +
                    "LEFT JOIN Movie m ON w.Movie_ID = m.Movie_ID " +
                    "LEFT JOIN User u ON w.User_ID = u.User_ID";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                watchlists.add(extractWatchlistFromResultSet(rs));
            }
        }
        return watchlists;
    }

    public boolean save(Watchlist watchlist) throws Exception {
        String sql = "INSERT INTO Watchlist (Watch_ID, Movie_ID, User_ID) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, watchlist.getWatchId());
            if (watchlist.getMovie() != null) {
                stmt.setInt(2, watchlist.getMovie().getMovieId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            if (watchlist.getUser() != null) {
                stmt.setInt(3, watchlist.getUser().getUserId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Watchlist watchlist) throws Exception {
        String sql = "UPDATE Watchlist SET Movie_ID = ?, User_ID = ? WHERE Watch_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (watchlist.getMovie() != null) {
                stmt.setInt(1, watchlist.getMovie().getMovieId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            if (watchlist.getUser() != null) {
                stmt.setInt(2, watchlist.getUser().getUserId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setInt(3, watchlist.getWatchId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM Watchlist WHERE Watch_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Movie> findMoviesByUserId(int userId) throws Exception {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT m.* FROM Watchlist w " +
                    "INNER JOIN Movie m ON w.Movie_ID = m.Movie_ID " +
                    "WHERE w.User_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movies.add(extractMovieFromResultSet(rs));
                }
            }
        }
        return movies;
    }

    public boolean isMovieInWatchlist(int userId, int movieId) throws Exception {
        String sql = "SELECT COUNT(*) as count FROM Watchlist WHERE User_ID = ? AND Movie_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        }
        return false;
    }

    public boolean removeFromWatchlist(int userId, int movieId) throws Exception {
        String sql = "DELETE FROM Watchlist WHERE User_ID = ? AND Movie_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, movieId);
            return stmt.executeUpdate() > 0;
        }
    }

    public int getWatchlistCount(int userId) throws Exception {
        String sql = "SELECT COUNT(*) as count FROM Watchlist WHERE User_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        }
        return 0;
    }

    private Watchlist extractWatchlistFromResultSet(ResultSet rs) throws SQLException {
        Movie movie = extractMovieFromResultSet(rs);
        User user = extractUserFromResultSet(rs);
        
        return new Watchlist(
            rs.getInt("Watch_ID"),
            movie,
            user
        );
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

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("User_ID"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("role"),
            rs.getString("profilePic"),
            rs.getString("bio")
        );
    }
} 