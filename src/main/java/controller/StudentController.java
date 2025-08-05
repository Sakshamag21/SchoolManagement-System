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
import java.util.Optional;

public class StudentController {
    private final AttendanceService attendanceService = new AttendanceService();
    private final GradeService gradeService = new GradeService();

    public void start(User user) {
        while (true) {
            System.out.println("\n[Student Menu]");
            System.out.println("1. View Attendance");
            System.out.println("2. View Grades");
            System.out.println("3. Logout");

            Optional<Integer> choiceOpt = InputHelper.readInt("> ");
            if (choiceOpt.isEmpty()) {
                System.out.println(" Cancelled.");
                continue;
            }

            switch (choiceOpt.get()) {
                case 1 -> viewAttendance(user.getId());
                case 2 -> viewGrades(user.getId());
                case 3 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println(" Invalid option");
            }
        }
    }

    private void viewAttendance(int studentId) {
        List<Attendance> records = attendanceService.getByStudentId(studentId);
        if (records.isEmpty()) {
            System.out.println("No attendance records.");
            return;
        }

        Optional<Boolean> sortOpt = InputHelper.readYesNo("Sort by date?");
        if (sortOpt.isPresent() && sortOpt.get()) {
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

        Optional<Boolean> sortOpt = InputHelper.readYesNo("Sort by score?");
        if (sortOpt.isPresent() && sortOpt.get()) {
            grades = ListUtil.sort(grades, Comparator.comparingDouble(Grade::getScore));
        }

        for (Grade g : grades) {
            System.out.printf("Course ID: %d | Score: %.2f\n", g.getCourseId(), g.getScore());
        }
    }
}
