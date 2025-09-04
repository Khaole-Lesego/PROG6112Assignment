package mysterygame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * MysteryGameMain.java
 *
 * Main entry point and full replacement for MysteryGameApplication.
 * - Uses a single Scanner (gameScan) for all input.
 * - Uses GameUtilities for validated input and formatting.
 * - Orchestrates character selection, path choice, investigation loop,
 *   and final climax/ending invocation.
 *
 * Notes:
 * - If a method name in your existing classes differs, check inline comments
 *   where reflection fallback tries multiple candidate method names.
 * - Target: Java 17 compatible.
 */
public class MysteryGameMain {

    public static void main(String[] args) {
        Scanner gameScan = new Scanner(System.in);
        try {
            GameUtilities.printDivider();
            System.out.println("WELCOME to MYSTERY GAME");
            GameUtilities.printDivider();
            runGame(gameScan);
        } finally {
            // close scanner at shutdown (do not let domain classes close System.in)
            try { gameScan.close(); } catch (Exception ignored) {}
        }
    }

    private static void runGame(Scanner gameScan) {
        // Instantiate GameStateManager
        GameStateManager gsm;
        try {
            gsm = new GameStateManager();
        } catch (Throwable t) {
            System.out.println("[ERROR] Could not instantiate GameStateManager. Ensure the class exists.");
            t.printStackTrace();
            return;
        }

        // Character selection
        System.out.println();
        System.out.println("Choose your character:");
        System.out.println(" 1) Hunter");
        System.out.println(" 2) Mage");
        int charChoice = GameUtilities.getValidatedInt(gameScan, "Select (1-2): ", 1, 2);
        String name = GameUtilities.nextLine(gameScan, "Enter your character's name: ");

        Object player = createCharacter(charChoice, name);
        if (player == null) {
            System.out.println("[ERROR] Failed to create character. Exiting.");
            return;
        }

        // Register player with GameStateManager if possible
        boolean regOk = registerPlayerWithGSM(gsm, player);
        if (!regOk) {
            System.out.println("[WARN] Could not register player with GameStateManager via common setter names.");
            System.out.println("The game may still function, but state manager won't track player automatically.");
        }

        // Path selection
        System.out.println();
        System.out.println("Choose initial path:");
        System.out.println(" 1) Forest");
        System.out.println(" 2) Manor");
        System.out.println(" 3) Social");
        int pathChoice = GameUtilities.getValidatedInt(gameScan, "Select path (1-3): ", 1, 3);
        String pathName = (pathChoice == 1) ? "Forest" : (pathChoice == 2 ? "Manor" : "Social");
        System.out.printf("You chose: %s path. Good luck, %s!%n", pathName, name);

        // Main loop
        boolean exit = false;
        while (!exit) {
            GameUtilities.printDivider();
            System.out.println("Main Menu:");
            System.out.println(" 1) Investigate a location");
            System.out.println(" 2) Show status");
            System.out.println(" 3) Attempt to finish (go to climax)");
            System.out.println(" 4) Quit");
            int choice = GameUtilities.getValidatedInt(gameScan, "Choose (1-4): ", 1, 4);

            switch (choice) {
                case 1 -> handleInvestigation(gameScan, player, gsm);
                case 2 -> showStatus(player, gsm);
                case 3 -> {
                    handleClimaxAndEnding(gameScan,player, gsm);
                    exit = true;
                }
                case 4 -> {
                    boolean confirm = GameUtilities.confirmYesNo(gameScan, "Are you sure you want to quit? (y/n): ");
                    if (confirm) {
                        System.out.println("Goodbye!");
                        exit = true;
                    }
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // ---------- Character creation ----------
    private static Object createCharacter(int charChoice, String name) {
        try {
            if (charChoice == 1) {
                // try direct constructor
                try {
                    return new HunterCharacter(name);
                } catch (Throwable ignored) {
                    // fallback: reflective constructor
                    Class<?> cls = Class.forName("mysterygame.HunterCharacter");
                    Constructor<?> ctor = cls.getConstructor(String.class);
                    return ctor.newInstance(name);
                }
            } else {
                try {
                    return new MageCharacter(name);
                } catch (Throwable ignored) {
                    Class<?> cls = Class.forName("mysterygame.MageCharacter");
                    Constructor<?> ctor = cls.getConstructor(String.class);
                    return ctor.newInstance(name);
                }
            }
        } catch (ClassNotFoundException cnf) {
            System.out.println("[ERROR] Character class not found: " + cnf.getMessage());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("[ERROR] Failed to instantiate character: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // ---------- Register player with GameStateManager ----------
    private static boolean registerPlayerWithGSM(GameStateManager gsm, Object player) {
        // Try common setter names
        String[] candidateNames = {"setPlayerCharacter", "setCharacter", "registerPlayer", "setCurrentCharacter", "setPlayer"};
        for (String name : candidateNames) {
            try {
                Method m = findMethod(gsm.getClass(), name, player.getClass());
                if (m != null) {
                    m.invoke(gsm, player);
                    return true;
                }
            } catch (Throwable ignored) {}
        }

        // Try any single-arg method accepting a BaseCharacter or Object
        for (Method m : gsm.getClass().getMethods()) {
            if (m.getParameterCount() == 1) {
                Class<?> ptype = m.getParameterTypes()[0];
                if (ptype.getName().contains("BaseCharacter") || ptype.equals(Object.class)) {
                    try {
                        m.invoke(gsm, player);
                        return true;
                    } catch (Throwable ignored) {}
                }
            }
        }
        return false;
    }

    // ---------- Investigation ----------
    private static void handleInvestigation(Scanner scan, Object player, GameStateManager gsm) {
        System.out.println();
        System.out.println("Investigation Locations:");
        System.out.println(" 1) Manor");
        System.out.println(" 2) Forest");
        System.out.println(" 3) Social");
        int loc = GameUtilities.getValidatedInt(scan, "Choose location (1-3): ", 1, 3);
        String location = (loc == 1) ? "Manor" : (loc == 2 ? "Forest" : "Social");

        try {
            InvestigationNode node;

            // PRIMARY: try to use the constructor that your InvestigationNode defines:
            // InvestigationNode(String nodeId, String nodeName, String nodeDescription, String nodeType)
            String nodeId = location.toLowerCase().replaceAll("\\s+", "_") + "_01";
            String nodeName = location + " - Default Site";
            String nodeDescription = "You investigate the " + location + ". It's quiet and holds many clues.";
            String nodeType = location; // "Manor", "Forest", or "Social"

            try {
                // direct constructor call (preferred)
                node = new InvestigationNode(nodeId, nodeName, nodeDescription, nodeType);
            } catch (Throwable ctorEx) {
                // fallback: reflective constructor with same 4-string signature
                try {
                    Class<?> nodeClass = Class.forName("mysterygame.InvestigationNode");
                    Constructor<?> ctor4 = nodeClass.getConstructor(String.class, String.class, String.class, String.class);
                    node = (InvestigationNode) ctor4.newInstance(nodeId, nodeName, nodeDescription, nodeType);
                } catch (NoSuchMethodException nsme) {
                    // if no 4-arg constructor exists, try older single-arg or no-arg options (previous fallback)
                    // try single-String constructor
                    try {
                        Constructor<?> singleCtor = Class.forName("mysterygame.InvestigationNode").getConstructor(String.class);
                        node = (InvestigationNode) singleCtor.newInstance(location);
                    } catch (NoSuchMethodException | ClassNotFoundException exSingle) {
                        // try no-arg then set location if setter exists
                        Constructor<?> noArgCtor = Class.forName("mysterygame.InvestigationNode").getConstructor();
                        Object obj = noArgCtor.newInstance();
                        node = (InvestigationNode) obj;
                        try {
                            Method setLoc = findMethod(node.getClass(), "setLocation", String.class);
                            if (setLoc != null) setLoc.invoke(node, location);
                        } catch (Throwable ignore) {}
                    }
                }
            }

            // Try to invoke an investigation method in several candidate forms:
            // (player, GameStateManager, Scanner), (player, GameStateManager), (player), ()
            String[] methodCandidates = {"processAction", "investigate", "performAction", "interact", "handleAction", "start"};
            boolean invoked = false;
            for (String mname : methodCandidates) {
                // try (player, gsm, scanner)
                Method m = findMethod(node.getClass(), mname, player.getClass(), GameStateManager.class, Scanner.class);
                if (m != null) {
                    m.invoke(node, player, gsm, scan);
                    invoked = true;
                    break;
                }
                // try (player, gsm)
                m = findMethod(node.getClass(), mname, player.getClass(), GameStateManager.class);
                if (m != null) {
                    m.invoke(node, player, gsm);
                    invoked = true;
                    break;
                }
                // try (player)
                m = findMethod(node.getClass(), mname, player.getClass());
                if (m != null) {
                    m.invoke(node, player);
                    invoked = true;
                    break;
                }
                // try no-arg
                m = findMethod(node.getClass(), mname);
                if (m != null) {
                    m.invoke(node);
                    invoked = true;
                    break;
                }
            }

            if (!invoked) {
                System.out.println("[INFO] Couldn't find a standard investigation method on InvestigationNode.");
                System.out.println("You may need to call InvestigationNode methods directly in this file if method names differ.");
            }

        } catch (ClassNotFoundException cnf) {
            System.out.println("[ERROR] InvestigationNode class not found.");
        } catch (Throwable t) {
            System.out.println("[ERROR] Exception during investigation:");
            t.printStackTrace();
        }
    }

    // ---------- Status ----------
    private static void showStatus(Object player, GameStateManager gsm) {
        System.out.println();
        System.out.println("=== PLAYER STATUS ===");
        try {
            Method m = findMethod(player.getClass(), "getStatus");
            if (m == null) m = findMethod(player.getClass(), "status");
            if (m != null) {
                Object out = m.invoke(player);
                System.out.println(out == null ? player.toString() : out.toString());
            } else {
                System.out.println(player.toString());
            }
        } catch (Throwable ignored) {
            System.out.println(player.toString());
        }

        System.out.println();
        System.out.println("=== GAME STATE SUMMARY ===");
        try {
            Method gm = findMethod(gsm.getClass(), "getSummary");
            if (gm == null) gm = findMethod(gsm.getClass(), "getStatus");
            if (gm != null) {
                Object s = gm.invoke(gsm);
                System.out.println(s == null ? "(no summary returned)" : s.toString());
            } else {
                System.out.println("[INFO] No summary method on GameStateManager.");
            }
        } catch (Throwable ignored) {
            System.out.println("[INFO] Could not retrieve GameStateManager summary.");
        }
    }

    // ---------- Climax & Ending ----------
    private static void handleClimaxAndEnding(Scanner scan, Object player, GameStateManager gsm) {
    System.out.println();
    System.out.println("Choose how to resolve the climax:");
    System.out.println(" 1) RESTORE (attempt ritual to bind / restore memories)");
    System.out.println(" 2) DESTROY (hunt and destroy the creature)");
    System.out.println(" 3) BARGAIN (seek political/legal bargain to contain the threat)");
    int c = GameUtilities.getValidatedInt(scan, "Select (1-3): ", 1, 3);
    String climaxChoice = switch (c) {
        case 1 -> "RESTORE";
        case 2 -> "DESTROY";
        default -> "BARGAIN";
    };

    System.out.println();
    System.out.println("Calculating final ending using choice: " + climaxChoice + " ...");

    try {
        // NOTE: ClimaxManager requires a GameStateManager in constructor (per PDFs).
        // See ClimaxManager(GameStateManager) in the project files.
        ClimaxManager climax = new ClimaxManager(gsm); // <-- fix: pass gsm here. 

        // Preferred direct call: determineEnding(String)
        try {
            Method det = findMethod(climax.getClass(), "determineEnding", String.class);
            if (det != null) {
                Object res = det.invoke(climax, climaxChoice);
                printEndingObject(res);
                return;
            }
        } catch (Throwable ignore) {}

        // Fallbacks (if a different API exists) - try a method accepting GameStateManager, or no-arg
        String[] candidate = {"calculateEnding", "getEnding", "generateEnding", "resolveEnding"};
        boolean done = false;
        for (String name : candidate) {
            // try (GameStateManager, String)
            Method m = findMethod(climax.getClass(), name, GameStateManager.class, String.class);
            if (m != null) {
                Object res = m.invoke(climax, gsm, climaxChoice);
                printEndingObject(res);
                done = true; break;
            }
            // try (GameStateManager) returning a String
            m = findMethod(climax.getClass(), name, GameStateManager.class);
            if (m != null) {
                Object res = m.invoke(climax, gsm);
                printEndingObject(res); done = true; break;
            }
            // try no-arg
            m = findMethod(climax.getClass(), name);
            if (m != null) {
                Object res = m.invoke(climax);
                printEndingObject(res); done = true; break;
            }
        }

        if (!done) {
            System.out.println("[INFO] Could not find a known ending method on ClimaxManager.");
            System.out.println("Expected determineEnding(String) or similar. Please check ClimaxManager API.");
        }
    } catch (Throwable t) {
        System.out.println("[ERROR] Exception while running ClimaxManager:");
        t.printStackTrace();
    }
}


    private static void printEndingObject(Object res) {
        System.out.println();
        System.out.println("===== FINAL ENDING =====");
        if (res == null) {
            System.out.println("(ClimaxManager returned null or prints its own endings.)");
        } else {
            System.out.println(res.toString());
        }
        System.out.println("========================");
    }

    // ---------- Reflection helpers ----------
    private static Method findMethod(Class<?> cls, String name, Class<?>... params) {
        try {
            return cls.getMethod(name, params);
        } catch (NoSuchMethodException e) {
            for (Method m : cls.getMethods()) {
                if (!m.getName().equals(name)) continue;
                Class<?>[] pts = m.getParameterTypes();
                if (pts.length != params.length) continue;
                boolean ok = true;
                for (int i = 0; i < pts.length; i++) {
                    if (params[i] == null) continue;
                    if (!pts[i].isAssignableFrom(params[i])) { ok = false; break; }
                }
                if (ok) return m;
            }
            return null;
        }
    }
}
