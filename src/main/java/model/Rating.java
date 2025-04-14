package model;

import java.security.Timestamp;

public class Rating {
    private int ratingId;      // Primary key
    private double score;      // Rating score
    private Timestamp ratedAt; // When the rating was submitted
    private int movieId;       // Foreign key to Movie

    public Rating(int ratingId, double score, Timestamp ratedAt, int movieId, int userId) {
        this.ratingId = ratingId;
        this.score = score;
        this.ratedAt = ratedAt;
        this.movieId = movieId;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Timestamp getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(Timestamp ratedAt) {
        this.ratedAt = ratedAt;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    private int userId;        // Foreign key to User
}
