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
import java.util.Optional;

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

            Optional<Integer> choiceOpt = InputHelper.readInt("> ");
            if (choiceOpt.isEmpty()) {
                System.out.println("⛔ Cancelled.");
                continue;
            }

            switch (choiceOpt.get()) {
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
        Integer studentId = ValidationHelper.readValidStudentId(studentService);
        if (studentId == null) {
            System.out.println("⛔ Cancelled.");
            return;
        }

        Optional<String> dateOpt = InputHelper.readLine("Date (yyyy-mm-dd): ");
        if (dateOpt.isEmpty()) {
            System.out.println("⛔ Cancelled.");
            return;
        }

        Optional<Boolean> presentOpt = InputHelper.readYesNo("Is present?");
        if (presentOpt.isEmpty()) {
            System.out.println("⛔ Cancelled.");
            return;
        }

        attendanceService.mark(new Attendance(studentId, dateOpt.get(), presentOpt.get()));
        System.out.println("✅ Attendance recorded.");
    }

    private void assignGrade() {
        Integer studentId = ValidationHelper.readValidStudentId(studentService);
        if (studentId == null) {
            System.out.println("⛔ Cancelled.");
            return;
        }

        Integer courseId = ValidationHelper.readValidCourseId(courseService);
        if (courseId == null) {
            System.out.println("⛔ Cancelled.");
            return;
        }

        Optional<Double> scoreOpt = InputHelper.readDouble("Score: ");
        if (scoreOpt.isEmpty()) {
            System.out.println("⛔ Cancelled.");
            return;
        }

        gradeService.assign(new Grade(studentId, courseId, scoreOpt.get()));
        System.out.println("✅ Grade assigned.");
    }

    private void listStudentsSortedByGpa() {
        List<Student> students = studentService.getAll();

        Optional<Boolean> ascendingOpt = InputHelper.readYesNo("Sort GPA ascending? (No = descending)");
        Comparator<Student> comparator = Comparator.comparingDouble(Student::getGpa);

        if (ascendingOpt.isPresent() && !ascendingOpt.get()) {
            comparator = comparator.reversed();
        }

        students = ListUtil.sort(students, comparator);
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
