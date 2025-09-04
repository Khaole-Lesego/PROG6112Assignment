/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysterygame;

// GameStateManager.java - Manages all game state and investigation combinations
public class GameStateManager 
{
    private BaseCharacter currentCharacter;
    private InvestigationNode[] manorNodes;
    private InvestigationNode[] forestNodes;
    private InvestigationNode[] socialNodes;
    private boolean[] gameFlags;
    private int[] investigationScores;
    private String currentPath;
    private boolean gameCompleted;
    private String finalOutcome;
    
    // Flag indices for tracking game state
    private static final int FLAG_CHOSE_FOREST = 0;
    private static final int FLAG_CHOSE_MANOR = 1;
    private static final int FLAG_OFFICE_DOCS_FOUND = 2;
    private static final int FLAG_FOUND_KEY = 3;
    private static final int FLAG_DIARY_FOUND = 4;
    private static final int FLAG_LOCKET_FOUND = 5;
    private static final int FLAG_TOYS_FOUND = 6;
    private static final int FLAG_LIBRARY_TRIGGERED = 7;
    private static final int FLAG_SECRET_ROOM_TRAPPED = 8;
    private static final int FLAG_BEAST_OBSERVED = 9;
    private static final int FLAG_TRACKS_FOLLOWED = 10;
    private static final int FLAG_HUT_EXAMINED = 11;
    private static final int FLAG_CAVE_EXAMINED = 12;
    private static final int FLAG_HORN_OBTAINED = 13;
    private static final int FLAG_STONE_CIRCLE_FOUND = 14;
    private static final int FLAG_CHIEF_INFORMED = 15;
    private static final int FLAG_LEDGER_TAKEN = 16;
    private static final int FLAG_MAYOR_ALERTED = 17;
    private static final int FLAG_HID_UNDER_BED = 18;
    private static final int FLAG_HID_IN_CLOSET = 19;
    private static final int FLAG_JUMPED_WINDOW = 20;
    private static final int FLAG_RITUAL_FRAGMENTS_OBTAINED = 21;
    private static final int FLAG_ANCHOR_TOKEN_FOUND = 22;
    private static final int FLAG_TRUE_NAME_KNOWN = 23;
    
    private static final int TOTAL_FLAGS = 24;
    
    public GameStateManager() 
    {
        this.gameFlags = new boolean[TOTAL_FLAGS];
        this.investigationScores = new int[4]; // Manor, Forest, Social, Total
        this.manorNodes = new InvestigationNode[3];
        this.forestNodes = new InvestigationNode[3];
        this.socialNodes = new InvestigationNode[2];
        this.gameCompleted = false;
        initializeInvestigationNodes();
    }
    
    private void initializeInvestigationNodes() 
    {
        // Manor Investigation Nodes
        manorNodes[0] = new InvestigationNode(
            "MANOR_OFFICE", 
            "The Mystery Villager's Office",
            "A dimly lit study filled with shipping manifests, ledgers, and the scent of old parchment. " +
            "Moonlight filters through heavy curtains, casting shadows across documents that may hold dark secrets. " +
            "The massive oak desk dominates the room, its drawers secured with intricate locks that seem to guard " +
            "more than mere paperwork.",
            "Manor"
        );
        
        manorNodes[1] = new InvestigationNode(
            "MANOR_BEDROOM", 
            "The Barricaded Bedroom",
            "Behind the barricaded door lies a chamber frozen in grief and supernatural dread. Torn children's toys " +
            "scatter the floor like memories of innocence lost, while strange shadows seem to move independently " +
            "of their sources. A locket gleams on the nightstand beside a leather-bound diary, its pages filled " +
            "with desperate words that seem to whisper when the wind stirs them.",
            "Manor"
        );
        
        manorNodes[2] = new InvestigationNode(
            "MANOR_LIBRARY", 
            "The Manor's Private Library",
            "Towering bookshelves reach toward vaulted ceilings, filled with rare tomes and forbidden knowledge. " +
            "A peculiar crystalline object pulses with otherworldly energy from across the room, its light casting " +
            "dancing shadows that form almost recognizable shapes. The scent of ancient magic and something darker " +
            "permeates this scholarly sanctuary.",
            "Manor"
        );
        
        // Forest Investigation Nodes
        forestNodes[0] = new InvestigationNode(
            "FOREST_HUT", 
            "The Abandoned Hut",
            "This crumbling shelter bears the scars of supernatural encounters that defy natural explanation. " +
            "Three-toed prints circle the structure in disturbing spiral patterns, while scorched earth tells " +
            "of flames that burned with unnatural cold rather than heat. Fragments of a journal flutter in " +
            "the wind, their torn pages whispering of ancient debts and impossible bargains.",
            "Forest"
        );
        
        forestNodes[1] = new InvestigationNode(
            "FOREST_CAVE", 
            "The Hidden Cave",
            "Deep within the forest's heart lies this underground chamber, carved by hands unknown centuries ago. " +
            "An altar of black stone dominates the space, surrounded by fractured binding crystals that still " +
            "hum with residual power. Children's toys are arranged in ritualistic patterns that hurt to look at " +
            "directly, speaking of dark ceremonies and stolen innocence.",
            "Forest"
        );
        
        forestNodes[2] = new InvestigationNode(
            "FOREST_STONE_CIRCLE", 
            "The Ancient Stone Circle",
            "Weathered monoliths stand sentinel in this sacred grove, their surfaces etched with symbols older than " +
            "memory itself. Here dwells Derren, the hermit guardian who keeps watch over ancient covenants and " +
            "broken promises. The very air thrums with ancestral power, and whispers of long-dead heroes echo " +
            "between the standing stones.",
            "Forest"
        );
        
        // Social Investigation Nodes
        socialNodes[0] = new InvestigationNode(
            "VILLAGE_CHIEF", 
            "Village Chief's Hall",
            "The heart of village governance, where decisions that affect all are made in the shadow of growing dread. " +
            "Chief Aldric, a man weathered by years of leadership and recent supernatural terror, knows the old stories " +
            "and darker truths that most prefer to forget. His hall holds records of ancient pacts, modern crimes, " +
            "and the increasingly desperate measures taken to protect what remains of the village's children.",
            "Social"
        );
        
        socialNodes[1] = new InvestigationNode(
            "MARA_COTTAGE", 
            "Mara's Cottage",
            "The wise woman's dwelling sits at the village edge, where conventional wisdom meets mystical knowledge. " +
            "Mara possesses understanding of protective charms and ancient rituals, her trust earned through careful " +
            "conversation and demonstrated wisdom. Her cottage overflows with herbs, amulets, and hidden lore that " +
            "may hold the key to combating supernatural threats.",
            "Social"
        );
    }
    
    public void setCharacter(BaseCharacter character) 
    {
        this.currentCharacter = character;
    }
    
    public void choosePath(String pathChoice) 
    {
        if (pathChoice.equalsIgnoreCase("FOREST")) 
        {
            setFlag(FLAG_CHOSE_FOREST, true);
            this.currentPath = "Forest Investigation";
        } 
        else if (pathChoice.equalsIgnoreCase("MANOR")) 
        {
            setFlag(FLAG_CHOSE_MANOR, true);
            this.currentPath = "Manor Investigation";
        }
        else if (pathChoice.equalsIgnoreCase("SOCIAL"))
        {
            this.currentPath = "Social Investigation";
        }
        else
        {
            this.currentPath = "General Investigation";
        }
    }
    
    public String investigateLocation(String locationType, int nodeIndex, int actionIndex) 
    {
        InvestigationNode targetNode = null;
        
        if (locationType.equalsIgnoreCase("MANOR") && nodeIndex < manorNodes.length) 
        {
            targetNode = manorNodes[nodeIndex];
        } 
        else if (locationType.equalsIgnoreCase("FOREST") && nodeIndex < forestNodes.length) 
        {
            targetNode = forestNodes[nodeIndex];
        } 
        else if (locationType.equalsIgnoreCase("SOCIAL") && nodeIndex < socialNodes.length) 
        {
            targetNode = socialNodes[nodeIndex];
        }
        
        if (targetNode == null) 
        {
            return "Invalid location specified.";
        }
        
        // Check if character can access this location
        if (!currentCharacter.canAccessLocation(targetNode.getNodeName())) 
        {
            return "Your character type cannot easily access this location. Consider a different approach.";
        }
        
        String result = targetNode.performAction(actionIndex, currentCharacter);
        updateGameStateBasedOnAction(targetNode, actionIndex, result);
        
        return result;
    }
    
    private void updateGameStateBasedOnAction(InvestigationNode node, int actionIndex, String result) 
    {
        String nodeName = node.getNodeName();
        String[] actions = node.getAvailableActions();
        
        if (actionIndex < actions.length && actions[actionIndex] != null) 
        {
            String action = actions[actionIndex];
            
            // Manor-specific flag updates
            if (nodeName.contains("Office")) 
            {
                if (action.contains("Ledger") || action.contains("Documents")) 
                {
                    setFlag(FLAG_OFFICE_DOCS_FOUND, true);
                    setFlag(FLAG_LEDGER_TAKEN, true);
                    investigationScores[0] += 3; // Manor score
                }
                if (action.contains("Key")) 
                {
                    setFlag(FLAG_FOUND_KEY, true);
                    investigationScores[0] += 2;
                }
            }
            else if (nodeName.contains("Bedroom")) 
            {
                if (action.equals("Hide Under Bed")) 
                {
                    setFlag(FLAG_HID_UNDER_BED, true);
                    investigationScores[0] += 3;
                }
                else if (action.equals("Hide in Closet")) 
                {
                    setFlag(FLAG_HID_IN_CLOSET, true);
                    investigationScores[0] += 3;
                }
                else if (action.contains("Window")) 
                {
                    setFlag(FLAG_JUMPED_WINDOW, true);
                    investigationScores[0] += 2;
                }
                if (action.contains("Diary")) 
                {
                    setFlag(FLAG_DIARY_FOUND, true);
                    investigationScores[0] += 4;
                }
                if (action.contains("Locket")) 
                {
                    setFlag(FLAG_LOCKET_FOUND, true);
                    setFlag(FLAG_ANCHOR_TOKEN_FOUND, true);
                    investigationScores[0] += 4;
                }
            }
            else if (nodeName.contains("Library")) 
            {
                if (action.contains("Crystalline") || action.contains("Object")) 
                {
                    setFlag(FLAG_LIBRARY_TRIGGERED, true);
                    investigationScores[0] += 3;
                }
                if (action.contains("Secret Room")) 
                {
                    setFlag(FLAG_SECRET_ROOM_TRAPPED, true);
                    investigationScores[0] += 3;
                }
                if (action.contains("Beast") || action.contains("Observe")) 
                {
                    setFlag(FLAG_BEAST_OBSERVED, true);
                    setFlag(FLAG_TRUE_NAME_KNOWN, true);
                    investigationScores[0] += 5;
                }
            }
            
            // Forest-specific flag updates
            if (nodeName.contains("Hut")) 
            {
                if (action.contains("Tracks")) 
                {
                    setFlag(FLAG_TRACKS_FOLLOWED, true);
                    setFlag(FLAG_HUT_EXAMINED, true);
                    investigationScores[1] += 3; // Forest score
                }
                if (action.contains("Scorched") || action.contains("Journal")) 
                {
                    investigationScores[1] += 2;
                }
            }
            else if (nodeName.contains("Cave")) 
            {
                if (action.contains("Altar")) 
                {
                    setFlag(FLAG_CAVE_EXAMINED, true);
                    setFlag(FLAG_ANCHOR_TOKEN_FOUND, true);
                    investigationScores[1] += 4;
                }
                if (action.contains("Crystal") || action.contains("Toys")) 
                {
                    investigationScores[1] += 2;
                }
            }
            else if (nodeName.contains("Stone Circle")) 
            {
                if (action.contains("Horn")) 
                {
                    setFlag(FLAG_HORN_OBTAINED, true);
                    investigationScores[1] += 4;
                }
                if (action.contains("Derren")) 
                {
                    setFlag(FLAG_STONE_CIRCLE_FOUND, true);
                    investigationScores[1] += 3;
                }
                if (action.contains("Ritual") || action.contains("Symbols")) 
                {
                    setFlag(FLAG_RITUAL_FRAGMENTS_OBTAINED, true);
                    investigationScores[1] += 3;
                }
            }
            
            // Social-specific flag updates
            if (nodeName.contains("Chief")) 
            {
                setFlag(FLAG_CHIEF_INFORMED, true);
                investigationScores[2] += 3; // Social score
                if (action.contains("Evidence")) 
                {
                    investigationScores[2] += 2;
                }
            }
            else if (nodeName.contains("Mara")) 
            {
                investigationScores[2] += 2;
                if (action.contains("Ritual")) 
                {
                    setFlag(FLAG_RITUAL_FRAGMENTS_OBTAINED, true);
                    investigationScores[2] += 3;
                }
            }
        }
        
        // Update total score
        investigationScores[3] = investigationScores[0] + investigationScores[1] + investigationScores[2];
    }
    
    public void setFlag(int flagIndex, boolean value) 
    {
        if (flagIndex >= 0 && flagIndex < TOTAL_FLAGS) 
        {
            gameFlags[flagIndex] = value;
        }
    }
    
    public boolean getFlag(int flagIndex) 
    {
        if (flagIndex >= 0 && flagIndex < TOTAL_FLAGS) 
        {
            return gameFlags[flagIndex];
        }
        return false;
    }
    
    public int getTotalKnowledgeScore() 
    {
        return investigationScores[3] + currentCharacter.getKnowledgePoints();
    }
    
    // Getters for information hiding
    public InvestigationNode[] getManorNodes() { return manorNodes; }
    public InvestigationNode[] getForestNodes() { return forestNodes; }
    public InvestigationNode[] getSocialNodes() { return socialNodes; }
    public String getCurrentPath() { return currentPath; }
    public boolean isGameCompleted() { return gameCompleted; }
    public BaseCharacter getCurrentCharacter() { return currentCharacter; }
    
    // Additional methods for game state information
    public int getManorScore() { return investigationScores[0]; }
    public int getForestScore() { return investigationScores[1]; }
    public int getSocialScore() { return investigationScores[2]; }
    public int getTotalInvestigationScore() { return investigationScores[3]; }
    
    public String getSummary() 
    {
        StringBuilder summary = new StringBuilder();
        summary.append("Investigation Progress:\n");
        summary.append("Manor Score: ").append(investigationScores[0]).append("\n");
        summary.append("Forest Score: ").append(investigationScores[1]).append("\n");
        summary.append("Social Score: ").append(investigationScores[2]).append("\n");
        summary.append("Total Investigation Score: ").append(investigationScores[3]).append("\n");
        summary.append("Character Knowledge: ").append(currentCharacter.getKnowledgePoints()).append("\n");
        summary.append("Combined Knowledge Score: ").append(getTotalKnowledgeScore()).append("\n");
        
        // Key flags status
        summary.append("\nKey Discoveries:\n");
        if (getFlag(FLAG_DIARY_FOUND)) summary.append("• Found child's diary\n");
        if (getFlag(FLAG_ANCHOR_TOKEN_FOUND)) summary.append("• Discovered anchor token\n");
        if (getFlag(FLAG_BEAST_OBSERVED)) summary.append("• Observed the creature\n");
        if (getFlag(FLAG_TRUE_NAME_KNOWN)) summary.append("• Learned creature's true name\n");
        if (getFlag(FLAG_HORN_OBTAINED)) summary.append("• Obtained ancient horn\n");
        if (getFlag(FLAG_CHIEF_INFORMED)) summary.append("• Informed village chief\n");
        if (getFlag(FLAG_RITUAL_FRAGMENTS_OBTAINED)) summary.append("• Gathered ritual fragments\n");
        
        return summary.toString();
    }
}