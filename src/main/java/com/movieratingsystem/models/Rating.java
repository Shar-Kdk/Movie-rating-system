package com.movieratingsystem.model;

import java.util.Date;

public class Rating {
    private int ratingId;
    private double score;
    private Date ratingDate;
    private Movie movie;

    public Rating(int ratingId, double score, Date ratingDate, Movie movie) {
        this.ratingId = ratingId;
        this.score = score;
        this.ratingDate = ratingDate;
        this.movie = movie;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(Date ratingDate) {
        this.ratingDate = ratingDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
