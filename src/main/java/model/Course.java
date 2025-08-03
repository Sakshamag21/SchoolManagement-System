package model;

public class Course {
    private int id;
    private String name;
    private int teacherId;

    public Course(int id, String name, int teacherId) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getTeacherId() { return teacherId; }

    public String toText() {
        return id + "," + name + "," + teacherId;
    }

    public static Course fromText(String line) {
        String[] parts = line.split(",");
        return new Course(
                Integer.parseInt(parts[0]),
                parts[1],
                Integer.parseInt(parts[2])
        );
    }
}
