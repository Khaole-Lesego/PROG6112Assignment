# Mystery Game - Comprehensive Documentation

## Overview

Mystery Game is a text-based adventure game where players investigate supernatural occurrences in a village called Hollowfen. Players choose between two character classes (Hunter or Mage) and explore three investigation paths (Manor, Forest, Social) to uncover clues and ultimately resolve the mystery.

## Project Structure

### BaseCharacter.java
**Abstract parent class for all character types**

#### Responsibilities:
- Defines core character attributes and behaviors
- Provides template for specialized character classes
- Manages character state and progression

#### Key Components:
- **Protected Fields**: Stores character name, type, knowledge points, tools, skills, and investigation count
- **Abstract Methods**: `initializeSkills()`, `initializeTools()`, `performSpecialAction()`, `canAccessLocation()`
- **Knowledge Management**: Protected method for adding knowledge points with validation
- **Status Information**: Comprehensive character status reporting
- **Encapsulation**: Returns copies of arrays to prevent external modification

#### Important Methods:
- `addKnowledgePoints()`: Protected method for incremental knowledge gain
- `getStatus()`: Returns formatted character information
- `hasSkill()`/`hasTool()`: Utility methods for capability checking
- `toString()`: Formatted string representation for debugging

### ClimaxManager.java
**Ending resolution system with narrative outcomes**

#### Responsibilities:
- Determines game endings based on player choices and progress
- Manages ending conditions and descriptions
- Calculates success probabilities for different resolution paths

#### Key Components:
- **Ending Constants**: 16 possible ending types with descriptive names
- **Ending Tiers**: Stellar wins, clean wins, survival endings, and loss endings
- **Scoring System**: Calculates ritual, combat, and bargain scores
- **Narrative Generation**: Creates detailed ending descriptions

#### Important Methods:
- `determineEnding()`: Main method that selects appropriate ending
- `determineRitualEnding()`: Logic for ritual-based resolutions
- `determineDestructionEnding()`: Logic for combat-based resolutions
- `determineBargainEnding()`: Logic for social/political resolutions
- `generateDetailedEnding()`: Creates narrative-rich ending descriptions

### GameStateManager.java
**Central game state management system**

#### Responsibilities:
- Tracks investigation progress and flags
- Manages location nodes and interactions
- Calculates and stores investigation scores
- Coordinates between character and game world

#### Key Components:
- **Flag System**: 24 boolean flags tracking specific game events
- **Investigation Nodes**: Three arrays for different location types
- **Scoring System**: Tracks manor, forest, social, and total scores
- **Path Management**: Records player's chosen investigation path

#### Important Methods:
- `investigateLocation()`: Main interaction method
- `setFlag()`/`getFlag()`: Flag management with bounds checking
- `getTotalKnowledgeScore()`: Combines character and investigation knowledge
- `getSummary()`: Provides progress overview

### GameUtilities.java
**Utility class with reusable methods**

#### Responsibilities:
- Provides input validation and formatting
- Handles user interaction consistently
- Offers common game-related utilities

#### Key Components:
- **Input Validation**: Robust number and string validation
- **Formatting Helpers**: Headers, dividers, and formatted output
- **Console Management**: Screen clearing and pause functionality
- **Safe Operations**: Null-safe string comparisons and operations

#### Important Methods:
- `getValidatedInt()`: Validates integer input within ranges
- `nextLine()`: Safe string input with prompt support
- `confirmYesNo()`: Yes/no confirmation with validation
- `printHeader()`/`printSectionHeader()`: Formatted output helpers

### HunterCharacter.java
**Specialized character class for physical investigation**

#### Responsibilities:
- Implements hunter-specific abilities and mechanics
- Manages hunting evidence collection
- Provides combat-focused gameplay options

#### Key Components:
- **Special Abilities**: Predator's patience, trap setting, tracking
- **Evidence System**: Collects and tracks physical evidence
- **Skill Set**: Tracking, stealth, traps, combat, terrain reading
- **Tools**: Hunting bow, trap kit, scent stone, notebook, horn

#### Important Methods:
- `addHuntingEvidence()`: Records physical evidence findings
- `investigatePhysicalEvidence()`: Hunter-specific investigation
- Specialized action implementations for hunter abilities

### InvestigationNode.java
**Location and interaction system**

#### Responsibilities:
- Defines investigation locations and their properties
- Manages available actions and evidence collection
- Processes character-specific interactions

#### Key Components:
- **Node Types**: Manor, forest, and social locations
- **Action System**: Context-specific actions for each location
- **Evidence Tracking**: Records clues found at each location
- **Character-Specific Responses**: Different outcomes based on character type

#### Important Methods:
- `performAction()`: Executes player-selected actions
- `processActionForCharacter()`: Generates character-specific responses
- `addEvidence()`: Records discovered clues

### MageCharacter.java
**Specialized character class for magical investigation**

#### Responsibilities:
- Implements mage-specific abilities and mechanics
- Manages ritual fragments and arcane residues
- Provides magic-focused gameplay options

#### Key Components:
- **Special Abilities**: Thread of Knowing, scrying, ritual casting
- **Fragment System**: Collects and tracks ritual knowledge
- **Skill Set**: Stealth, magic detection (but not physical skills)
- **Tools**: Scrying lens, sigil chalk, grimoire fragments, amulet reader

#### Important Methods:
- `addRitualFragment()`: Records magical knowledge findings
- `addArcaneResidue()`: Tracks magical residue discoveries
- `detectMagicalAura()`: Mage-specific investigation
- `performRitualAnalysis()`: Reviews collected magical knowledge

### MysteryGameMain.java
**Main game class and entry point**

#### Responsibilities:
- Coordinates game flow and user interaction
- Manages character creation and path selection
- Handles the main game loop and menu system

#### Key Components:
- **Game Loop**: Main investigation cycle
- **Menu System**: Player option selection
- **Climax Handling**: Final confrontation resolution
- **Progress Tracking**: Investigation count and completion check

#### Important Methods:
- `main()`: Game entry point
- `runGame()`: Main game flow controller
- `handleLocationInvestigation()`: Manages location exploration
- `handleClimaxAndEnding()`: Resolves the game's conclusion

## How the Game Works

### Character System
Players choose between Hunter and Mage characters, each with:
- Unique skills and tools
- Different investigation approaches
- Special abilities that affect gameplay
- Character-specific story responses

### Investigation System
Three investigation paths with multiple locations:
1. **Manor Path**: Explore the Mystery Villager's estate
2. **Forest Path**: Follow tracks into the wilderness
3. **Social Path**: Interact with villagers and authorities

Each location offers:
- Multiple interactive elements
- Character-specific responses
- Evidence collection opportunities
- Knowledge point rewards

### Progression System
- **Knowledge Points**: Earned through successful investigations
- **Evidence Collection**: Physical evidence (Hunter) or ritual fragments (Mage)
- **Flag System**: Tracks story progress and discoveries
- **Scoring**: Separate scores for each path plus total knowledge

### Ending System
Three resolution approaches with multiple outcomes:
1. **RESTORE**: Magical binding solution
2. **DESTROY**: Physical confrontation solution
3. **BARGAIN**: Social/political solution

Endings are determined by:
- Total knowledge score
- Specific evidence collected
- Character type
- Choice of resolution approach

## Technical Features

### Encapsulation
- Protected fields with getter methods
- Array cloning to prevent external modification
- Validation of inputs and parameters

### Polymorphism
- BaseCharacter abstract class with specialized implementations
- Character-specific responses to the same actions
- Different investigation approaches based on character type

### State Management
- Comprehensive flag system tracking story progress
- Investigation scoring across multiple dimensions
- Character-specific progression tracking

### Input Validation
- Robust number input with range checking
- String validation and sanitization
- Yes/confirm confirmation with character validation

## How to Play

1. Choose character class (Hunter or Mage)
2. Select initial investigation path (Manor, Forest, or Social)
3. Explore locations and interact with elements
4. Collect evidence and gain knowledge points
5. Review progress through status menu
6. When ready, proceed to final confrontation
7. Choose resolution approach and see your ending

## Conclusion

This Mystery Game implementation demonstrates object-oriented programming principles including encapsulation, inheritance, polymorphism, and abstraction. The game features a rich narrative system with multiple endings, character-specific gameplay, and a robust state management system. The code is organized with clear responsibilities separated between classes, making it maintainable and extensible.


# Harvard-Style Reference List

## YouTube Video References

Cave, N. (2021) *Java Programming Tutorial - Inheritance, Abstract Classes and Abstract Methods*. YouTube. Available at: https://www.youtube.com/watch?v=pQKg5SuGdPg (Accessed: 4 September 2025).

Bro Code (2023) *Java Polymorphism Fully Explained In 7 Minutes*. YouTube. Available at: https://www.youtube.com/watch?v=jhDUxynEQRI (Accessed: 4 September 2025).

RyiSnow (2022) *(For Complete Beginners) Text Adventure Game Tutorial in Java* [Playlist]. YouTube. Available at: https://www.youtube.com/playlist?list=PL_QPQmz5C6WX9X9bhBLnIU-eeNCBSyWmG (Accessed: 4 September 2025).

Gamefromscratch (2018) *State Management in Games*. YouTube. Available at: https://www.youtube.com/watch?v=jKe44NeFzwE (Accessed: 4 September 2025).

Infallible Code (2019) *When, How, and Why to Use the Game State*. YouTube. Available at: https://www.youtube.com/watch?v=l_ItJFnPRgA (Accessed: 4 September 2025).

Baraltech (2022) *An introduction to finite state machines and the state design pattern*. YouTube. Available at: https://www.youtube.com/watch?v=-ZP2Xm-mY4E (Accessed: 4 September 2025).

Game Maker's Toolkit (2017) *Designing Narrative Choice - Add Branching Paths to Game Stories*. YouTube. Available at: https://www.youtube.com/watch?v=Gdt5zCdXoSc (Accessed: 4 September 2025).

The Game Overanalyser (2021) *Do True Endings Enhance the Plot of Games?*. YouTube. Available at: https://www.youtube.com/watch?v=wqFBnxloJZ8 (Accessed: 4 September 2025).

Digital Learning Service (2020) *Twine for Beginners: How to create an interactive story online*. YouTube. Available at: https://www.youtube.com/watch?v=lhn39SPETMM (Accessed: 4 September 2025).

Alex Lee (2019) *How to Get User Input and Validate It Using Java (Simple)*. YouTube. Available at: https://www.youtube.com/watch?v=rioHqpj5xWo (Accessed: 4 September 2025).

## AI Tool Reference

OpenAI (2023) *ChatGPT* (September 4 version) [Large language model]. Available at: https://chatgpt.com/ (Accessed: 4 September 2025).

## Note on ChatGPT Usage

The development of this Mystery Game project utilized ChatGPT extensively for:

1. **Architectural Planning**: Assistance in designing the class structure and relationships between game components
2. **Code Implementation**: Guidance on Java-specific implementations including string manipulation with `String.repeat()`
3. **Scenario Design**: Brainstorming multiple narrative approaches and branching storylines
4. **Problem Solving**: Debugging assistance and optimization suggestions
5. **Documentation**: Help in creating comprehensive documentation and explanations

The AI tool served as a collaborative partner in the thought process, particularly in understanding how to integrate complex game mechanics and maintain consistency across multiple implementation approaches.
