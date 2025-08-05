package service;

import model.Teacher;
import util.FileUtil;
import util.IDGenerator;

import java.io.*;
import java.util.*;

public class TeacherService {
    private final List<Teacher> teachers = new ArrayList<>();
    private final String FILE = "src/main/resources/data/teachers.txt";

    public TeacherService() {
        load();
    }

    public void add(Teacher teacher) {
        FileUtil.appendLine(FILE, teacher.toText());
    }

    public List<Teacher> getAll() {
        load();
        return new ArrayList<>(teachers);
    }

    public Teacher getById(int id) {
        return getAll().stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }


    private void load() {
        teachers.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                teachers.add(Teacher.fromText(line));
            }
        } catch (IOException ignored) {}
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Teacher t : teachers) {
                pw.println(t.toText());
            }
        } catch (IOException e) {
            System.out.println("Could not save teachers.");
        }
    }
}
