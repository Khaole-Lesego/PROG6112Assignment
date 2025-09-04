package mysterygame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * ClimaxManagerTest.java
 * Unit tests for the ClimaxManager class
 * Tests ending determination logic for different paths (ritual, combat, bargain) 
 * and scoring systems
 */
public class ClimaxManagerTest {
    
    private ClimaxManager climaxManager;
    private GameStateManager gameState;
    private HunterCharacter hunter;
    private MageCharacter mage;
    
    @BeforeEach
    public void setUp() {
        gameState = new GameStateManager();
        hunter = new HunterCharacter("TestHunter");
        mage = new MageCharacter("TestMage");
        gameState.setCharacter(hunter);
        climaxManager = new ClimaxManager(gameState);
    }
    
    // === TESTING CLIMAX MANAGER INITIALIZATION ===
    @Test
    public void testClimaxManagerCreation() {
        String[] endings = climaxManager.getAllPossibleEndings();
        boolean hasEndings = endings.length > 0;
        boolean expected = true;
        assertEquals(hasEndings, expected);
        System.out.println("Has Endings: " + hasEndings);
        System.out.println("Expected: " + expected);
        
        int resultCount = endings.length;
        boolean hasMultipleEndings = resultCount >= 10;
        boolean expectedMultiple = true;
        assertEquals(hasMultipleEndings, expectedMultiple);
        System.out.println("Has Multiple Endings: " + hasMultipleEndings);
        System.out.println("Expected: " + expectedMultiple);
    }
    
    // === TESTING ENDING DETERMINATION - RITUAL PATH ===
    @Test
    public void testDetermineRitualEndingLowKnowledge() {
        // Set low knowledge scenario
        String result = climaxManager.determineEnding("RESTORE");
        boolean isValidEnding = result != null && result.length() > 0;
        boolean expected = true;
        assertEquals(isValidEnding, expected);
        System.out.println("Valid Ending Generated: " + isValidEnding);
        System.out.println("Expected: " + expected);
        
        boolean containsKnowledgeScore = result.contains("Final Knowledge Score:");
        boolean expectedContains = true;
        assertEquals(containsKnowledgeScore, expectedContains);
        System.out.println("Contains Knowledge Score: " + containsKnowledgeScore);
        System.out.println("Expected: " + expectedContains);
    }
    
    @Test
    public void testDetermineRitualEndingHighKnowledge() {
        // Set high knowledge scenario
        gameState.setCharacter(mage);
        mage.addKnowledgePoints(10);
        mage.addRitualFragment("Test Fragment 1");
        mage.addRitualFragment("Test Fragment 2");
        gameState.setFlag(22, true); // FLAG_ANCHOR_TOKEN_FOUND
        gameState.setFlag(21, true); // FLAG_RITUAL_FRAGMENTS_OBTAINED
        
        String result = climaxManager.determineEnding("RESTORE");
        boolean containsStellar = result.contains("STELLAR") || result.contains("✨");
        boolean expected = true;
        assertEquals(containsStellar, expected);
        System.out.println("Contains Stellar Ending: " + containsStellar);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testDetermineRitualEndingMage() {
        gameState.setCharacter(mage);
        mage.addKnowledgePoints(5);
        gameState.setFlag(21, true); // FLAG_RITUAL_FRAGMENTS_OBTAINED
        
        String result = climaxManager.determineEnding("RESTORE");
        boolean containsCharacterType = result.contains("Mage");
        boolean expected = true;
        assertEquals(containsCharacterType, expected);
        System.out.println("Contains Mage Reference: " + containsCharacterType);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING ENDING DETERMINATION - COMBAT PATH ===
    @Test
    public void testDetermineDestroyEndingHunter() {
        gameState.setCharacter(hunter);
        hunter.addKnowledgePoints(8);
        gameState.setFlag(13, true); // FLAG_HORN_OBTAINED
        gameState.setFlag(9, true);  // FLAG_BEAST_OBSERVED
        gameState.setFlag(23, true); // FLAG_TRUE_NAME_KNOWN
        
        String result = climaxManager.determineEnding("DESTROY");
        boolean isValidEnding = result != null && result.length() > 0;
        boolean expected = true;
        assertEquals(isValidEnding, expected);
        System.out.println("Valid Combat Ending: " + isValidEnding);
        System.out.println("Expected: " + expected);
        
        boolean containsCharacterType = result.contains("Hunter");
        boolean expectedContains = true;
        assertEquals(containsCharacterType, expectedContains);
        System.out.println("Contains Hunter Reference: " + containsCharacterType);
        System.out.println("Expected: " + expectedContains);
    }
    
    @Test
    public void testDetermineDestroyEndingLowPreparation() {
        gameState.setCharacter(hunter);
        hunter.addKnowledgePoints(2);
        // No flags set - low preparation
        
        String result = climaxManager.determineEnding("DESTROY");
        boolean containsLoss = result.contains("LOSS") || result.contains("💀");
        boolean expected = true;
        assertEquals(containsLoss, expected);
        System.out.println("Contains Loss Ending: " + containsLoss);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testDetermineDestroyEndingHighPreparation() {
        gameState.setCharacter(hunter);
        hunter.addKnowledgePoints(10);
        gameState.setFlag(13, true); // FLAG_HORN_OBTAINED
        gameState.setFlag(9, true);  // FLAG_BEAST_OBSERVED
        gameState.setFlag(23, true); // FLAG_TRUE_NAME_KNOWN
        
        String result = climaxManager.determineEnding("DESTROY");
        boolean containsVictory = result.contains("TRIUMPH") || result.contains("VICTORY") || 
                                 result.contains("⚔️") || result.contains("STELLAR");
        boolean expected = true;
        assertEquals(containsVictory, expected);
        System.out.println("Contains Victory Ending: " + containsVictory);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING ENDING DETERMINATION - BARGAIN PATH ===
    @Test
    public void testDetermineBargainEndingWithEvidence() {
        gameState.setFlag(16, true); // FLAG_LEDGER_TAKEN
        gameState.setFlag(15, true); // FLAG_CHIEF_INFORMED
        gameState.setFlag(4, true);  // FLAG_DIARY_FOUND
        hunter.addKnowledgePoints(6);
        
        String result = climaxManager.determineEnding("BARGAIN");
        boolean isValidEnding = result != null && result.length() > 0;
        boolean expected = true;
        assertEquals(isValidEnding, expected);
        System.out.println("Valid Bargain Ending: " + isValidEnding);
        System.out.println("Expected: " + expected);
        
        boolean containsBargain = result.contains("BARGAIN") || result.contains("⚖️");
        boolean expectedBargain = true;
        assertEquals(containsBargain, expectedBargain);
        System.out.println("Contains Bargain Elements: " + containsBargain);
        System.out.println("Expected: " + expectedBargain);
    }
    
    @Test
    public void testDetermineBargainEndingWithoutEvidence() {
        // No evidence flags set
        hunter.addKnowledgePoints(3);
        
        String result = climaxManager.determineEnding("BARGAIN");
        boolean containsFailure = result.contains("LOSS") || result.contains("RUIN") || 
                                 result.contains("DEBT");
        boolean expected = true;
        assertEquals(containsFailure, expected);
        System.out.println("Contains Bargain Failure: " + containsFailure);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testDetermineBargainEndingPoliticalReform() {
        gameState.setFlag(16, true); // FLAG_LEDGER_TAKEN
        gameState.setFlag(15, true); // FLAG_CHIEF_INFORMED
        gameState.setFlag(4, true);  // FLAG_DIARY_FOUND
        hunter.addKnowledgePoints(10);
        
        String result = climaxManager.determineEnding("BARGAIN");
        boolean containsReform = result.contains("REFORM") || result.contains("POLITICAL");
        boolean expected = true;
        assertEquals(containsReform, expected);
        System.out.println("Contains Political Reform: " + containsReform);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING INVALID ENDING CHOICES ===
    @Test
    public void testInvalidEndingChoice() {
        String result = climaxManager.determineEnding("INVALID_CHOICE");
        boolean containsLoss = result.contains("CONSUMED") || result.contains("💀");
        boolean expected = true;
        assertEquals(containsLoss, expected);
        System.out.println("Invalid Choice Results in Loss: " + containsLoss);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testNullEndingChoice() {
        String result = climaxManager.determineEnding(null);
        boolean containsLoss = result.contains("CONSUMED") || result.contains("💀");
        boolean expected = true;
        assertEquals(containsLoss, expected);
        System.out.println("Null Choice Results in Loss: " + containsLoss);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING ENDING DESCRIPTIONS ===
    @Test
    public void testGetEndingDescriptionValid() {
        String result = climaxManager.getEndingDescription("STELLAR_RITUAL_APOTHEOSIS");
        boolean hasDescription = result != null && !result.equals("Unknown ending type.");
        boolean expected = true;
        assertEquals(hasDescription, expected);
        System.out.println("Has Valid Description: " + hasDescription);
        System.out.println("Expected: " + expected);
        
        boolean containsBinding = result.contains("Binding") || result.contains("weave");
        boolean expectedBinding = true;
        assertEquals(containsBinding, expectedBinding);
        System.out.println("Description Contains Expected Content: " + containsBinding);
        System.out.println("Expected: " + expectedBinding);
    }
    
    @Test
    public void testGetEndingDescriptionCombat() {
        String result = climaxManager.getEndingDescription("STELLAR_COMBAT_TRIUMPH");
        boolean containsCombat = result.contains("Beast") || result.contains("Slayer");
        boolean expected = true;
        assertEquals(containsCombat, expected);
        System.out.println("Combat Description Contains Expected: " + containsCombat);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testGetEndingDescriptionPolitical() {
        String result = climaxManager.getEndingDescription("STELLAR_POLITICAL_REFORM");
        boolean containsPolitical = result.contains("Architect") || result.contains("justice");
        boolean expected = true;
        assertEquals(containsPolitical, expected);
        System.out.println("Political Description Contains Expected: " + containsPolitical);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testGetUnknownEndingDescription() {
        String result = climaxManager.getEndingDescription("INVALID_ENDING");
        String expected = "Unknown ending type.";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testGetNullEndingDescription() {
        String result = climaxManager.getEndingDescription(null);
        String expected = "Unknown ending type.";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING MEMORY PAYMENT SYSTEM ===
    @Test
    public void testMemoryPaymentDefault() {
        boolean result = climaxManager.isMemoryPaid();
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Default Memory Paid: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testSetMemoryPayment() {
        climaxManager.setMemoryPaid(true);
        boolean result = climaxManager.isMemoryPaid();
        boolean expected = true;
        assertEquals(result, expected);
        System.out.println("Memory Paid Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testToggleMemoryPayment() {
        climaxManager.setMemoryPaid(true);
        climaxManager.setMemoryPaid(false);
        boolean result = climaxManager.isMemoryPaid();
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Toggled Memory Paid: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING RITUAL FAILURE METER ===
    @Test
    public void testRitualFailureMeterDefault() {
        int result = climaxManager.getRitualFailureMeter();
        int expected = 0;
        assertEquals(result, expected);
        System.out.println("Default Ritual Failure: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testAddRitualFailure() {
        climaxManager.addRitualFailure(3);
        int result = climaxManager.getRitualFailureMeter();
        int expected = 3;
        assertEquals(result, expected);
        System.out.println("Ritual Failure Meter: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testAddMultipleRitualFailures() {
        climaxManager.addRitualFailure(2);
        climaxManager.addRitualFailure(3);
        climaxManager.addRitualFailure(1);
        int result = climaxManager.getRitualFailureMeter();
        int expected = 6;
        assertEquals(result, expected);
        System.out.println("Multiple Failures Total: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testAddNegativeRitualFailure() {
        climaxManager.addRitualFailure(-2);
        int result = climaxManager.getRitualFailureMeter();
        int expected = -2; // Should allow negative values
        assertEquals(result, expected);
        System.out.println("Negative Failure Value: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING CHARACTER TYPE INFLUENCE ON ENDINGS ===
    @Test
    public void testHunterVsMageEndingDifference() {
        // Setup same conditions for both characters
        gameState.setCharacter(hunter);
        hunter.addKnowledgePoints(8);
        gameState.setFlag(21, true); // FLAG_RITUAL_FRAGMENTS_OBTAINED
        String hunterResult = climaxManager.determineEnding("RESTORE");
        
        // Reset and test with mage
        gameState = new GameStateManager();
        gameState.setCharacter(mage);
        climaxManager = new ClimaxManager(gameState);
        mage.addKnowledgePoints(8);
        gameState.setFlag(21, true);
        String mageResult = climaxManager.determineEnding("RESTORE");
        
        boolean resultsDifferent = !hunterResult.equals(mageResult);
        boolean expected = true;
        assertEquals(resultsDifferent, expected);
        System.out.println("Hunter vs Mage Results Different: " + resultsDifferent);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING ENDING FORMATTING ===
    @Test
    public void testEndingContainsFormatting() {
        String result = climaxManager.determineEnding("RESTORE");
        boolean containsFormatting = result.contains("=") && result.contains("FINAL OUTCOME");
        boolean expected = true;
        assertEquals(containsFormatting, expected);
        System.out.println("Contains Proper Formatting: " + containsFormatting);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testEndingContainsCharacterInfo() {
        gameState.setCharacter(hunter);
        String result = climaxManager.determineEnding("DESTROY");
        boolean containsCharInfo = result.contains("Character Type:") && 
                                  result.contains("Final Knowledge Score:");
        boolean expected = true;
        assertEquals(containsCharInfo, expected);
        System.out.println("Contains Character Info: " + containsCharInfo);
        System.out.println("Expected: " + expected);
    }
}