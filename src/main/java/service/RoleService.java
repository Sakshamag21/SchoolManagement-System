package service;

import java.io.*;
import java.util.*;

public class RoleService {
    private final Set<String> roles = new HashSet<>();
    private final String FILE = "src/main/resources/data/roles.txt";

    public RoleService() {
        load();
    }

    public void addRole(String role) {
        role = role.toLowerCase().trim();
        if (!roles.contains(role)) {
            roles.add(role);
            save();
        }
    }

    public boolean exists(String role) {
        return roles.contains(role.toLowerCase().trim());
    }

    public List<String> getAllRoles() {
        return new ArrayList<>(roles);
    }

    private void load() {
        roles.clear();
        File file = new File(FILE);
        if (!file.exists()) {
            // Initialize with default roles
            roles.add("admin");
            roles.add("teacher");
            roles.add("student");
            save();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                roles.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("❌ Failed to load roles: " + e.getMessage());
        }
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (String role : roles) {
                pw.println(role);
            }
        } catch (IOException e) {
            System.out.println("❌ Failed to save roles: " + e.getMessage());
        }
    }
}
