/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysterygame;

// MageCharacter.java - Specific character implementation
public class MageCharacter extends BaseCharacter 
{
    private boolean threadOfKnowingActive;
    private int persuasionBonus;
    private String[] ritualFragments;
    private int fragmentCount;
    private String[] arcaneResidues;
    private int residueCount;
    
    public MageCharacter(String characterName) 
    {
        super(characterName, "Mage");
        this.threadOfKnowingActive = true;
        this.persuasionBonus = 2;
        this.ritualFragments = new String[5];
        this.arcaneResidues = new String[5];
        this.fragmentCount = 0;
        this.residueCount = 0;
    }
    
    @Override
    protected void initializeSkills() 
    {
        this.skillset = new boolean[6];
        this.skillset[0] = false; // tracking
        this.skillset[1] = true; // stealth (magical)
        this.skillset[2] = false; // traps
        this.skillset[3] = false; // physical confrontation
        this.skillset[4] = false; // reading terrain
        this.skillset[5] = true; // magic detection
    }
    
    @Override
    protected void initializeTools() 
    {
        this.tools = new String[4];
        this.tools[0] = "Scrying Lens";
        this.tools[1] = "Sigil Chalk";
        this.tools[2] = "Grimoire Fragments";
        this.tools[3] = "Amulet Reader";
    }
    
    @Override
    public String performSpecialAction(String action) 
    {
        if (action.equals("THREAD_OF_KNOWING")) 
        {
            return "Your magical intuition reveals arcane residue and hidden ritual fragments. NPCs are more likely to reveal secrets.";
        }
        return "Unknown mage action.";
    }
    
    @Override
    public boolean canAccessLocation(String location) 
    {
        // Mages favor dialogue and magical locations
        String[] favoredLocations = {"Manor", "Library", "Office", "Secret Room"};
        for (String favoredLocation : favoredLocations) 
        {
            if (location.contains(favoredLocation)) 
            {
                return true;
            }
        }
        return true; // Mages can access locations through persuasion
    }
    
    public void addRitualFragment(String fragment) 
    {
        if (fragmentCount < ritualFragments.length) 
        {
            ritualFragments[fragmentCount] = fragment;
            fragmentCount++;
            addKnowledgePoints(3); // Mages get 3 points per ritual fragment
        }
    }
    
    public void addArcaneResidue(String residue) 
    {
        if (residueCount < arcaneResidues.length) 
        {
            arcaneResidues[residueCount] = residue;
            residueCount++;
            addKnowledgePoints(1); // Residues give 1 point
        }
    }
    
    public String[] getRitualFragments() 
    {
        String[] actualFragments = new String[fragmentCount];
        for (int i = 0; i < fragmentCount; i++) 
        {
            actualFragments[i] = ritualFragments[i];
        }
        return actualFragments;
    }
}
