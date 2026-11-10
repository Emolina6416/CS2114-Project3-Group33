package courseRequestSim;
public class Student
{
    //~ Fields ................................................................
    private String name;
    private int id;
    private int classYear;
    private int currCredits;
    //~ Constructors ..........................................................
    public Student(String name, int id, int classYear){
        this.name = name;
        this.id = id;
        this.classYear = classYear;
        this.currCredits = 0;
    }
    //~Public  Methods ........................................................
    public String getName(){
        return name;
    }
    public int getID(){
        return id;
    }
    public int getClassYear(){
        return classYear;
    }
    public int getCurrentCredits(){
        return currCredits;
    }
    public void addCredits(){
        currCredits += 3;
    }
    public void removeCredits(){
        if (getCurrentCredits() < 3){
            throw new IllegalStateException("Student has no credits to remove.");
        }
        currCredits -= 3;
    }

}
