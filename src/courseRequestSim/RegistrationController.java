package courseRequestSim;

import java.util.ArrayList;
import java.util.List;

/**
 *  The Controller of the Registration System, handles the logic
 *  and the bulk of bad input handling
 * 
 *  @author Emerson Molina
 *  @version Nov 3, 2026
 */
public class RegistrationController
{
    //~ Fields ................................................................
    
    private CourseCatalog catalog;
    private Student currentUser;
    private boolean systemRunning;
    private ConsoleView view;
 
    //~ Constructors ..........................................................
    
    /**
     * Sets up the different parts from the fields that will be used throughout
     * the program
     */
    public RegistrationController()
    {
        this.catalog = new CourseCatalog();
        this.catalog.initializeCatalog();
        this.view = new ConsoleView();
        this.systemRunning = true;
    }
    //~Public  Methods ........................................................
    /**
     * Main point of entry for the system loops through the
     * login and main menu
     */
    public void runSystem() 
    {
        while (systemRunning) 
        {
            handleLogin(); 
            
            // Loop for the current user thats logged in (Choosing D resets
            // this by making currentUser null
            while (currentUser != null) 
            {
                view.showMainMenu(); 
                String choice = getValidString("Select an option: ").toUpperCase();
                processMenuOptions(choice); 
            }
        }
    }
    
    /**
     * handles the logic for when a user logs in
     */
    public void handleLogin() 
    {
        String name = getValidString("Enter your name: ");
        
        int id = 0;
        while (id < 10000 || id > 99999) //Invalid ID just keeps asking input
        { 
            id = getValidInt("Enter your 5-digit Student ID: ");
            if (id < 10000 || id > 99999) //Strictly 5 digit
            {
                view.displayMessage("Error: ID must be exactly 5 digits.");
            }
        }

        int year = 0;
        while (year < 1 || year > 4) 
        { 
            year = getValidInt("Enter your class year (1-Fresh, 2-Soph, 3-Jun, 4-Sen): ");
            if (year < 1 || year > 4) 
            {
                view.displayMessage("Error: Please select a valid year (1-4).");
            }
        }

        currentUser = new Student(name, id, year);
        view.displayMessage("Welcome to the registration system, " + name + "!");
    }
    
    /**
     * Says what to do when processing option from the menu
     */
    public void processMenuOptions(String choice) 
    {
        switch (choice) 
        {
            case "A": //Looks at the schedule
                executeViewSchedule();
                break;
            case "B": //Looking at courses
                view.displayMessage("--- Available Courses for Year " 
                    + currentUser.getClassYear() + " ---");
                
                List<Course> availableCourses = 
                        catalog.getCoursesByYear(currentUser.getClassYear());
                
                // Checks for an empty list first (shouldnt have to worry about 
                // this though, I think since we'll have classes for all years
                // but just in case, if this does pop up its a problem)
                if (availableCourses.isEmpty()) {
                    view.displayMessage("No courses currently available for your year.");
                    break;
                }

                // Numbered list of classes so that a user picks from 1 to
                // however many classes 
                for (int i = 0; i < availableCourses.size(); i++) 
                {
                    // i + 1 makes the list print as 1, 2, 3 instead of 0, 1, 2
                    view.displayMessage((i + 1) + ". " + 
                    availableCourses.get(i).getCourseName());
                }
                view.displayMessage("--------------------------------");
                
                // Loops till valid choice is picked 
                int courseChoice = 0;
                while (courseChoice < 1 || courseChoice > availableCourses.size()) 
                {
                    courseChoice = getValidInt("Enter the number of the course to add: ");
                    if (courseChoice < 1 || courseChoice > availableCourses.size()) 
                    {
                        view.displayMessage
                        ("Invalid choice. Please select a valid number from the list.");
                    }
                }
                
                // Get the name of the course (subtract 1 for the real index)
                String courseToAdd = availableCourses.get(courseChoice - 1).getCourseName();
                
                // Passed to add class where it uses that name to add the class
                executeAddClass(courseToAdd);
                break;
            case "C": //Removing a course 
                view.displayMessage("--- Your Enrolled Courses ---");
                
                // List of courses, helper method used here 
                List<Course> enrolledCourses = getEnrolledCourses();
                
                //checks if the user does not have any classes added yet
                if (enrolledCourses.isEmpty()) 
                {
                    view.displayMessage("You are not currently enrolled in any classes to drop.");
                    break; 
                }
                
                // Print the numbered list (same i + 1 logic from choice B)
                for (int i = 0; i < enrolledCourses.size(); i++) 
                {
                    view.displayMessage((i + 1) + ". " + enrolledCourses.get(i).getCourseName());
                }
                view.displayMessage("-----------------------------");
                
                //Loops until valid number is picked 
                int dropChoice = 0;
                while (dropChoice < 1 || dropChoice > enrolledCourses.size()) 
                {
                    dropChoice = getValidInt("Enter the number of the course to drop: ");
                    if (dropChoice < 1 || dropChoice > enrolledCourses.size()) 
                    {
                        view.displayMessage("Invalid choice. Please select a valid number from the list.");
                    }
                }
                
                // Retrieve the string name and execute the drop
                String courseToRemove = 
                    enrolledCourses.get(dropChoice - 1).getCourseName();
                executeRemoveClass(courseToRemove); 
                break;
            case "D": //Logging out
                view.displayMessage("Logging out...");
                currentUser = null; 
                break; 
            default:
                view.displayMessage("Invalid Option. Please select A, B, C, or D.");
        }
    }
    
    /**
     * handles the logic for adding a class, this only adds them to the
     * queue when looking at the schedule thats made at the time of execution
     * every time
     * @param courseName the name of the course being added
     */
    public void executeAddClass(String courseName) 
    {
        Course course = catalog.findCourseByName(courseName);
        
        if (course == null) 
        {
            view.displayMessage("Course not found. Returning to menu.");
            return;
        }
        
        boolean success = course.addStudent(currentUser);
        
        if (success) 
        {
            view.displayMessage("Successfully joined the queue for " + courseName + ".");
        } 
        else 
        {
            // prompt for force add could be put here when its added
            view.displayMessage("Failed to add class. The queue is full or you exceed 15 credits.");
        }
    }
    
    /**
     * handles the logic for removing a class removes them from the queue
     * @param courseName the name of the course being removed
     */
    public void executeRemoveClass(String courseName) {
        Course course = catalog.findCourseByName(courseName);
        
        if (course == null) 
        {
            view.displayMessage("Course not found.");
            return; // Stops  early so it doesnt crash trying to remove 
            // from a null course
        }
        
        // Course.removeStudent should return false if they aren't in the queue
        boolean success = course.removeStudent(currentUser);
        
        if (success) 
        {
            view.displayMessage("Successfully dropped " + courseName + ". Credits updated.");
        } 
        else 
        {
            // This satisfies your "Bad input" spec requirement
            view.displayMessage("You are not enrolled in this class.");
        }
    }
    
    /**
     * handles the logic for viewing the schedule goes through the courses and
     * checks if a user is in a class and prints those out to the user 
     */
    public void executeViewSchedule() 
    {
        view.displayMessage("--- Your Current Schedule ---");
        
        // Call your new helper method!
        List<Course> enrolledCourses = getEnrolledCourses();
        
        for (Course course : enrolledCourses) 
        {
            view.displayMessage("- " + course.getCourseName());
        }
        
        if (enrolledCourses.isEmpty()) 
        {
            view.displayMessage("You are not currently enrolled in any classes.");
        }
        
        view.displayMessage("Total Credits: " + currentUser.getCurrentCredits() + "/15");
        view.displayMessage("-----------------------------\n");
    }
    
    /**
     * Validates string inputs from the user
     * @param prompt the input to be processed
     * @return the validated string input to be used
     */
    public String getValidString(String prompt) 
    {
        String input = "";
        while (input.trim().isEmpty()) 
        {
            input = view.getUserInput(prompt);
            if (input.trim().isEmpty()) 
            {
                view.displayMessage("Input cannot be empty. Please try again.");
            }
        }
        return input.trim();
    }
    
    /**
     * Validates integer inputs from the user
     * @param prompt the input to be processed
     * @return returns the valid number processed
     */
    public int getValidInt(String prompt) 
    {
        while (true) 
        {
            String input = getValidString(prompt);
            try 
            {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                view.displayMessage("Invalid input. Please enter a "
                    + " valid whole number.");
            }
        }
    }
    /**
     * Helper method to get the users enrolledClasses used in view schedule
     * and option C
     */
    private List<Course> getEnrolledCourses() 
    {
        List<Course> yearCourses = catalog.getCoursesByYear(currentUser.getClassYear());
        List<Course> enrolled = new ArrayList<>(); 
        
        for (Course course : yearCourses) 
        {
            if (course.isEnrolled(currentUser)) 
            {
                enrolled.add(course);
            }
        }
        return enrolled;
    }
    
    
}
