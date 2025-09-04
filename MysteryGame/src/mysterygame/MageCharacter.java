/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysterygame;

// MageCharacter.java - Specific character implementation with enhanced magical abilities
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
        this.ritualFragments = new String[10]; // Increased capacity
        this.arcaneResidues = new String[10];  // Increased capacity
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
            return "Your Thread of Knowing reveals the hidden connections between all things. Magical residue becomes " +
                   "visible, arcane symbols translate themselves, and NPCs are compelled to share deeper truths. " +
                   "You can sense the supernatural currents that flow through locations and objects.";
        }
        else if (action.equals("SCRYING"))
        {
            return "Using your scrying lens, you peer beyond the veil of normal perception. Distant locations become " +
                   "visible, hidden magical auras are revealed, and the true nature of supernatural entities is exposed.";
        }
        else if (action.equals("RITUAL_CASTING"))
        {
            return "You begin weaving the complex patterns of a ritual spell. Your knowledge of arcane formulas and " +
                   "collected ritual fragments determine the power and scope of your magical working.";
        }
        return "Unknown mage action: " + action;
    }
    
    @Override
    public boolean canAccessLocation(String location) 
    {
        // Mages favor locations with magical significance or social interaction
        String[] favoredLocations = {"Manor", "Library", "Office", "Secret Room", "Village", "Cottage"};
        for (String favoredLocation : favoredLocations) 
        {
            if (location.contains(favoredLocation)) 
            {
                return true;
            }
        }
        // Mages can access most locations through persuasion and magical means
        return true; 
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
    
    public String[] getArcaneResidues() 
    {
        String[] actualResidues = new String[residueCount];
        for (int i = 0; i < residueCount; i++) 
        {
            actualResidues[i] = arcaneResidues[i];
        }
        return actualResidues;
    }
    
    public int getFragmentCount() 
    {
        return fragmentCount;
    }
    
    public int getResidueCount() 
    {
        return residueCount;
    }
    
    public boolean hasThreadOfKnowing() 
    {
        return threadOfKnowingActive;
    }
    
    public int getPersuasionBonus() 
    {
        return persuasionBonus;
    }
    
    // Enhanced investigation methods for mages
    public String detectMagicalAura(String location) 
    {
        StringBuilder result = new StringBuilder();
        result.append("Your Thread of Knowing extends into ").append(location).append(", revealing magical traces.\n");
        
        if (threadOfKnowingActive) 
        {
            result.append("Your magical senses detect:\n");
            result.append("• Residual enchantment patterns\n");
            result.append("• Supernatural entity signatures\n");
            result.append("• Hidden magical objects or portals\n");
            addKnowledgePoints(1);
        }
        
        return result.toString();
    }
    
    public String performRitualAnalysis() 
    {
        StringBuilder analysis = new StringBuilder();
        analysis.append("Analyzing collected ritual fragments:\n");
        
        for (int i = 0; i < fragmentCount; i++) 
        {
            analysis.append("• ").append(ritualFragments[i]).append("\n");
        }
        
        if (fragmentCount >= 3) 
        {
            analysis.append("\nSufficient fragments collected for complex ritual work.");
        }
        else 
        {
            analysis.append("\nMore fragments needed for powerful rituals.");
        }
        
        return analysis.toString();
    }
    
    @Override
    public String toString() 
    {
        return String.format("Mage %s - Knowledge: %d, Fragments: %d, Residues: %d, Skills: Magic/Persuasion", 
                           getCharacterName(), getKnowledgePoints(), fragmentCount, residueCount);
    }
}