package mysterygame;

import java.util.Scanner;

/**
 * GameUtilities.java
 *
 * Utility helpers for input validation, menu handling, and formatting.
 */
public final class GameUtilities {

    private GameUtilities() {}

    public static void printDivider() {
        System.out.println("--------------------------------------------------");
    }

    public static String nextLine(Scanner gameScanner, String prompt) {
        if (prompt != null && !prompt.isEmpty()) {
            System.out.print(prompt);
        }
        String line = "";
        try {
            line = gameScanner.nextLine();
        } catch (Exception ignored) {}
        return line == null ? "" : line.trim();
    }

    public static boolean confirmYesNo(Scanner gameScanner, String prompt) {
        while (true) {
            String response = nextLine(gameScanner, prompt).toLowerCase();
            if (response.startsWith("y")) return true;
            if (response.startsWith("n")) return false;
            System.out.println("Please answer 'y' or 'n'.");
        }
    }

    public static int getValidatedInt(Scanner gameScanner, String prompt, int min, int max) {
        while (true) {
            String input = nextLine(gameScanner, prompt);
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {}
            System.out.printf("Enter a number between %d and %d.%n", min, max);
        }
    }

    public static int chooseFromMenu(Scanner gameScanner, String prompt, String[] options) {
        System.out.println();
        System.out.println(prompt);
        for (int i = 0; i < options.length; i++) {
            System.out.printf(" %d) %s%n", i + 1, options[i]);
        }
        return getValidatedInt(gameScanner, "Enter choice: ", 1, options.length);
    }
}
