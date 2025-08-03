package controller;

import model.User;
import service.UserService;
import util.IDGenerator;
import util.InputHelper;

public class MainMenu {
    private final UserService userService = new UserService();

    public void show() {
        while (true) {
            System.out.println("\n[Main Menu]");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            int choice = InputHelper.readInt("> ");
            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("❌ Invalid choice");
            }
        }
    }

    private void login() {
        String username = InputHelper.readLine("Username: ");
        User user = userService.getByUsername(username);
        if (user == null) {
            System.out.println("❌ User not found.");
            return;
        }

        System.out.println("✅ Logged in as " + user.getRole());
        switch (user.getRole().toLowerCase()) {
            case "admin" -> new AdminController().start(user);
            case "teacher" -> new TeacherController().start(user);
            case "student" -> new StudentController().start(user);
            default -> System.out.println("❌ Unknown role.");
        }
    }

    private void register() {
        String username = InputHelper.readLine("Choose username: ");
        String role = InputHelper.readLine("Role (student/teacher/admin): ").toLowerCase();

        if (!role.matches("student|teacher|admin")) {
            System.out.println("❌ Invalid role.");
            return;
        }

        if (userService.getByUsername(username) != null) {
            System.out.println("❌ Username already exists.");
            return;
        }

        int id = IDGenerator.getNextId("user");
        userService.add(new User(id, username, role));
        System.out.println("✅ Registered successfully. You can now login.");
    }
}
