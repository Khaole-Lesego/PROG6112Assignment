package seriesapp;

// These are tools that help us write tests to check if our code works properly.
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals; // Helps check if two values are the same.
import static org.junit.Assert.assertTrue;   // Helps check if something is true.
import static org.junit.Assert.assertFalse;  // Helps check if something is false.
import static org.junit.Assert.assertNotNull; // Helps check if something is not null.
import static org.junit.Assert.assertNull;    // Helps check if something is null.

// This is a class to test the Series features, like searching, updating, and deleting series.
public class SeriesTest {
    
    // We create a new Series object to use in our tests.
    Series manager = new Series();
    
    // This is the constructor for our test class. We set up test data here.
    public SeriesTest() {
        // Add some test series data
        manager.addSeries(new SeriesModel("S01", "Sample Show", 12, 10));
        manager.addSeries(new SeriesModel("S02", "Another Show", 8, 24));
    }

    // Test to check if we can find a series that exists.
    @Test
    public void testSearchSeriesEqualsCorrect() {
        SeriesModel result = manager.searchSeries("S01"); // Look for series S01
        String resultName = result != null ? result.getSeriesName() : null;
        String expected = "Sample Show"; // We expect to find "Sample Show"
        
        assertEquals(resultName, expected); // Check if we found what we expected
        System.out.println("Result: " + resultName);
        System.out.println("Expected: " + expected);
    }

    // Test to see what happens when we search for a series that doesn't exist.
    @Test
    public void testSearchSeriesEqualsIncorrect() {
        SeriesModel result = manager.searchSeries("NOT_EXIST"); // Look for something that doesn't exist
        SeriesModel expected = null; // We expect to get nothing (null)
        
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }

    // Test that searching for an existing series returns something (not null).
    @Test
    public void testSearchSeriesNotNull() {
        SeriesModel result = manager.searchSeries("S01"); // Look for S01
        assertNotNull(result); // It should not be null
        System.out.println("Result: " + (result != null ? "Found series: " + result.getSeriesName() : "null"));
    }

    // Test that searching for a non-existing series returns null.
    @Test
    public void testSearchSeriesNull() {
        SeriesModel result = manager.searchSeries("DOES_NOT_EXIST"); // Look for something that doesn't exist
        assertNull(result); // It should be null
        System.out.println("Result: " + result);
    }

    // Test if updating an existing series works correctly.
    @Test
    public void testUpdateSeriesEqualsCorrect() {
        boolean result = manager.updateSeriesById("S01", "Updated Name", null, null); // Try to update S01
        boolean expected = true; // We expect it to work
        
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }

    // Test what happens when we try to update a series that doesn't exist.
    @Test
    public void testUpdateSeriesEqualsIncorrect() {
        boolean result = manager.updateSeriesById("NOT_EXIST", "New Name", null, null); // Try to update something that doesn't exist
        boolean expected = false; // We expect it to fail
        
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }

    // Simple test that updating an existing series returns true.
    @Test
    public void testUpdateSeriesTrue() {
        boolean result = manager.updateSeriesById("S01", "Another Updated Name", null, null);
        assertTrue(result); // Should return true
        System.out.println("Result: " + result);
    }

    // Test that trying to update a non-existing series returns false.
    @Test
    public void testUpdateSeriesFalse() {
        boolean result = manager.updateSeriesById("FAKE_ID", "Some Name", null, null);
        assertFalse(result); // Should return false
        System.out.println("Result: " + result);
    }

    // Test if deleting an existing series works correctly.
    @Test
    public void testDeleteSeriesEqualsCorrect() {
        boolean result = manager.deleteSeriesById("S02"); // Try to delete S02
        boolean expected = true; // We expect it to work
        
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }

    // Test what happens when we try to delete a series that doesn't exist.
    @Test
    public void testDeleteSeriesEqualsIncorrect() {
        boolean result = manager.deleteSeriesById("NOT_EXIST"); // Try to delete something that doesn't exist
        boolean expected = false; // We expect it to fail
        
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }

    // Simple test that deleting an existing series returns true.
    @Test
    public void testDeleteSeriesTrue() {
        // Create a fresh manager for this test to avoid conflicts
        Series freshManager = new Series();
        freshManager.addSeries(new SeriesModel("TEST_DELETE", "Test Series", 10, 5));
        boolean result = freshManager.deleteSeriesById("TEST_DELETE");
        assertTrue(result); // Should return true
        System.out.println("Result: " + result);
    }

    // Test that trying to delete a non-existing series returns false.
    @Test
    public void testDeleteSeriesFalse() {
        boolean result = manager.deleteSeriesById("DOES_NOT_EXIST");
        assertFalse(result); // Should return false
        System.out.println("Result: " + result);
    }

    // Test if a series with valid age restriction is accepted.
    @Test
    public void testSeriesAgeRestrictionEqualsCorrect() {
        // Create a new series with valid age
        SeriesModel newSeries = new SeriesModel("AGE_TEST", "Age Test Series", 15, 8);
        Series testManager = new Series();
        testManager.addSeries(newSeries);
        
        SeriesModel result = testManager.searchSeries("AGE_TEST");
        boolean ageValid = (result != null && result.getSeriesAge() >= 2 && result.getSeriesAge() <= 18);
        boolean expected = true;
        
        assertEquals(ageValid, expected);
        System.out.println("Result: " + ageValid);
        System.out.println("Expected: " + expected);
    }

    // Test that creating a series with invalid age should be handled properly.
    @Test
    public void testSeriesAgeRestrictionEqualsIncorrect() {
        try {
            SeriesModel invalidSeries = new SeriesModel("INVALID_AGE", "Invalid Age Series", 25, 10); // Age 25 is invalid
            boolean result = false; // If we get here, no exception was thrown
            boolean expected = true; // We expected an exception
            
            assertEquals(result, expected); // This should fail because no exception was thrown
            System.out.println("Result: " + result + " (No exception thrown)");
            System.out.println("Expected: " + expected + " (Exception should be thrown)");
        } catch (IllegalArgumentException e) {
            // Exception was thrown as expected
            boolean result = true; // Exception was thrown
            boolean expected = true; // We expected an exception
            
            assertEquals(result, expected);
            System.out.println("Result: " + result + " (Exception thrown as expected)");
            System.out.println("Expected: " + expected);
        }
    }
}