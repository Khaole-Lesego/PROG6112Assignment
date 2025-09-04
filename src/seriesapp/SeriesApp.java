package seriesapp;

/*
 * SeriesApp.java
 * PROG6112 Practical Assignment - Section A menu app
 *
 * Student Name: Lesego Khaole
 * Student Number: ST10455441
 * Module: PROG6112
 * Assignment: Practical Assignment
 */

import java.util.Scanner;

public class SeriesApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Series seriesManager = new Series();
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Select option: ");
            String option = sc.nextLine().trim();

            switch (option) {
                case "1":
                    seriesManager.captureSeries(sc);
                    break;
                case "2":
                    System.out.print("Enter Series ID to search: ");
                    String id = sc.nextLine().trim();
                    SeriesModel found = seriesManager.searchSeries(id);
                    if (found != null) System.out.println("Found: " + found);
                    else System.out.println("No series found with ID: " + id);
                    break;
                case "3":
                    seriesManager.updateSeries(sc);
                    break;
                case "4":
                    seriesManager.deleteSeries(sc);
                    break;
                case "5":
                    seriesManager.seriesReport();
                    break;
                case "6":
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-6.");
            }
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n--- TV Series Management ---");
        System.out.println("1. Capture Series");
        System.out.println("2. Search Series");
        System.out.println("3. Update Series");
        System.out.println("4. Delete Series");
        System.out.println("5. Series Report");
        System.out.println("6. Exit");
    }
}
