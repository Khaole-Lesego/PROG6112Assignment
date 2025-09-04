package mysterygame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * GameUtilitiesTest.java
 * Unit tests for the GameUtilities class
 * Tests utility functions like ordinal formatting, string validation, and safe comparisons
 */
public class GameUtilitiesTest {
    
    // === TESTING ORDINAL FORMATTING ===
    @Test
    public void testFormatOrdinal1st() {
        String result = GameUtilities.formatOrdinal(1);
        String expected = "1st";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinal2nd() {
        String result = GameUtilities.formatOrdinal(2);
        String expected = "2nd";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinal3rd() {
        String result = GameUtilities.formatOrdinal(3);
        String expected = "3rd";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinal4th() {
        String result = GameUtilities.formatOrdinal(4);
        String expected = "4th";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinal11th() {
        String result = GameUtilities.formatOrdinal(11);
        String expected = "11th";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinal12th() {
        String result = GameUtilities.formatOrdinal(12);
        String expected = "12th";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinal13th() {
        String result = GameUtilities.formatOrdinal(13);
        String expected = "13th";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinal21st() {
        String result = GameUtilities.formatOrdinal(21);
        String expected = "21st";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinal22nd() {
        String result = GameUtilities.formatOrdinal(22);
        String expected = "22nd";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinal23rd() {
        String result = GameUtilities.formatOrdinal(23);
        String expected = "23rd";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinal100th() {
        String result = GameUtilities.formatOrdinal(100);
        String expected = "100th";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinal101st() {
        String result = GameUtilities.formatOrdinal(101);
        String expected = "101st";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING STRING VALIDATION ===
    @Test
    public void testIsValidStringTrue() {
        boolean result = GameUtilities.isValidString("Valid String");
        boolean expected = true;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testIsValidStringTrueWithNumbers() {
        boolean result = GameUtilities.isValidString("String123");
        boolean expected = true;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testIsValidStringTrueWithSpaces() {
        boolean result = GameUtilities.isValidString("Valid String With Spaces");
        boolean expected = true;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testIsValidStringFalseNull() {
        boolean result = GameUtilities.isValidString(null);
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testIsValidStringFalseEmpty() {
        boolean result = GameUtilities.isValidString("");
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testIsValidStringFalseWhitespace() {
        boolean result = GameUtilities.isValidString("   ");
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testIsValidStringFalseTabsAndSpaces() {
        boolean result = GameUtilities.isValidString("\t\n   \r");
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING SAFE STRING COMPARISON ===
    @Test
    public void testSafeEqualsBothNull() {
        boolean result = GameUtilities.safeEquals(null, null);
        boolean expected = true;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testSafeEqualsFirstNull() {
        boolean result = GameUtilities.safeEquals(null, "test");
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testSafeEqualsSecondNull() {
        boolean result = GameUtilities.safeEquals("test", null);
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testSafeEqualsEqual() {
        boolean result = GameUtilities.safeEquals("test", "test");
        boolean expected = true;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testSafeEqualsNotEqual() {
        boolean result = GameUtilities.safeEquals("test1", "test2");
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testSafeEqualsEmptyStrings() {
        boolean result = GameUtilities.safeEquals("", "");
        boolean expected = true;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testSafeEqualsCaseSensitive() {
        boolean result = GameUtilities.safeEquals("Test", "test");
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testSafeEqualsWithSpaces() {
        boolean result = GameUtilities.safeEquals("test ", "test");
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING EDGE CASES ===
    @Test
    public void testFormatOrdinalZero() {
        String result = GameUtilities.formatOrdinal(0);
        String expected = "0th";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinalNegative() {
        String result = GameUtilities.formatOrdinal(-1);
        String expected = "-1st";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinalNegative2nd() {
        String result = GameUtilities.formatOrdinal(-2);
        String expected = "-2nd";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinalNegative3rd() {
        String result = GameUtilities.formatOrdinal(-3);
        String expected = "-3rd";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinalNegative4th() {
        String result = GameUtilities.formatOrdinal(-4);
        String expected = "-4th";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING SPECIAL CASES FOR TEENS ===
    @Test
    public void testFormatOrdinalTeen111th() {
        String result = GameUtilities.formatOrdinal(111);
        String expected = "111th";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinalTeen112th() {
        String result = GameUtilities.formatOrdinal(112);
        String expected = "112th";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testFormatOrdinalTeen113th() {
        String result = GameUtilities.formatOrdinal(113);
        String expected = "113th";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
}