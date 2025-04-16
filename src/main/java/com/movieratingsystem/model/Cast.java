import java.util.Date;

public class Cast {

    private int CastId;
    private String Cast_name;
    private Date birth_date;
    private String biography;
    private String photo;
    private String gender;
    private String char_name;

    public Cast(int castId, String cast_name, Date birth_date, String biography, String photo, String gender, String char_name) {
        CastId = castId;
        Cast_name = cast_name;
        this.birth_date = birth_date;
        this.biography = biography;
        this.photo = photo;
        this.gender = gender;
        this.char_name = char_name;
    }

    public int getCastId() {
        return CastId;
    }

    public void setCastId(int castId) {
        CastId = castId;
    }

    public String getCast_name() {
        return Cast_name;
    }

    public void setCast_name(String cast_name) {
        Cast_name = cast_name;
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

    public String getChar_name() {
        return char_name;
    }

    public void setChar_name(String char_name) {
        this.char_name = char_name;
    }
}
