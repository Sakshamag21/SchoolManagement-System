package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {
    private static final String FILE_PATH = "src/main/resources/data/id_tracker.txt";
    private static final Map<String, Integer> idMap = new HashMap<>();

    static {
        loadIds();
    }

    private static final AtomicInteger counter = new AtomicInteger(1000);

    public static int generateId() {
        return counter.getAndIncrement();
    }

    private static void loadIds() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                idMap.put(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (IOException e) {
            System.out.println("Failed to load ID tracker.");
        }
    }


}
