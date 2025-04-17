package com.movieratingsystem.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtil {
    private static final Logger LOGGER = Logger.getLogger(DatabaseUtil.class.getName());
    private static final Properties properties = new Properties();
    private static final String PROPERTY_FILE = "db.properties";

    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream(PROPERTY_FILE)) {
            if (input == null) {
                LOGGER.severe("Unable to find " + PROPERTY_FILE);
                throw new RuntimeException("Unable to find " + PROPERTY_FILE);
            }
            properties.load(input);
            Class.forName(properties.getProperty("db.driver"));
            LOGGER.info("Database configuration loaded successfully");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading database properties", e);
            throw new RuntimeException("Error loading database properties", e);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Database driver not found", e);
            throw new RuntimeException("Database driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
            );
            LOGGER.fine("Database connection established");
            return conn;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to establish database connection", e);
            throw e;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.fine("Database connection closed");
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing database connection", e);
            }
        }
    }

    public static void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
                LOGGER.info("Transaction rolled back");
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error rolling back transaction", e);
            }
        }
    }
} 