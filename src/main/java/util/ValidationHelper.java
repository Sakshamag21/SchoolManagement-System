package util;

import model.Course;
import model.Student;
import service.CourseService;
import service.StudentService;

public class ValidationHelper {

    public static Integer readValidStudentId(StudentService studentService) {
        try {
            int id = InputHelper.readInt("Enter Student ID (or 'exit'): ");
            Student s = studentService.getById(id);
            if (s == null) {
                System.out.println("❌ Student not found.");
                return null;
            }
            System.out.println("✅ Found student: " + s.getName());
            return id;
        } catch (RuntimeException e) {
            System.out.println("⛔ " + e.getMessage());
            return null;
        }
    }

    public static Integer readValidCourseId(CourseService courseService) {
        try {
            int id = InputHelper.readInt("Enter Course ID (or 'exit'): ");
            Course c = courseService.getAll()
                    .stream()
                    .filter(course -> course.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (c == null) {
                System.out.println("❌ Course not found.");
                return null;
            }
            System.out.println("✅ Found course: " + c.getName());
            return id;
        } catch (RuntimeException e) {
            System.out.println("⛔ " + e.getMessage());
            return null;
        }
    }
}
