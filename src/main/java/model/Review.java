import java.util.Date;

public class Review {

    private int reviewId;
    private String comment;
    private Date reviewedAt;
    private boolean isSpoiler;
    private int movieId;

    public Review(int reviewId, String comment, Date reviewedAt, boolean isSpoiler, int movieId) {
        this.reviewId = reviewId;
        this.comment = comment;
        this.reviewedAt = reviewedAt;
        this.isSpoiler = isSpoiler;
        this.movieId = movieId;
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

    public Date getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Date reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public boolean isSpoiler() {
        return isSpoiler;
    }

    public void setSpoiler(boolean spoiler) {
        isSpoiler = spoiler;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
