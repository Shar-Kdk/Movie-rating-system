package com.movieratingsystem.model;

import java.util.Date;

public class Movie {

    private int movieId;
    private String title;
    private Date releaseDate;
    private String image;
    private int minutes;

    public Movie(int movieId, String title, Date releaseDate, String image, int minutes) {
        this.movieId = movieId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.image = image;
        this.minutes = minutes;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
