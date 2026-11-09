package courseRequestSim;

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

    public void close() {
        scanner.close();
    }
}
