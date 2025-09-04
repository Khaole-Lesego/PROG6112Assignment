/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysterygame;

// ClimaxManager.java - Complete ending resolution system
public class ClimaxManager 
{
    private GameStateManager gameState;
    private String[] possibleEndings;
    private String[] endingDescriptions;
    private int endingCount;
    private int ritualFailureMeter;
    private boolean memoryPaid;
    
    // Enhanced ending constants matching the Mystery Game PDF
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
        endingDescriptions[0] = "The Master's Binding - You taste absence where a warm July afternoon used to sit. The weave holds perfectly.";
        
        possibleEndings[1] = STELLAR_COMBAT_TRIUMPH;
        endingDescriptions[1] = "The Beast Slayer - When the creature fell the earth sighed. You are lauded and quietly feared.";
        
        possibleEndings[2] = STELLAR_POLITICAL_REFORM;
        endingDescriptions[2] = "The Architect - The compact stood in the square, read by everyone. You are hero and architect of a new order.";

        // Clean Win Endings (Tier 2)
        possibleEndings[3] = CLEAN_RITUAL_WIN;
        endingDescriptions[3] = "Memory's Price - Kiri laughs again. You cannot remember your mother's kitchen, and that loss lives like a quiet wound.";
        
        possibleEndings[4] = TACTICAL_DESTROY_WIN;
        endingDescriptions[4] = "The Wounded Victory - The beast tore and bled. Hollowfen sleeps tonight, uneasy but alive.";
        
        possibleEndings[5] = PRACTICAL_BARGAIN_WIN;
        endingDescriptions[5] = "The Legal Compact - A ledger binds the town. You bargained with law instead of blood.";

        // Survival Endings (Tier 3)
        possibleEndings[6] = PARTIAL_BINDING_SURVIVE;
        endingDescriptions[6] = "We Bought Time - The mist thins, then settles in a new place. You have time, guilt, and watch to keep.";
        
        possibleEndings[7] = WOUNDED_CREATURE_SURVIVE;
        endingDescriptions[7] = "Flee & Heal - Creature wounded and retreating. New responsibilities await.";
        
        possibleEndings[8] = MEMORY_SCAR_SURVIVE;
        endingDescriptions[8] = "The Ally's Sacrifice - An ally wakes without the face of a memory. The town sleeps; you cannot unsee what you traded.";

        // Loss Endings (Tier 4)
        possibleEndings[9] = LOSS_CONSUMED;
        endingDescriptions[9] = "The Devourer's Victory - You are a name someone once whispered and could no longer recall.";
        
        possibleEndings[10] = LOSS_POLITICAL_RUIN;
        endingDescriptions[10] = "The Cover-Up - You sleep in a high bed that smells of cedar and burned paper. You lost your face in the crowd.";
        
        possibleEndings[11] = LOSS_MORAL_DEBT;
        endingDescriptions[11] = "The Bell Tolls - At night a bell rings. Parents count fingers when their children are named.";
        
        possibleEndings[12] = LOSS_FALSE_BIND;
        endingDescriptions[12] = "The Rebound - The ritual cracked and fed the dark. A name hunts the town at night.";
        
        possibleEndings[13] = LOSS_MEMORY_EROSION;
        endingDescriptions[13] = "The Forgetting - Books grow blank, and children forget their grandmothers' names.";

        // Special Character Endings
        possibleEndings[14] = SPECIAL_HUNTER_PREDATOR_VICTORY;
        endingDescriptions[14] = "The Predator's Hunt - Your tracking led to the creature's ultimate weakness.";
        
        possibleEndings[15] = SPECIAL_MAGE_ARCANE_MASTERY;
        endingDescriptions[15] = "The Mystic's Transcendence - Your Thread of Knowing transformed the threat into wisdom.";
        
        endingCount = 16;
    }

    public String determineEnding(String climaxChoice) 
    {
        int totalKnowledge = gameState.getTotalKnowledgeScore();
        BaseCharacter character = gameState.getCurrentCharacter();
        String characterType = character.getCharacterType();
        
        // Calculate ritual fragments, evidence, and special items
        int ritualFragments = calculateRitualFragments();
        boolean hasAnchorToken = gameState.getFlag(22); // FLAG_ANCHOR_TOKEN_FOUND
        boolean hasTruthEvidence = gameState.getFlag(4) || gameState.getFlag(16); // diary or ledger
        boolean hasLedger = gameState.getFlag(16); // FLAG_LEDGER_TAKEN
        boolean beastObserved = gameState.getFlag(9); // FLAG_BEAST_OBSERVED
        boolean trueName = gameState.getFlag(23); // FLAG_TRUE_NAME_KNOWN
        boolean hornObtained = gameState.getFlag(13); // FLAG_HORN_OBTAINED
        boolean chiefInformed = gameState.getFlag(15); // FLAG_CHIEF_INFORMED
        boolean mayorConfessPublic = gameState.getFlag(17); // FLAG_MAYOR_ALERTED

        if (climaxChoice.equalsIgnoreCase("RESTORE")) 
        {
            return determineRitualEnding(totalKnowledge, characterType, ritualFragments, 
                                       hasAnchorToken, hasTruthEvidence);
        }
        else if (climaxChoice.equalsIgnoreCase("DESTROY")) 
        {
            return determineDestructionEnding(totalKnowledge, characterType, beastObserved, 
                                            trueName, hornObtained);
        }
        else if (climaxChoice.equalsIgnoreCase("BARGAIN")) 
        {
            return determineBargainEnding(totalKnowledge, characterType, hasLedger, 
                                        chiefInformed, mayorConfessPublic);
        }
        
        return LOSS_CONSUMED; // Default for invalid choice
    }

    private String determineRitualEnding(int totalKnowledge, String characterType, 
                                       int ritualFragments, boolean hasAnchorToken, 
                                       boolean hasTruthEvidence) 
    {
        // Check requirements: ritual_fragments >= 2, anchor_token, truth evidence
        boolean hasRequiredEvidence = (ritualFragments >= 2) && hasAnchorToken && hasTruthEvidence;
        
        if (!hasRequiredEvidence) 
        {
            if (ritualFragments >= 1 && hasAnchorToken) 
            {
                return generateDetailedEnding(PARTIAL_BINDING_SURVIVE, characterType, totalKnowledge);
            }
            else 
            {
                return generateDetailedEnding(LOSS_FALSE_BIND, characterType, totalKnowledge);
            }
        }

        // Stellar Win conditions (TK >= 10, ritual_fragments >= 3, perfect execution)
        if (totalKnowledge >= 10 && ritualFragments >= 3 && ritualFailureMeter == 0) 
        {
            if (characterType.equals("Mage") && memoryPaid) 
            {
                return generateDetailedEnding(STELLAR_RITUAL_APOTHEOSIS, characterType, totalKnowledge);
            }
        }

        // Clean Win conditions (TK >= 8, all requirements met)
        if (totalKnowledge >= 8 && memoryPaid) 
        {
            return generateDetailedEnding(CLEAN_RITUAL_WIN, characterType, totalKnowledge);
        }
        
        // Survival with partial success
        if (totalKnowledge >= 5) 
        {
            if (memoryPaid) 
            {
                return generateDetailedEnding(PARTIAL_BINDING_SURVIVE, characterType, totalKnowledge);
            }
            else 
            {
                return generateDetailedEnding(MEMORY_SCAR_SURVIVE, characterType, totalKnowledge);
            }
        }

        // Low knowledge ritual failure
        return generateDetailedEnding(LOSS_FALSE_BIND, characterType, totalKnowledge);
    }

    private String determineDestructionEnding(int totalKnowledge, String characterType, 
                                            boolean beastObserved, boolean trueName, 
                                            boolean hornObtained) 
    {
        int combatSuccessScore = 0;
        
        // Combat success factors
        if (characterType.equals("Hunter")) combatSuccessScore += 3;
        if (hornObtained) combatSuccessScore += 3;
        if (beastObserved) combatSuccessScore += 2;
        if (trueName) combatSuccessScore += 4;
        if (totalKnowledge >= 8) combatSuccessScore += 2;

        // Stellar Combat Win (TK >= 10, true_name_known, multiple advantages)
        if (totalKnowledge >= 10 && trueName && combatSuccessScore >= 10) 
        {
            if (characterType.equals("Hunter")) 
            {
                return generateDetailedEnding(STELLAR_COMBAT_TRIUMPH, characterType, totalKnowledge);
            }
            else 
            {
                return generateDetailedEnding(SPECIAL_MAGE_ARCANE_MASTERY, characterType, totalKnowledge);
            }
        }

        // Tactical Destroy Win
        if (totalKnowledge >= 8 && combatSuccessScore >= 6) 
        {
            return generateDetailedEnding(TACTICAL_DESTROY_WIN, characterType, totalKnowledge);
        }

        // Wounded Creature Survival
        if (combatSuccessScore >= 4 || totalKnowledge >= 5) 
        {
            return generateDetailedEnding(WOUNDED_CREATURE_SURVIVE, characterType, totalKnowledge);
        }

        // Combat failure - consumed
        return generateDetailedEnding(LOSS_CONSUMED, characterType, totalKnowledge);
    }

    private String determineBargainEnding(int totalKnowledge, String characterType, 
                                        boolean hasLedger, boolean chiefInformed, 
                                        boolean mayorConfessPublic) 
    {
        int bargainSuccessScore = 0;
        
        if (hasLedger) bargainSuccessScore += 4;
        if (chiefInformed) bargainSuccessScore += 2;
        if (mayorConfessPublic) bargainSuccessScore += 3;
        if (totalKnowledge >= 6) bargainSuccessScore += 2;

        // Stellar Political Reform
        if (totalKnowledge >= 10 && hasLedger && chiefInformed && mayorConfessPublic) 
        {
            return generateDetailedEnding(STELLAR_POLITICAL_REFORM, characterType, totalKnowledge);
        }

        // Practical Bargain Win
        if (bargainSuccessScore >= 6 && hasLedger) 
        {
            return generateDetailedEnding(PRACTICAL_BARGAIN_WIN, characterType, totalKnowledge);
        }

        // Political survival with compromise
        if (bargainSuccessScore >= 3) 
        {
            return generateDetailedEnding(PARTIAL_BINDING_SURVIVE, characterType, totalKnowledge);
        }

        // Bargain failure
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
        
        // Manor library secret room
        if (gameState.getFlag(7)) fragments++; // FLAG_LIBRARY_TRIGGERED
        
        // Mage-specific ritual fragments from character
        BaseCharacter character = gameState.getCurrentCharacter();
        if (character instanceof MageCharacter) 
        {
            MageCharacter mage = (MageCharacter) character;
            fragments += mage.getRitualFragments().length;
        }
        
        // Forest cave fragments
        if (gameState.getFlag(12)) fragments++; // FLAG_CAVE_EXAMINED
        
        // Stone circle lore
        if (gameState.getFlag(14)) fragments++; // FLAG_STONE_CIRCLE_FOUND
        
        return fragments;
    }

    private String generateDetailedEnding(String endingType, String characterType, int totalKnowledge) 
    {
        StringBuilder ending = new StringBuilder();
        ending.append("=".repeat(70)).append("\n");
        ending.append("                    FINAL OUTCOME                    \n");
        ending.append("=".repeat(70)).append("\n\n");

        switch (endingType) 
        {
            case STELLAR_RITUAL_APOTHEOSIS:
                ending.append("✨ THE MASTER'S BINDING - STELLAR VICTORY ✨\n\n");
                ending.append("As a ").append(characterType).append(" with ").append(totalKnowledge)
                      .append(" knowledge points, you have achieved the impossible.\n\n");
                ending.append("You taste absence where a warm July afternoon used to sit - the memory of your first ");
                ending.append("magical awakening dissolves like morning mist. But the sacrifice is perfect, and the weave holds.\n\n");
                ending.append("The ancient words flow from your lips like liquid starlight, each syllable binding the creature's ");
                ending.append("essence to the fractured anchor you discovered. Your Thread of Knowing guides the ritual with ");
                ending.append("flawless precision, weaving together confession fragments, the beast's true name, and the ");
                ending.append("broken covenant into an unbreakable new compact.\n\n");
                ending.append("The name-consuming spirit screams as it is drawn back into the binding crystal, its power ");
                ending.append("transformed from malevolence into protection. The village sleeps peacefully for the first time ");
                ending.append("in months, and the Night's Breath becomes nothing more than cool evening air.\n\n");
                ending.append("Kiri plays in the village square again, her laughter echoing off ancient stones. Mara nods ");
                ending.append("approvingly as she folds her protective amulets away. The village chief presents you with a ");
                ending.append("ceremonial staff, carved with symbols representing your legendary deed.\n\n");
                ending.append("Years later, your ritual becomes the foundation for a new understanding between the mortal ");
                ending.append("and spirit worlds. Other mages seek your wisdom, and the binding technique you perfected ");
                ending.append("saves dozens of communities from similar threats.\n\n");
                ending.append("🏆 Achievement Unlocked: 'Master of the Ancient Compact' - Perfect ritual execution\n");
                break;

            case STELLAR_COMBAT_TRIUMPH:
                ending.append("⚔️ THE BEAST SLAYER - STELLAR VICTORY ⚔️\n\n");
                ending.append("As a legendary ").append(characterType).append(" with ").append(totalKnowledge)
                      .append(" knowledge points, your hunt reaches its ultimate conclusion.\n\n");
                ending.append("When the creature fell, the earth itself sighed in relief. Your perfect knowledge of its ");
                ending.append("true name gave you power over its essence, while the ancient horn's call summoned spectral ");
                ending.append("guardians to hold it in place for your killing blow.\n\n");
                ending.append("The beast's death reveals something unprecedented - as its form dissolves, it releases the ");
                ending.append("names and memories it had consumed over centuries. Children across three kingdoms suddenly ");
                ending.append("remember grandparents they had forgotten, and villages recall their true histories.\n\n");
                ending.append("The fractured binding crystal in your hand pulses with contained power - not malevolent now, ");
                ending.append("but protective. Derren's ghostly form appears one final time, bowing deeply before fading ");
                ending.append("into peaceful rest. His vigil is ended, his failure redeemed through your success.\n\n");
                ending.append("Your victory becomes legend. The hunter who knew the ancient ways, who could track not just ");
                ending.append("footprints but the paths between worlds. Village chiefs seek your aid, and you find yourself ");
                ending.append("the founder of a new order - guardians trained to recognize and combat supernatural threats.\n\n");
                ending.append("The shard you bring back to the village hums like a warning bell, but also like a promise. ");
                ending.append("Hollowfen will never again fall prey to such horrors, and neither will any village under ");
                ending.append("your protection.\n\n");
                ending.append("🏆 Achievement Unlocked: 'Legendary Beast Slayer' - Perfect combat resolution\n");
                break;

            case STELLAR_POLITICAL_REFORM:
                ending.append("⚖️ THE ARCHITECT OF JUSTICE - STELLAR VICTORY ⚖️\n\n");
                ending.append("As a master negotiator with ").append(totalKnowledge)
                      .append(" knowledge points, you have achieved the impossible - true justice.\n\n");
                ending.append("The compact stands in the village square, carved in stone and read by everyone. The ledger ");
                ending.append("evidence you gathered didn't just expose the Mystery Villager's crimes - it revealed an ");
                ending.append("entire network of human trafficking that stretched across three kingdoms.\n\n");
                ending.append("Your political maneuvering turned the mayor's public confession into a catalyst for legal ");
                ending.append("reform. The chief's support gave you the authority to rewrite the ancient laws, creating ");
                ending.append("new protections against supernatural exploitation and holding merchants accountable for ");
                ending.append("their 'exotic' trades.\n\n");
                ending.append("The creature, bound by legal precedent rather than magical ritual, finds itself constrained ");
                ending.append("by laws that recognize its nature while protecting human rights. It cannot consume names ");
                ending.append("without due process, cannot make bargains without witnesses, cannot trade in suffering ");
                ending.append("without consequence.\n\n");
                ending.append("You have become both hero and architect of a new order. Other kingdoms adopt your legal ");
                ending.append("frameworks, and the trade in supernatural services becomes regulated, monitored, and just. ");
                ending.append("The Mystery Villager, freed from his supernatural bondage, testifies before royal courts ");
                ending.append("and helps dismantle the networks that once enslaved him.\n\n");
                ending.append("🏆 Achievement Unlocked: 'Architect of the New Law' - Perfect political resolution\n");
                break;

            case CLEAN_RITUAL_WIN:
                ending.append("🕯️ MEMORY'S PRICE - CLEAN VICTORY 🕯️\n\n");
                ending.append("The ritual succeeds, but at the cost you knew it would demand.\n\n");
                ending.append("Kiri laughs again in the village square, her voice bright with restored innocence. The ");
                ending.append("Night's Breath no longer carries whispers of stolen names, and parents sleep peacefully ");
                ending.append("knowing their children are safe.\n\n");
                ending.append("But you cannot remember the color of your mother's kitchen walls, cannot recall the warmth ");
                ending.append("of her hand on your forehead when fever took you as a child. That memory - precious and ");
                ending.append("irreplaceable - was the price the ritual demanded, and you paid it willingly.\n\n");
                ending.append("The loss lives like a quiet wound, a gap in your history that aches when you're not ");
                ending.append("careful. But when you see the village children playing safely, when you hear their laughter ");
                ending.append("unmarred by supernatural dread, you know the trade was fair.\n\n");
                ending.append("Mara visits you sometimes, bringing tea and understanding silence. She knows the weight ");
                ending.append("of sacrifice, the burden of protection. Together, you keep watch over the village's borders, ");
                ending.append("ensuring the binding holds and no similar threats can take root.\n\n");
                ending.append("🏅 Achievement Unlocked: 'Guardian's Sacrifice' - Successful ritual completion\n");
                break;

            case LOSS_CONSUMED:
                ending.append("💀 THE DEVOURER'S VICTORY - TRAGIC LOSS 💀\n\n");
                ending.append("Your journey as a ").append(characterType).append(" with only ").append(totalKnowledge)
                      .append(" knowledge points ends in the creature's triumph.\n\n");
                ending.append("Without sufficient understanding of the beast's true nature, you fell victim to its most ");
                ending.append("insidious power - the illusion of victory. As you approached what you believed to be ");
                ending.append("success, reality warped around you like a malevolent dream.\n\n");
                ending.append("The creature's hunger consumed not just your life, but your very identity. Your name fades ");
                ending.append("from memory, your deeds become whispered warnings that grow fainter with each telling. ");
                ending.append("The village children play as if nothing has happened, unable to recall that someone once ");
                ending.append("tried to save them.\n\n");
                ending.append("But even in defeat, your sacrifice plants seeds of future salvation. Mara finds fragments ");
                ending.append("of your investigation notes, clues that will help the next hero who dares to challenge ");
                ending.append("the darkness. Your failure becomes the foundation for another's success.\n\n");
                ending.append("In the end, you are a name someone once whispered and could no longer recall - but ");
                ending.append("whispers have power, and sometimes that is enough.\n\n");
                ending.append("💔 The Mystery Continues - Your sacrifice guides future heroes\n");
                break;

            case LOSS_POLITICAL_RUIN:
                ending.append("📜 THE COVER-UP - POLITICAL LOSS 📜\n\n");
                ending.append("You sleep in a high bed that smells of cedar and burned paper.\n\n");
                ending.append("The ledger evidence that could have saved everyone burns in the mayor's private fireplace. ");
                ending.append("Your attempts to expose the truth were turned against you, transformed into accusations ");
                ending.append("of madness and sedition. The political machinery you tried to use consumed you instead.\n\n");
                ending.append("The creature continues its work, but now with official protection. Missing children become ");
                ending.append("'runaways,' strange sounds are 'settling foundations,' and the Night's Breath is merely ");
                ending.append("'unfortunate weather patterns' that the town council doesn't discuss.\n\n");
                ending.append("You lost your face in the crowd, your identity subsumed into the very corruption you ");
                ending.append("tried to fight. The mayor's gold feels heavy in your pockets, but not as heavy as the ");
                ending.append("knowledge of what you've become.\n\n");
                ending.append("Sometimes, in dreams, you remember who you used to be. But dawn always comes, and with ");
                ending.append("it the comfortable numbness of complicity.\n\n");
                ending.append("💰 Achievement Unlocked: 'Part of the Problem' - Consumed by corruption\n");
                break;

            case LOSS_MORAL_DEBT:
                ending.append("🔔 THE BELL TOLLS - MORAL DEBT 🔔\n\n");
                ending.append("The town survives, but at a cost that will echo through generations.\n\n");
                ending.append("Your bargain with the creature created a legal compact that protects the village - but ");
                ending.append("the terms were more complex than you understood. The beast can no longer consume names ");
                ending.append("freely, but it can still collect its due through 'legitimate transactions.'\n\n");
                ending.append("At night, a bell rings thirteen times. Parents count their children's fingers when they ");
                ending.append("speak their names aloud, because sometimes - just sometimes - a name goes missing for ");
                ending.append("a day or two before returning. The children always come back, but they're a little ");
                ending.append("different, a little quieter.\n\n");
                ending.append("You saved the village from immediate destruction, but condemned it to slow erosion. ");
                ending.append("The creature feeds carefully now, sustainably, taking just enough to survive while ");
                ending.append("keeping its part of the bargain.\n\n");
                ending.append("The worst part is that everyone knows. They see the contracts you signed, the legal ");
                ending.append("protections you invoked, and they're grateful. After all, losing a memory occasionally ");
                ending.append("is better than losing everything at once.\n\n");
                ending.append("⚖️ Achievement Unlocked: 'Devil's Advocate' - Survival through compromise\n");
                break;

            default:
                ending.append("The outcome remains shrouded in mystery...\n");
                break;
        }

        ending.append("\n").append("=".repeat(70)).append("\n");
        ending.append("Final Score: ").append(totalKnowledge).append(" Knowledge Points\n");
        ending.append("Character: ").append(characterType).append("\n");
        ending.append("=".repeat(70)).append("\n");

        return ending.toString();
    }

    // Utility methods for memory sacrifice and ritual failure
    public void setMemoryPaid(boolean paid) 
    {
        this.memoryPaid = paid;
    }

    public void addRitualFailure(int failurePoints) 
    {
        this.ritualFailureMeter += failurePoints;
    }

    public boolean isMemoryPaid() 
    {
        return memoryPaid;
    }

    public int getRitualFailureMeter() 
    {
        return ritualFailureMeter;
    }

    // Information hiding getters
    public String[] getAllPossibleEndings() 
    {
        String[] endings = new String[endingCount];
        for (int i = 0; i < endingCount; i++) 
        {
            endings[i] = possibleEndings[i];
        }
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
