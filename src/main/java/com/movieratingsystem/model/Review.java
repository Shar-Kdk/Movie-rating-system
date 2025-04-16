package com.movieratingsystem.model;

import java.util.Date;

public class Review {

    private int reviewId;
    private String comment;
    private Date reviewDate;
    private User user;
    private Movie movie;

    public Review(int reviewId, String comment, Date reviewDate, User user, Movie movie) {
        this.reviewId = reviewId;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.user = user;
        this.movie = movie;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
