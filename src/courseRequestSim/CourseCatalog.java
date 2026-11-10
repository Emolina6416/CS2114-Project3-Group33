package courseRequestSim;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CourseCatalog
{
    //~ Fields ................................................................
    private final List<Course> allCourses = new ArrayList<>();
    private final Random random = new Random();
    // Avoid matching a real student ID 
    private int dummyID = 1000000;
    //~ Constructors ..........................................................
    public CourseCatalog(){
        initializeCatalog();
    }
    
    //~Public  Methods ........................................................
    public void initializeCatalog(){
        if (!allCourses.isEmpty()){
            return;
        }
        //Year 1
        allCourses.add(new Course("Introduction to Programming", 1));
        allCourses.add(new Course("Calculus I", 1));
        allCourses.add(new Course("English Composition", 1));
        allCourses.add(new Course("Introduction to Computing", 1));
        allCourses.add(new Course("College Algebra", 1));
        allCourses.add(new Course("Public Speaking", 1));
        allCourses.add(new Course("Introduction to Psychology", 1));
        allCourses.add(new Course("World History", 1));
        allCourses.add(new Course("Introduction to Statistics", 1));
        allCourses.add(new Course("First-Year Seminar", 1));
        //Year 2
        allCourses.add(new Course("Data Structures", 2));
        allCourses.add(new Course("Discrete Mathematics", 2));
        allCourses.add(new Course("Calculus II", 2));
        allCourses.add(new Course("Computer Organization", 2));
        allCourses.add(new Course("Object-Oriented Programming", 2));
        allCourses.add(new Course("Linear Algebra", 2));
        allCourses.add(new Course("Technical Writing", 2));
        allCourses.add(new Course("Web Development", 2));
        allCourses.add(new Course("Introduction to Databases", 2));
        allCourses.add(new Course("Computing Ethics", 2));
        //Year 3
        allCourses.add(new Course("Algorithms", 3));
        allCourses.add(new Course("Operating Systems", 3));
        allCourses.add(new Course("Computer Networks", 3));
        allCourses.add(new Course("Software Engineering", 3));
        allCourses.add(new Course("Database Systems", 3));
        allCourses.add(new Course("Cybersecurity", 3));
        allCourses.add(new Course("Programming Languages", 3));
        allCourses.add(new Course("Human-Computer Interaction", 3));
        allCourses.add(new Course("Mobile App Development", 3));
        allCourses.add(new Course("Probability", 3));
        //Year 4
        allCourses.add(new Course("Senior Project", 4));
        allCourses.add(new Course("Artificial Intelligence", 4));
        allCourses.add(new Course("Machine Learning", 4));
        allCourses.add(new Course("Distributed Systems", 4));
        allCourses.add(new Course("Cloud Computing", 4));
        allCourses.add(new Course("Computer Graphics", 4));
        allCourses.add(new Course("Advanced Cybersecurity", 4));
        allCourses.add(new Course("Compiler Design", 4));
        allCourses.add(new Course("Software Testing", 4));
        allCourses.add(new Course("Data Mining", 4));

        for (Course course : allCourses){
            populateRandomStudents(course);
        }
        }

    public List<Course> getCoursesByYear(int classYear){
        List<Course> matchingCourses = new ArrayList<>();
        if (classYear < 1 || classYear > 4){
            return matchingCourses;
        }
        for (Course course : allCourses){
            if (course.getClassYear() == classYear){
                matchingCourses.add(course);

            }
        }
        return matchingCourses;

    }
    public Course findCourseByName(String courseName){
        if (courseName == null){
            return null;
        }
        for (Course course : allCourses){
            if (course.getCourseName().equalsIgnoreCase(courseName.trim())){
                return course;
            }
        }
        return null;
    }

    private void populateRandomStudents(Course course){
        int numOfStudents = random.nextInt(11);
        for (int i = 0; i < numOfStudents; i++){
            int id = dummyID++;
            Student dummy = new Student("Student" + id, id, course.getClassYear());
            course.addStudent(dummy);

        }
    }
}
