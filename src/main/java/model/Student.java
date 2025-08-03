package model;

public class Student {
    private int id;
    private String name;
    private double gpa;

    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getGpa() { return gpa; }

    public String toText() {
        return id + "," + name + "," + gpa;
    }

    public static Student fromText(String line) {
        String[] parts = line.split(",");
        return new Student(
                Integer.parseInt(parts[0]),
                parts[1],
                Double.parseDouble(parts[2])
        );
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | GPA: " + gpa;
    }
}
