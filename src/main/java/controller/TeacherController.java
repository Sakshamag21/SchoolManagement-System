package controller;

import model.Attendance;
import model.Grade;
import model.Student;
import model.User;
import service.AttendanceService;
import service.CourseService;
import service.GradeService;
import service.StudentService;
import util.InputHelper;
import util.ListUtil;
import util.ValidationHelper;

import java.util.Comparator;
import java.util.List;

public class TeacherController {
    private final AttendanceService attendanceService = new AttendanceService();
    private final GradeService gradeService = new GradeService();
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();

    public void start(User user) {
        while (true) {
            System.out.println("\n[Teacher Menu]");
            System.out.println("1. Mark Attendance");
            System.out.println("2. Assign Grade");
            System.out.println("3. List Students by GPA");
            System.out.println("4. Logout");

            int choice;
            try {
                choice = InputHelper.readInt("> ");
            } catch (RuntimeException e) {
                System.out.println("⛔ " + e.getMessage());
                continue;
            }

            switch (choice) {
                case 1 -> markAttendance();
                case 2 -> assignGrade();
                case 3 -> listStudentsSortedByGpa();
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("❌ Invalid option");
            }
        }
    }

    private void markAttendance() {
        try {
            Integer studentId = ValidationHelper.readValidStudentId(studentService);
            if (studentId == null) return;

            String date = InputHelper.readLine("Date (yyyy-mm-dd): ");
            boolean present = InputHelper.readYesNo("Is present?");
            attendanceService.mark(new Attendance(studentId, date, present));
            System.out.println("✅ Attendance recorded.");
        } catch (RuntimeException e) {
            System.out.println("⛔ " + e.getMessage());
        }
    }

    private void assignGrade() {
        try {
            Integer studentId = ValidationHelper.readValidStudentId(studentService);
            if (studentId == null) return;

            Integer courseId = ValidationHelper.readValidCourseId(courseService);
            if (courseId == null) return;

            double score = InputHelper.readDouble("Score: ");
            gradeService.assign(new Grade(studentId, courseId, score));
            System.out.println("✅ Grade assigned.");
        } catch (RuntimeException e) {
            System.out.println("⛔ " + e.getMessage());
        }
    }

    private void listStudentsSortedByGpa() {
        List<Student> students = studentService.getAll();
        boolean ascending = InputHelper.readYesNo("Sort GPA ascending? (No = descending)");
        Comparator<Student> comparator = Comparator.comparingDouble(Student::getGpa);
        if (!ascending) comparator = comparator.reversed();
        students = ListUtil.sort(students, comparator);

        for (Student s : students) {
            System.out.println(s);
        }
    }
}
