package service;

import model.Student;

import java.io.*;
import java.util.*;

public class StudentService {
    private final List<Student> students = new ArrayList<>();
    private final String FILE = "src/main/resources/data/students.txt";

    public StudentService() {
        load();
    }

    public void add(Student s) {
        students.add(s);
        save();
    }

    public Student getById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public List<Student> getAll() {
        return new ArrayList<>(students);
    }

    public boolean exists(int studentId) {
        return getById(studentId) != null;
    }


    private void load() {
        students.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                students.add(Student.fromText(line));
            }
        } catch (IOException ignored) {}
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Student s : students) {
                pw.println(s.toText());
            }
        } catch (IOException e) {
            System.out.println("❌ Could not save students.");
        }
    }
}
