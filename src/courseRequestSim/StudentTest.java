package courseRequestSim;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentTest {
    
    private Student student;

    @Before
    public void setUp(){
        student = new Student("Tim", 12345, 2);
    }

    @Test 
    public void testGetName(){
        assertEquals("Tim", student.getName());
    }

    @Test 
    public void testGetID(){
        assertEquals(12345, student.getID());
    }

    @Test 
    public void testGetClassYear(){
        assertEquals(2, student.getClassYear());
    }

    @Test 
    public void testInitialCredits(){
        assertEquals(0, student.getCurrentCredits());
    }

    @Test 
    public void testAddCredits(){
        student.addCredits();
        assertEquals(3, student.getCurrentCredits());
        student.addCredits();
        assertEquals(6, student.getCurrentCredits());
    }

    @Test 
    public void testRemoveCredits(){
        student.addCredits();
        student.addCredits();
        student.addCredits();
        assertEquals(9, student.getCurrentCredits());
        student.removeCredits();
        student.removeCredits();
        student.removeCredits();
        assertEquals(0, student.getCurrentCredits());
    }

    @Test 
    public void testRemoveZeroCredits(){
        Exception exception = null;
        try {
            student.removeCredits();
        }
        catch (IllegalStateException e){
            assertEquals(0, student.getCurrentCredits());
            exception = e;
        }
        assertNotNull(exception);
    }


}
