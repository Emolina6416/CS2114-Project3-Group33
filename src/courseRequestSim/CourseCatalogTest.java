package courseRequestSim;

import java.util.List;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CourseCatalogTest {
    
    private CourseCatalog catalog;

    @Before 
    public void setUp(){
        catalog = new CourseCatalog();
    }

    @Test
    public void testConstructor() {
        int total = 0;

        for (int year = 1; year <= 4; year++) {
            List<Course> courses = catalog.getCoursesByYear(year);

            assertEquals(10, courses.size());
            total += courses.size();
        }

        assertEquals(40, total);
    }

    @Test
    public void testGetCoursesByYear() {
        for (int year = 1; year <= 4; year++) {
            List<Course> courses = catalog.getCoursesByYear(year);

            assertEquals(10, courses.size());

            for (Course course : courses) {
                assertEquals(year, course.getClassYear());
            }
        }
    }

    @Test
    public void testGetBadYearInput() {
        assertTrue(catalog.getCoursesByYear(0).isEmpty());
        assertTrue(catalog.getCoursesByYear(5).isEmpty());
    }

    @Test
    public void testFindCourseByName() {
        Course course =
            catalog.findCourseByName("  dAtA sTrUcTuReS  ");

        assertNotNull(course);
        assertEquals("Data Structures", course.getCourseName());
        assertEquals(2, course.getClassYear());
    }

    @Test
    public void testFindInvalidCourseName() {
        assertNull(catalog.findCourseByName(null));
        assertNull(catalog.findCourseByName(""));
        assertNull(catalog.findCourseByName("   "));
        assertNull(catalog.findCourseByName("tim"));
    }


    //indirect check on randomly generated students
    @Test
    public void testPopulateRandomStudentsData() {
        Set<Integer> ids = new HashSet<>();

        for (int year = 1; year <= 4; year++) {
            for (Course course : catalog.getCoursesByYear(year)) {
                for (Student student : course.getQueueStudents()) {
                    assertEquals(year, student.getClassYear());
                    assertEquals(3, student.getCurrentCredits());
                    assertTrue(student.getID() >= 1000000);
                    assertEquals(
                        "Student" + student.getID(),
                        student.getName());

                    assertTrue(ids.add(student.getID()));
                }
            }
        }
    }

    //checks enrollment bounds (year, enrollment size)
    @Test
    public void testPopulateRandomStudentsEnrollmentBounds() {
        for (int year = 1; year <= 4; year++) {
            for (Course course : catalog.getCoursesByYear(year)) {
                int enrollment = course.getEnrollment();

                assertTrue(enrollment >= 0);
                assertTrue(enrollment <= 10);
                assertEquals(
                    enrollment,
                    course.getQueueStudents().size());
            }
        }
    }

}
