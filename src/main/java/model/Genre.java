public class Genre {
    private int genreId;
    private String genreName;
    private int movieId; // foreign key

    public Genre(int genreId, String genreName, int movieId) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.movieId = movieId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
