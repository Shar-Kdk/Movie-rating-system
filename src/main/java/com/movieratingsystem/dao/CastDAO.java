package com.movieratingsystem.dao;

import com.movieratingsystem.model.Cast;
import com.movieratingsystem.model.Movie;
import com.movieratingsystem.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CastDAO {
    
    public Cast findById(int id) throws Exception {
        String sql = "SELECT c.*, m.* FROM Cast c LEFT JOIN Movie m ON c.Movie_ID = m.Movie_ID WHERE c.Cast_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractCastFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Cast> findAll() throws Exception {
        List<Cast> casts = new ArrayList<>();
        String sql = "SELECT c.*, m.* FROM Cast c LEFT JOIN Movie m ON c.Movie_ID = m.Movie_ID";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                casts.add(extractCastFromResultSet(rs));
            }
        }
        return casts;
    }

    public boolean save(Cast cast) throws Exception {
        String sql = "INSERT INTO Cast (Cast_ID, Cast_name, birth_date, biography, gender, char_name, Movie_ID, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cast.getCastId());
            stmt.setString(2, cast.getCastName());
            stmt.setDate(3, new java.sql.Date(cast.getBirthDate().getTime()));
            stmt.setString(4, cast.getBiography());
            stmt.setString(5, cast.getGender());
            stmt.setString(6, cast.getCharName());
            if (cast.getMovie() != null) {
                stmt.setInt(7, cast.getMovie().getMovieId());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            stmt.setString(8, cast.getPhoto());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Cast cast) throws Exception {
        String sql = "UPDATE Cast SET Cast_name = ?, birth_date = ?, biography = ?, gender = ?, char_name = ?, Movie_ID = ?, photo = ? WHERE Cast_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cast.getCastName());
            stmt.setDate(2, new java.sql.Date(cast.getBirthDate().getTime()));
            stmt.setString(3, cast.getBiography());
            stmt.setString(4, cast.getGender());
            stmt.setString(5, cast.getCharName());
            if (cast.getMovie() != null) {
                stmt.setInt(6, cast.getMovie().getMovieId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }
            stmt.setString(7, cast.getPhoto());
            stmt.setInt(8, cast.getCastId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM Cast WHERE Cast_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Cast> findByMovieId(int movieId) throws Exception {
        List<Cast> casts = new ArrayList<>();
        String sql = "SELECT c.*, m.* FROM Cast c LEFT JOIN Movie m ON c.Movie_ID = m.Movie_ID WHERE c.Movie_ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    casts.add(extractCastFromResultSet(rs));
                }
            }
        }
        return casts;
    }

    public List<Cast> findByCastName(String castName) throws Exception {
        List<Cast> casts = new ArrayList<>();
        String sql = "SELECT c.*, m.* FROM Cast c LEFT JOIN Movie m ON c.Movie_ID = m.Movie_ID WHERE c.Cast_name LIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + castName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    casts.add(extractCastFromResultSet(rs));
                }
            }
        }
        return casts;
    }

    public List<Cast> findByCharacterName(String characterName) throws Exception {
        List<Cast> casts = new ArrayList<>();
        String sql = "SELECT c.*, m.* FROM Cast c LEFT JOIN Movie m ON c.Movie_ID = m.Movie_ID WHERE c.char_name LIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + characterName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    casts.add(extractCastFromResultSet(rs));
                }
            }
        }
        return casts;
    }

    public List<Cast> findByGender(String gender) throws Exception {
        List<Cast> casts = new ArrayList<>();
        String sql = "SELECT c.*, m.* FROM Cast c LEFT JOIN Movie m ON c.Movie_ID = m.Movie_ID WHERE c.gender = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gender);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    casts.add(extractCastFromResultSet(rs));
                }
            }
        }
        return casts;
    }

    private Cast extractCastFromResultSet(ResultSet rs) throws SQLException {
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
        
        return new Cast(
            rs.getInt("Cast_ID"),
            rs.getString("Cast_name"),
            rs.getDate("birth_date"),
            rs.getString("biography"),
            rs.getString("photo"),
            rs.getString("gender"),
            rs.getString("char_name"),
            movie
        );
    }
} 