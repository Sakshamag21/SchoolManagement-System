package model;

public class User {
    private int id;
    private String username;
    private String role; // student, teacher, admin

    public User(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }

    public String toText() {
        return id + "," + username + "," + role;
    }

    public static User fromText(String line) {
        String[] parts = line.split(",");
        return new User(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2]
        );
    }
}
