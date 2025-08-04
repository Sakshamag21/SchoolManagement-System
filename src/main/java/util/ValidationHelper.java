package util;

import model.Teacher;
import service.CourseService;
import service.StudentService;
import service.TeacherService;

import java.util.Optional;

public class ValidationHelper {

    public static Integer readValidStudentId(StudentService studentService) {
        while (true) {
            Optional<String> inputOpt = InputHelper.readLine("Enter Student ID (or 'exit'): ");
            if (inputOpt.isEmpty()) return null;

            String input = inputOpt.get();
            try {
                int id = Integer.parseInt(input);
                if (studentService.getById(id) != null) {
                    return id;
                } else {
                    System.out.println("❌ Student not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number.");
            }
        }
    }

    public static Integer readValidTeacherId(TeacherService teacherService) {
        while (true) {
            Optional<String> inputOpt = InputHelper.readLine("Enter Teacher ID (or 'exit'): ");
            if (inputOpt.isEmpty()) return null;

            String input = inputOpt.get();
            try {
                int id = Integer.parseInt(input);
                Teacher teacher = teacherService.getById(id);
                if (teacher != null) {
                    return id;
                } else {
                    System.out.println("❌ Teacher not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number.");
            }
        }
    }

    public static Integer readValidCourseId(CourseService courseService) {
        while (true) {
            Optional<String> inputOpt = InputHelper.readLine("Enter Course ID (or 'exit'): ");
            if (inputOpt.isEmpty()) return null;

            String input = inputOpt.get();
            try {
                int id = Integer.parseInt(input);
                if (courseService.getById(id) != null) {
                    return id;
                } else {
                    System.out.println("❌ Course not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number.");
            }
        }
    }
}
