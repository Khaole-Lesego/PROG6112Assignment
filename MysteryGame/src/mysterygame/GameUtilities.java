package mysterygame;

import java.util.Scanner;

/**
 * GameUtilities.java
 *
 * Enhanced utility class with reusable static input and formatting methods.
 * Provides robust input validation and consistent formatting for the Mystery Game.
 * 
 * Features:
 * - Input validation with retry logic
 * - Consistent error handling
 * - Formatted output methods
 * - Safe Scanner operations
 */
public final class GameUtilities {

    private GameUtilities() { 
        // Prevent instantiation - utility class only
    }

    /**
     * Gets a validated integer input within specified bounds
     */
    public static int getValidatedInt(Scanner sc, String prompt, int min, int max) {
        if (sc == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }
        
        while (true) {
            System.out.print(prompt);
            String line;
            
            try {
                line = sc.nextLine();
            } catch (Exception e) {
                // Fallback for scanner issues
                try { 
                    line = sc.next(); 
                } catch (Exception ex) { 
                    line = ""; 
                }
            }
            
            if (line == null) line = "";
            line = line.trim();
            
            if (line.isEmpty()) {
                System.out.println("Please enter a number.");
                continue;
            }
            
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.printf("Please enter a number between %d and %d.%n", min, max);
                    continue;
                }
                return value;
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid number format. Please try again.");
            }
        }
    }

    /**
     * Gets a line of text input with optional prompt
     */
    public static String nextLine(Scanner sc, String prompt) {
        if (sc == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }
        
        if (prompt != null && !prompt.isEmpty()) {
            System.out.print(prompt);
        }
        
        try {
            String line = sc.nextLine();
            return (line == null) ? "" : line.trim();
        } catch (Exception e) {
            try {
                String token = sc.next();
                return (token == null) ? "" : token.trim();
            } catch (Exception ex) {
                return "";
            }
        }
    }

    /**
     * Gets a yes/no confirmation from user
     */
    public static boolean confirmYesNo(Scanner sc, String prompt) {
        if (sc == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }
        
        while (true) {
            String response = nextLine(sc, prompt).toLowerCase();
            
            if (response.isEmpty()) {
                System.out.println("Please answer 'y' for yes or 'n' for no.");
                continue;
            }
            
            char firstChar = response.charAt(0);
            if (firstChar == 'y') {
                return true;
            } else if (firstChar == 'n') {
                return false;
            } else {
                System.out.println("Please answer 'y' for yes or 'n' for no.");
            }
        }
    }

    /**
     * Prints a decorative divider line
     */
    public static void printDivider() {
        System.out.println("=" .repeat(60));
    }

    /**
     * Prints a simple divider line
     */
    public static void printSimpleDivider() {
        System.out.println("-".repeat(40));
    }

    /**
     * Waits for user to press Enter to continue
     */
    public static void pressEnterToContinue(Scanner sc) {
        System.out.print("Press Enter to continue...");
        try { 
            sc.nextLine(); 
        } catch (Exception ignored) {
            // If readline fails, just continue
        }
    }

    /**
     * Prints formatted header text
     */
    public static void printHeader(String title) {
        printDivider();
        int padding = (60 - title.length()) / 2;
        System.out.println(" ".repeat(Math.max(0, padding)) + title.toUpperCase());
        printDivider();
    }

    /**
     * Prints formatted section header
     */
    public static void printSectionHeader(String section) {
        System.out.println();
        System.out.println("=== " + section.toUpperCase() + " ===");
    }

    /**
     * Prints an error message with consistent formatting
     */
    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    /**
     * Prints a warning message with consistent formatting
     */
    public static void printWarning(String message) {
        System.out.println("[WARNING] " + message);
    }

    /**
     * Prints an info message with consistent formatting
     */
    public static void printInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    /**
     * Clears the console (attempts to, may not work on all systems)
     */
    public static void clearScreen() {
        try {
            // This works on most modern terminals
            System.out.print("\033[2J\033[H");
            System.out.flush();
        } catch (Exception e) {
            // Fallback - print several blank lines
            for (int i = 0; i < 25; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Prints a menu with numbered options
     */
    public static void printMenu(String title, String[] options) {
        printSectionHeader(title);
        for (int i = 0; i < options.length; i++) {
            if (options[i] != null && !options[i].trim().isEmpty()) {
                System.out.println(" " + (i + 1) + ") " + options[i]);
            }
        }
    }

    /**
     * Validates that a string is not null or empty
     */
    public static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Safe string comparison that handles nulls
     */
    public static boolean safeEquals(String str1, String str2) {
        if (str1 == null && str2 == null) return true;
        if (str1 == null || str2 == null) return false;
        return str1.equals(str2);
    }

    /**
     * Gets a non-empty string from user with validation
     */
    public static String getNonEmptyString(Scanner sc, String prompt) {
        while (true) {
            String input = nextLine(sc, prompt);
            if (isValidString(input)) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Formats a number with appropriate suffix (1st, 2nd, 3rd, 4th, etc.)
     */
    public static String formatOrdinal(int number) {
        if (number >= 11 && number <= 13) {
            return number + "th";
        }
        
        switch (number % 10) {
            case 1: return number + "st";
            case 2: return number + "nd"; 
            case 3: return number + "rd";
            default: return number + "th";
        }
    }
}