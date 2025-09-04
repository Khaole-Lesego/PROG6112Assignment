/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysterygame;

// HunterCharacter.java - Specific character implementation with enhanced tracking abilities
public class HunterCharacter extends BaseCharacter 
{
    private boolean predatorsPatienceActive;
    private int stealthBonus;
    private String[] huntingEvidence;
    private int evidenceCount;
    
    public HunterCharacter(String characterName) 
    {
        super(characterName, "Hunter");
        this.predatorsPatienceActive = true;
        this.stealthBonus = 1;
        this.huntingEvidence = new String[15]; // Increased capacity
        this.evidenceCount = 0;
    }
    
    @Override
    protected void initializeSkills() 
    {
        this.skillset = new boolean[6];
        this.skillset[0] = true; // tracking
        this.skillset[1] = true; // stealth
        this.skillset[2] = true; // traps
        this.skillset[3] = true; // physical confrontation
        this.skillset[4] = true; // reading terrain
        this.skillset[5] = false; // magic detection
    }
    
    @Override
    protected void initializeTools() 
    {
        this.tools = new String[5];
        this.tools[0] = "Hunting Bow";
        this.tools[1] = "Trap Kit";
        this.tools[2] = "Scent Stone";
        this.tools[3] = "Tracker's Notebook";
        this.tools[4] = "Horn (findable)";
    }
    
    @Override
    public String performSpecialAction(String action) 
    {
        if (action.equals("PREDATOR_PATIENCE")) 
        {
            return "Your predator's patience grants you enhanced tracking abilities. You can remain motionless for hours, " +
                   "observing your target's patterns and waiting for the perfect moment to strike. Physical evidence and " +
                   "creature tracks are more easily discovered.";
        }
        else if (action.equals("SET_TRAP"))
        {
            return "Using your trap kit, you set a carefully concealed snare designed to capture supernatural entities. " +
                   "The trap is baited with items that draw creatures seeking to feed on human essence.";
        }
        else if (action.equals("TRACK_CREATURE"))
        {
            return "Your tracking expertise allows you to follow even supernatural trails. You can read the signs of " +
                   "otherworldly passage and predict where the creature will appear next.";
        }
        return "Unknown hunter action: " + action;
    }
    
    @Override
    public boolean canAccessLocation(String location) 
    {
        // Hunters excel at outdoor and dangerous locations
        String[] favoredLocations = {"Forest", "Cave", "Hut", "Stone Circle", "Abandoned", "Wild"};
        for (String favoredLocation : favoredLocations) 
        {
            if (location.contains(favoredLocation)) 
            {
                return true;
            }
        }
        // Hunters can access most locations through stealth and force
        return true; 
    }
    
    public void addHuntingEvidence(String evidence) 
    {
        if (evidenceCount < huntingEvidence.length) 
        {
            huntingEvidence[evidenceCount] = evidence;
            evidenceCount++;
            addKnowledgePoints(2); // Hunters get 2 points per physical evidence
        }
    }
    
    public String[] getHuntingEvidence() 
    {
        String[] actualEvidence = new String[evidenceCount];
        for (int i = 0; i < evidenceCount; i++) 
        {
            actualEvidence[i] = huntingEvidence[i];
        }
        return actualEvidence;
    }
    
    public int getEvidenceCount() 
    {
        return evidenceCount;
    }
    
    public boolean hasPredatorsPatience() 
    {
        return predatorsPatienceActive;
    }
    
    public int getStealthBonus() 
    {
        return stealthBonus;
    }
    
    // Enhanced investigation methods for hunters
    public String investigatePhysicalEvidence(String location) 
    {
        StringBuilder result = new StringBuilder();
        result.append("Using your tracker's instincts, you examine ").append(location).append(" for physical signs.\n");
        
        if (predatorsPatienceActive) 
        {
            result.append("Your predator's patience reveals subtle details others would miss:\n");
            result.append("• Faint impressions in the earth\n");
            result.append("• Disturbed vegetation patterns\n");
            result.append("• Scent traces of supernatural activity\n");
            addKnowledgePoints(1);
        }
        
        return result.toString();
    }
    
    @Override
    public String toString() 
    {
        return String.format("Hunter %s - Knowledge: %d, Evidence Collected: %d, Skills: Tracking/Combat", 
                           getCharacterName(), getKnowledgePoints(), evidenceCount);
    }
}