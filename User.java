class User {
    int userId;
    String username;
    String email;
    String password;
    String userType;

    User(int userId, String username, String email, String password, String userType) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    void update(String username, String email, String password, String userType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    String toCSV() {
        return userId + "," + username + "," + email + "," + password + "," + userType;
    }
}
