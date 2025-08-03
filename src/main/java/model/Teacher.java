package model;

public class Teacher {
    private int id;
    private String name;

    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public String toText() {
        return id + "," + name;
    }

    public static Teacher fromText(String line) {
        String[] parts = line.split(",");
        return new Teacher(Integer.parseInt(parts[0]), parts[1]);
    }
}
