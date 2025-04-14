package model;

import java.util.Date;

public class Cast {
    private int castId;          // Primary key
    private String fullName;     // Actor's full name
    private Date birthdate;      // Actor's birthdate
    private String nationality;  // Actor's nationality
    private String biography;    // Actor's biography
    private String castImg;      // Path or URL to actor's image

    public Cast(int castId, String fullName, Date birthdate, String nationality, String biography, String castImg) {
        this.castId = castId;
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.biography = biography;
        this.castImg = castImg;
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getCastImg() {
        return castImg;
    }

    public void setCastImg(String castImg) {
        this.castImg = castImg;
    }
}
