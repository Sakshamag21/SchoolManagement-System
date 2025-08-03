package util;

import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                throw new RuntimeException("Input cancelled by user.");
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number. Try again.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                throw new RuntimeException("Input cancelled by user.");
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid decimal number. Try again.");
            }
        }
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("exit")) {
            throw new RuntimeException("Input cancelled by user.");
        }
        return input;
    }

    public static boolean readYesNo(String prompt) {
        System.out.print(prompt + " (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("exit")) {
            throw new RuntimeException("Input cancelled by user.");
        }
        return input.equals("y") || input.equals("yes");
    }
}
