/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysterygame;

// InvestigationNode.java - Core investigation system
public class InvestigationNode 
{
    private String nodeId;
    private String nodeName;
    private String nodeDescription;
    private String[] availableActions;
    private String[] evidenceFound;
    private boolean[] actionCompleted;
    private int evidenceCount;
    private boolean nodeVisited;
    private String nodeType; // "Manor", "Forest", "Social"
    
    public InvestigationNode(String nodeId, String nodeName, String nodeDescription, String nodeType) 
    {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.nodeDescription = nodeDescription;
        this.nodeType = nodeType;
        this.availableActions = new String[5];
        this.evidenceFound = new String[10];
        this.actionCompleted = new boolean[5];
        this.evidenceCount = 0;
        this.nodeVisited = false;
        initializeNodeActions();
    }
    
    private void initializeNodeActions() 
    {
        if (nodeType.equals("Manor")) 
        {
            if (nodeName.contains("Office")) 
            {
                availableActions[0] = "Search Desk Drawers";
                availableActions[1] = "Examine Ledgers";
                availableActions[2] = "Look for Hidden Key";
                availableActions[3] = "Copy Documents";
                availableActions[4] = "Force Entry";
            } 
            else if (nodeName.contains("Bedroom")) 
            {
                availableActions[0] = "Hide Under Bed";
                availableActions[1] = "Hide in Closet";
                availableActions[2] = "Escape Through Window";
                availableActions[3] = "Examine Diary";
                availableActions[4] = "Study Locket";
            } 
            else if (nodeName.contains("Library")) 
            {
                availableActions[0] = "Touch Shiny Object";
                availableActions[1] = "Search Bookshelves";
                availableActions[2] = "Enter Secret Room";
                availableActions[3] = "Observe Beast";
                availableActions[4] = "Attempt Communication";
            }
        } 
        else if (nodeType.equals("Forest")) 
        {
            if (nodeName.contains("Hut")) 
            {
                availableActions[0] = "Follow Tracks";
                availableActions[1] = "Examine Scorched Leaves";
                availableActions[2] = "Search for Journal Pages";
                availableActions[3] = "Set Trap";
                availableActions[4] = "Continue to Cave";
            } 
            else if (nodeName.contains("Cave")) 
            {
                availableActions[0] = "Examine Altar";
                availableActions[1] = "Study Binding Crystal";
                availableActions[2] = "Arrange Toys";
                availableActions[3] = "Destroy Altar";  
                availableActions[4] = "Use Scent Stone";
            } 
            else if (nodeName.contains("Stone Circle")) 
            {
                availableActions[0] = "Meet Derren";
                availableActions[1] = "Request Horn";
                availableActions[2] = "Complete Favor";
                availableActions[3] = "Learn Ancient Lore";
                availableActions[4] = "Prepare Final Ritual";
            }
        }
    }
    
    public String performAction(int actionIndex, BaseCharacter character) 
    {
        if (actionIndex < 0 || actionIndex >= availableActions.length || availableActions[actionIndex] == null) 
        {
            return "Invalid action selected.";
        }
        
        if (actionCompleted[actionIndex]) 
        {
            return "You have already completed this action.";
        }
        
        String action = availableActions[actionIndex];
        String result = processActionForCharacter(action, character);
        actionCompleted[actionIndex] = true;
        nodeVisited = true;
        
        return result;
    }
    
    private String processActionForCharacter(String action, BaseCharacter character) 
    {
        String characterType = character.getCharacterType();
        String result = "";
        
        if (action.equals("Hide Under Bed")) 
        {
            if (characterType.equals("Hunter")) 
            {
                result = "Your hunter instincts serve you well. You remain perfectly still as heavy footsteps pace above. " +
                        "Through the floorboards, you hear muffled whispers: 'I will do anything to save her... what I did to my children...' " +
                        "The anguish in his voice cuts through the silence like a blade. You've gained crucial confession fragments.";
                addEvidence("Whispered Confession - Guilt over wife and children");
                character.addKnowledgePoints(3);
            } 
            else 
            {
                result = "Your magical senses tingle as you hide beneath the ornate bed frame. The wooden floor vibrates with each step above. " +
                        "Ethereal whispers seem to flow through the manor's magical aura: 'The bargain... the names... I had no choice...' " +
                        "Your Thread of Knowing reveals deeper ritual implications in his words.";
                addEvidence("Magical Confession - Ritual bargain implications");
                ((MageCharacter) character).addRitualFragment("Confession Fragment");
                character.addKnowledgePoints(3);
            }
        }
        else if (action.equals("Hide in Closet")) 
        {
            if (characterType.equals("Hunter")) 
            {
                result = "You squeeze between expensive garments, your tracker's eye immediately noting the exotic materials. " +
                        "Silk from the Eastern Territories, leather with strange maker's marks, and hats adorned with symbols you don't recognize. " +
                        "Through the keyhole, you glimpse him muttering while examining a torn piece of parchment. Foreign merchant connections confirmed.";
                addEvidence("Exotic Garments - Foreign merchant connections");
                ((HunterCharacter) character).addHuntingEvidence("Foreign Trader Marks");
            } 
            else 
            {
                result = "Hidden among the luxurious clothing, your scrying lens detects faint magical residue on several garments. " +
                        "The exotic hats bear arcane symbols from distant magical traditions. As he enters, you hear incantation snippets in an ancient tongue. " +
                        "Your magical knowledge identifies them as binding phrases used in name-stealing rituals.";
                addEvidence("Arcane Symbols - Binding ritual components");
                ((MageCharacter) character).addRitualFragment("Binding Incantation");
                ((MageCharacter) character).addArcaneResidue("Foreign Magic Residue");
            }
        }
        else if (action.equals("Escape Through Window")) 
        {
            result = "You carefully unlatch the window and slip into the cool night air. As your feet touch the ground, " +
                    "a firm hand grasps your shoulder. A weathered guard with knowing eyes stands behind you. " +
                    "'You've seen too much,' he whispers urgently. 'Come with me - there are things about my master you need to understand.' " +
                    "He leads you to a small chamber where candlelight reveals his tortured expression.";
            addEvidence("Guard's Secret - Direct witness testimony");
            character.addKnowledgePoints(2);
        }
        
        // Add more action processing...
        
        return result;
    }
    
    private void addEvidence(String evidence) 
    {
        if (evidenceCount < evidenceFound.length) 
        {
            evidenceFound[evidenceCount] = evidence;
            evidenceCount++;
        }
    }
    
    // Getters for information hiding
    public String getNodeName() { return nodeName; }
    public String getNodeDescription() { return nodeDescription; }
    public boolean isNodeVisited() { return nodeVisited; }
    public String[] getAvailableActions() { return availableActions; }
    public String[] getEvidenceFound() 
    {
        String[] actualEvidence = new String[evidenceCount];
        for (int i = 0; i < evidenceCount; i++) 
        {
            actualEvidence[i] = evidenceFound[i];
        }
        return actualEvidence;
    }
}
