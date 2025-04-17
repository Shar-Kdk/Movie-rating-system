package com.movieratingsystem.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashUtil {
    
    // The higher the workload factor, the more iterations of the hashing algorithm
    private static final int WORKLOAD = 12;

    /**
     * Hash a password using BCrypt
     * @param plainTextPassword The password to hash
     * @return The hashed password
     */
    public static String hashPassword(String plainTextPassword) {
        if (plainTextPassword == null || plainTextPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    /**
     * Verify a password against a stored hash
     * @param plainTextPassword The password to verify
     * @param hashedPassword The stored hash to verify against
     * @return true if the password matches the hash, false otherwise
     */
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        if (plainTextPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainTextPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Check if a password meets minimum security requirements
     * @param password The password to check
     * @return true if the password meets requirements, false otherwise
     */
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isLowerCase(c)) hasLowercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (specialChars.indexOf(c) >= 0) hasSpecialChar = true;
        }

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }

    /**
     * Generate a random password that meets the strength requirements
     * @param length The desired length of the password
     * @return A randomly generated password
     */
    public static String generateRandomPassword(int length) {
        if (length < 8) {
            length = 8; // Minimum length for security
        }

        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()_+-=[]";
        String allChars = upperChars + lowerChars + digits + specialChars;

        StringBuilder password = new StringBuilder();
        // Ensure at least one of each required character type
        password.append(upperChars.charAt((int) (Math.random() * upperChars.length())));
        password.append(lowerChars.charAt((int) (Math.random() * lowerChars.length())));
        password.append(digits.charAt((int) (Math.random() * digits.length())));
        password.append(specialChars.charAt((int) (Math.random() * specialChars.length())));

        // Fill the rest with random characters
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt((int) (Math.random() * allChars.length())));
        }

        // Shuffle the password
        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }

        return new String(passwordArray);
    }
}
