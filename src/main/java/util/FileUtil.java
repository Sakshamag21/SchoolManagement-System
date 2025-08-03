package util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static void appendLine(String filename, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("❌ Error writing to file: " + filename);
        }
    }

    public static List<String> readLines(String filename) {
        List<String> lines = new ArrayList<>();
        Path path = Paths.get(filename);

        if (!Files.exists(path)) {
            return lines; // return empty list
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + filename);
        }

        return lines;
    }

    public static void overwrite(String filename, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error writing to file: " + filename);
        }
    }
}
