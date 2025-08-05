package service;

import model.User;
import util.IDGenerator;

import java.io.*;
import java.util.*;

public class UserService {
    private final List<User> users = new ArrayList<>();
    private final String FILE = "src/main/resources/data/users.txt";

    public UserService() {
        load();
    }

    public void add(User u) {
        users.add(u);
        save();
    }

    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    public User register(String username, String role) {
        boolean exists = users.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username) &&
                        u.getRole().equalsIgnoreCase(role));

        if (exists) {
            throw new RuntimeException("User already exists with this username and role.");
        }

        int id = IDGenerator.generateId();
        User user = new User(id, username, role);
        users.add(user);
        save();
        return user;
    }

    public boolean exists(String username, String role) {
        return getAll().stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username) && u.getRole().equalsIgnoreCase(role));
    }



    public User findByUsernameAndRole(String username, String role) {
        load();
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username) &&
                        u.getRole().equalsIgnoreCase(role))
                .findFirst()
                .orElse(null);
    }



    private void load() {
        users.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                users.add(User.fromText(line));
            }
        } catch (IOException ignored) {}
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (User u : users) {
                pw.println(u.toText());
            }
        } catch (IOException e) {
            System.out.println("Could not save users.");
        }
    }
}
