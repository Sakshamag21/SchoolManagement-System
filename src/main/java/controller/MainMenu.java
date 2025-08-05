package controller;

import model.Student;
import model.Teacher;
import model.User;
import service.StudentService;
import service.TeacherService;
import service.UserService;
import util.IDGenerator;
import util.InputHelper;

import java.util.Optional;

public class MainMenu {
    private final UserService userService = new UserService();

    public void show() {
        while (true) {
            System.out.println("\n[Main Menu]");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            Optional<Integer> choiceOpt = InputHelper.readInt("> ");
            if (choiceOpt.isEmpty()) {
                System.out.println(" Cancelled.");
                continue;
            }

            switch (choiceOpt.get()) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void login() {
        Optional<String> usernameOpt = InputHelper.readLine("Username: ");
        if (usernameOpt.isEmpty()) {
            System.out.println("Cancelled.");
            return;
        }

        Optional<String> roleOpt = InputHelper.readLine("Role (admin / teacher / student): ");
        if (roleOpt.isEmpty()) {
            System.out.println("Cancelled.");
            return;
        }

        String username = usernameOpt.get();
        String role = roleOpt.get().toLowerCase();

        User user = userService.findByUsernameAndRole(username, role);

        if (user == null) {
            System.out.println("Invalid credentials or role.");
            return;
        }

        System.out.println(" Login successful as " + user.getRole());

        switch (user.getRole()) {
            case "admin" -> new AdminController().start(user);
            case "teacher" -> new TeacherController().start(user);
            case "student" -> new StudentController().start(user);
            default -> System.out.println(" Unknown role.");
        }
    }

    private void register() {
        Optional<String> usernameOpt = InputHelper.readLine("Choose username: ");
        if (usernameOpt.isEmpty()) {
            System.out.println(" Cancelled.");
            return;
        }

        Optional<String> roleOpt = InputHelper.readLine("Role (student/teacher/admin): ");
        if (roleOpt.isEmpty()) {
            System.out.println(" Cancelled.");
            return;
        }

        String username = usernameOpt.get();
        String role = roleOpt.get().toLowerCase();

        if (!role.equals("student") && !role.equals("teacher") && !role.equals("admin")) {
            System.out.println(" Invalid role.");
            return;
        }

        try {
            User user = userService.register(username, role);
            System.out.println(" Registered successfully. You can now login.");
            if (role.equals("student")) {
                new StudentService().add(new Student(user.getId(), username, 0.0));
            }

            if (role.equals("teacher")) {
                new TeacherService().add(new Teacher(user.getId(), username));
            }

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
