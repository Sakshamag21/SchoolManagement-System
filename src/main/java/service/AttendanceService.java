package service;

import model.Attendance;

import java.io.*;
import java.util.*;

public class AttendanceService {
    private final List<Attendance> records = new ArrayList<>();
    private final String FILE = "src/main/resources/data/attendance.txt";

    public AttendanceService() {
        load();
    }

    public void mark(Attendance a) {
        records.add(a);
        save();
    }

    public List<Attendance> getByStudentId(int studentId) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance a : records) {
            if (a.getStudentId() == studentId) {
                result.add(a);
            }
        }
        return result;
    }

    private void load() {
        records.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(Attendance.fromText(line));
            }
        } catch (IOException ignored) {}
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Attendance a : records) {
                pw.println(a.toText());
            }
        } catch (IOException e) {
            System.out.println("❌ Could not save attendance.");
        }
    }
}
