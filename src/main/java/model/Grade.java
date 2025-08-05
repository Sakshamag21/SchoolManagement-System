package model;

public class Grade {
    private int studentId;
    private int courseId;
    private double score;

    public Grade(int studentId, int courseId, double score) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
    }
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public double getScore() { return score; }

    public String toText() {
        return studentId + "," + courseId + "," + score;
    }
    public static Grade fromText(String line) {
        String[] parts = line.split(",");
        return new Grade(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Double.parseDouble(parts[2])
        );
    }
}
