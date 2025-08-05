package model;

public class Attendance {
    private int studentId;
    private String date;
    private boolean present;

    public Attendance(int studentId, String date, boolean present) {
        this.studentId = studentId;
        this.date = date;
        this.present = present;
    }

    public int getStudentId() { return studentId; }
    public String getDate() { return date; }
    public boolean isPresent() { return present; }

    public String toText() {
        return studentId + "," + date + "," + (present ? "1" : "0");
    }

    public static Attendance fromText(String line) {
        String[] parts = line.split(",");
        return new Attendance(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2].equals("1")
        );
    }
}
