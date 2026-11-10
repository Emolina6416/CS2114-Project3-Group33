package courseRequestSim;
public class Course
{
    //~ Fields ................................................................
    private String courseName;
    private int classYear;
    private int capacity = 10;
    private Queue classLine = new Queue();

    //~ Constructors ..........................................................
    public Course(String courseName, int classYear){
        if (courseName == null || courseName.trim().isEmpty()){
            throw new IllegalArgumentException("Course name is required.");
        }
        if (classYear < 1 || classYear > 4){
            throw new IllegalArgumentException("Class year must be 1-4.");
        }
        this.courseName = courseName.trim();
        this.classYear = classYear;
    }
    //~Public  Methods ........................................................

    public boolean isEnrolled(Student student){
        return classLine.contains(student);
    }

    public String getCourseName(){
        return courseName;
    }
    public int getClassYear(){
        return classYear;
    }
    public int getEnrollment(){
        return classLine.getSize();
    }

    public boolean addStudent(Student student){
        if (student == null || isEnrolled(student)){
            return false;
        }
        if (classLine.getSize() >= capacity){
            return false;
        }
        if (student.getCurrentCredits() + 3 > 15){
            return false;
        }
        classLine.enqueue(student);
        student.addCredits();
        return true;
    }

    public boolean forceAddStudent(Student student){
        if (student == null || isEnrolled(student)){
            return false;
        }
        // 30% chance that a student gets added from force add
        if (Math.random() >= 0.30){
            return false;
        }
        classLine.enqueue(student);
        student.addCredits();
        return true;

    }
    
    public boolean removeStudent(Student student){
        Student currentStudent = classLine.findStudent(student);
        if (currentStudent == null){
            return false;
        }
        if (classLine.removeSpecific(currentStudent)){
            currentStudent.removeCredits();
            return true;

        }
        return false;

    }
}

