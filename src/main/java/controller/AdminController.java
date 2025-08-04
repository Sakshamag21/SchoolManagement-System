package controller;

import model.Course;
import model.Student;
import model.Teacher;
import model.User;
import service.CourseService;
import service.StudentService;
import service.TeacherService;
import service.UserService;
import util.IDGenerator;
import util.InputHelper;
import util.ListUtil;
import util.ValidationHelper;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AdminController {
    private final TeacherService teacherService = new TeacherService();
    private final CourseService courseService = new CourseService();
    private final StudentService studentService = new StudentService();

    public void start(User user) {
        while (true) {
            System.out.println("\n[Admin Menu]");
            System.out.println("1. Add Teacher");
            System.out.println("2. Add Course");
            System.out.println("3. View Courses");
            System.out.println("4. View Students by GPA");
            System.out.println("5. Logout");

            Optional<Integer> choiceOpt = InputHelper.readInt("> ");
            if (choiceOpt.isEmpty()) {
                System.out.println("⛔ Cancelled.");
                continue;
            }

            switch (choiceOpt.get()) {
                case 1 -> addTeacher();
                case 2 -> addCourse();
                case 3 -> viewCourses();
                case 4 -> viewStudentsByGpa();
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("❌ Invalid option");
            }
        }
    }

    private void addTeacher() {
        Optional<String> nameOpt = InputHelper.readLine("Enter teacher name (or 'exit'): ");
        if (nameOpt.isEmpty()) {
            System.out.println("⛔ Cancelled.");
            return;
        }

        String name = nameOpt.get().trim().toLowerCase();
        UserService userService = new UserService();

        // Check if username already exists with role teacher
        if (userService.exists(name, "teacher")) {
            System.out.println("❌ A teacher with this username already exists.");
            return;
        }

        try {
            // Register teacher in users.txt
            User user = userService.register(name, "teacher");

            // Add to teachers.txt
            Teacher teacher = new Teacher(user.getId(), name);
            new TeacherService().add(teacher);

            System.out.println("✅ Teacher added with ID: " + teacher.getId());

        } catch (RuntimeException e) {
            System.out.println("⛔ " + e.getMessage());
        }
    }


    private void addCourse() {
        Optional<String> courseNameOpt = InputHelper.readLine("Enter course name (or 'exit'): ");
        if (courseNameOpt.isEmpty()) {
            System.out.println("⛔ Cancelled.");
            return;
        }

        Integer teacherId = ValidationHelper.readValidTeacherId(teacherService);
        if (teacherId == null) {
            System.out.println("⛔ Cancelled.");
            return;
        }

        Course course = courseService.add(courseNameOpt.get(), teacherId);
        System.out.println("✅ Course added with ID: " + course.getId());
    }

    private void viewCourses() {
        List<Course> courses = courseService.getAll();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        Optional<Boolean> sortOpt = InputHelper.readYesNo("Sort by name?");
        if (sortOpt.isPresent() && sortOpt.get()) {
            courses = ListUtil.sort(courses, Comparator.comparing(Course::getName));
        }

        for (Course c : courses) {
            System.out.printf("ID: %d | Name: %s | Teacher ID: %d\n", c.getId(), c.getName(), c.getTeacherId());
        }
    }

    private void viewStudentsByGpa() {
        List<Student> students = studentService.getAll();

        Optional<Double> gpaOpt = InputHelper.readDouble("Enter minimum GPA to filter: ");
        if (gpaOpt.isEmpty()) {
            System.out.println("⛔ Cancelled.");
            return;
        }

        List<Student> filtered = ListUtil.filter(students, s -> s.getGpa() >= gpaOpt.get());

        Optional<Boolean> sortOpt = InputHelper.readYesNo("Sort by GPA descending?");
        if (sortOpt.isPresent() && sortOpt.get()) {
            filtered = ListUtil.sort(filtered, Comparator.comparingDouble(Student::getGpa).reversed());
        }

        for (Student s : filtered) {
            System.out.println(s);
        }
    }
}
