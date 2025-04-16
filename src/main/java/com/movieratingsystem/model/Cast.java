package com.movieratingsystem.model;
import java.util.Date;

public class Cast {

    private int castId;
    private String castName;
    private Date birthDate;
    private String biography;
    private String photo;
    private String gender;
    private String charName;
    private Movie movie;

    public Cast(int castId, String castName, Date birthDate, String biography, String photo, String gender, String charName, Movie movie) {
        this.castId = castId;
        this.castName = castName;
        this.birthDate = birthDate;
        this.biography = biography;
        this.photo = photo;
        this.gender = gender;
        this.charName = charName;
        this.movie = movie;
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
