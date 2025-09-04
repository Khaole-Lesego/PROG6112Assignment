package mysterygame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * GameStateManagerTest.java
 * Unit tests for the GameStateManager class
 * Tests core game state management including character setting, path choices, 
 * flag management, and investigation scoring
 */
public class GameStateManagerTest {
    
    private GameStateManager gameState;
    private HunterCharacter hunter;
    private MageCharacter mage;
    
    @BeforeEach
    public void setUp() {
        gameState = new GameStateManager();
        hunter = new HunterCharacter("TestHunter");
        mage = new MageCharacter("TestMage");
    }
    
    // === TESTING CHARACTER SETTING ===
    @Test
    public void testSetCharacter() {
        gameState.setCharacter(hunter);
        BaseCharacter result = gameState.getCurrentCharacter();
        BaseCharacter expected = hunter;
        assertEquals(result, expected);
        System.out.println("Character Set Successfully: " + (result == expected));
        System.out.println("Expected: true");
    }
    
    // === TESTING PATH CHOICE ===
    @Test
    public void testChooseForestPath() {
        gameState.choosePath("FOREST");
        String result = gameState.getCurrentPath();
        String expected = "Forest Investigation";
        assertEquals(result, expected);
        System.out.println("Path Result: " + result);
        System.out.println("Expected: " + expected);
        
        boolean forestFlag = gameState.getFlag(0); // FLAG_CHOSE_FOREST
        boolean expectedFlag = true;
        assertEquals(forestFlag, expectedFlag);
        System.out.println("Forest Flag Result: " + forestFlag);
        System.out.println("Expected: " + expectedFlag);
    }
    
    @Test
    public void testChooseManorPath() {
        gameState.choosePath("MANOR");
        String result = gameState.getCurrentPath();
        String expected = "Manor Investigation";
        assertEquals(result, expected);
        System.out.println("Path Result: " + result);
        System.out.println("Expected: " + expected);
        
        boolean manorFlag = gameState.getFlag(1); // FLAG_CHOSE_MANOR
        boolean expectedFlag = true;
        assertEquals(manorFlag, expectedFlag);
        System.out.println("Manor Flag Result: " + manorFlag);
        System.out.println("Expected: " + expectedFlag);
    }
    
    @Test
    public void testChooseSocialPath() {
        gameState.choosePath("SOCIAL");
        String result = gameState.getCurrentPath();
        String expected = "Social Investigation";
        assertEquals(result, expected);
        System.out.println("Path Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testChooseInvalidPath() {
        gameState.choosePath("INVALID");
        String result = gameState.getCurrentPath();
        String expected = "General Investigation";
        assertEquals(result, expected);
        System.out.println("Path Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING FLAG MANAGEMENT ===
    @Test
    public void testSetAndGetFlag() {
        gameState.setFlag(4, true); // FLAG_DIARY_FOUND
        boolean result = gameState.getFlag(4);
        boolean expected = true;
        assertEquals(result, expected);
        System.out.println("Flag Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testSetFlagFalse() {
        gameState.setFlag(4, false);
        boolean result = gameState.getFlag(4);
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Flag Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testInvalidFlagIndex() {
        gameState.setFlag(999, true); // Invalid index
        boolean result = gameState.getFlag(999);
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Invalid Flag Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testNegativeFlagIndex() {
        gameState.setFlag(-1, true); // Negative index
        boolean result = gameState.getFlag(-1);
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Negative Flag Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING KNOWLEDGE SCORE CALCULATION ===
    @Test
    public void testTotalKnowledgeScore() {
        gameState.setCharacter(hunter);
        hunter.addKnowledgePoints(5);
        
        int result = gameState.getTotalKnowledgeScore();
        int expected = 5; // Character knowledge only, no investigation score yet
        assertEquals(result, expected);
        System.out.println("Total Knowledge Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testTotalKnowledgeScoreWithInvestigation() {
        gameState.setCharacter(mage);
        mage.addKnowledgePoints(8);
        gameState.setFlag(4, true); // This should trigger some investigation scoring
        
        int result = gameState.getTotalKnowledgeScore();
        boolean resultValid = result >= 8; // Should be at least character knowledge
        boolean expected = true;
        assertEquals(resultValid, expected);
        System.out.println("Knowledge Score >= Character Points: " + resultValid);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING INVESTIGATION LOCATION ===
    @Test
    public void testInvestigateManorOffice() {
        gameState.setCharacter(hunter);
        String result = gameState.investigateLocation("MANOR", 0, 0);
        boolean containsResult = result.contains("desk") || result.contains("office") || 
                                result.contains("drawers") || result.contains("search");
        boolean expected = true;
        assertEquals(containsResult, expected);
        System.out.println("Investigation contains expected content: " + containsResult);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testInvestigateForestHut() {
        gameState.setCharacter(hunter);
        String result = gameState.investigateLocation("FOREST", 0, 0);
        boolean containsResult = result.contains("tracks") || result.contains("hut") || 
                                result.contains("forest") || result.contains("follow");
        boolean expected = true;
        assertEquals(containsResult, expected);
        System.out.println("Investigation contains expected content: " + containsResult);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testInvalidLocationInvestigation() {
        gameState.setCharacter(hunter);
        String result = gameState.investigateLocation("INVALID", 0, 0);
        String expected = "Invalid location specified.";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testInvalidNodeIndexInvestigation() {
        gameState.setCharacter(hunter);
        String result = gameState.investigateLocation("MANOR", 999, 0);
        String expected = "Invalid location specified.";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING INVESTIGATION SCORES ===
    @Test
    public void testInitialScores() {
        int manorScore = gameState.getManorScore();
        int expectedManor = 0;
        assertEquals(manorScore, expectedManor);
        System.out.println("Initial Manor Score: " + manorScore);
        System.out.println("Expected: " + expectedManor);
        
        int forestScore = gameState.getForestScore();
        int expectedForest = 0;
        assertEquals(forestScore, expectedForest);
        System.out.println("Initial Forest Score: " + forestScore);
        System.out.println("Expected: " + expectedForest);
        
        int socialScore = gameState.getSocialScore();
        int expectedSocial = 0;
        assertEquals(socialScore, expectedSocial);
        System.out.println("Initial Social Score: " + socialScore);
        System.out.println("Expected: " + expectedSocial);
    }
    
    @Test
    public void testTotalInvestigationScore() {
        int result = gameState.getTotalInvestigationScore();
        int expected = 0; // Should start at 0
        assertEquals(result, expected);
        System.out.println("Total Investigation Score: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING NODE ARRAYS ===
    @Test
    public void testManorNodesNotNull() {
        InvestigationNode[] manorNodes = gameState.getManorNodes();
        boolean resultNotNull = manorNodes != null;
        boolean expected = true;
        assertEquals(resultNotNull, expected);
        System.out.println("Manor Nodes Not Null: " + resultNotNull);
        System.out.println("Expected: " + expected);
        
        boolean hasNodes = manorNodes.length > 0;
        boolean expectedHasNodes = true;
        assertEquals(hasNodes, expectedHasNodes);
        System.out.println("Has Manor Nodes: " + hasNodes);
        System.out.println("Expected: " + expectedHasNodes);
    }
    
    @Test
    public void testForestNodesNotNull() {
        InvestigationNode[] forestNodes = gameState.getForestNodes();
        boolean resultNotNull = forestNodes != null;
        boolean expected = true;
        assertEquals(resultNotNull, expected);
        System.out.println("Forest Nodes Not Null: " + resultNotNull);
        System.out.println("Expected: " + expected);
        
        boolean hasNodes = forestNodes.length > 0;
        boolean expectedHasNodes = true;
        assertEquals(hasNodes, expectedHasNodes);
        System.out.println("Has Forest Nodes: " + hasNodes);
        System.out.println("Expected: " + expectedHasNodes);
    }
    
    @Test
    public void testSocialNodesNotNull() {
        InvestigationNode[] socialNodes = gameState.getSocialNodes();
        boolean resultNotNull = socialNodes != null;
        boolean expected = true;
        assertEquals(resultNotNull, expected);
        System.out.println("Social Nodes Not Null: " + resultNotNull);
        System.out.println("Expected: " + expected);
        
        boolean hasNodes = socialNodes.length > 0;
        boolean expectedHasNodes = true;
        assertEquals(hasNodes, expectedHasNodes);
        System.out.println("Has Social Nodes: " + hasNodes);
        System.out.println("Expected: " + expectedHasNodes);
    }
    
    // === TESTING GAME COMPLETION ===
    @Test
    public void testInitialGameCompletionStatus() {
        boolean result = gameState.isGameCompleted();
        boolean expected = false;
        assertEquals(result, expected);
        System.out.println("Initial Game Completed: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING SUMMARY GENERATION ===
    @Test
    public void testGetSummary() {
        gameState.setCharacter(hunter);
        String result = gameState.getSummary();
        boolean containsSummary = result.contains("Investigation Progress") && 
                                 result.contains("Manor Score") &&
                                 result.contains("Character Knowledge");
        boolean expected = true;
        assertEquals(containsSummary, expected);
        System.out.println("Summary Contains Expected Content: " + containsSummary);
        System.out.println("Expected: " + expected);
    }
}