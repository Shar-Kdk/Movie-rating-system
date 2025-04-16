package com.movieratingsystem.model;

public class Watchlist {
    private int watchId;
    private Movie movie;
    private User user;

    public Watchlist(int watchId, Movie movie, User user) {
        this.watchId = watchId;
        this.movie = movie;
        this.user = user;
    }

    public int getWatchId() {
        return watchId;
    }

    public void setWatchId(int watchId) {
        this.watchId = watchId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}