package service;

import model.Course;
import util.FileUtil;
import util.IDGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final List<Course> courses = new ArrayList<>();
    private final String FILE = "src/main/resources/data/courses.txt";

    public CourseService() {
        load();
    }

    public Course add(String name, int teacherId) {
        int id = IDGenerator.generateId();
        Course course = new Course(id, name, teacherId);
        courses.add(course);
        save();
        return course;
    }

    public void add(Course c) {
        courses.add(c);
        save();
    }

    public List<Course> getAll() {
        return new ArrayList<>(courses);
    }

    public Course getById(int id) {
        return courses.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void load() {
        courses.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                courses.add(Course.fromText(line));
            }
        } catch (IOException e) {
            System.out.println("⚠️ Could not load courses.");
        }
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Course c : courses) {
                pw.println(c.toText());
            }
        } catch (IOException e) {
            System.out.println("❌ Could not save courses.");
        }
    }
}
