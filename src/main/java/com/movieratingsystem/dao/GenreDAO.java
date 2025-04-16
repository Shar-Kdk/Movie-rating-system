package com.movieratingsystem.dao;

import com.movieratingsystem.model.Genre;
import com.movieratingsystem.model.Movie;
import com.movieratingsystem.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    
    public Genre findById(int id) throws Exception {
        String sql = "SELECT g.*, m.* FROM Genre g LEFT JOIN Movie m ON g.Movie_ID = m.Movie_ID WHERE g.Genre_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractGenreFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Genre> findAll() throws Exception {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT g.*, m.* FROM Genre g LEFT JOIN Movie m ON g.Movie_ID = m.Movie_ID";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                genres.add(extractGenreFromResultSet(rs));
            }
        }
        return genres;
    }

    public boolean save(Genre genre) throws Exception {
        String sql = "INSERT INTO Genre (Genre_ID, genre_name, description, Movie_ID) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, genre.getGenreId());
            stmt.setString(2, genre.getGenreName());
            stmt.setString(3, genre.getDescription());
            if (genre.getMovie() != null) {
                stmt.setInt(4, genre.getMovie().getMovieId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Genre genre) throws Exception {
        String sql = "UPDATE Genre SET genre_name = ?, description = ?, Movie_ID = ? WHERE Genre_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genre.getGenreName());
            stmt.setString(2, genre.getDescription());
            if (genre.getMovie() != null) {
                stmt.setInt(3, genre.getMovie().getMovieId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setInt(4, genre.getGenreId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM Genre WHERE Genre_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Genre> findByMovieId(int movieId) throws Exception {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT g.*, m.* FROM Genre g LEFT JOIN Movie m ON g.Movie_ID = m.Movie_ID WHERE g.Movie_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    genres.add(extractGenreFromResultSet(rs));
                }
            }
        }
        return genres;
    }

    public List<Genre> findByGenreName(String genreName) throws Exception {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT g.*, m.* FROM Genre g LEFT JOIN Movie m ON g.Movie_ID = m.Movie_ID WHERE g.genre_name LIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + genreName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    genres.add(extractGenreFromResultSet(rs));
                }
            }
        }
        return genres;
    }

    public List<Genre> findGenresWithMovies() throws Exception {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT g.*, m.* FROM Genre g INNER JOIN Movie m ON g.Movie_ID = m.Movie_ID";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                genres.add(extractGenreFromResultSet(rs));
            }
        }
        return genres;
    }

    private Genre extractGenreFromResultSet(ResultSet rs) throws SQLException {
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
        
        return new Genre(
            rs.getInt("Genre_ID"),
            rs.getString("genre_name"),
            rs.getString("description"),
            movie
        );
    }
}