/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysterygame;


// HunterCharacter.java - Specific character implementation
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
        this.huntingEvidence = new String[10];
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
            return "Your predator's patience grants you enhanced tracking abilities. You spot physical tokens at half visibility threshold.";
        }
        return "Unknown hunter action.";
    }
    
    @Override
    public boolean canAccessLocation(String location) 
    {
        // Hunters favor direct approaches and physical locations
        String[] favoredLocations = {"Forest", "Cave", "Hut", "Stone Circle"};
        for (String favoredLocation : favoredLocations) 
        {
            if (location.contains(favoredLocation)) 
            {
                return true;
            }
        }
        return true; // Hunters can access most locations through force/stealth
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
}
