package courseRequestSim;
import java.util.NoSuchElementException;
public class Queue
{
    private static class Node{
        Student student;
        Node next;
        Node(Student student){
            this.student = student;
        }
    }

    //~ Fields ................................................................
    private Node head;
    private Node tail;
    private int size;
    //~ Constructors ..........................................................

    //~Public  Methods ........................................................
    public boolean isEmpty(){
        return head == null;
    }

    public int getSize(){
        return size;
    }

    public void enqueue(Student student){
        if (student == null){
            throw new IllegalArgumentException("Student cannot be null.");
        }
        Node newNode = new Node(student);

        if (isEmpty()){
            head = newNode;
        }
        else{
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    public Student dequeue(){
        if (isEmpty()){
            throw new NoSuchElementException("Queue is empty.");
        }
        Student student = head.student;
        head = head.next;
        size--;
        if (head == null){
            tail = null;
        }
        return student;
    }

    public Student peek(){
        if (isEmpty()){
            return null;
        }
        else{
            return head.student;
        }
    }

    public boolean contains(Student student){
        if (student == null){
            return false;
        }
        Node curr = head;
        while (curr != null){
            if (curr.student.getID() == student.getID()){
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public boolean removeSpecific(Student student){
        if (student == null){
            return false;
        }
        Node curr = head;
        Node prev = null;
        while (curr != null){
            if (curr.student.getID() == student.getID()){
                if (prev == null){
                    head = curr.next;
                }
                else{
                    prev.next = curr.next;
                }
                if (curr == tail){
                tail = prev;
            }
            size--;
            return true;
        }
        prev = curr;
        curr = curr.next;
    }
        return false;
    }
    //Helper for removeStudent()
    public Student findStudent(Student student){
        if (student == null){
            return null;
        }
        Node curr = head;
        while (curr != null){
            if (curr.student.getID() == student.getID()){
                return curr.student;
            }
            curr = curr.next;
        }
        return null;
    }

}
