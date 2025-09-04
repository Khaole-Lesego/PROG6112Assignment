/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysterygame;

// ClimaxManager.java - Complete ending resolution system with enhanced narrative
public class ClimaxManager 
{
    private GameStateManager gameState;
    private String[] possibleEndings;
    private String[] endingDescriptions;
    private int endingCount;
    private int ritualFailureMeter;
    private boolean memoryPaid;
    
    // Enhanced ending constants matching the Mystery Game concept
    private static final String STELLAR_RITUAL_APOTHEOSIS = "STELLAR_RITUAL_APOTHEOSIS";
    private static final String STELLAR_COMBAT_TRIUMPH = "STELLAR_COMBAT_TRIUMPH";
    private static final String STELLAR_POLITICAL_REFORM = "STELLAR_POLITICAL_REFORM";
    private static final String CLEAN_RITUAL_WIN = "CLEAN_RITUAL_WIN";
    private static final String TACTICAL_DESTROY_WIN = "TACTICAL_DESTROY_WIN";
    private static final String PRACTICAL_BARGAIN_WIN = "PRACTICAL_BARGAIN_WIN";
    private static final String PARTIAL_BINDING_SURVIVE = "PARTIAL_BINDING_SURVIVE";
    private static final String WOUNDED_CREATURE_SURVIVE = "WOUNDED_CREATURE_SURVIVE";
    private static final String MEMORY_SCAR_SURVIVE = "MEMORY_SCAR_SURVIVE";
    private static final String LOSS_CONSUMED = "LOSS_CONSUMED";
    private static final String LOSS_POLITICAL_RUIN = "LOSS_POLITICAL_RUIN";
    private static final String LOSS_MORAL_DEBT = "LOSS_MORAL_DEBT";
    private static final String LOSS_FALSE_BIND = "LOSS_FALSE_BIND";
    private static final String LOSS_MEMORY_EROSION = "LOSS_MEMORY_EROSION";
    private static final String SPECIAL_HUNTER_PREDATOR_VICTORY = "SPECIAL_HUNTER_PREDATOR_VICTORY";
    private static final String SPECIAL_MAGE_ARCANE_MASTERY = "SPECIAL_MAGE_ARCANE_MASTERY";

    public ClimaxManager(GameStateManager gameState) 
    {
        this.gameState = gameState;
        this.possibleEndings = new String[16];
        this.endingDescriptions = new String[16];
        this.endingCount = 0;
        this.ritualFailureMeter = 0;
        this.memoryPaid = false;
        initializeAllEndings();
    }

    private void initializeAllEndings() 
    {
        // Stellar Win Endings (Tier 1 - Best Outcomes)
        possibleEndings[0] = STELLAR_RITUAL_APOTHEOSIS;
        endingDescriptions[0] = "The Master's Binding - You taste absence where warm memories used to sit. The weave holds perfectly.";
        
        possibleEndings[1] = STELLAR_COMBAT_TRIUMPH;
        endingDescriptions[1] = "The Beast Slayer - When the creature fell, the earth sighed. You are lauded and quietly feared.";
        
        possibleEndings[2] = STELLAR_POLITICAL_REFORM;
        endingDescriptions[2] = "The Architect - The compact stands in the square, read by all. You are hero and architect of justice.";

        // Clean Win Endings (Tier 2)
        possibleEndings[3] = CLEAN_RITUAL_WIN;
        endingDescriptions[3] = "Memory's Price - Children laugh again. You cannot remember your first pet's name, and that loss aches.";
        
        possibleEndings[4] = TACTICAL_DESTROY_WIN;
        endingDescriptions[4] = "The Wounded Victory - The beast retreated, bleeding essence. The village sleeps uneasy but alive.";
        
        possibleEndings[5] = PRACTICAL_BARGAIN_WIN;
        endingDescriptions[5] = "The Legal Compact - A binding contract constrains the creature. Justice through law, not blood.";

        // Survival Endings (Tier 3)
        possibleEndings[6] = PARTIAL_BINDING_SURVIVE;
        endingDescriptions[6] = "We Bought Time - The mist thins but doesn't fade. You have time, guilt, and eternal watch.";
        
        possibleEndings[7] = WOUNDED_CREATURE_SURVIVE;
        endingDescriptions[7] = "Flee & Heal - Creature wounded and retreating. New responsibilities and dangers await.";
        
        possibleEndings[8] = MEMORY_SCAR_SURVIVE;
        endingDescriptions[8] = "The Ally's Sacrifice - Someone else paid the price. The victory feels hollow and stained.";

        // Loss Endings (Tier 4)
        possibleEndings[9] = LOSS_CONSUMED;
        endingDescriptions[9] = "The Devourer's Victory - You are a name someone whispered and could no longer recall.";
        
        possibleEndings[10] = LOSS_POLITICAL_RUIN;
        endingDescriptions[10] = "The Cover-Up - You sleep in comfort bought with silence. Your soul was the price of peace.";
        
        possibleEndings[11] = LOSS_MORAL_DEBT;
        endingDescriptions[11] = "The Bell Tolls - At night it rings thirteen times. Parents count fingers when children are named.";
        
        possibleEndings[12] = LOSS_FALSE_BIND;
        endingDescriptions[12] = "The Rebound - Your ritual fed the darkness. A hungrier shadow haunts the town.";
        
        possibleEndings[13] = LOSS_MEMORY_EROSION;
        endingDescriptions[13] = "The Forgetting - Books grow blank, and children forget their own names come morning.";

        // Special Character Endings
        possibleEndings[14] = SPECIAL_HUNTER_PREDATOR_VICTORY;
        endingDescriptions[14] = "The Apex Predator - Your patience revealed the creature's weakness. The hunt ends perfectly.";
        
        possibleEndings[15] = SPECIAL_MAGE_ARCANE_MASTERY;
        endingDescriptions[15] = "The Mystic's Transcendence - Your Thread of Knowing transformed threat into wisdom.";
        
        endingCount = 16;
    }

    public String determineEnding(String climaxChoice) 
    {
        int totalKnowledge = gameState.getTotalKnowledgeScore();
        BaseCharacter character = gameState.getCurrentCharacter();
        String characterType = character.getCharacterType();
        
        // Calculate key evidence and achievements
        int ritualFragments = calculateRitualFragments();
        boolean hasAnchorToken = gameState.getFlag(22); // FLAG_ANCHOR_TOKEN_FOUND
        boolean hasTruthEvidence = gameState.getFlag(4) || gameState.getFlag(16); // diary or ledger
        boolean hasLedger = gameState.getFlag(16); // FLAG_LEDGER_TAKEN
        boolean beastObserved = gameState.getFlag(9); // FLAG_BEAST_OBSERVED
        boolean trueName = gameState.getFlag(23); // FLAG_TRUE_NAME_KNOWN
        boolean hornObtained = gameState.getFlag(13); // FLAG_HORN_OBTAINED
        boolean chiefInformed = gameState.getFlag(15); // FLAG_CHIEF_INFORMED
        boolean ritualFragmentsGathered = gameState.getFlag(21); // FLAG_RITUAL_FRAGMENTS_OBTAINED

        // Determine outcome based on choice
        if (climaxChoice.equalsIgnoreCase("RESTORE")) 
        {
            return determineRitualEnding(totalKnowledge, characterType, ritualFragments, 
                                       hasAnchorToken, hasTruthEvidence, ritualFragmentsGathered);
        }
        else if (climaxChoice.equalsIgnoreCase("DESTROY")) 
        {
            return determineDestructionEnding(totalKnowledge, characterType, beastObserved, 
                                            trueName, hornObtained);
        }
        else if (climaxChoice.equalsIgnoreCase("BARGAIN")) 
        {
            return determineBargainEnding(totalKnowledge, characterType, hasLedger, 
                                        chiefInformed, hasTruthEvidence);
        }
        
        return generateDetailedEnding(LOSS_CONSUMED, characterType, totalKnowledge);
    }

    private String determineRitualEnding(int totalKnowledge, String characterType, 
                                       int ritualFragments, boolean hasAnchorToken, 
                                       boolean hasTruthEvidence, boolean ritualFragmentsGathered) 
    {
        // Calculate ritual success probability
        int ritualScore = 0;
        if (ritualFragments >= 2) ritualScore += 3;
        if (hasAnchorToken) ritualScore += 3;
        if (hasTruthEvidence) ritualScore += 2;
        if (ritualFragmentsGathered) ritualScore += 2;
        if (characterType.equals("Mage")) ritualScore += 2;
        
        // Stellar Win conditions (high knowledge + perfect preparation)
        if (totalKnowledge >= 12 && ritualScore >= 8) 
        {
            if (characterType.equals("Mage")) 
            {
                return generateDetailedEnding(STELLAR_RITUAL_APOTHEOSIS, characterType, totalKnowledge);
            }
            else 
            {
                return generateDetailedEnding(SPECIAL_MAGE_ARCANE_MASTERY, characterType, totalKnowledge);
            }
        }

        // Clean Win conditions (good knowledge + solid preparation)
        if (totalKnowledge >= 8 && ritualScore >= 6) 
        {
            return generateDetailedEnding(CLEAN_RITUAL_WIN, characterType, totalKnowledge);
        }
        
        // Survival outcomes (moderate success)
        if (totalKnowledge >= 5 && ritualScore >= 4) 
        {
            return generateDetailedEnding(PARTIAL_BINDING_SURVIVE, characterType, totalKnowledge);
        }
        else if (totalKnowledge >= 5)
        {
            return generateDetailedEnding(MEMORY_SCAR_SURVIVE, characterType, totalKnowledge);
        }

        // Ritual failure
        return generateDetailedEnding(LOSS_FALSE_BIND, characterType, totalKnowledge);
    }

    private String determineDestructionEnding(int totalKnowledge, String characterType, 
                                            boolean beastObserved, boolean trueName, 
                                            boolean hornObtained) 
    {
        int combatScore = 0;
        
        // Combat success factors
        if (characterType.equals("Hunter")) combatScore += 4;
        if (hornObtained) combatScore += 3;
        if (beastObserved) combatScore += 3;
        if (trueName) combatScore += 4;
        if (totalKnowledge >= 8) combatScore += 2;

        // Stellar Combat Win (perfect hunter setup)
        if (totalKnowledge >= 10 && combatScore >= 12) 
        {
            if (characterType.equals("Hunter")) 
            {
                return generateDetailedEnding(STELLAR_COMBAT_TRIUMPH, characterType, totalKnowledge);
            }
            else 
            {
                return generateDetailedEnding(SPECIAL_HUNTER_PREDATOR_VICTORY, characterType, totalKnowledge);
            }
        }

        // Tactical Victory (solid preparation)
        if (totalKnowledge >= 7 && combatScore >= 8) 
        {
            return generateDetailedEnding(TACTICAL_DESTROY_WIN, characterType, totalKnowledge);
        }

        // Wounded but surviving creature
        if (combatScore >= 5 || totalKnowledge >= 6) 
        {
            return generateDetailedEnding(WOUNDED_CREATURE_SURVIVE, characterType, totalKnowledge);
        }

        // Combat failure
        return generateDetailedEnding(LOSS_CONSUMED, characterType, totalKnowledge);
    }

    private String determineBargainEnding(int totalKnowledge, String characterType, 
                                        boolean hasLedger, boolean chiefInformed, 
                                        boolean hasTruthEvidence) 
    {
        int bargainScore = 0;
        
        if (hasLedger) bargainScore += 4;
        if (chiefInformed) bargainScore += 3;
        if (hasTruthEvidence) bargainScore += 3;
        if (totalKnowledge >= 6) bargainScore += 2;

        // Stellar Political Reform
        if (totalKnowledge >= 10 && bargainScore >= 9) 
        {
            return generateDetailedEnding(STELLAR_POLITICAL_REFORM, characterType, totalKnowledge);
        }

        // Practical Bargain Win
        if (bargainScore >= 6 && hasLedger) 
        {
            return generateDetailedEnding(PRACTICAL_BARGAIN_WIN, characterType, totalKnowledge);
        }

        // Partial success
        if (bargainScore >= 4) 
        {
            return generateDetailedEnding(PARTIAL_BINDING_SURVIVE, characterType, totalKnowledge);
        }

        // Bargain failures
        if (hasLedger) 
        {
            return generateDetailedEnding(LOSS_POLITICAL_RUIN, characterType, totalKnowledge);
        }
        else 
        {
            return generateDetailedEnding(LOSS_MORAL_DEBT, characterType, totalKnowledge);
        }
    }

    private int calculateRitualFragments() 
    {
        int fragments = 0;
        
        // Check various sources of ritual knowledge
        if (gameState.getFlag(7)) fragments++; // FLAG_LIBRARY_TRIGGERED
        if (gameState.getFlag(12)) fragments++; // FLAG_CAVE_EXAMINED
        if (gameState.getFlag(14)) fragments++; // FLAG_STONE_CIRCLE_FOUND
        if (gameState.getFlag(21)) fragments++; // FLAG_RITUAL_FRAGMENTS_OBTAINED
        
        // Mage-specific fragments
        BaseCharacter character = gameState.getCurrentCharacter();
        if (character instanceof MageCharacter) 
        {
            MageCharacter mage = (MageCharacter) character;
            fragments += mage.getFragmentCount();
        }
        
        return Math.min(fragments, 5); // Cap at 5 for balance
    }

    private String generateDetailedEnding(String endingType, String characterType, int totalKnowledge) 
    {
        StringBuilder ending = new StringBuilder();
        ending.append("=".repeat(70)).append("\n");
        ending.append("                    FINAL OUTCOME                    \n");
        ending.append("=".repeat(70)).append("\n\n");

        switch (endingType) 
        {
            case STELLAR_RITUAL_APOTHEOSIS -> {
                ending.append("✨ THE MASTER'S BINDING - STELLAR VICTORY ✨\n\n");
                ending.append("As a ").append(characterType).append(" with ").append(totalKnowledge)
                      .append(" total knowledge, you have achieved the impossible.\n\n");
                ending.append("The ritual unfolds with perfect precision. Ancient words flow from your lips like ");
                ending.append("liquid starlight, each syllable binding the creature's essence to your will. The ");
                ending.append("memory you sacrifice - your first glimpse of magic as a child - dissolves like ");
                ending.append("morning mist, but the trade is fair.\n\n");
                ending.append("The name-consuming entity screams as it's drawn back into a crystalline prison of ");
                ending.append("your own making. Its power transforms from malevolence into protection, creating ");
                ending.append("a ward that will guard this village for generations.\n\n");
                ending.append("Children play safely in streets where shadows once whispered hunger. Your sacrifice ");
                ending.append("has bought not just victory, but legendary status. Other mages will study your ");
                ending.append("technique for centuries to come.\n\n");
                ending.append("🏆 Achievement: 'Legendary Ritualist' - Perfect magical resolution\n");
            }
            
            case STELLAR_COMBAT_TRIUMPH -> {
                ending.append("⚔️ THE BEAST SLAYER - STELLAR VICTORY ⚔️\n\n");
                ending.append("Your hunt reaches its ultimate conclusion. As a ").append(characterType)
                      .append(" with ").append(totalKnowledge).append(" total knowledge, you are the apex predator.\n\n");
                ending.append("The ancient horn's call summons spectral allies as you speak the creature's true name. ");
                ending.append("Bound by its own identity, the entity cannot escape as your weapons find their mark. ");
                ending.append("When it falls, the earth itself sighs in relief.\n\n");
                ending.append("The creature's death releases centuries of stolen names. Across the kingdom, people ");
                ending.append("suddenly remember lost children, forgotten loves, and their own true identities. ");
                ending.append("You have not just saved this village - you've restored the memories of thousands.\n\n");
                ending.append("Legends speak of the hunter who tracked the untrackable and slew the immortal. ");
                ending.append("Village children will grow up hearing your name whispered with awe and gratitude.\n\n");
                ending.append("🏆 Achievement: 'Legendary Beast Slayer' - Perfect combat resolution\n");
            }
            
            case STELLAR_POLITICAL_REFORM -> {
                ending.append("⚖️ THE ARCHITECT OF JUSTICE - STELLAR VICTORY ⚖️\n\n");
                ending.append("Your evidence and political maneuvering create something unprecedented: true justice.\n\n");
                ending.append("The compact you negotiate doesn't just bind the creature - it establishes legal ");
                ending.append("precedent for supernatural crimes. The Mystery Villager's ledger exposes an ");
                ending.append("international trafficking network, leading to arrests across three kingdoms.\n\n");
                ending.append("More importantly, your legal framework creates protections against future ");
                ending.append("supernatural exploitation. The creature finds itself bound not by mystical chains, ");
                ending.append("but by laws that recognize its nature while protecting human rights.\n\n");
                ending.append("Your work becomes the foundation for a new branch of jurisprudence. Universities ");
                ending.append("teach courses based on your legal innovations, and other kingdoms adopt your reforms.\n\n");
                ending.append("🏆 Achievement: 'Architect of Justice' - Perfect political resolution\n");
            }
            
            case CLEAN_RITUAL_WIN -> {
                ending.append("🕯️ MEMORY'S PRICE - CLEAN VICTORY 🕯️\n\n");
                ending.append("The binding ritual succeeds, but the cost is deeply personal.\n\n");
                ending.append("As the final words of power leave your lips, you feel something precious slip away - ");
                ending.append("the memory of your first pet's name. That small joy, that innocent connection, ");
                ending.append("becomes the anchor that holds the creature in its mystical prison.\n\n");
                ending.append("The village awakens to safety. Children laugh without fear, and parents sleep ");
                ending.append("peacefully knowing their offspring's names are secure. Your sacrifice has purchased ");
                ending.append("their future, and though the loss aches like a phantom limb, you know the trade was just.\n\n");
                ending.append("Wise Mara visits you sometimes, bringing tea and understanding. She knows the weight ");
                ending.append("of such choices, and together you keep vigil against similar threats.\n\n");
                ending.append("🏅 Achievement: 'Guardian's Sacrifice' - Successful ritual with personal cost\n");
            }
            
            case LOSS_CONSUMED -> {
                ending.append("💀 THE DEVOURER'S VICTORY - TRAGIC LOSS 💀\n\n");
                ending.append("Your investigation ends in the creature's ultimate triumph.\n\n");
                ending.append("Despite gathering ").append(totalKnowledge).append(" points of knowledge, you lacked ");
                ending.append("the crucial understanding needed to face such an entity. The creature's hunger ");
                ending.append("proved stronger than your preparation.\n\n");
                ending.append("As your identity dissolves, your name becomes just another whisper in the Night's ");
                ending.append("Breath. The village children continue to disappear, their families forgetting them ");
                ending.append("before the tears have time to dry.\n\n");
                ending.append("Yet even in defeat, hope remains. Mara finds fragments of your investigation, ");
                ending.append("clues that will help the next brave soul who dares challenge this darkness. ");
                ending.append("Your sacrifice, though tragic, plants seeds of future victory.\n\n");
                ending.append("💔 The Mystery Continues - Your efforts guide future heroes\n");
            }
            
            default -> {
                ending.append("UNKNOWN OUTCOME\n\n");
                ending.append("The threads of fate weave in unexpected patterns. Your investigation into ");
                ending.append("Hollowfen's supernatural plague reaches an uncertain conclusion.\n\n");
                ending.append("With ").append(totalKnowledge).append(" total knowledge gathered, you faced ");
                ending.append("the unknown with courage. Whatever the outcome, your efforts will be remembered.\n\n");
            }
        }

        ending.append("\n").append("=".repeat(70)).append("\n");
        ending.append("Final Knowledge Score: ").append(totalKnowledge).append("\n");
        ending.append("Character Type: ").append(characterType).append("\n");
        ending.append("Investigation Approach: ").append(gameState.getCurrentPath()).append("\n");
        ending.append("=".repeat(70)).append("\n");

        return ending.toString();
    }

    // Utility methods
    public void setMemoryPaid(boolean paid) { this.memoryPaid = paid; }
    public void addRitualFailure(int points) { this.ritualFailureMeter += points; }
    public boolean isMemoryPaid() { return memoryPaid; }
    public int getRitualFailureMeter() { return ritualFailureMeter; }
    
    public String[] getAllPossibleEndings() 
    {
        String[] endings = new String[endingCount];
        System.arraycopy(possibleEndings, 0, endings, 0, endingCount);
        return endings;
    }

    public String getEndingDescription(String endingType) 
    {
        for (int i = 0; i < endingCount; i++) 
        {
            if (possibleEndings[i].equals(endingType)) 
            {
                return endingDescriptions[i];
            }
        }
        return "Unknown ending type.";
    }
}