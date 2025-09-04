package seriesapp;

/*
 * Series.java
 * PROG6112 Practical Assignment - Section A business logic & methods
 *
 * Student Name: Lesego Khaole
 * Student Number: ST10455441
 * Module: PROG6112
 * Assignment: Practical Assignment
 */

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Series {
    private final ArrayList<SeriesModel> seriesList;

    public Series() {
        seriesList = new ArrayList<>();
    }

    // ==========================
    // Interactive methods (console)
    // ==========================
    public void captureSeries(Scanner sc) {
        System.out.println("\n--- Capture New Series ---");

        System.out.print("Enter Series ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Enter Series Name: ");
        String name = sc.nextLine().trim();

        int age = readValidatedInt(sc, "Enter Age restriction (2 - 18): ", 2, 18);
        int episodes = readValidatedInt(sc, "Enter number of episodes (>=1): ", 1, Integer.MAX_VALUE);

        SeriesModel model = new SeriesModel(id, name, age, episodes);
        seriesList.add(model);
        System.out.println("Series saved successfully.\n");
    }

    public boolean updateSeries(Scanner sc) {
        System.out.println("\n--- Update Series ---");
        System.out.print("Enter Series ID to update: ");
        String id = sc.nextLine().trim();
        SeriesModel s = searchSeries(id);
        if (s == null) {
            System.out.println("No series found with ID: " + id);
            return false;
        }
        System.out.println("Found: " + s);

        System.out.print("Enter new name (press Enter to keep current: " + s.getSeriesName() + "): ");
        String newName = sc.nextLine();
        if (!newName.trim().isEmpty()) s.setSeriesName(newName.trim());

        System.out.print("Enter new age restriction (2-18) or press Enter to keep current (" + s.getSeriesAge() + "): ");
        String ageLine = sc.nextLine().trim();
        if (!ageLine.isEmpty()) {
            int newAge = parseIntOrPrompt(sc, ageLine, 2, 18, "Age must be numeric between 2 and 18. Try again: ");
            s.setSeriesAge(newAge);
        }

        System.out.print("Enter new number of episodes or press Enter to keep current (" + s.getSeriesNumberOfEpisodes() + "): ");
        String epLine = sc.nextLine().trim();
        if (!epLine.isEmpty()) {
            int newEp = parseIntOrPrompt(sc, epLine, 1, Integer.MAX_VALUE, "Episodes must be an integer >= 1. Try again: ");
            s.setSeriesNumberOfEpisodes(newEp);
        }

        System.out.println("Series updated: " + s);
        return true;
    }

    public boolean deleteSeries(Scanner sc) {
        System.out.println("\n--- Delete Series ---");
        System.out.print("Enter Series ID to delete: ");
        String id = sc.nextLine().trim();
        SeriesModel s = searchSeries(id);
        if (s == null) {
            System.out.println("No series found with ID: " + id);
            return false;
        }
        System.out.println("Found: " + s);
        System.out.print("Are you sure you want to delete this series? (Y/N): ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            seriesList.remove(s);
            System.out.println("Series deleted.");
            return true;
        } else {
            System.out.println("Deletion canceled.");
            return false;
        }
    }

    public void seriesReport() {
        System.out.println("\n--- Series Report ---");
        if (seriesList.isEmpty()) {
            System.out.println("No series captured yet.");
            return;
        }
        System.out.printf("%-10s %-25s %-5s %-8s%n", "SeriesID", "Name", "Age", "Episodes");
        System.out.println("-----------------------------------------------------------");
        for (SeriesModel s : seriesList) {
            System.out.printf("%-10s %-25s %-5d %-8d%n",
                    s.getSeriesId(), s.getSeriesName(), s.getSeriesAge(), s.getSeriesNumberOfEpisodes());
        }
    }

    // ==========================
    // Programmatic methods (for tests)
    // ==========================
    public SeriesModel searchSeries(String seriesId) {
        if (seriesId == null) return null;
        Optional<SeriesModel> found = seriesList.stream()
                .filter(s -> s.getSeriesId().equalsIgnoreCase(seriesId.trim()))
                .findFirst();
        return found.orElse(null);
    }

    public boolean updateSeriesById(String id, String newName, Integer newAge, Integer newEpisodes) {
        SeriesModel s = searchSeries(id);
        if (s == null) return false;
        if (newName != null && !newName.trim().isEmpty()) s.setSeriesName(newName.trim());
        if (newAge != null) s.setSeriesAge(newAge);
        if (newEpisodes != null) s.setSeriesNumberOfEpisodes(newEpisodes);
        return true;
    }

    public boolean deleteSeriesById(String id) {
        SeriesModel s = searchSeries(id);
        if (s == null) return false;
        return seriesList.remove(s);
    }

    public int getCount() { return seriesList.size(); }
    public void addSeries(SeriesModel s) {
        if (s == null) throw new IllegalArgumentException("SeriesModel cannot be null.");
        seriesList.add(s);
    }

    // ==========================
    // Helpers
    // ==========================
    private int readValidatedInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(line);
                if (v < min || v > max) {
                    System.out.println("Invalid range. Please enter a value between " + min + " and " + max + ".");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number; please enter digits only.");
            }
        }
    }

    private int parseIntOrPrompt(Scanner sc, String firstTry, int min, int max, String retryPrompt) {
        String attempt = firstTry;
        while (true) {
            try {
                int v = Integer.parseInt(attempt);
                if (v < min || v > max) {
                    System.out.print(retryPrompt);
                    attempt = sc.nextLine().trim();
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.print(retryPrompt);
                attempt = sc.nextLine().trim();
            }
        }
    }

    // ==========================
    // Capitalised wrapper names (assignment compatibility)
    // ==========================
    public void CaptureSeries() { captureSeries(new Scanner(System.in)); }
    public SeriesModel SearchSeries(String seriesId) { return searchSeries(seriesId); }
    public boolean UpdateSeries() { return updateSeries(new Scanner(System.in)); }
    public boolean DeleteSeries() { return deleteSeries(new Scanner(System.in)); }
    public void SeriesReport() { seriesReport(); }
    public void ExitSeriesApplication() { System.out.println("Exit requested."); }
}
