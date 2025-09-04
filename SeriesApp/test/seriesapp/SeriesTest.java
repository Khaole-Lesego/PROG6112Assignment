package seriesapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class SeriesTest {
    private Series manager;

    @BeforeEach
    public void setup() {
        manager = new Series();
        // Deterministic test data
        manager.addSeries(new SeriesModel("S01", "Sample Show", 12, 10));
        manager.addSeries(new SeriesModel("S02", "Another Show", 8, 24));
    }

    @Test
    public void TestSearchSeries() {
        SeriesModel found = manager.searchSeries("S01");
        assertNotNull(found, "S01 should be found");
        assertEquals("Sample Show", found.getSeriesName());
    }

    @Test
    public void TestSearchSeries_SeriesNotFound() {
        assertNull(manager.searchSeries("NOT_EXIST"), "Non-existent series should return null");
    }

    @Test
    public void TestUpdateSeries() {
        boolean result = manager.updateSeriesById("S01", "Updated Name", null, null);
        assertTrue(result, "Update should succeed for existing series");
        SeriesModel updated = manager.searchSeries("S01");
        assertEquals("Updated Name", updated.getSeriesName());
        // unchanged fields
        assertEquals(12, updated.getSeriesAge());
        assertEquals(10, updated.getSeriesNumberOfEpisodes());
    }

    @Test
    public void TestDeleteSeries() {
        boolean deleted = manager.deleteSeriesById("S02");
        assertTrue(deleted, "S02 should be deleted");
        assertNull(manager.searchSeries("S02"));
        assertEquals(1, manager.getCount());
    }

    @Test
    public void TestDeleteSeries_SeriesNotFound() {
        assertFalse(manager.deleteSeriesById("NOPE"), "Deletion should fail for non-existent ID");
        assertEquals(2, manager.getCount());
    }

    @Test
    public void TestSeriesAgeRestriction_AgeValid() {
        manager.addSeries(new SeriesModel("A1", "ValidAge", 5, 5));
        SeriesModel found = manager.searchSeries("A1");
        assertNotNull(found);
        assertTrue(found.getSeriesAge() >= 2 && found.getSeriesAge() <= 18);
    }

    @Test
    public void TestSeriesAgeRestriction_SeriesAgeInValid() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SeriesModel("A2", "InvalidAge", 50, 10);
        });
    }
}
