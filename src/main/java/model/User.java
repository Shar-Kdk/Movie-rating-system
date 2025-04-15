import java.util.Date;

public class User {

    private int userId;
    private String username;
    private String email;
    private String password;
    private String role;
    private String userAvatar;
    private int watchID;

    public User(int userId, String username, String email, String password, Date registerDate, int roleId, String userAvatar) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = "user";
        this.userAvatar = userAvatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getWatchID() {
        return watchID;
    }

    public void setWatchID(int watchID) {
        this.watchID = watchID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
