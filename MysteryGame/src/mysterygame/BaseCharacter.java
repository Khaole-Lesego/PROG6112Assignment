/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysterygame;

// BaseCharacter.java - Abstract parent class
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
    
    // Information hiding - protected getters/setters
    protected void addKnowledgePoints(int points) 
    {
        this.knowledgePoints += points;
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
}
