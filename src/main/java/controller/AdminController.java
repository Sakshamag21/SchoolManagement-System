package controller;

import model.Course;
import model.Student;
import model.Teacher;
import model.User;
import service.CourseService;
import service.StudentService;
import service.TeacherService;
import util.InputHelper;
import util.ListUtil;

import java.util.Comparator;
import java.util.List;

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

            int choice;
            try {
                choice = InputHelper.readInt("> ");
            } catch (RuntimeException e) {
                System.out.println("⛔ " + e.getMessage());
                continue;
            }

            switch (choice) {
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
        try {
            String teacherName = InputHelper.readLine("Enter teacher name (or 'exit'): ");
            Teacher teacher = teacherService.add(teacherName);
            System.out.println("✅ Teacher added with ID: " + teacher.getId());
        } catch (RuntimeException e) {
            System.out.println("⛔ " + e.getMessage());
        }
    }

    private void addCourse() {
        try {
            String courseName = InputHelper.readLine("Enter course name (or 'exit'): ");
            int teacherId = InputHelper.readInt("Enter teacher ID (or 'exit'): ");
            Course course = courseService.add(courseName, teacherId);
            System.out.println("✅ Course added with ID: " + course.getId());
        } catch (RuntimeException e) {
            System.out.println("⛔ " + e.getMessage());
        }
    }

    private void viewCourses() {
        List<Course> courses = courseService.getAll();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        boolean sort = InputHelper.readYesNo("Sort by name?");
        if (sort) {
            courses = ListUtil.sort(courses, Comparator.comparing(Course::getName));
        }

        for (Course c : courses) {
            System.out.printf("ID: %d | Name: %s | Teacher ID: %d\n", c.getId(), c.getName(), c.getTeacherId());
        }
    }

    private void viewStudentsByGpa() {
        List<Student> students = studentService.getAll();

        double minGpa;
        try {
            minGpa = InputHelper.readDouble("Enter minimum GPA to filter: ");
        } catch (RuntimeException e) {
            System.out.println("⛔ " + e.getMessage());
            return;
        }

        List<Student> filtered = ListUtil.filter(students, s -> s.getGpa() >= minGpa);
        boolean sort = InputHelper.readYesNo("Sort by GPA descending?");
        if (sort) {
            filtered = ListUtil.sort(filtered, Comparator.comparingDouble(Student::getGpa).reversed());
        }

        for (Student s : filtered) {
            System.out.println(s);
        }
    }
}
