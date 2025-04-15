import java.util.Date;

public class Actor {

    private int actorId;
    private String actor_name;
    private Date birth_date;
    private String Gender;
    private String character_name;
    private String actor_Img;
    private int movieId; // foreign key


    public Actor(int actorId, String actor_name, Date birth_date, String gender, String character_name, String actor_Img) {
        this.actorId = actorId;
        this.actor_name = actor_name;
        this.birth_date = birth_date;
        Gender = gender;
        this.character_name = character_name;
        this.movieId = movieId;
        this.actor_Img = actor_Img;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getActor_name() {
        return actor_name;
    }

    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCharacter_name() {
        return character_name;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    public String getActor_Img() {
        return actor_Img;
    }

    public void setActor_Img(String actor_Img) {
        this.actor_Img = actor_Img;
    }
}
