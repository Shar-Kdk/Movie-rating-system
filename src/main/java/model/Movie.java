import java.util.Date;

public class Movie {

    private int movieId;
    private String title;
    private Date releaseDate;
    private int duration; // in minute
    private String movieImg;

    public Movie(int movieId, String title, Date releaseDate, int duration, String movieImg) {
        this.movieId = movieId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.movieImg = movieImg;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMovieImg() {
        return movieImg;
    }

    public void setMovieImg(String movieImg) {
        this.movieImg = movieImg;
    }
}
