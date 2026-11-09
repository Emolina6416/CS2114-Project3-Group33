package courseRequestSim;

import java.util.List;
import java.util.Scanner;

/**
 * VIEW: only prints text and collects raw input.
 */
public class ConsoleView {

    private final Scanner scanner = new Scanner(System.in);

    // Main menu: greets the user, then lists options A-D
    public void showMainMenu(String studentName) {
        System.out.println("\n=== Course Registration ===");
        System.out.println("Welcome, " + studentName + "!");
        System.out.println("A - View Schedule");
        System.out.println("B - View Catalog");
        System.out.println("C - Remove Class");
        System.out.println("D - Log Out");
    }

    // Catalog submenu, where Add Class lives (option B from the main menu)
    public void showCatalogMenu() {
        System.out.println("\n--- Course Catalog ---");
        System.out.println("A - Add Class");
        System.out.println("B - Back to Main Menu");
    }

    // Prints a titled list of strings (catalog courses, schedule, etc.)
    // Controller builds the strings, e.g. "Intro to CS (3 cr) - 7/10 seats"
    public void displayList(String title, List<String> items) {
        System.out.println("\n--- " + title + " ---");
        if (items.isEmpty()) {
            System.out.println("(none)");
            return;
        }
        for (String item : items) {
            System.out.println(item);
        }
    }

    // Prints a system message like "Class Added!" or "Course not found"
    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    // Prompts and returns the raw line the user typed (controller validates it)
    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Same as getUserInput (name used in the UML diagram)
    public String getStringInput(String prompt) {
        return getUserInput(prompt);
    }

    // Keeps asking until the user types a whole number (range checks stay in the controller)
    public int getIntInput(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(getUserInput(prompt));
            } catch (NumberFormatException e) {
                displayMessage("Please enter a whole number.");
            }
        }
    }

    public void close() {
        scanner.close();
    }
}
