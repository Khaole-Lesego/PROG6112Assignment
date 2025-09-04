package mysterygame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * HunterCharacterTest.java
 * Unit tests for the HunterCharacter class
 * Tests hunter-specific functionality like predator's patience, hunting evidence collection, 
 * skills, tools, and special actions
 */
public class HunterCharacterTest {
    
    private HunterCharacter hunter;
    
    @BeforeEach
    public void setUp() {
        hunter = new HunterCharacter("TestHunter");
    }
    
    // === TESTING HUNTER INITIALIZATION ===
    @Test
    public void testHunterCharacterCreation() {
        String resultName = hunter.getCharacterName();
        String expectedName = "TestHunter";
        assertEquals(resultName, expectedName);
        System.out.println("Result: " + resultName);
        System.out.println("Expected: " + expectedName);
        
        String resultType = hunter.getCharacterType();
        String expectedType = "Hunter";
        assertEquals(resultType, expectedType);
        System.out.println("Result: " + resultType);
        System.out.println("Expected: " + expectedType);
    }
    
    // === TESTING HUNTER SKILLS INITIALIZATION ===
    @Test
    public void testHunterSkillsInitialization() {
        boolean resultTracking = hunter.hasSkill(0); // tracking
        boolean expectedTracking = true;
        assertEquals(resultTracking, expectedTracking);
        System.out.println("Tracking Skill Result: " + resultTracking);
        System.out.println("Expected: " + expectedTracking);
        
        boolean resultStealth = hunter.hasSkill(1); // stealth
        boolean expectedStealth = true;
        assertEquals(resultStealth, expectedStealth);
        System.out.println("Stealth Skill Result: " + resultStealth);
        System.out.println("Expected: " + expectedStealth);
        
        boolean resultMagic = hunter.hasSkill(5); // magic detection
        boolean expectedMagic = false;
        assertEquals(resultMagic, expectedMagic);
        System.out.println("Magic Detection Result: " + resultMagic);
        System.out.println("Expected: " + expectedMagic);
    }
    
    // === TESTING HUNTER TOOLS INITIALIZATION ===
    @Test
    public void testHunterToolsInitialization() {
        boolean resultBow = hunter.hasTool("Hunting Bow");
        boolean expectedBow = true;
        assertEquals(resultBow, expectedBow);
        System.out.println("Has Hunting Bow Result: " + resultBow);
        System.out.println("Expected: " + expectedBow);
        
        boolean resultTrap = hunter.hasTool("Trap Kit");
        boolean expectedTrap = true;
        assertEquals(resultTrap, expectedTrap);
        System.out.println("Has Trap Kit Result: " + resultTrap);
        System.out.println("Expected: " + expectedTrap);
    }
    
    // === TESTING PREDATOR'S PATIENCE ===
    @Test
    public void testPredatorsPatience() {
        boolean result = hunter.hasPredatorsPatience();
        boolean expected = true;
        assertEquals(result, expected);
        System.out.println("Predator's Patience Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING HUNTING EVIDENCE COLLECTION ===
    @Test
    public void testAddHuntingEvidence() {
        hunter.addHuntingEvidence("Three-toed tracks");
        hunter.addHuntingEvidence("Scorched earth");
        
        int resultCount = hunter.getEvidenceCount();
        int expectedCount = 2;
        assertEquals(resultCount, expectedCount);
        System.out.println("Evidence Count Result: " + resultCount);
        System.out.println("Expected: " + expectedCount);
        
        String[] evidence = hunter.getHuntingEvidence();
        String resultFirstEvidence = evidence[0];
        String expectedFirstEvidence = "Three-toed tracks";
        assertEquals(resultFirstEvidence, expectedFirstEvidence);
        System.out.println("First Evidence Result: " + resultFirstEvidence);
        System.out.println("Expected: " + expectedFirstEvidence);
    }
    
    // === TESTING LOCATION ACCESS ===
    @Test
    public void testCanAccessForestLocation() {
        boolean result = hunter.canAccessLocation("Forest Cave");
        boolean expected = true;
        assertEquals(result, expected);
        System.out.println("Can Access Forest Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testCanAccessManorLocation() {
        boolean result = hunter.canAccessLocation("Manor Library");
        boolean expected = true; // Hunters can access most locations
        assertEquals(result, expected);
        System.out.println("Can Access Manor Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING SPECIAL ACTIONS ===
    @Test
    public void testPredatorPatienceAction() {
        String result = hunter.performSpecialAction("PREDATOR_PATIENCE");
        boolean containsExpected = result.contains("enhanced tracking abilities");
        boolean expected = true;
        assertEquals(containsExpected, expected);
        System.out.println("Contains Expected Text: " + containsExpected);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testSetTrapAction() {
        String result = hunter.performSpecialAction("SET_TRAP");
        boolean containsExpected = result.contains("trap kit");
        boolean expected = true;
        assertEquals(containsExpected, expected);
        System.out.println("Contains Expected Text: " + containsExpected);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testTrackCreatureAction() {
        String result = hunter.performSpecialAction("TRACK_CREATURE");
        boolean containsExpected = result.contains("tracking expertise");
        boolean expected = true;
        assertEquals(containsExpected, expected);
        System.out.println("Contains Expected Text: " + containsExpected);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING STEALTH BONUS ===
    @Test
    public void testStealthBonus() {
        int result = hunter.getStealthBonus();
        int expected = 1;
        assertEquals(result, expected);
        System.out.println("Stealth Bonus Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING PHYSICAL EVIDENCE INVESTIGATION ===
    @Test
    public void testInvestigatePhysicalEvidence() {
        String result = hunter.investigatePhysicalEvidence("Abandoned Hut");
        boolean containsLocation = result.contains("Abandoned Hut");
        boolean expected = true;
        assertEquals(containsLocation, expected);
        System.out.println("Contains Location: " + containsLocation);
        System.out.println("Expected: " + expected);
    }
}