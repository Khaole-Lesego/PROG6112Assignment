# TV Series Management System

## Overview

This Java application is a TV Series Management System that allows users to manage a collection of TV series through a console-based interface. The system supports adding, searching, updating, deleting, and generating reports for TV series.

## Project Structure

The project consists of three main Java files:

1. **SeriesModel.java** - Data model class
2. **Series.java** - Business logic and management class
3. **SeriesApp.java** - Main application and user interface class

## Detailed Explanation

### SeriesModel.java

This file defines the data structure for storing TV series information.

```java
package seriesapp;
```

- **package seriesapp;** - Declares that this class belongs to the `seriesapp` package, organizing related classes together.

```java
public class SeriesModel {
    private String seriesId;
    private String seriesName;
    private int seriesAge;              // 2..18
    private int seriesNumberOfEpisodes; // >= 1
```

- **private String seriesId;** - A private field to store the series ID (only accessible within this class)
- **private String seriesName;** - A private field to store the series name
- **private int seriesAge;** - A private field to store the age restriction (2-18)
- **// 2..18** - Comment explaining the valid range for the age field

```java
// Default constructor
public SeriesModel() {
    this.seriesId = "";
    this.seriesName = "";
    this.seriesAge = 2;
    this.seriesNumberOfEpisodes = 1;
}
```

- **// Default constructor** - Comment indicating this is a default constructor
- **public SeriesModel()** - A constructor that creates a SeriesModel with default values
- **this.seriesId = "";** - Sets the seriesId to an empty string
- **this.seriesAge = 2;** - Sets the default age restriction to 2

```java
// Overloaded constructor (validates via setters)
public SeriesModel(String seriesId, String seriesName, int seriesAge, int seriesNumberOfEpisodes) {
    setSeriesId(seriesId);
    setSeriesName(seriesName);
    setSeriesAge(seriesAge);
    setSeriesNumberOfEpisodes(seriesNumberOfEpisodes);
}
```

- **// Overloaded constructor** - Comment explaining this is an alternative constructor
- **setSeriesId(seriesId);** - Uses the setter method to assign and validate the seriesId

```java
// Getters
public String getSeriesId() { return seriesId; }
public String getSeriesName() { return seriesName; }
public int getSeriesAge() { return seriesAge; }
public int getSeriesNumberOfEpisodes() { return seriesNumberOfEpisodes; }
```

- **// Getters** - Comment indicating these methods retrieve field values
- **public String getSeriesId()** - Method that returns the seriesId value

```java
// Setters with validation
public void setSeriesId(String seriesId) {
    if (seriesId == null) seriesId = "";
    this.seriesId = seriesId.trim();
}
```

- **// Setters with validation** - Comment indicating these methods set field values with validation
- **if (seriesId == null) seriesId = "";** - Checks if the input is null and replaces it with an empty string
- **.trim()** - Removes any leading or trailing whitespace from the string

```java
public void setSeriesAge(int seriesAge) {
    if (seriesAge < 2 || seriesAge > 18) {
        throw new IllegalArgumentException("Series age must be between 2 and 18 inclusive.");
    }
    this.seriesAge = seriesAge;
}
```

- **throw new IllegalArgumentException(...)** - Throws an exception if the age is not between 2 and 18

```java
@Override
public String toString() {
    return String.format("ID: %s | Name: %s | Age: %d | Episodes: %d",
            seriesId, seriesName, seriesAge, seriesNumberOfEpisodes);
}
```

- **@Override** - Indicates this method overrides the toString method from the Object class
- **String.format()** - Creates a formatted string with the series information

### Series.java

This file contains the main business logic for managing TV series.

```java
private final ArrayList<SeriesModel> seriesList;
```

- **private final ArrayList<SeriesModel> seriesList;** - Declares a list that will store SeriesModel objects

```java
public Series() {
    seriesList = new ArrayList<>();
}
```

- **seriesList = new ArrayList<>();** - Initializes the seriesList as a new empty ArrayList

```java
public void captureSeries(Scanner sc) {
    System.out.println("\n--- Capture New Series ---");
```

- **public void captureSeries(Scanner sc)** - Method to capture a new series from user input
- **System.out.println("\n--- Capture New Series ---");** - Prints a header message to the console

```java
System.out.print("Enter Series ID: ");
String id = sc.nextLine().trim();
```

- **sc.nextLine()** - Reads a line of text from the scanner input
- **.trim()** - Removes any leading or trailing whitespace

```java
int age = readValidatedInt(sc, "Enter Age restriction (2 - 18): ", 2, 18);
```

- **readValidatedInt()** - Calls a helper method to get a validated integer input

```java
SeriesModel model = new SeriesModel(id, name, age, episodes);
seriesList.add(model);
```

- **new SeriesModel(...)** - Creates a new SeriesModel object with the provided values
- **seriesList.add(model);** - Adds the new series to the list

```java
public boolean updateSeries(Scanner sc) {
```

- **public boolean updateSeries(Scanner sc)** - Method to update an existing series

```java
SeriesModel s = searchSeries(id);
if (s == null) {
    System.out.println("No series found with ID: " + id);
    return false;
}
```

- **searchSeries(id)** - Calls a method to find a series by ID
- **return false;** - Returns false to indicate the operation was not successful

```java
System.out.print("Enter new name (press Enter to keep current: " + s.getSeriesName() + "): ");
String newName = sc.nextLine();
if (!newName.trim().isEmpty()) s.setSeriesName(newName.trim());
```

- **!newName.trim().isEmpty()** - Checks if the new name is not empty after trimming whitespace

```java
public boolean deleteSeries(Scanner sc) {
    System.out.println("\n--- Delete Series ---");
```

- **public boolean deleteSeries(Scanner sc)** - Method to delete a series

```java
System.out.print("Are you sure you want to delete this series? (Y/N): ");
String confirm = sc.nextLine().trim();
if (confirm.equalsIgnoreCase("Y")) {
```

- **equalsIgnoreCase("Y")** - Checks if the input is "Y" regardless of case

```java
public void seriesReport() {
    System.out.println("\n--- Series Report ---");
    if (seriesList.isEmpty()) {
        System.out.println("No series captured yet.");
        return;
    }
```

- **seriesList.isEmpty()** - Checks if the series list is empty
- **return;** - Exits the method early if there are no series

```java
System.out.printf("%-10s %-25s %-5s %-8s%n", "SeriesID", "Name", "Age", "Episodes");
```

- **System.out.printf()** - Prints formatted output
- **%-10s** - Format specifier for a left-aligned string with 10 characters width

```java
public SeriesModel searchSeries(String seriesId) {
    if (seriesId == null) return null;
    Optional<SeriesModel> found = seriesList.stream()
            .filter(s -> s.getSeriesId().equalsIgnoreCase(seriesId.trim()))
            .findFirst();
    return found.orElse(null);
}
```

- **Optional<SeriesModel>** - A container object that may or may not contain a non-null value
- **.stream()** - Creates a stream from the list for functional-style operations
- **.filter()** - Filters elements based on a condition
- **s -> s.getSeriesId().equalsIgnoreCase(...)** - Lambda expression that checks if series ID matches
- **.findFirst()** - Returns the first element of the stream
- **.orElse(null)** - Returns the value if present, otherwise returns null

```java
public boolean updateSeriesById(String id, String newName, Integer newAge, Integer newEpisodes) {
```

- **Integer newAge** - Uses Integer object instead of primitive int to allow null values

```java
public int getCount() { return seriesList.size(); }
```

- **seriesList.size()** - Returns the number of elements in the list

```java
public void addSeries(SeriesModel s) {
    if (s == null) throw new IllegalArgumentException("SeriesModel cannot be null.");
    seriesList.add(s);
}
```

- **throw new IllegalArgumentException(...)** - Throws an exception if the parameter is null

```java
private int readValidatedInt(Scanner sc, String prompt, int min, int max) {
    while (true) {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        try {
            int v = Integer.parseInt(line);
            if (v < min || v > max) {
                System.out.println("Invalid range. Please enter a value between " + min + " and " + max + ".");
                continue;
            }
            return v;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number; please enter digits only.");
        }
    }
}
```

- **while (true)** - Creates an infinite loop that will break only with a return statement
- **Integer.parseInt(line)** - Converts a string to an integer, may throw NumberFormatException
- **catch (NumberFormatException e)** - Handles the exception if parsing fails
- **continue;** - Skips the rest of the loop iteration and starts a new one

```java
// Capitalised wrapper names (assignment compatibility)
public void CaptureSeries() { captureSeries(new Scanner(System.in)); }
```

- **// Capitalised wrapper names** - Comment explaining these are wrapper methods
- **new Scanner(System.in)** - Creates a new Scanner object that reads from standard input

### SeriesApp.java

This file contains the main application and user interface logic.

```java
public static void main(String[] args) {
```

- **public static void main(String[] args)** - The main method where program execution begins

```java
Scanner sc = new Scanner(System.in);
Series seriesManager = new Series();
boolean running = true;
```

- **Scanner sc = new Scanner(System.in);** - Creates a Scanner to read user input
- **Series seriesManager = new Series();** - Creates an instance of the Series class
- **boolean running = true;** - A flag to control the main loop

```java
while (running) {
    printMenu();
    System.out.print("Select option: ");
    String option = sc.nextLine().trim();
```

- **while (running)** - Continues looping as long as running is true
- **printMenu();** - Calls a method to display the menu options
- **sc.nextLine().trim()** - Reads and trims user input

```java
switch (option) {
    case "1":
        seriesManager.captureSeries(sc);
        break;
```

- **switch (option)** - Evaluates the user's menu choice
- **case "1":** - If the user entered "1", execute the following code
- **break;** - Exits the switch statement

```java
case "6":
    System.out.println("Exiting application. Goodbye!");
    running = false;
    break;
```

- **running = false;** - Sets the flag to false, which will exit the loop on next iteration

```java
default:
    System.out.println("Invalid option. Please choose 1-6.");
```

- **default:** - Handles any input that doesn't match the defined cases

```java
sc.close();
```

- **sc.close();** - Closes the Scanner to free resources

```java
private static void printMenu() {
    System.out.println("\n--- TV Series Management ---");
    System.out.println("1. Capture Series");
    System.out.println("2. Search Series");
    System.out.println("3. Update Series");
    System.out.println("4. Delete Series");
    System.out.println("5. Series Report");
    System.out.println("6. Exit");
}
```

- **private static void printMenu()** - A helper method to display the menu
- **System.out.println()** - Prints a line of text to the console

## How to Use the Application

1. Compile all Java files:
   ```
   javac seriesapp/*.java
   ```

2. Run the application:
   ```
   java seriesapp.SeriesApp
   ```

3. Follow the menu options to manage your TV series collection:
   - Option 1: Add a new TV series
   - Option 2: Search for a TV series by ID
   - Option 3: Update an existing TV series
   - Option 4: Delete a TV series
   - Option 5: View a report of all TV series
   - Option 6: Exit the application

## ChatGPT Assistance in Development

During the development of this TV Series Management System, ChatGPT was consulted for various aspects of the project:

### Conceptual Understanding
ChatGPT helped clarify object-oriented programming concepts, particularly:
- The Model-View-Controller (MVC) pattern implementation
- Separation of concerns between data model, business logic, and user interface
- Proper use of encapsulation through private fields with public getters/setters

### Code Structure and Organization
Assistance was provided in:
- Designing the class structure for optimal maintainability
- Implementing validation logic in the SeriesModel class
- Creating a user-friendly menu system in SeriesApp

### Input Validation Techniques
ChatGPT suggested robust input handling approaches:
- The readValidatedInt method for ensuring numeric input within specific ranges
- Using try-catch blocks for exception handling
- Implementing trim() to clean user input

### Advanced Java Features
Guidance was provided on using:
- Java Streams API for searching operations
- Optional class for handling potential null values
- String.format() for consistent output formatting

### Debugging Assistance
When encountering issues, ChatGPT helped:
- Identify problems with scanner input handling
- Resolve logical errors in the update process
- Improve the user experience with better prompts

### Alternative Approaches
For various scenarios, multiple solutions were discussed:
- Different ways to implement the search functionality
- Various approaches to input validation
- Alternative menu navigation designs

This collaborative process with ChatGPT enhanced the learning experience and resulted in a more robust, well-structured application that demonstrates proper Java programming practices.

## Harvard-Style Reference List

**YouTube Video Reference**  
Learn Programming (2025) *Student Management System Console App (Full Tutorial) Entire Skill* [YouTube video]. Available at: https://www.youtube.com/watch?v=ZoyzvFhWm4Y (Accessed: 4 September 2025).

**AI Assistant Reference**  
OpenAI (2025) *ChatGPT* [AI language model]. Available at: https://chatgpt.com/ (Accessed: 4 September 2025).

**Java Documentation Reference**  
Oracle Corporation (2025) *Java SE Documentation* [Online]. Available at: https://docs.oracle.com/javase/8/docs/ (Accessed: 4 September 2025).

## Key Features

- Input validation for age restrictions (2-18) and episode count (≥1)
- Case-insensitive search functionality
- Confirmation prompt before deletion
- Formatted report output
- Error handling for invalid inputs
- Menu-driven user interface

This application demonstrates object-oriented programming principles including encapsulation, separation of concerns, and code reusability through its class structure and method organization.
