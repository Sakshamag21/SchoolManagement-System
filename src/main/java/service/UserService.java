package service;

import model.User;

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

    public User getByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
    }

    public List<User> getAll() {
        return new ArrayList<>(users);
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
            System.out.println("❌ Could not save users.");
        }
    }
}
