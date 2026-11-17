# CS2114-Project3-Group33

# Course Waitlist and Registration

A Model-View-Controller Java Console application for student course registration and queue based waitlist.

# Project Design and Architecture: 

Model: Features a First-In, First-Out (FIFO) Queue structure that backs the 'Course' waitlist logic alongside other parts such as the 'CourseCatalog' and 'Student'.

View: 'ConsoleView' serves as the presentational layer, displaying output and reading user input. 

Controller: 'RegistrationController' is where bad input is handled, the state is managed, course queue interaction is managed, and the menu flow navigation is defined. This is the logic that controls the whole system

# How to run

### Via IDE (Eclipse / VSCode / etc.)
* Open the project in your IDE
* Locate and open 'src/Main.java'
* Right-click 'Main.java' -> select Run as -> select Java Application
* Follow console prompts to log in, view catalog options, add/drop courses, and exit

# Reflection

How did your design absorb the change? List the classes your feature actually touched. Compare that with the prediction in your D3 Next Steps: what matched, what surprised you?

* The force add feature had its own class created in Course called forceAddStudent; this change affected executeAddClass and, in turn, changed how the menu option B worked in processMenuOptions in the RegistrationController class. The predictions made in the Next Steps section in the last submission were accurate and hit the different parts of the new feature. 

Did the change cross the model/view line? Did the view have to learn anything new about the model? If a change rippled through layers it shouldn't have, where and why?

* Since there needed to be a method defined in model it affected that section of the code but beyond that everything was defined in the controller. No changes rippled through the layers there was one small change in model but besides that it was all in controller. Nothing was added to the view. 

What changed from your D3 plan? Features, signatures, validation, or tests that differ from what you said you'd do, and why.

* The admin menu was added from the D3 plan. This includes getters for students in the Queue class and Course class that help get specific information from the queue lines. These methods are simply called public List<Student> getStudentsInOrder() and public List<Student> getQueueStudents(). This gets information from the classes.
* Handle login was also overhauled. Further testing led to observations on student login mix ups our program did not take in to account a student logging off and then another going on and using the same ID. This same ID led to that 2nd student being seen as the first student and any actions performed in addition of classes could lead to the program sending a "Already in class option". This was eased by redoing the handleLogin method to authenticate students. A studentProcessed() private field was added at the top and an array of these students was initialized in order to track the students. Private helper methods such as private Student authenticateStudent(String name), private Student findStudentById(int id), and private int promptForClassYear() were used to help this process.
* Specifics were added to the executeAddClass() method, before it was general error messaged of "full class or exceeds credits" now it specifies which one it is.
* Changes to how the B option is presented in processMenuOptions were made. The classes now display the number of students in the class

What could make the next feature cheaper? 

* Adding a semester field to the course class could make the next feature cheaper, as this would help track every semester instead of tracking a flat allCourses list. Attempting to add this feature now with Fall, Winter, Spring, and Summer could cause changes to ripple throughout the application which is expensive. 

What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing.

* All MVP features were added, the change feature "forceAdd" was successfully implemented with a 30% acceptance and 70% denial rate. Something that we could still add could be a more visually appealing menu for people being processed. Some tests for controller are still missing. 


# Next Steps

What is left for the final build? Remaining features and fixes, in order.

* First thing that can be done is a last revision of the code, any simplifications made or helper methods added should be changed then
* Second thing would be a more visually appealing menu display for the application. It is boring right now and should have more visuals as described in the scope
* Third is the final test implementations 

Which stretch goals are you committing to, and which are you dropping?

* The force add on is something that we have committed to and something that has thus far been successfully implemented. The future scheduling is a stretch goal that we are dropping as the complexity of adding this feature would have us overhauling some already implemented code and data storage. 

What will your 3-minute demo show? The path you'll walk through, including the new feature.

* The 3-minute demo will first demonstrate the login feature by creating a student name, ID, and class year with bad inputs shown as it continues. The person doing the demo will then go through adding classes, dropping classes, viewing the schedule, and forceAdd.
* After this part, the Demo will transition to logging out and logging in using a different person; this is where the duplicate ID bad input can be shown.
* After this, the person doing the demo can continue to add classes using this user before logging out once again and logging back in with the first person to demonstrate the program remembering the first user; a "Welcome Back" Message should be displayed then.
* The last part of the demo should include once again logging out of this student account and going into the admin menu where the person demoing can go in to the different admin options demonstrating the data that can be seen through this menu.

Who does what? Which team member owns each remaining item.

* Emerson: Menu revisions to be more visually appealing
* Suhana: Additional testing, Menu feedback
* Andrew: Additional testing, final model walk through 


# Attribution and GenAI note 

Model: Andrew
View: Suhana
Controller: Emerson

| Section | Member Work | GenAI note |   
| :--- | :--- | :--- |
| ConsoleView | Suhana: Drafted and Revised | No GenAI used in this section |
| Course | Andrew: Drafted and Revised | Used GenAI to draft and revise |
| CourseCatalog | Andrew: Drafted and Revised | Used GenAI to draft and revise |
| Queue | Andrew: Drafted and Revised | Used GenAI to draft and revise |
| Student | Andrew: Drafted and Revised | Used GenAI to draft and revise |
| QueueTest | Andrew: Drafted and revised | Used GenAI to draft and revise |
| RegistrationController | Emerson: Drafted, revised, and reviewed with AI | AI used in checking over work, feedback, and additions that were checked before use |
| Main | Emerson: Drafted, and revised | No GenAI used in this section |




