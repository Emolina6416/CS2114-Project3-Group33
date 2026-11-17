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
    private List<Student> registeredStudents;
 
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
        this.registeredStudents = new ArrayList<>();
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
            while (currentUser != null && systemRunning) 
            {
                view.showMainMenu(currentUser.getName()); 
                String choice = getValidString("Select an option: ").toUpperCase();
                processMenuOptions(choice); 
            }
        }
        view.displayMessage("Exiting the Registration System. Goodbye!");
    }
    
    /**
     * handles the logic for when a user logs in
     */
    public void handleLogin() 
    {
        String name = getValidString("Enter your name (or type 'exit' to quit): ");
        
        if (name.equalsIgnoreCase("exit")) 
        {
            systemRunning = false;
            return;
        }
        
        if (name.equalsIgnoreCase("$dci%04/")) 
        {
            executeAdmin();
            return;
        }
        
        currentUser = authenticateStudent(name);
    }
    
    /**
     * helper method for handleLogin helps organization and 
     * so it doesnt take too long
     * 
     * This method helps authenticate students so that students dont
     * repeat use of an ID and students that have already logged in 
     * can relogin without issues
     */
    private Student authenticateStudent(String name) 
    {
        while (true) 
        {
            int id = getValidInt("Enter your 5-digit Student ID: ");
            
            if (id < 10000 || id > 99999) 
            {
                view.displayMessage("Error: ID must be exactly 5 digits.");
            } 
            else 
            {
                Student existing = findStudentById(id);
                
                if (existing == null) 
                {
                    int year = promptForClassYear();
                    Student newStudent = new Student(name, id, year);
                    registeredStudents.add(newStudent);
                    view.displayMessage("Welcome to the registration system, " + name + "!");
                    return newStudent;
                } 
                else if (existing.getName().equalsIgnoreCase(name)) 
                {
                    view.displayMessage("Welcome back, " + existing.getName() + "!");
                    return existing;
                } 
                else 
                {
                    view.displayMessage("Error: That Student ID belongs to another student. "
                        + "Enter a unique ID.");
                }
            }
        }
    }
    
    /**
     * helper method for authenticating student
     */
    private Student findStudentById(int id) 
    {
        for (int i = 0; i < registeredStudents.size(); i++) 
        {
            if (registeredStudents.get(i).getID() == id) 
            {
                return registeredStudents.get(i);
            }
        }
        return null;
    }
    
    /**
     * helper method for authenticating student 
     */
    private int promptForClassYear() 
    {
        int year = 0;
        while (year < 1 || year > 4) 
        {
            year = getValidInt("Enter your class year (1-Fresh, 2-Soph, 3-Jun, 4-Sen): ");
            if (year < 1 || year > 4) 
            {
                view.displayMessage("Error: Please select a valid year (1-4).");
            }
        }
        return year;
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
                
                List<Course> availableCourses = catalog.getCoursesByYear(currentUser.getClassYear());
                
                //shouldnt be empty since all years will have courses but...
                //just in case?
                if (availableCourses.isEmpty()) {
                    view.displayMessage("No courses currently available for your year.");
                    break;
                }
                
                //Courses printed (with number of students already in queue)
                for (int i = 0; i < availableCourses.size(); i++) 
                {
                    Course c = availableCourses.get(i);
                    int enrolled = c.getEnrollment();
                    String status = "";
                    if (enrolled >= 10)
                    {
                        status = " [Full - " + enrolled + "/10]";
                    }
                    else
                    {
                        status = " (" + enrolled + "/10 in queue)";
                    }
                    view.displayMessage((i + 1) + ". " + 
                        c.getCourseName() + status);
                }
                
                // 0 to go back
                view.displayMessage("0. Go Back to Main Menu");
                view.displayMessage("--------------------------------");
                
               
                int courseChoice = -1; 
                
                // Loop goes till the user inputs valid option
                while (courseChoice < 0 || courseChoice > availableCourses.size()) 
                {
                    courseChoice = getValidInt("Enter course number to add (or 0 to cancel): ");
                    if (courseChoice < 0 || courseChoice > availableCourses.size()) 
                    {
                        view.displayMessage("Invalid choice. Select a valid number or 0 to go back.");
                    }
                }
                
                // If the user chooses 0 instantly go back
                if (courseChoice == 0) {
                    break; 
                }
                
                // if not 0 then it adds class 
                String courseToAdd = availableCourses.get(courseChoice - 1).getCourseName();
                executeAddClass(courseToAdd);
                break;
            case "C": //Removing a course 
                view.displayMessage("--- Your Enrolled Courses ---");
                
                // List of user courses
                List<Course> enrolledCourses = getEnrolledCourses();
                
                // If they dont have any just go back to main menu
                if (enrolledCourses.isEmpty()) 
                {
                    view.displayMessage("You are not currently enrolled in any classes to drop.");
                    break; 
                }
                
                // List them out
                for (int i = 0; i < enrolledCourses.size(); i++) 
                {
                    view.displayMessage((i + 1) + ". " + enrolledCourses.get(i).getCourseName());
                }
                
                // 0 to go back
                view.displayMessage("0. Go Back to Main Menu");
                view.displayMessage("-----------------------------");
                
               
                int dropChoice = -1;
                
                // Loops till user picks a valid number 
                while (dropChoice < 0 || dropChoice > enrolledCourses.size()) 
                {
                    dropChoice = getValidInt("Enter the number of the course to drop (or 0 to cancel): ");
                    if (dropChoice < 0 || dropChoice > enrolledCourses.size()) 
                    {
                        view.displayMessage("Invalid choice. Select a valid number or 0 to go back.");
                    }
                }
                
                // If 0 just go back instantly 
                if (dropChoice == 0) {
                    break; 
                }
                
                // If not 0, remove it 
                String courseToRemove = enrolledCourses.get(dropChoice - 1).getCourseName();
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
     * defines what happens when admin key is used in login
     */
    public void executeAdmin()
    {
        boolean inAdmin = true;
        while (inAdmin) 
        {
            view.displayMessage("\n=== Administrator Menu ===");
            view.displayMessage("1 - View All Course Queue Sizes (By Year)");
            view.displayMessage("2 - Inspect Specific Course Queue & Student Info");
            view.displayMessage("0 - Exit Admin Mode");

            int choice = getValidInt("Select an admin option: ");
            switch (choice) 
            {
                case 1:
                    adminChoice1();
                    break;

                case 2:
                    adminChoice2();
                    break;

                case 0:
                    view.displayMessage("Exiting Admin Mode...\n");
                    inAdmin = false;
                    break;

                default:
                    view.displayMessage("Invalid option. Please enter 1, 2, or 0.");
            }
        }
    }
    
    /*
     * helper for execudeAdmin for choice 1 didnt want to make
     * the executeAdmin method too long
     */
    public void adminChoice1()
    {
        for (int yr = 1; yr <= 4; yr++) 
        {
            view.displayMessage("--- Year " + yr + " Courses ---");
            for (Course c : catalog.getCoursesByYear(yr)) 
            {
                view.displayMessage("  " + c.getCourseName() + " | Queue Size: " + c.getEnrollment() + "/10");
            }
        }
    }
    
    /**
     * helper for execudeAdmin for choice 2 didnt want to make
     * the executeAdmin method too long
     */
    public void adminChoice2()
    {
        String searchName = getValidString("Enter exact course name to inspect: ");
        Course target = catalog.findCourseByName(searchName);
        if (target == null) 
        {
            view.displayMessage("Course not found.");
        } 
        else 
        {
            List<Student> line = target.getQueueStudents();
            view.displayMessage("--- Queue for " + target.getCourseName() + " (" + line.size() + "/10) ---");
            if (line.isEmpty()) 
            {
                view.displayMessage("Queue is currently empty.");
            } 
            else 
            {
                for (int i = 0; i < line.size(); i++) 
                {
                    Student s = line.get(i);
                    view.displayMessage((i + 1) + ". " + s.getName() 
                        + " | ID: " + s.getID() 
                        + " | Year: " + s.getClassYear() 
                        + " | Credits: " + s.getCurrentCredits() + "/15");
                }
            }
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
        
        //specific checks if already in course or exceeding creds
        if (course.isEnrolled(currentUser)) 
        {
            view.displayMessage("You are already in the queue for " + 
                courseName + ".");
            return;
        }
        if (currentUser.getCurrentCredits() + 3 > 15) 
        {
            view.displayMessage("Exceeds credits allowed (15 max).");
            return;
        }

        // just joining the class if there is space available
        if (course.getEnrollment() < 10) 
        {
            if (course.addStudent(currentUser)) 
            {
                view.displayMessage("Successfully joined the queue for " + courseName + ".");
            }
            return;
        }

        // full class triggers a force add 
        view.displayMessage("This class is currently full (10/10).");
        String response = "";
        while (!response.equals("Y") && !response.equals("N")) 
        {
            response = 
                getValidString
                ("Would you like to request a Force Add? (Y/N): ").toUpperCase();
            if (!response.equals("Y") && !response.equals("N")) 
            {
                view.displayMessage("Invalid input. Please enter Y or N.");
            }
        }

        if (response.equals("Y")) 
        {
            if (course.forceAddStudent(currentUser)) 
            {
                view.displayMessage
                ("Force add approved! You have been added to " + courseName + ".");
            } 
            else 
            {
                view.displayMessage
                ("Force add denied. You were not added to " + courseName + ".");
            }
        } 
        else 
        {
            view.displayMessage
            ("Force add canceled. Returning to menu.");
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
        view.displayMessage("-----------------------------");
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
