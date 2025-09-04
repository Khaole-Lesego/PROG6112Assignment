package mysterygame;

import java.util.Scanner;

/**
 * MysteryGameMain.java
 *
 * Main entry point for the MysteryGame project.
 * Orchestrates character selection, path choice, investigations,
 * and climax resolution.
 */
public class MysteryGameMain {

    public static void main(String[] args) {
        Scanner gameScanner = new Scanner(System.in);
        try {
            GameUtilities.printDivider();
            System.out.println("WELCOME to MYSTERY GAME");
            GameUtilities.printDivider();
            runGameLoop(gameScanner);
        } finally {
            try { gameScanner.close(); } catch (Exception ignored) {}
        }
    }

    private static void runGameLoop(Scanner gameScanner) {
        // Create the game state manager
        GameStateManager gameStateManager = new GameStateManager();

        // Character selection
        System.out.println();
        System.out.println("Choose your character:");
        String[] characterOptions = {"Hunter", "Mage"};
        int characterChoice = GameUtilities.chooseFromMenu(gameScanner, "Select a character:", characterOptions);
        String playerName = GameUtilities.nextLine(gameScanner, "Enter your character's name: ");

        BaseCharacter playerCharacter;
        if (characterChoice == 1) {
            playerCharacter = new HunterCharacter(playerName);
        } else {
            playerCharacter = new MageCharacter(playerName);
        }

        // Register player with GameStateManager
        gameStateManager.setPlayerCharacter(playerCharacter);

        // Path selection
        System.out.println();
        String[] pathOptions = {"Forest", "Manor", "Social"};
        int pathChoice = GameUtilities.chooseFromMenu(gameScanner, "Choose initial path:", pathOptions);
        String chosenPath = pathOptions[pathChoice - 1];
        System.out.printf("You chose the %s path. Good luck, %s!%n", chosenPath, playerName);

        // Main loop
        boolean exitRequested = false;
        while (!exitRequested) {
            GameUtilities.printDivider();
            String[] menuOptions = {
                "Investigate a location",
                "Show status",
                "Attempt final climax",
                "Quit game"
            };
            int menuChoice = GameUtilities.chooseFromMenu(gameScanner, "Main Menu - choose an option:", menuOptions);

            switch (menuChoice) {
                case 1 -> handleInvestigation(gameScanner, playerCharacter);
                case 2 -> showStatus(playerCharacter, gameStateManager);
                case 3 -> {
                    handleClimax(gameScanner, gameStateManager);
                    exitRequested = true;
                }
                case 4 -> {
                    if (GameUtilities.confirmYesNo(gameScanner, "Are you sure you want to quit? (y/n): ")) {
                        System.out.println("Exiting game. Goodbye.");
                        exitRequested = true;
                    }
                }
            }
        }
    }

    // Investigation flow
    private static void handleInvestigation(Scanner gameScanner, BaseCharacter playerCharacter) {
        System.out.println();
        String[] locationOptions = {"Manor", "Forest", "Social"};
        int locationChoice = GameUtilities.chooseFromMenu(gameScanner, "Choose location to investigate:", locationOptions);
        String locationName = locationOptions[locationChoice - 1];

        // Construct InvestigationNode
        String nodeId = locationName.toLowerCase() + "_01";
        String nodeDisplayName = locationName + " Site";
        String nodeDescription = "Exploring the " + locationName + " may reveal hidden truths.";
        String nodeType = locationName;

        InvestigationNode node = new InvestigationNode(nodeId, nodeDisplayName, nodeDescription, nodeType);

        // Present actions
        String[] actions = node.getAvailableActions();
        java.util.List<String> validActions = new java.util.ArrayList<>();
        for (String a : actions) {
            if (a != null && !a.isBlank()) {
                validActions.add(a);
            }
        }

        if (validActions.isEmpty()) {
            System.out.println("No actions available here.");
            return;
        }

        int actionChoice = GameUtilities.chooseFromMenu(
            gameScanner,
            "Available actions at " + node.getNodeName() + ":",
            validActions.toArray(new String[0])
        );

        int actionIndex = actionChoice - 1;
        String result = node.performAction(actionIndex, playerCharacter);

        System.out.println();
        System.out.println(result);
    }

    // Status display
    private static void showStatus(BaseCharacter playerCharacter, GameStateManager gameStateManager) {
        System.out.println();
        System.out.println("=== PLAYER STATUS ===");
        System.out.println(playerCharacter);

        System.out.println();
        System.out.println("=== GAME STATE SUMMARY ===");
        try {
            System.out.println(gameStateManager.getSummary());
        } catch (Exception e) {
            System.out.println("[INFO] No summary available.");
        }
    }

    // Climax and ending
    private static void handleClimax(Scanner gameScanner, GameStateManager gameStateManager) {
        System.out.println();
        String[] climaxOptions = {"RESTORE", "DESTROY", "BARGAIN"};
        int climaxChoice = GameUtilities.chooseFromMenu(gameScanner, "Choose climax resolution:", climaxOptions);
        String climaxKey = climaxOptions[climaxChoice - 1];

        ClimaxManager climaxManager = new ClimaxManager(gameStateManager);
        String ending = climaxManager.determineEnding(climaxKey);

        System.out.println();
        System.out.println("===== FINAL ENDING =====");
        System.out.println(ending);
        System.out.println("========================");
    }
}
