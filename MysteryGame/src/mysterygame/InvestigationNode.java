/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysterygame;

// InvestigationNode.java - Core investigation system with full action processing
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
                availableActions[1] = "Examine Shipping Ledgers";
                availableActions[2] = "Look for Hidden Key";
                availableActions[3] = "Copy Incriminating Documents";
                availableActions[4] = "Force Lock on Cabinet";
            } 
            else if (nodeName.contains("Bedroom")) 
            {
                availableActions[0] = "Hide Under Bed";
                availableActions[1] = "Hide in Closet";
                availableActions[2] = "Escape Through Window";
                availableActions[3] = "Examine Child's Diary";
                availableActions[4] = "Study Mysterious Locket";
            } 
            else if (nodeName.contains("Library")) 
            {
                availableActions[0] = "Touch Crystalline Object";
                availableActions[1] = "Search Ancient Tomes";
                availableActions[2] = "Enter Secret Room";
                availableActions[3] = "Observe the Beast";
                availableActions[4] = "Attempt Communication";
            }
        } 
        else if (nodeType.equals("Forest")) 
        {
            if (nodeName.contains("Hut")) 
            {
                availableActions[0] = "Follow Strange Tracks";
                availableActions[1] = "Examine Scorched Earth";
                availableActions[2] = "Search for Journal Pages";
                availableActions[3] = "Set Hunter's Trap";
                availableActions[4] = "Continue to Deep Cave";
            } 
            else if (nodeName.contains("Cave")) 
            {
                availableActions[0] = "Examine Black Stone Altar";
                availableActions[1] = "Study Fractured Crystal";
                availableActions[2] = "Investigate Arranged Toys";
                availableActions[3] = "Attempt to Destroy Altar";  
                availableActions[4] = "Use Magical Scrying";
            } 
            else if (nodeName.contains("Stone Circle")) 
            {
                availableActions[0] = "Speak with Hermit Derren";
                availableActions[1] = "Request Ancient Horn";
                availableActions[2] = "Complete Derren's Task";
                availableActions[3] = "Learn Binding Ritual";
                availableActions[4] = "Study Circle Symbols";
            }
        }
        else if (nodeType.equals("Social")) 
        {
            if (nodeName.contains("Chief")) 
            {
                availableActions[0] = "Present Evidence";
                availableActions[1] = "Ask About Village History";
                availableActions[2] = "Request Official Support";
                availableActions[3] = "Warn of Immediate Danger";
                availableActions[4] = "Seek Legal Authority";
            }
            else if (nodeName.contains("Mara")) 
            {
                availableActions[0] = "Ask About Protective Charms";
                availableActions[1] = "Request Ritual Knowledge";
                availableActions[2] = "Share Investigation Findings";
                availableActions[3] = "Ask About Missing Children";
                availableActions[4] = "Seek Magical Guidance";
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
            return "You have already completed this action at this location.";
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
        
        // Manor Actions
        if (action.equals("Hide Under Bed")) 
        {
            if (characterType.equals("Hunter")) 
            {
                result = "Your predator's patience serves you well. Lying motionless beneath the ornate bed frame, " +
                        "you control your breathing as heavy footsteps pace overhead. Through gaps in the floorboards, " +
                        "you hear the Mystery Villager's anguished whispers: 'I will do anything to save her... forgive me " +
                        "for what I did to my own children...' His voice cracks with genuine grief. The confession fragments " +
                        "you've gathered paint a picture of a desperate father who made terrible bargains to try to save his family.";
                addEvidence("Whispered Confession - Desperate father's guilt");
                ((HunterCharacter) character).addHuntingEvidence("Father's Grief Confession");
            } 
            else 
            {
                result = "Your Thread of Knowing hums with magical resonance as you hide beneath the bed. The manor's " +
                        "enchantments seem to amplify certain sounds while muffling others. Through this supernatural acoustics, " +
                        "you hear ritual phrases mixed with his lament: 'The binding holds... but the price... my children's names " +
                        "were the first sacrifice...' Your magical insight reveals he didn't choose evil - he was manipulated by " +
                        "something far more sinister into making an impossible choice.";
                addEvidence("Magical Confession - Ritual binding implications");
                ((MageCharacter) character).addRitualFragment("Father's Binding Confession");
            }
        }
        else if (action.equals("Hide in Closet")) 
        {
            if (characterType.equals("Hunter")) 
            {
                result = "Pressed between expensive garments, your tracker's instincts catalog every detail. The clothing " +
                        "bears marks of distant places - silk with Eastern weaving patterns, leather tooled in the Northern style, " +
                        "and boots caked with soil that doesn't match local earth. Through the keyhole, you observe him examining " +
                        "shipping manifests with trembling hands. 'Forty-three contracts... forty-three families destroyed...' he mutters. " +
                        "This isn't just about his family - he's been trafficking supernatural 'services' across multiple kingdoms.";
                addEvidence("Shipping Evidence - Multi-kingdom trafficking operation");
                ((HunterCharacter) character).addHuntingEvidence("International Trade Markers");
            } 
            else 
            {
                result = "Hidden among luxurious fabrics, your scrying lens detects layers of magical residue on the garments. " +
                        "Each piece of clothing carries traces of different supernatural encounters. As he enters, you hear him " +
                        "reciting names in multiple languages - not incantations, but remembrance. 'Maria Santos, age 7... Jin Wei, age 9... " +
                        "little Astrid, age 6...' Your magical knowledge recognizes these as the names of children whose identities " +
                        "were traded to feed the creature's hunger across distant lands.";
                addEvidence("Arcane Residue - Names of consumed children");
                ((MageCharacter) character).addRitualFragment("List of Sacrificed Names");
                ((MageCharacter) character).addArcaneResidue("Multi-regional Suffering Traces");
            }
        }
        else if (action.equals("Escape Through Window")) 
        {
            result = "You carefully unlatch the window and drop silently into the manor's garden. As you steady yourself " +
                    "against the stone wall, a weathered hand grasps your shoulder. A grizzled guard with haunted eyes " +
                    "stands behind you - not threatening, but desperate. 'You've seen his pain,' he whispers urgently. " +
                    "'But you don't understand the whole truth. My master isn't the villain - he's the victim. Three years ago, " +
                    "something came to him disguised as salvation for his dying daughter. It promised to save her life in exchange " +
                    "for \"small services.\" By the time he understood what those services truly meant, it was too late to refuse.'";
            addEvidence("Guard's Testimony - Master manipulated by supernatural entity");
            character.addKnowledgePoints(3);
        }
        else if (action.equals("Examine Child's Diary")) 
        {
            if (characterType.equals("Hunter")) 
            {
                result = "The diary's pages are filled with a child's innocent handwriting that grows increasingly erratic. " +
                        "Early entries describe 'the nice shadow man who promises to make Mama better.' Later entries become desperate: " +
                        "'Papa cries when he thinks I'm not looking. The shadow man says I have to forget my name to save Mama. " +
                        "But if I forget my name, who will I be?' The final entry is just a few words: 'Mama's better but I can't " +
                        "remember yesterday.' This isn't just about supernatural contracts - it's about a father's impossible choice " +
                        "between losing his wife or his children's identities.";
                addEvidence("Child's Diary - Documentation of name-stealing process");
                ((HunterCharacter) character).addHuntingEvidence("Victim's Personal Account");
            }
            else
            {
                result = "Your magical senses detect layers of enchantment woven into the very ink of this diary. Each word " +
                        "was written under supernatural influence, creating a record of the binding process itself. The child's " +
                        "handwriting becomes increasingly faint as her sense of self was slowly drained away. Between the lines, " +
                        "your Thread of Knowing reveals hidden text - ritual formulas that detail exactly how names are harvested " +
                        "and transferred. This diary isn't just evidence; it's an instruction manual for the creature's feeding process.";
                addEvidence("Enchanted Diary - Ritual instruction manual");
                ((MageCharacter) character).addRitualFragment("Name-Harvesting Technique");
                ((MageCharacter) character).addArcaneResidue("Child's Fading Identity");
            }
        }
        else if (action.equals("Study Mysterious Locket")) 
        {
            result = "The locket appears to be ordinary silver, but your investigation reveals its true nature. When opened, " +
                    "instead of a portrait, it contains a small crystal fragment that pulses with otherworldly light. This is " +
                    "an anchor token - a piece of the original binding that trapped the name-consuming entity. The creature cannot " +
                    "fully manifest or move beyond its designated territory while these tokens exist. However, you notice hairline " +
                    "cracks in the crystal. The binding is weakening, which explains why the creature's influence has been growing stronger.";
            addEvidence("Anchor Token - Weakened binding crystal");
            character.addKnowledgePoints(4);
        }
        
        // Forest Actions
        else if (action.equals("Follow Strange Tracks")) 
        {
            if (characterType.equals("Hunter")) 
            {
                result = "Your tracking expertise reveals a disturbing pattern. The three-toed prints aren't random - they form " +
                        "a precise spiral around the abandoned hut, growing deeper and more defined toward the center. The creature " +
                        "was performing some kind of ritual here. Scattered among the tracks are small personal items: a child's " +
                        "shoe, a torn piece of dress, a wooden toy carved in the shape of a bird. Each item still carries the faint " +
                        "scent of its former owner, though you suspect those children can no longer remember these belongings exist.";
                addEvidence("Ritual Track Pattern - Systematic name harvesting");
                ((HunterCharacter) character).addHuntingEvidence("Creature Movement Patterns");
            }
            else 
            {
                result = "Following the strange tracks, your magical senses detect residual energy signatures embedded in the earth. " +
                        "The three-toed prints glow faintly under your scrying lens, revealing they were made not by physical claws " +
                        "but by concentrated supernatural force. The spiral pattern serves as a harvesting circle - each completed " +
                        "rotation draws more identity from victims within its influence. Your Thread of Knowing identifies traces " +
                        "of at least seven distinct personality essences absorbed into the ground here.";
                addEvidence("Harvesting Circle - Seven absorbed identities");
                ((MageCharacter) character).addRitualFragment("Spiral Harvesting Technique");
            }
        }
        else if (action.equals("Examine Black Stone Altar")) 
        {
            result = "The altar is carved from a type of stone that doesn't exist naturally in this region. Its surface bears " +
                    "symbols that seem to shift and writhe when observed directly. At the center lies a larger crystal fragment, " +
                    "clearly part of the same binding system as the locket token. However, this fragment is completely shattered. " +
                    "The break pattern suggests it wasn't accidental - someone or something deliberately destroyed this anchor point, " +
                    "weakening the overall binding. Arranged around the altar are children's toys, but they're positioned in a pattern " +
                    "that forms a summoning circle. This site isn't just where the creature feeds - it's where it was first bound, " +
                    "and where someone is trying to set it free.";
            addEvidence("Broken Binding Altar - Deliberate sabotage of containment");
            character.addKnowledgePoints(4);
        }
        else if (action.equals("Speak with Hermit Derren")) 
        {
            result = "Derren emerges from behind the ancient stones, his eyes reflecting decades of solitary vigil. 'You seek " +
                    "answers about the shadow that feeds on names,' he says without preamble. 'I am the last of those who bound " +
                    "it, sixty years past. We thought ourselves clever, trapping it in a web of crystal anchors and sacred sites. " +
                    "But bindings require maintenance, and I am old, and the others are long dead.' He gestures to the weathered " +
                    "stone circle. 'Someone has been destroying the anchor points systematically. The creature grows stronger with " +
                    "each broken binding. Soon, it will be free to expand beyond this village, to consume names across the entire realm.'";
            addEvidence("Derren's Warning - Systematic destruction of bindings");
            character.addKnowledgePoints(5);
        }
        else if (action.equals("Request Ancient Horn")) 
        {
            if (characterType.equals("Hunter")) 
            {
                result = "Derren studies you with ancient, knowing eyes. 'You have the bearing of a hunter, but do you understand " +
                        "what you truly hunt?' He produces a horn carved from some unknown material, its surface etched with protective " +
                        "runes. 'This horn was crafted from the bone of the last creature of its kind that we successfully destroyed. " +
                        "When blown, it calls forth the spirits of all those who died fighting such entities. They will come to your " +
                        "aid, but only if you prove worthy. Complete a task for me - find and destroy the source of the binding sabotage, " +
                        "and the horn is yours. Fail, and the spirits will judge you unworthy of their assistance.'";
                addEvidence("Ancient Horn - Requires proof of worthiness");
                ((HunterCharacter) character).addHuntingEvidence("Spirit-Calling Horn Available");
            }
            else
            {
                result = "Derren nods approvingly at your magical aura. 'A practitioner of the Thread of Knowing. Your kind helped " +
                        "create the original binding.' He reveals the horn, and your magical senses immediately recognize its power. " +
                        "'This horn does more than call spirits - it can temporarily strengthen the remaining binding anchors, giving " +
                        "you a window to perform a more powerful ritual. But beware - using it will alert the creature to your location " +
                        "and intent. You'll have perhaps an hour to complete your work before it comes for you with its full power.'";
                addEvidence("Ritual Horn - Strengthens bindings temporarily");
                ((MageCharacter) character).addRitualFragment("Binding Amplification Technique");
            }
        }
        
        // Social Actions
        else if (action.equals("Present Evidence")) 
        {
            result = "Village Chief Aldric examines your evidence with growing alarm. 'By the ancestors... I knew the Mystery " +
                    "Villager was involved in questionable trades, but this...' He spreads out documents and testimonies. 'Child " +
                    "trafficking disguised as supernatural services, systematic destruction of protective bindings, names stolen " +
                    "from dozens of families across multiple kingdoms.' His expression hardens. 'This evidence is sufficient to " +
                    "mobilize our guard and request aid from neighboring territories. More importantly, it gives us legal grounds " +
                    "to act against the Mystery Villager directly - though I suspect he may be as much victim as perpetrator.'";
            addEvidence("Official Recognition - Legal authority to act");
            character.addKnowledgePoints(3);
        }
        else if (action.equals("Ask About Protective Charms")) 
        {
            result = "Wise woman Mara's cottage overflows with herbs and protective amulets. 'The old charms still hold some power,' " +
                    "she explains while grinding silver and iron into protective powder. 'But they're weakening as the creature grows " +
                    "stronger. The traditional methods - salt circles, cold iron, burning sage - these provide temporary protection but " +
                    "won't stop a fully manifested name-eater.' She hands you a specially prepared amulet. 'This will hide your true " +
                    "name from the creature's senses for a short time. Use it wisely - once it knows your real name, no charm can " +
                    "protect you from its hunger.'";
            addEvidence("Protective Amulet - Temporary name concealment");
            character.addKnowledgePoints(2);
        }
        else if (action.equals("Request Ritual Knowledge")) 
        {
            if (characterType.equals("Mage")) 
            {
                result = "Mara recognizes your magical training immediately. 'You seek to repair the binding, don't you? I can help.' " +
                        "She retrieves an ancient tome from a hidden compartment. 'The original binding required seven anchors, but " +
                        "only three remain functional. However, there's a way to create a new binding using the creature's own consumed " +
                        "names as anchor points. It's dangerous - you'll need to give up one of your most precious memories as payment " +
                        "to the ritual. But if successful, the creature will be bound more securely than ever before.'";
                addEvidence("Advanced Binding Ritual - Memory sacrifice required");
                ((MageCharacter) character).addRitualFragment("Memory-Anchor Binding");
            }
            else
            {
                result = "Mara looks at you with concern. 'You're no mage, but your heart is in the right place. I can't teach you " +
                        "the complex rituals, but I can give you something else.' She produces a vial of shimmering liquid. 'This is " +
                        "distilled moonlight, captured during the binding sixty years ago. If poured on the creature's physical form, " +
                        "it will reveal its true shape and make it vulnerable to conventional weapons. One chance, one shot. Make it count.'";
                addEvidence("Moonlight Essence - Makes creature vulnerable");
                ((HunterCharacter) character).addHuntingEvidence("Creature Vulnerability Weapon");
            }
        }
        
        // Default responses for unmatched actions
        else if (action.contains("Search") || action.contains("Examine") || action.contains("Study")) 
        {
            result = "You conduct a thorough investigation of the area. Your careful search reveals additional clues about " +
                    "the supernatural threat plaguing the village. The evidence you gather will be crucial for the final confrontation.";
            addEvidence("General Investigation - Additional context clues");
            character.addKnowledgePoints(1);
        }
        else if (action.contains("Touch") || action.contains("Enter") || action.contains("Attempt"))
        {
            result = "You take a bold action that brings you closer to the truth. The experience is both enlightening and " +
                    "dangerous, providing valuable insight into the nature of your supernatural adversary.";
            addEvidence("Direct Encounter - Firsthand supernatural experience");
            character.addKnowledgePoints(2);
        }
        else 
        {
            result = "You perform the action, gaining valuable experience and knowledge about the mysterious circumstances " +
                    "surrounding the village. Every piece of information brings you closer to understanding the full scope of the threat.";
            addEvidence("Investigation Progress - General findings");
            character.addKnowledgePoints(1);
        }
        
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