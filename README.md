# PROG6112Assignment
PROG6112  Assignment 

# TV Series Management System & Mystery Game

## Overview

This repository contains two distinct Java applications developed for an academic assignment:

1. **TV Series Management System** (Section A) - A console application for managing TV series data
2. **Mystery Game** (Section B) - A text-based adventure game with supernatural investigation elements

Both applications demonstrate object-oriented programming principles, input validation, and proper software architecture.

## Section A: TV Series Management System

### Description
A console-based application that allows users to manage a collection of TV series through a menu-driven interface. Users can add, search, update, delete, and generate reports for TV series.

### Key Features
- Menu-driven console interface
- Input validation for age restrictions (2-18)
- CRUD operations (Create, Read, Update, Delete)
- Data persistence using arrays/ArrayLists
- Formatted reporting

### Project Structure
```
SeriesApp/
├── SeriesModel.java    # Data model class
├── Series.java         # Business logic class
└── SeriesApp.java      # Main application class
```

### How to Run
1. Compile all Java files:
   ```
   javac seriesapp/*.java
   ```
2. Run the application:
   ```
   java seriesapp.SeriesApp
   ```

## Section B: Mystery Game

### Description
A text-based adventure game where players investigate supernatural occurrences in a village called Hollowfen. Players choose between Hunter and Mage character classes and explore three investigation paths (Manor, Forest, Social) to uncover clues and resolve the mystery.

### Key Features
- Character class system (Hunter/Mage) with unique abilities
- Multiple investigation paths with different outcomes
- Complex narrative with 16 possible endings
- Knowledge point system for progression
- Robust input validation and game state management

### Project Structure
```
MysteryGame/
├── BaseCharacter.java       # Abstract character class
├── HunterCharacter.java     # Hunter specialization
├── MageCharacter.java       # Mage specialization
├── InvestigationNode.java   # Location and interaction system
├── GameStateManager.java    # Game progress tracking
├── ClimaxManager.java       # Ending resolution system
├── GameUtilities.java       # Utility functions
└── MysteryGameMain.java     # Main game class
```

### How to Run
1. Compile all Java files:
   ```
   javac mysterygame/*.java
   ```
2. Run the game:
   ```
   java mysterygame.MysteryGameMain
   ```

## Common Technical Features

Both applications demonstrate:
- Object-oriented programming principles
- Encapsulation through private fields with public accessors
- Input validation and error handling
- Menu-driven user interfaces
- Data persistence in memory
- Comprehensive unit testing (for Section A)

## Development Process

### ChatGPT Assistance
During development, ChatGPT was consulted for:
- Architectural planning and class structure design
- Implementation guidance for Java-specific features
- Debugging assistance and optimization suggestions
- Narrative design for the Mystery Game
- Documentation and explanation of concepts

### Key Concepts Implemented
- Inheritance and polymorphism
- Arrays and ArrayLists
- Loops and control structures
- Information hiding and encapsulation
- Exception handling
- String manipulation and formatting

## Academic Integrity Statement

This work represents my original implementation of the assignment requirements. While ChatGPT was consulted for conceptual understanding and problem-solving approaches, all code was written and tested by me. AI assistance was used primarily for:
- Clarifying object-oriented programming concepts
- Suggesting implementation approaches
- Debugging assistance
- Documentation formatting

## References

### YouTube Video References
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

### AI Tool Reference
OpenAI (2023) *ChatGPT* (September 4 version) [Large language model]. Available at: https://chatgpt.com/ (Accessed: 4 September 2025).

### Java Documentation Reference
Oracle Corporation (2025) *Java SE Documentation* [Online]. Available at: https://docs.oracle.com/javase/8/docs/ (Accessed: 4 September 2025).

## Grading Considerations

Both projects address the specific requirements outlined in the assignment:

### Section A (TV Series Management)
- Complete menu system with all required options
- Proper data model implementation
- Full CRUD functionality
- Input validation for age restrictions
- Comprehensive reporting feature
- Unit tests for all required functionality

### Section B (Mystery Game)
- Original console application with creative concept
- Implementation of arrays, loops, inheritance, and information hiding
- Complex narrative with multiple paths and endings
- Proper class structure and encapsulation
- Comprehensive unit tests

## Conclusion

These applications demonstrate proficiency in Java programming, object-oriented design principles, and software development best practices. The TV Series Management System shows practical business application development skills, while the Mystery Game demonstrates creative problem-solving and complex system design capabilities.
