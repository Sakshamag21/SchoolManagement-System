package service;

import model.Grade;

import java.io.*;
import java.util.*;

public class GradeService {
    private final List<Grade> grades = new ArrayList<>();
    private final String FILE = "src/main/resources/data/grades.txt";

    public GradeService() {
        load();
    }

    public void assign(Grade g) {
        grades.add(g);
        save();
        updateStudentGpa(g.getStudentId());

    }

    public List<Grade> getByStudentId(int studentId) {
        List<Grade> result = new ArrayList<>();
        for (Grade g : grades) {
            if (g.getStudentId() == studentId) {
                result.add(g);
            }
        }
        return result;
    }

    private void updateStudentGpa(int studentId) {
        load();
        List<Grade> studentGrades = getByStudentId(studentId);
        if (studentGrades.isEmpty()) return;

        double total = 0;
        for (Grade g : studentGrades) {
            total += g.getScore();
        }

        double gpa = total / studentGrades.size();

        // Now update in students.txt
        StudentService studentService = new StudentService();
        studentService.updateGpa(studentId, gpa);
    }


    private void load() {
        grades.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                grades.add(Grade.fromText(line));
            }
        } catch (IOException ignored) {}
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Grade g : grades) {
                pw.println(g.toText());
            }
        } catch (IOException e) {
            System.out.println("❌ Could not save grades.");
        }
    }
}
