import java.util.Date;

public class Director {

    private int directorId;
    private String director_name;
    private Date birth_date;
    private String biography;
    private String director_Img;

    public Director(int directorId, String director_name, Date birth_date, String biography, String director_Img) {
        this.directorId = directorId;
        this.director_name = director_name;
        this.birth_date = birth_date;
        this.biography = biography;
        this.director_Img = director_Img;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public String getDirector_name() {
        return director_name;
    }

    public void setDirector_name(String director_name) {
        this.director_name = director_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getDirector_Img() {
        return director_Img;
    }

    public void setDirector_Img(String director_Img) {
        this.director_Img = director_Img;
    }
}
