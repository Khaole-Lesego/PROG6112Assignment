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
            "The massive oak desk dominates the room, its drawers secured with intricate locks.",
            "Manor"
        );
        
        manorNodes[1] = new InvestigationNode(
            "MANOR_BEDROOM", 
            "The Barricaded Bedroom",
            "Behind the barricaded door lies a chamber frozen in grief. Torn children's toys scatter the floor " +
            "like memories of innocence lost. A locket gleams on the nightstand beside a leather-bound diary, " +
            "its pages filled with desperate words. The air feels heavy with sorrow and terrible secrets.",
            "Manor"
        );
        
        manorNodes[2] = new InvestigationNode(
            "MANOR_LIBRARY", 
            "The Manor's Private Library",
            "Towering bookshelves reach toward vaulted ceilings, filled with rare tomes and forbidden knowledge. " +
            "A peculiar shimmer catches your eye from across the room - a crystalline object that seems to pulse " +
            "with otherworldly energy. The scent of ancient magic permeates this scholarly sanctuary.",
            "Manor"
        );
        
        // Forest Investigation Nodes
        forestNodes[0] = new InvestigationNode(
            "FOREST_HUT", 
            "The Abandoned Hut",
            "This crumbling shelter bears the scars of supernatural encounters. Three-toed prints circle the structure " +
            "in disturbing patterns, while scorched earth tells of flames that burned with unnatural heat. " +
            "Fragments of a journal flutter in the wind, their torn pages whispering of ancient debts.",
            "Forest"
        );
        
        forestNodes[1] = new InvestigationNode(
            "FOREST_CAVE", 
            "The Hidden Cave",
            "Deep within the forest's heart lies this underground chamber, carved by hands unknown. " +
            "An altar of black stone dominates the space, surrounded by fractured binding crystals that " +
            "still hum with residual power. Children's toys arranged in ritualistic patterns speak of dark ceremonies.",
            "Forest"
        );
        
        forestNodes[2] = new InvestigationNode(
            "FOREST_STONE_CIRCLE", 
            "The Ancient Stone Circle",
            "Weathered monoliths stand sentinel in this sacred grove, their surfaces etched with symbols older than memory. " +
            "Here dwells Derren, the hermit guardian who keeps watch over ancient covenants. " +
            "The very air thrums with ancestral power and forgotten wisdom.",
            "Forest"
        );
        
        // Social Investigation Nodes
        socialNodes[0] = new InvestigationNode(
            "VILLAGE_CHIEF", 
            "Village Chief's Hall",
            "The heart of village governance, where decisions that affect all are made. " +
            "Chief Aldric, a man weathered by years of leadership, knows the old stories and darker truths " +
            "that most prefer to forget. His hall holds records of ancient pacts and modern crimes.",
            "Social"
        );
        
        socialNodes[1] = new InvestigationNode(
            "MARA_COTTAGE", 
            "Mara's Cottage",
            "The wise woman's dwelling sits at the village edge, where conventional meets mystical. " +
            "Mara possesses knowledge of protective charms and ancient rituals, her trust earned through " +
            "careful conversation and demonstrated wisdom. Her cottage overflows with herbs and hidden lore.",
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
                    investigationScores[0] += 2;
                }
                else if (action.equals("Hide in Closet")) 
                {
                    setFlag(FLAG_HID_IN_CLOSET, true);
                    investigationScores[0] += 2;
                }
                else if (action.contains("Window")) 
                {
                    setFlag(FLAG_JUMPED_WINDOW, true);
                    investigationScores[0] += 1;
                }
                if (action.contains("Diary")) 
                {
                    setFlag(FLAG_DIARY_FOUND, true);
                    investigationScores[0] += 3;
                }
                if (action.contains("Locket")) 
                {
                    setFlag(FLAG_LOCKET_FOUND, true);
                    setFlag(FLAG_ANCHOR_TOKEN_FOUND, true);
                    investigationScores[0] += 2;
                }
            }
            else if (nodeName.contains("Library")) 
            {
                if (action.contains("Shiny Object")) 
                {
                    setFlag(FLAG_LIBRARY_TRIGGERED, true);
                    investigationScores[0] += 2;
                }
                if (action.contains("Secret Room")) 
                {
                    setFlag(FLAG_SECRET_ROOM_TRAPPED, true);
                    investigationScores[0] += 3;
                }
                if (action.contains("Beast")) 
                {
                    setFlag(FLAG_BEAST_OBSERVED, true);
                    setFlag(FLAG_TRUE_NAME_KNOWN, true);
                    investigationScores[0] += 4;
                }
            }
            
            // Forest-specific flag updates
            if (nodeName.contains("Hut")) 
            {
                if (action.contains("Tracks")) 
                {
                    setFlag(FLAG_TRACKS_FOLLOWED, true);
                    setFlag(FLAG_HUT_EXAMINED, true);
                    investigationScores[1] += 2; // Forest score
                }
            }
            else if (nodeName.contains("Cave")) 
            {
                if (action.contains("Altar")) 
                {
                    setFlag(FLAG_CAVE_EXAMINED, true);
                    setFlag(FLAG_ANCHOR_TOKEN_FOUND, true);
                    investigationScores[1] += 3;
                }
            }
            else if (nodeName.contains("Stone Circle")) 
            {
                if (action.contains("Horn")) 
                {
                    setFlag(FLAG_HORN_OBTAINED, true);
                    investigationScores[1] += 3;
                }
                if (action.contains("Derren")) 
                {
                    setFlag(FLAG_STONE_CIRCLE_FOUND, true);
                    investigationScores[1] += 2;
                }
            }
            
            // Social-specific flag updates
            if (nodeName.contains("Chief")) 
            {
                setFlag(FLAG_CHIEF_INFORMED, true);
                investigationScores[2] += 2; // Social score
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
}
