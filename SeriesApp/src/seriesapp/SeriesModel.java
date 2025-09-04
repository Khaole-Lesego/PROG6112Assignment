package seriesapp;

/*
 * SeriesModel.java
 * PROG6112 Practical Assignment - Section A model class
 *
 * Student Name: Lesego Khaole
 * Student Number: ST10455441
 * Module: PROG6112
 * Assignment: Practical Assignment
 */

public class SeriesModel {
    private String seriesId;
    private String seriesName;
    private int seriesAge;              // 2..18
    private int seriesNumberOfEpisodes; // >= 1

    // Default constructor
    public SeriesModel() {
        this.seriesId = "";
        this.seriesName = "";
        this.seriesAge = 2;
        this.seriesNumberOfEpisodes = 1;
    }

    // Overloaded constructor (validates via setters)
    public SeriesModel(String seriesId, String seriesName, int seriesAge, int seriesNumberOfEpisodes) {
        setSeriesId(seriesId);
        setSeriesName(seriesName);
        setSeriesAge(seriesAge);
        setSeriesNumberOfEpisodes(seriesNumberOfEpisodes);
    }

    // Getters
    public String getSeriesId() { return seriesId; }
    public String getSeriesName() { return seriesName; }
    public int getSeriesAge() { return seriesAge; }
    public int getSeriesNumberOfEpisodes() { return seriesNumberOfEpisodes; }

    // Setters with validation
    public void setSeriesId(String seriesId) {
        if (seriesId == null) seriesId = "";
        this.seriesId = seriesId.trim();
    }

    public void setSeriesName(String seriesName) {
        if (seriesName == null) seriesName = "";
        this.seriesName = seriesName.trim();
    }

    public void setSeriesAge(int seriesAge) {
        if (seriesAge < 2 || seriesAge > 18) {
            throw new IllegalArgumentException("Series age must be between 2 and 18 inclusive.");
        }
        this.seriesAge = seriesAge;
    }

    public void setSeriesNumberOfEpisodes(int seriesNumberOfEpisodes) {
        if (seriesNumberOfEpisodes < 1) {
            throw new IllegalArgumentException("Series number of episodes must be >= 1.");
        }
        this.seriesNumberOfEpisodes = seriesNumberOfEpisodes;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Age: %d | Episodes: %d",
                seriesId, seriesName, seriesAge, seriesNumberOfEpisodes);
    }
}
