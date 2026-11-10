package courseRequestSim;

import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class QueueTest {

    private Queue queue;
    private Student first;
    private Student second;
    private Student third;

    @Before
    public void setUp() {
        queue = new Queue();
        first = makeStudent(1);
        second = makeStudent(2);
        third = makeStudent(3);
    }

    // helper method to make students
    private Student makeStudent(int id) {
        return new Student("Student " + id, id, 2027);
    }   

    @Test
    public void testNewQueueIsEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testSizeAfterEnqueueAndDequeue() {
        queue.enqueue(first);
        queue.enqueue(second);

        assertFalse(queue.isEmpty());
        assertEquals(2, queue.getSize());

        queue.dequeue();
        assertEquals(1, queue.getSize());

        queue.dequeue();
        assertEquals(0, queue.getSize());
        assertTrue(queue.isEmpty());
    }

    // enqueue()

    @Test
    public void testEnqueueOneStudent() {
        queue.enqueue(first);

        assertSame(first, queue.peek());
        assertEquals(1, queue.getSize());
        assertFalse(queue.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnqueueNullThrowsException() {
        queue.enqueue(null);
    }

    // dequeue() tests

    @Test
    public void testDequeueInFirstInFirstOutOrder() {
        queue.enqueue(first);
        queue.enqueue(second);
        queue.enqueue(third);

        assertSame(first, queue.dequeue());
        assertSame(second, queue.dequeue());
        assertSame(third, queue.dequeue());
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDequeueEmptyQueueThrowsException() {
        queue.dequeue();
    }

    @Test
    public void testEnqueueAfterQueueIsEmptied() {
        queue.enqueue(first);
        queue.dequeue();

        queue.enqueue(second);

        assertEquals(1, queue.getSize());
        assertSame(second, queue.peek());
        assertSame(second, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    // peek()

    @Test
    public void testPeekEmptyQueueReturnsNull() {
        assertNull(queue.peek());
    }

    @Test
    public void testPeekDoesNotRemoveStudent() {
        queue.enqueue(first);
        queue.enqueue(second);

        assertSame(first, queue.peek());
        assertSame(first, queue.peek());
        assertEquals(2, queue.getSize());
        assertSame(first, queue.dequeue());
    }

    // contains()

    @Test
    public void testContainsOnEmptyQueue() {
        assertFalse(queue.contains(first));
    }

    @Test
    public void testContainsStudentsAtAllPositions() {
        queue.enqueue(first);
        queue.enqueue(second);
        queue.enqueue(third);

        assertTrue(queue.contains(first));
        assertTrue(queue.contains(second));
        assertTrue(queue.contains(third));
    }

    @Test
    public void testContainsMissingStudent() {
        queue.enqueue(first);

        assertFalse(queue.contains(second));
    }

    @Test
    public void testContainsNull() {
        queue.enqueue(first);

        assertFalse(queue.contains(null));
    }

    @Test
    public void testContainsMatchesById() {
        queue.enqueue(first);
        Student sameId = makeStudent(1);

        assertNotSame(first, sameId);
        assertTrue(queue.contains(sameId));
    }

    // removeSpecific()

    @Test
    public void testRemoveFromEmptyQueue() {
        assertFalse(queue.removeSpecific(first));
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testRemoveNull() {
        queue.enqueue(first);

        assertFalse(queue.removeSpecific(null));
        assertEquals(1, queue.getSize());
        assertSame(first, queue.peek());
    }

    @Test
    public void testRemoveMissingStudent() {
        queue.enqueue(first);

        assertFalse(queue.removeSpecific(second));
        assertEquals(1, queue.getSize());
        assertSame(first, queue.peek());
    }

    @Test
    public void testRemoveHead() {
        queue.enqueue(first);
        queue.enqueue(second);
        queue.enqueue(third);

        assertTrue(queue.removeSpecific(first));
        assertEquals(2, queue.getSize());
        assertFalse(queue.contains(first));
        assertSame(second, queue.dequeue());
        assertSame(third, queue.dequeue());
    }

    @Test
    public void testRemoveMiddle() {
        queue.enqueue(first);
        queue.enqueue(second);
        queue.enqueue(third);

        assertTrue(queue.removeSpecific(second));
        assertEquals(2, queue.getSize());
        assertFalse(queue.contains(second));
        assertSame(first, queue.dequeue());
        assertSame(third, queue.dequeue());
    }

    @Test
    public void testRemoveTailThenEnqueue() {
        queue.enqueue(first);
        queue.enqueue(second);

        assertTrue(queue.removeSpecific(second));
        assertEquals(1, queue.getSize());

        queue.enqueue(third);

        assertEquals(2, queue.getSize());
        assertSame(first, queue.dequeue());
        assertSame(third, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testRemoveOnlyStudentThenEnqueue() {
        queue.enqueue(first);

        assertTrue(queue.removeSpecific(first));
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
        assertNull(queue.peek());

        queue.enqueue(second);

        assertSame(second, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testRemoveMatchesById() {
        queue.enqueue(first);
        Student sameId = makeStudent(1);

        assertTrue(queue.removeSpecific(sameId));
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testRemoveOnlyFirstMatchingStudent() {
        Student sameId = makeStudent(1);
        queue.enqueue(first);
        queue.enqueue(sameId);
        queue.enqueue(second);

        assertTrue(queue.removeSpecific(first));
        assertEquals(2, queue.getSize());
        assertSame(sameId, queue.dequeue());
        assertSame(second, queue.dequeue());
    }

    // findStudent()

    @Test
    public void testFindStudentOnEmptyQueue() {
        assertNull(queue.findStudent(first));
    }

    @Test
    public void testFindStudentAtAllPositions() {
        queue.enqueue(first);
        queue.enqueue(second);
        queue.enqueue(third);

        assertSame(first, queue.findStudent(first));
        assertSame(second, queue.findStudent(second));
        assertSame(third, queue.findStudent(third));
        assertEquals(3, queue.getSize());
        assertSame(first, queue.peek());
    }

    @Test
    public void testFindMissingStudent() {
        queue.enqueue(first);

        assertNull(queue.findStudent(second));
    }

    @Test
    public void testFindNullStudent() {
        queue.enqueue(first);

        assertNull(queue.findStudent(null));
    }

    @Test
    public void testFindReturnsStoredStudentWithMatchingId() {
        queue.enqueue(first);
        Student sameId = makeStudent(1);

        assertSame(first, queue.findStudent(sameId));
    }


}
