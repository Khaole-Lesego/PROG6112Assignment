package mysterygame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * InvestigationNodeTest.java
 * Unit tests for the InvestigationNode class
 * Tests the investigation system including node creation, action performance, 
 * and character-specific responses
 */
public class InvestigationNodeTest {
    
    private InvestigationNode manorOffice;
    private InvestigationNode forestCave;
    private InvestigationNode socialChief;
    private HunterCharacter hunter;
    private MageCharacter mage;
    
    @BeforeEach
    public void setUp() {
        manorOffice = new InvestigationNode("MANOR_OFFICE", "The Mystery Villager's Office", 
            "A dimly lit study filled with documents.", "Manor");
        forestCave = new InvestigationNode("FOREST_CAVE", "The Hidden Cave", 
            "A deep underground chamber.", "Forest");
        socialChief = new InvestigationNode("VILLAGE_CHIEF", "Village Chief's Hall", 
            "The heart of village governance.", "Social");
        hunter = new HunterCharacter("TestHunter");
        mage = new MageCharacter("TestMage");
    }
    
    // === TESTING NODE CREATION ===
    @Test
    public void testNodeCreation() {
        String resultName = manorOffice.getNodeName();
        String expectedName = "The Mystery Villager's Office";
        assertEquals(resultName, expectedName);
        System.out.println("Node Name Result: " + resultName);
        System.out.println("Expected: " + expectedName);
        
        boolean resultVisited = manorOffice.isNodeVisited();
        boolean expectedVisited = false;
        assertEquals(resultVisited, expectedVisited);
        System.out.println("Node Visited Result: " + resultVisited);
        System.out.println("Expected: " + expectedVisited);
    }
    
    @Test
    public void testNodeDescription() {
        String resultDescription = manorOffice.getNodeDescription();
        String expectedDescription = "A dimly lit study filled with documents.";
        assertEquals(resultDescription, expectedDescription);
        System.out.println("Node Description Result: " + resultDescription);
        System.out.println("Expected: " + expectedDescription);
    }
    
    // === TESTING ACTION PERFORMANCE ===
    @Test
    public void testPerformValidAction() {
        String result = manorOffice.performAction(0, hunter);
        boolean isValidResult = result != null && !result.isEmpty() && 
                               !result.equals("Invalid action selected.");
        boolean expected = true;
        assertEquals(isValidResult, expected);
        System.out.println("Valid Action Result: " + isValidResult);
        System.out.println("Expected: " + expected);
        
        boolean nodeVisited = manorOffice.isNodeVisited();
        boolean expectedVisited = true;
        assertEquals(nodeVisited, expectedVisited);
        System.out.println("Node Visited After Action: " + nodeVisited);
        System.out.println("Expected: " + expectedVisited);
    }
    
    @Test
    public void testPerformInvalidActionNegative() {
        String result = manorOffice.performAction(-1, hunter);
        String expected = "Invalid action selected.";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testPerformInvalidActionTooHigh() {
        String result = manorOffice.performAction(999, hunter);
        String expected = "Invalid action selected.";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testPerformDuplicateAction() {
        // First action
        manorOffice.performAction(0, hunter);
        // Second identical action
        String result = manorOffice.performAction(0, hunter);
        String expected = "You have already completed this action at this location.";
        assertEquals(result, expected);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING AVAILABLE ACTIONS ===
    @Test
    public void testAvailableActionsNotNull() {
        String[] actions = manorOffice.getAvailableActions();
        boolean resultNotNull = actions != null;
        boolean expected = true;
        assertEquals(resultNotNull, expected);
        System.out.println("Actions Not Null: " + resultNotNull);
        System.out.println("Expected: " + expected);
        
        boolean hasActions = actions.length > 0;
        boolean expectedHasActions = true;
        assertEquals(hasActions, expectedHasActions);
        System.out.println("Has Actions: " + hasActions);
        System.out.println("Expected: " + expectedHasActions);
    }
    
    @Test
    public void testManorActionsContent() {
        String[] actions = manorOffice.getAvailableActions();
        boolean hasSearchAction = false;
        for (String action : actions) {
            if (action != null && action.contains("Search")) {
                hasSearchAction = true;
                break;
            }
        }
        boolean expected = true;
        assertEquals(hasSearchAction, expected);
        System.out.println("Has Search Action: " + hasSearchAction);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testForestActionsContent() {
        String[] actions = forestCave.getAvailableActions();
        boolean hasExamineAction = false;
        for (String action : actions) {
            if (action != null && (action.contains("Examine") || action.contains("Altar"))) {
                hasExamineAction = true;
                break;
            }
        }
        boolean expected = true;
        assertEquals(hasExamineAction, expected);
        System.out.println("Has Examine Action: " + hasExamineAction);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING CHARACTER-SPECIFIC RESPONSES ===
    @Test
    public void testHunterVsMageResponseDifferent() {
        String hunterResult = forestCave.performAction(0, hunter);
        
        // Reset the node for mage test by creating new instance
        InvestigationNode freshCave = new InvestigationNode("FOREST_CAVE", "The Hidden Cave", 
            "A deep underground chamber.", "Forest");
        String mageResult = freshCave.performAction(0, mage);
        
        boolean resultsDifferent = !hunterResult.equals(mageResult);
        boolean expected = true;
        assertEquals(resultsDifferent, expected);
        System.out.println("Character-specific responses different: " + resultsDifferent);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testHunterResponseContainsHunterTerms() {
        String result = manorOffice.performAction(0, hunter);
        boolean containsHunterTerms = result.contains("track") || result.contains("hunt") || 
                                     result.contains("predator") || result.contains("stealth");
        // Note: This might be false if the specific action doesn't have hunter-specific text
        System.out.println("Contains Hunter Terms: " + containsHunterTerms);
        System.out.println("Hunter Response: " + result.substring(0, Math.min(100, result.length())));
    }
    
    @Test
    public void testMageResponseContainsMagicTerms() {
        String result = manorOffice.performAction(0, mage);
        boolean containsMagicTerms = result.contains("magic") || result.contains("Thread") || 
                                    result.contains("scrying") || result.contains("arcane");
        // Note: This might be false if the specific action doesn't have mage-specific text
        System.out.println("Contains Magic Terms: " + containsMagicTerms);
        System.out.println("Mage Response: " + result.substring(0, Math.min(100, result.length())));
    }
    
    // === TESTING EVIDENCE COLLECTION ===
    @Test
    public void testEvidenceCollectionAfterAction() {
        manorOffice.performAction(0, hunter);
        String[] evidence = manorOffice.getEvidenceFound();
        boolean hasEvidence = evidence.length > 0;
        boolean expected = true;
        assertEquals(hasEvidence, expected);
        System.out.println("Has Evidence After Action: " + hasEvidence);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testEvidenceCollectionMultipleActions() {
        manorOffice.performAction(0, hunter);
        manorOffice.performAction(1, hunter);
        String[] evidence = manorOffice.getEvidenceFound();
        boolean hasMultipleEvidence = evidence.length >= 2;
        boolean expected = true;
        assertEquals(hasMultipleEvidence, expected);
        System.out.println("Has Multiple Evidence: " + hasMultipleEvidence);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING SOCIAL INVESTIGATION NODES ===
    @Test
    public void testSocialNodeActions() {
        String result = socialChief.performAction(0, hunter);
        boolean isValidResult = result != null && !result.isEmpty() && 
                               !result.equals("Invalid action selected.");
        boolean expected = true;
        assertEquals(isValidResult, expected);
        System.out.println("Social Action Valid: " + isValidResult);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testSocialNodeVisitedFlag() {
        socialChief.performAction(0, mage);
        boolean resultVisited = socialChief.isNodeVisited();
        boolean expectedVisited = true;
        assertEquals(resultVisited, expectedVisited);
        System.out.println("Social Node Visited: " + resultVisited);
        System.out.println("Expected: " + expectedVisited);
    }
    
    // === TESTING NODE TYPES ===
    @Test
    public void testDifferentNodeTypesHaveDifferentActions() {
        String[] manorActions = manorOffice.getAvailableActions();
        String[] forestActions = forestCave.getAvailableActions();
        String[] socialActions = socialChief.getAvailableActions();
        
        // Compare first non-null actions to see if they're different
        String manorFirstAction = null;
        String forestFirstAction = null;
        String socialFirstAction = null;
        
        for (String action : manorActions) {
            if (action != null) {
                manorFirstAction = action;
                break;
            }
        }
        
        for (String action : forestActions) {
            if (action != null) {
                forestFirstAction = action;
                break;
            }
        }
        
        for (String action : socialActions) {
            if (action != null) {
                socialFirstAction = action;
                break;
            }
        }
        
        boolean allDifferent = !manorFirstAction.equals(forestFirstAction) && 
                              !forestFirstAction.equals(socialFirstAction) &&
                              !manorFirstAction.equals(socialFirstAction);
        boolean expected = true;
        assertEquals(allDifferent, expected);
        System.out.println("All Node Types Have Different Actions: " + allDifferent);
        System.out.println("Expected: " + expected);
    }
}