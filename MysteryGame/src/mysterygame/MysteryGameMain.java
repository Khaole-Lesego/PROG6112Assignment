package mysterygame;

import java.util.Scanner;

/**
 * MysteryGameMain.java
 *
 * Main entry point for the Mystery Game.
 * Handles character selection, investigation flow, and climax resolution.
 */
public class MysteryGameMain {

    public static void main(String[] args) {
        Scanner gameScan = new Scanner(System.in);
        try {
            GameUtilities.printDivider();
            System.out.println("WELCOME to MYSTERY GAME");
            System.out.println("A supernatural mystery awaits you in the village of Hollowfen...");
            GameUtilities.printDivider();
            runGame(gameScan);
        } finally {
            try { gameScan.close(); } catch (Exception ignored) {}
        }
    }

    private static void runGame(Scanner gameScan) {
        // Initialize Game State Manager
        GameStateManager gsm = new GameStateManager();

        // Character selection
        System.out.println();
        System.out.println("Strange events plague the village of Hollowfen. Children's names are being stolen,");
        System.out.println("and an otherworldly mist brings whispers of forgotten memories...");
        System.out.println();
        System.out.println("Choose your character class:");
        System.out.println(" 1) Hunter - Track physical clues, set traps, excel in direct confrontation");
        System.out.println(" 2) Mage - Detect magical residue, perform rituals, uncover arcane secrets");
        int charChoice = GameUtilities.getValidatedInt(gameScan, "Select (1-2): ", 1, 2);
        String name = GameUtilities.nextLine(gameScan, "Enter your character's name: ");

        BaseCharacter player = createCharacter(charChoice, name);
        if (player == null) {
            System.out.println("[ERROR] Failed to create character. Exiting.");
            return;
        }

        gsm.setCharacter(player);

        // Initial path selection
        System.out.println();
        System.out.println("Three paths of investigation lie before you:");
        System.out.println(" 1) Forest Path - Follow tracks into the wilderness");
        System.out.println(" 2) Manor Path - Investigate the Mystery Villager's estate");
        System.out.println(" 3) Social Path - Speak with villagers and authorities");
        int pathChoice = GameUtilities.getValidatedInt(gameScan, "Choose your initial focus (1-3): ", 1, 3);
        String pathName = (pathChoice == 1) ? "FOREST" : (pathChoice == 2 ? "MANOR" : "SOCIAL");
        gsm.choosePath(pathName);
        
        System.out.printf("You begin your investigation on the %s path. Good luck, %s!%n", pathName, name);

        // Main investigation loop
        boolean gameComplete = false;
        int investigationCount = 0;
        final int MIN_INVESTIGATIONS = 3; // Minimum before climax is available
        
        while (!gameComplete) {
            GameUtilities.printDivider();
            System.out.println("Investigation Options:");
            System.out.println(" 1) Investigate Manor locations");
            System.out.println(" 2) Investigate Forest locations"); 
            System.out.println(" 3) Investigate Social connections");
            System.out.println(" 4) Review your findings");
            if (investigationCount >= MIN_INVESTIGATIONS) {
                System.out.println(" 5) Proceed to final confrontation");
            }
            System.out.println(" 6) Quit game");
            
            int maxChoice = (investigationCount >= MIN_INVESTIGATIONS) ? 6 : 5;
            int choice = GameUtilities.getValidatedInt(gameScan, "Choose (1-" + maxChoice + "): ", 1, maxChoice);

            switch (choice) {
                case 1 -> {
                    handleLocationInvestigation(gameScan, gsm, "MANOR");
                    investigationCount++;
                }
                case 2 -> {
                    handleLocationInvestigation(gameScan, gsm, "FOREST");
                    investigationCount++;
                }
                case 3 -> {
                    handleLocationInvestigation(gameScan, gsm, "SOCIAL");
                    investigationCount++;
                }
                case 4 -> showStatus(player, gsm);
                case 5 -> {
                    if (investigationCount >= MIN_INVESTIGATIONS) {
                        handleClimaxAndEnding(gameScan, gsm);
                        gameComplete = true;
                    } else {
                        System.out.println("You need more evidence before attempting the final confrontation.");
                    }
                }
                case 6 -> {
                    boolean confirm = GameUtilities.confirmYesNo(gameScan, "Are you sure you want to quit? (y/n): ");
                    if (confirm) {
                        System.out.println("The mystery remains unsolved... Goodbye!");
                        gameComplete = true;
                    }
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static BaseCharacter createCharacter(int charChoice, String name) {
        try {
            if (charChoice == 1) {
                return new HunterCharacter(name);
            } else {
                return new MageCharacter(name);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to create character: " + e.getMessage());
            return null;
        }
    }

    private static void handleLocationInvestigation(Scanner scan, GameStateManager gsm, String locationType) {
        System.out.println();
        System.out.println("=== " + locationType + " INVESTIGATION ===");
        
        InvestigationNode[] nodes;
        switch (locationType) {
            case "MANOR" -> nodes = gsm.getManorNodes();
            case "FOREST" -> nodes = gsm.getForestNodes();
            case "SOCIAL" -> nodes = gsm.getSocialNodes();
            default -> {
                System.out.println("Invalid location type.");
                return;
            }
        }

        // Display available locations
        System.out.println("Available investigation sites:");
        for (int i = 0; i < nodes.length; i++) {
            String visitedMark = nodes[i].isNodeVisited() ? " [VISITED]" : " [NEW]";
            System.out.println(" " + (i + 1) + ") " + nodes[i].getNodeName() + visitedMark);
        }
        
        int nodeChoice = GameUtilities.getValidatedInt(scan, "Choose site (1-" + nodes.length + "): ", 1, nodes.length);
        InvestigationNode selectedNode = nodes[nodeChoice - 1];
        
        System.out.println();
        System.out.println("=== " + selectedNode.getNodeName() + " ===");
        System.out.println(selectedNode.getNodeDescription());
        System.out.println();

        // Show available actions
        String[] actions = selectedNode.getAvailableActions();
        System.out.println("Available actions:");
        int validActionCount = 0;
        for (int i = 0; i < actions.length; i++) {
            if (actions[i] != null) {
                System.out.println(" " + (i + 1) + ") " + actions[i]);
                validActionCount++;
            }
        }
        
        if (validActionCount == 0) {
            System.out.println("No actions available at this location.");
            return;
        }
        
        int actionChoice = GameUtilities.getValidatedInt(scan, "Choose action (1-" + actions.length + "): ", 1, actions.length);
        
        // Perform the action
        String result = gsm.investigateLocation(locationType, nodeChoice - 1, actionChoice - 1);
        System.out.println();
        System.out.println("=== INVESTIGATION RESULT ===");
        System.out.println(result);
        
        // Show any evidence found
        String[] evidence = selectedNode.getEvidenceFound();
        if (evidence.length > 0) {
            System.out.println();
            System.out.println("Evidence discovered:");
            for (String e : evidence) {
                if (e != null) {
                    System.out.println("• " + e);
                }
            }
        }
        
        GameUtilities.pressEnterToContinue(scan);
    }

    private static void showStatus(BaseCharacter player, GameStateManager gsm) {
        System.out.println();
        System.out.println("=== CHARACTER STATUS ===");
        System.out.println("Name: " + player.getCharacterName());
        System.out.println("Type: " + player.getCharacterType());
        System.out.println("Knowledge Points: " + player.getKnowledgePoints());
        System.out.println("Total Knowledge Score: " + gsm.getTotalKnowledgeScore());
        
        // Character-specific status
        if (player instanceof HunterCharacter) {
            HunterCharacter hunter = (HunterCharacter) player;
            String[] evidence = hunter.getHuntingEvidence();
            if (evidence.length > 0) {
                System.out.println();
                System.out.println("Hunting Evidence Collected:");
                for (String e : evidence) {
                    System.out.println("• " + e);
                }
            }
        } else if (player instanceof MageCharacter) {
            MageCharacter mage = (MageCharacter) player;
            String[] fragments = mage.getRitualFragments();
            if (fragments.length > 0) {
                System.out.println();
                System.out.println("Ritual Fragments Discovered:");
                for (String f : fragments) {
                    System.out.println("• " + f);
                }
            }
        }
        
        System.out.println();
        System.out.println("Current Investigation Path: " + gsm.getCurrentPath());
    }

    private static void handleClimaxAndEnding(Scanner scan, GameStateManager gsm) {
        System.out.println();
        System.out.println("=== THE FINAL CONFRONTATION ===");
        System.out.println("The moment of truth has arrived. The creature's presence grows stronger,");
        System.out.println("and the village's fate hangs in the balance. How will you resolve this?");
        System.out.println();
        System.out.println("Choose your approach:");
        System.out.println(" 1) RESTORE - Attempt to bind the creature and restore stolen memories");
        System.out.println(" 2) DESTROY - Hunt down and destroy the creature permanently");
        System.out.println(" 3) BARGAIN - Seek a political or legal solution to contain the threat");
        
        int choice = GameUtilities.getValidatedInt(scan, "Select your final action (1-3): ", 1, 3);
        String climaxChoice = switch (choice) {
            case 1 -> "RESTORE";
            case 2 -> "DESTROY";
            default -> "BARGAIN";
        };

        System.out.println();
        System.out.println("Resolving the mystery with approach: " + climaxChoice);
        System.out.println("Calculating your fate based on gathered evidence and knowledge...");
        System.out.println();

        try {
            ClimaxManager climax = new ClimaxManager(gsm);
            String finalEnding = climax.determineEnding(climaxChoice);
            
            System.out.println(finalEnding);
            
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to determine ending: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback simple ending
            System.out.println("=== MYSTERY RESOLVED ===");
            System.out.println("Despite the technical difficulties, your investigation into the");
            System.out.println("supernatural threat has concluded. The village of Hollowfen");
            System.out.println("will remember your efforts, whatever the outcome.");
        }
        
        GameUtilities.pressEnterToContinue(scan);
    }
}