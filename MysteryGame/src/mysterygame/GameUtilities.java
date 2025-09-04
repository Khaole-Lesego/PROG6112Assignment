package mysterygame;

import java.util.Scanner;

/**
 * GameUtilities.java
 *
 * Small helper class with reusable static input and formatting utilities.
 * - Centralizes input validation to keep MysteryGameMain tidy.
 * - Uses a single Scanner provided by the caller.
 *
 * Public API:
 *  - getValidatedInt(Scanner, prompt, min, max)
 *  - nextLine(Scanner, prompt)
 *  - confirmYesNo(Scanner, prompt)
 *  - printDivider()
 *  - pressEnterToContinue(Scanner)
 *
 * Note: These helpers do not close the Scanner. The application should close it.
 */
public final class GameUtilities {

    private GameUtilities() { /* no instances */ }

    public static int getValidatedInt(Scanner sc, String prompt, int min, int max) {
        if (sc == null) throw new IllegalArgumentException("Scanner cannot be null");
        while (true) {
            System.out.print(prompt);
            String line;
            try {
                line = sc.nextLine();
            } catch (Exception e) {
                // As a fallback, try to read next token
                try { line = sc.next(); } catch (Exception ex) { line = ""; }
            }
            if (line == null) line = "";
            line = line.trim();
            try {
                int v = Integer.parseInt(line);
                if (v < min || v > max) {
                    System.out.printf("Please enter a number between %d and %d.%n", min, max);
                    continue;
                }
                return v;
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid integer input. Please try again.");
            }
        }
    }

    public static String nextLine(Scanner sc, String prompt) {
        if (sc == null) throw new IllegalArgumentException("Scanner cannot be null");
        if (prompt != null && !prompt.isEmpty()) System.out.print(prompt);
        try {
            String line = sc.nextLine();
            return (line == null) ? "" : line;
        } catch (Exception e) {
            try {
                return sc.next();
            } catch (Exception ex) {
                return "";
            }
        }
    }

    public static boolean confirmYesNo(Scanner sc, String prompt) {
        if (sc == null) throw new IllegalArgumentException("Scanner cannot be null");
        while (true) {
            String resp = nextLine(sc, prompt).trim().toLowerCase();
            if (resp.isEmpty()) continue;
            char c = resp.charAt(0);
            if (c == 'y') return true;
            if (c == 'n') return false;
            System.out.println("Please answer 'y' or 'n'.");
        }
    }

    public static void printDivider() {
        System.out.println("--------------------------------------------------");
    }

    public static void pressEnterToContinue(Scanner sc) {
        System.out.print("Press Enter to continue...");
        try { sc.nextLine(); } catch (Exception ignored) {}
    }
}
