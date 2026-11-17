package courseRequestSim;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CourseTest {
    
    private Course course;
    private Student student;

    @Before 
    public void setUp(){
        student = new Student("Tim", 12345, 2);
        course = new Course("math", 2);
    }

    @Test 
    public void testConstructor(){
        Course test = new Course("Science", 2);
        assertEquals(2, test.getClassYear());
        assertEquals("Science", test.getCourseName());
        assertEquals(0, test.getEnrollment());
    }

    @Test 
    public void testIsEnrolled(){
        assertTrue(course.addStudent(student));
        assertTrue(course.isEnrolled(student));

    }

    @Test 
    public void testIsEnrolledNull(){
        assertFalse(course.isEnrolled(null));
    }

    @Test 
    public void testGetCourseName(){
        assertEquals("math", course.getCourseName());
    }

    @Test 
    public void getCourseNameWSpace(){
        Course testCourse = new Course(" math ", 2);
        assertEquals("math", testCourse.getCourseName());
    }

    @Test 
    public void getClassYear(){
        assertEquals(2, course.getClassYear());
    }

    @Test 
    public void testGetEnrollment(){
        course.addStudent(student);
        assertEquals(1, course.getEnrollment());
    }

    @Test 
    public void testGetEmptyEnrollment(){
        assertEquals(0, course.getEnrollment());
    }

    @Test 
    public void testAddStudent(){
        assertTrue(course.addStudent(student));
        assertEquals(1, course.getEnrollment());
        assertEquals(3, student.getCurrentCredits()); 
    }

    @Test 
    public void testAddNullStudent(){
        assertFalse(course.addStudent(null));
        assertEquals(0, course.getEnrollment());
    }

    @Test 
    public void testForceAddStudent(){
        boolean add = course.forceAddStudent(student);
        if (add){
            assertTrue(course.isEnrolled(student));
            assertEquals(1, course.getEnrollment());
            assertEquals(3, student.getCurrentCredits());
        }
        else{
            assertFalse(course.isEnrolled(student));
            assertEquals(0, course.getEnrollment());
            assertEquals(0, student.getCurrentCredits());
        }
    }

    @Test 
    public void testForecAddStudentNull(){
        assertFalse(course.forceAddStudent(null));
        assertEquals(0, course.getEnrollment());
    }

    @Test
    public void testRemoveStudent() {
        assertTrue(course.addStudent(student));
        assertTrue(course.removeStudent(student));
        assertFalse(course.isEnrolled(student));
        assertEquals(0, course.getEnrollment());
        assertEquals(0, student.getCurrentCredits());
    }

    @Test
    public void testGetQueueStudents() {
        Student second = new Student("Tim", 123, 2);
        assertTrue(course.addStudent(student));
        assertTrue(course.addStudent(second));

        List<Student> students = course.getQueueStudents();

        assertEquals(2, students.size());
        assertSame(student, students.get(0));
        assertSame(second, students.get(1));
    }

    @Test
    public void testGetQueueStudentsEmpty() {
        List<Student> students = course.getQueueStudents();

        assertNotNull(students);
        assertTrue(students.isEmpty());
    }
}
