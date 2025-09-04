package mysterygame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * MageCharacterTest.java
 * Unit tests for the MageCharacter class
 * Tests mage-specific features including Thread of Knowing, ritual fragments, 
 * arcane residues, and magical abilities
 */
public class MageCharacterTest {
    
    private MageCharacter mage;
    
    @BeforeEach
    public void setUp() {
        mage = new MageCharacter("TestMage");
    }
    
    // === TESTING MAGE INITIALIZATION ===
    @Test
    public void testMageCharacterCreation() {
        String resultName = mage.getCharacterName();
        String expectedName = "TestMage";
        assertEquals(resultName, expectedName);
        System.out.println("Result: " + resultName);
        System.out.println("Expected: " + expectedName);
        
        String resultType = mage.getCharacterType();
        String expectedType = "Mage";
        assertEquals(resultType, expectedType);
        System.out.println("Result: " + resultType);
        System.out.println("Expected: " + expectedType);
    }
    
    // === TESTING MAGE SKILLS INITIALIZATION ===
    @Test
    public void testMageSkillsInitialization() {
        boolean resultTracking = mage.hasSkill(0); // tracking
        boolean expectedTracking = false;
        assertEquals(resultTracking, expectedTracking);
        System.out.println("Tracking Skill Result: " + resultTracking);
        System.out.println("Expected: " + expectedTracking);
        
        boolean resultMagic = mage.hasSkill(5); // magic detection
        boolean expectedMagic = true;
        assertEquals(resultMagic, expectedMagic);
        System.out.println("Magic Detection Result: " + resultMagic);
        System.out.println("Expected: " + expectedMagic);
        
        boolean resultStealth = mage.hasSkill(1); // magical stealth
        boolean expectedStealth = true;
        assertEquals(resultStealth, expectedStealth);
        System.out.println("Stealth Skill Result: " + resultStealth);
        System.out.println("Expected: " + expectedStealth);
    }
    
    // === TESTING MAGE TOOLS INITIALIZATION ===
    @Test
    public void testMageToolsInitialization() {
        boolean resultLens = mage.hasTool("Scrying Lens");
        boolean expectedLens = true;
        assertEquals(resultLens, expectedLens);
        System.out.println("Has Scrying Lens Result: " + resultLens);
        System.out.println("Expected: " + expectedLens);
        
        boolean resultGrimoire = mage.hasTool("Grimoire Fragments");
        boolean expectedGrimoire = true;
        assertEquals(resultGrimoire, expectedGrimoire);
        System.out.println("Has Grimoire Result: " + resultGrimoire);
        System.out.println("Expected: " + expectedGrimoire);
        
        boolean resultChalk = mage.hasTool("Sigil Chalk");
        boolean expectedChalk = true;
        assertEquals(resultChalk, expectedChalk);
        System.out.println("Has Sigil Chalk Result: " + resultChalk);
        System.out.println("Expected: " + expectedChalk);
    }
    
    // === TESTING THREAD OF KNOWING ===
    @Test
    public void testThreadOfKnowing() {
        boolean result = mage.hasThreadOfKnowing();
        boolean expected = true;
        assertEquals(result, expected);
        System.out.println("Thread of Knowing Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING RITUAL FRAGMENTS COLLECTION ===
    @Test
    public void testAddRitualFragments() {
        mage.addRitualFragment("Binding Circle");
        mage.addRitualFragment("Name Anchor");
        
        int resultCount = mage.getFragmentCount();
        int expectedCount = 2;
        assertEquals(resultCount, expectedCount);
        System.out.println("Fragment Count Result: " + resultCount);
        System.out.println("Expected: " + expectedCount);
        
        String[] fragments = mage.getRitualFragments();
        String resultFirstFragment = fragments[0];
        String expectedFirstFragment = "Binding Circle";
        assertEquals(resultFirstFragment, expectedFirstFragment);
        System.out.println("First Fragment Result: " + resultFirstFragment);
        System.out.println("Expected: " + expectedFirstFragment);
    }
    
    // === TESTING ARCANE RESIDUE COLLECTION ===
    @Test
    public void testAddArcaneResidue() {
        mage.addArcaneResidue("Shadow Magic Traces");
        mage.addArcaneResidue("Memory Essence");
        
        int resultCount = mage.getResidueCount();
        int expectedCount = 2;
        assertEquals(resultCount, expectedCount);
        System.out.println("Residue Count Result: " + resultCount);
        System.out.println("Expected: " + expectedCount);
        
        String[] residues = mage.getArcaneResidues();
        String resultFirstResidue = residues[0];
        String expectedFirstResidue = "Shadow Magic Traces";
        assertEquals(resultFirstResidue, expectedFirstResidue);
        System.out.println("First Residue Result: " + resultFirstResidue);
        System.out.println("Expected: " + expectedFirstResidue);
    }
    
    // === TESTING SPECIAL ACTIONS ===
    @Test
    public void testThreadOfKnowingAction() {
        String result = mage.performSpecialAction("THREAD_OF_KNOWING");
        boolean containsExpected = result.contains("hidden connections");
        boolean expected = true;
        assertEquals(containsExpected, expected);
        System.out.println("Contains Expected Text: " + containsExpected);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testScryingAction() {
        String result = mage.performSpecialAction("SCRYING");
        boolean containsExpected = result.contains("scrying lens");
        boolean expected = true;
        assertEquals(containsExpected, expected);
        System.out.println("Contains Expected Text: " + containsExpected);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testRitualCastingAction() {
        String result = mage.performSpecialAction("RITUAL_CASTING");
        boolean containsExpected = result.contains("ritual spell");
        boolean expected = true;
        assertEquals(containsExpected, expected);
        System.out.println("Contains Expected Text: " + containsExpected);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING PERSUASION BONUS ===
    @Test
    public void testPersuasionBonus() {
        int result = mage.getPersuasionBonus();
        int expected = 2;
        assertEquals(result, expected);
        System.out.println("Persuasion Bonus Result: " + result);
        System.out.println("Expected: " + expected);
    }
    
    // === TESTING MAGICAL AURA DETECTION ===
    @Test
    public void testDetectMagicalAura() {
        String result = mage.detectMagicalAura("Haunted Library");
        boolean containsLocation = result.contains("Haunted Library");
        boolean expected = true;
        assertEquals(containsLocation, expected);
        System.out.println("Contains Location: " + containsLocation);
        System.out.println("Expected: " + expected);
        
        boolean containsThreadOfKnowing = result.contains("Thread of Knowing");
        boolean expectedThread = true;
        assertEquals(containsThreadOfKnowing, expectedThread);
        System.out.println("Contains Thread Reference: " + containsThreadOfKnowing);
        System.out.println("Expected: " + expectedThread);
    }
    
    // === TESTING RITUAL ANALYSIS ===
    @Test
    public void testPerformRitualAnalysisWithFragments() {
        mage.addRitualFragment("Fragment 1");
        mage.addRitualFragment("Fragment 2");
        mage.addRitualFragment("Fragment 3");
        
        String result = mage.performRitualAnalysis();
        boolean containsComplexRitual = result.contains("complex ritual work");
        boolean expected = true;
        assertEquals(containsComplexRitual, expected);
        System.out.println("Can Perform Complex Ritual: " + containsComplexRitual);
        System.out.println("Expected: " + expected);
    }
    
    @Test
    public void testPerformRitualAnalysisWithoutFragments() {
        String result = mage.performRitualAnalysis();
        boolean containsNeedMore = result.contains("More fragments needed");
        boolean expected = true;
        assertEquals(containsNeedMore, expected);
        System.out.println("Needs More Fragments: " + containsNeedMore);
        System.out.println("Expected: " + expected);
    }
}