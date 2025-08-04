package util;

import java.util.Optional;
import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static Optional<Integer> readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) return Optional.empty();
            try {
                return Optional.of(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number. Try again.");
            }
        }
    }

    public static Optional<Double> readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) return Optional.empty();
            try {
                return Optional.of(Double.parseDouble(input));
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid decimal number. Try again.");
            }
        }
    }

    public static Optional<String> readLine(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("exit")) return Optional.empty();
        return Optional.of(input);
    }

    public static Optional<Boolean> readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equalsIgnoreCase("exit")) return Optional.empty();
            if (input.equals("y") || input.equals("yes")) return Optional.of(true);
            if (input.equals("n") || input.equals("no")) return Optional.of(false);
            System.out.println("❌ Please enter y/n or exit.");
        }
    }
}
