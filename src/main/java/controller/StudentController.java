package controller;

import model.Attendance;
import model.Grade;
import model.User;
import service.AttendanceService;
import service.GradeService;
import util.InputHelper;
import util.ListUtil;

import java.util.Comparator;
import java.util.List;

public class StudentController {
    private final AttendanceService attendanceService = new AttendanceService();
    private final GradeService gradeService = new GradeService();

    public void start(User user) {
        while (true) {
            System.out.println("\n[Student Menu]");
            System.out.println("1. View Attendance");
            System.out.println("2. View Grades");
            System.out.println("3. Logout");

            int choice;
            try {
                choice = InputHelper.readInt("> ");
            } catch (RuntimeException e) {
                System.out.println("⛔ " + e.getMessage());
                continue;
            }

            switch (choice) {
                case 1 -> viewAttendance(user.getId());
                case 2 -> viewGrades(user.getId());
                case 3 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("❌ Invalid option");
            }
        }
    }

    private void viewAttendance(int studentId) {
        List<Attendance> records = attendanceService.getByStudentId(studentId);
        if (records.isEmpty()) {
            System.out.println("No attendance records.");
            return;
        }

        boolean sort = InputHelper.readYesNo("Sort by date?");
        if (sort) {
            records = ListUtil.sort(records, Comparator.comparing(Attendance::getDate));
        }

        for (Attendance a : records) {
            System.out.printf("%s - %s\n", a.getDate(), a.isPresent() ? "Present" : "Absent");
        }
    }

    private void viewGrades(int studentId) {
        List<Grade> grades = gradeService.getByStudentId(studentId);
        if (grades.isEmpty()) {
            System.out.println("No grades found.");
            return;
        }

        boolean sort = InputHelper.readYesNo("Sort by score?");
        if (sort) {
            grades = ListUtil.sort(grades, Comparator.comparingDouble(Grade::getScore));
        }

        for (Grade g : grades) {
            System.out.printf("Course ID: %d | Score: %.2f\n", g.getCourseId(), g.getScore());
        }
    }
}
