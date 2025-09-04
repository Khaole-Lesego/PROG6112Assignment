/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysterygame;

// BaseCharacter.java - Abstract parent class with enhanced functionality
public abstract class BaseCharacter 
{
    protected String characterName;
    protected String characterType;
    protected int knowledgePoints;
    protected String[] tools;
    protected boolean[] skillset;
    protected int totalInvestigations;
    
    public BaseCharacter(String characterName, String characterType) 
    {
        this.characterName = characterName;
        this.characterType = characterType;
        this.knowledgePoints = 0;
        this.totalInvestigations = 0;
        initializeSkills();
        initializeTools();
    }
    
    protected abstract void initializeSkills();
    protected abstract void initializeTools();
    public abstract String performSpecialAction(String action);
    public abstract boolean canAccessLocation(String location);
    
    // Information hiding - protected knowledge point management
    protected void addKnowledgePoints(int points) 
    {
        if (points > 0) {
            this.knowledgePoints += points;
            this.totalInvestigations++;
        }
    }
    
    public int getKnowledgePoints() 
    {
        return this.knowledgePoints;
    }
    
    public String getCharacterName() 
    {
        return this.characterName;
    }
    
    public String getCharacterType() 
    {
        return this.characterType;
    }
    
    public int getTotalInvestigations() 
    {
        return this.totalInvestigations;
    }
    
    public String[] getTools() 
    {
        return this.tools.clone(); // Return a copy to maintain encapsulation
    }
    
    public boolean[] getSkillset() 
    {
        return this.skillset.clone(); // Return a copy to maintain encapsulation
    }
    
    
    // Enhanced character status information
    public String getStatus() 
    {
        StringBuilder status = new StringBuilder();
        status.append("Character: ").append(characterName).append(" (").append(characterType).append(")\n");
        status.append("Knowledge Points: ").append(knowledgePoints).append("\n");
        status.append("Investigations Completed: ").append(totalInvestigations).append("\n");
        
        status.append("Tools: ");
        for (int i = 0; i < tools.length; i++) {
            if (tools[i] != null) {
                if (i > 0) status.append(", ");
                status.append(tools[i]);
            }
        }
        status.append("\n");
        
        status.append("Skills: ");
        String[] skillNames = {"Tracking", "Stealth", "Traps", "Combat", "Terrain Reading", "Magic Detection"};
        boolean first = true;
        for (int i = 0; i < skillset.length && i < skillNames.length; i++) {
            if (skillset[i]) {
                if (!first) status.append(", ");
                status.append(skillNames[i]);
                first = false;
            }
        }
        
        return status.toString();
    }
    
    // Check if character has a specific skill
    public boolean hasSkill(int skillIndex) 
    {
        return skillIndex >= 0 && skillIndex < skillset.length && skillset[skillIndex];
    }
    
    // Check if character has a specific tool
    public boolean hasTool(String toolName) 
    {
        for (String tool : tools) {
            if (tool != null && tool.equalsIgnoreCase(toolName)) {
                return true;
            }
        }
        return false;
    }
    
    // Enhanced toString for debugging and display
    @Override
    public String toString() 
    {
        return String.format("%s %s - Knowledge: %d, Investigations: %d", 
                           characterType, characterName, knowledgePoints, totalInvestigations);
    }
}