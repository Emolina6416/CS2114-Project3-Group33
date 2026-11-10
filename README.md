# CS2114-Project3-Group33

# Course Waitlist and Registration

A Model-View-Controller Java Console application for student course registration and queue based waitlist.

## Project Design and Architecture: 

Model: Features a First-In, First-Out (FIFO) Queue structure that backs the 'Course' waitlist logic alongside other parts such as the 'CourseCatalog' and 'Student'.

View: 'ConsoleView' serves as the presentational layer, displaying output and reading user input. 

Controller: 'RegistrationController' is where bad input is handled, the state is managed, course queue interaction is managed, and the menu flow navigation is defined. This is the logic that controls the whole system

# Reflection

What changed from your specification? Classes, method signatures, where validation lives, test cases — what did the spec say, what did you build instead, and why?

* Some changes that were made from the specification to this prototype is a helper method in RegistrationController. The method is called List<Course> getEnrolledCourses; this made it easier to get the classes the user was currently enrolled in without looping every time they were needed. It allowed for a cleaner implementation. This change came from observations in the coding process where some methods looked long and messy.
  
* Node became an inner class of Queue because it only needed to be implemented with linked list. Removed parameters from addCredits() and removeCredits() since every class is worth 3 credits. Added Queue.contains(student) and Course.isEnrolled(Student) to check enrollment by student ID. Shortened field names (currentCredits, classYear, and studentData) for efficiency. Test cases for Queue remained consistent.
  
* Another change made was the addition of a Main class. This method could have been added in RegistrationController, but it was much cleaner to create a separate class and add the main method there to actually run our system.

Did the model really stand alone? Was there anything you had to move across the model/view line to make it run, or anything in the model that still knows about the view?

* Yes, the model does not reference ConsoleView or use input/output. The model only returns values or throws exceptions, and the controller then decides what will be displayed by the view. There was nothing that needed to be moved across the model/view line in order to make our system run. The model and view don't know of each other's existence. Only the controller knows of the Model and View. The model and view are separate parts of the system, with the controller connecting the logic together. 

What happened to your unknowns? Of the things you listed in your scope as not knowing how to do, which did you figure out, and which are still open?

* So far, what we have figured out with our unknowns is how we will organize the menu and how to randomly generate the number of students for all courses. The last unknown that will be addressed in the upcoming Prototype 2 is how the information will be displayed to admins when viewing information. 

What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing.

* The basic MVP features work for students; the part of the MVP that does not work yet is the administrator portal to view specific queue information. Tests that are still missing involve some logical tests for RegistrationController and smaller tests for classes in the Model. 

# Next Steps

What is left to finish the MVP? The remaining features and fixes, in the order you will do them.

* The only part of the MVP that is missing is the administrator portal which is the first thing that will be done. This includes an additional input that the login screen can take, a separate admin menu, and possible commands that they will need to do.
* Some additional changes that could be made is some cleaner splitting of the RegistrationController, like the processMenuOptions, because it's a little messy at the moment. 

Your change feature. Deliverable 4 requires you to add one non-trivial feature that was not in your MVP. Name it, then predict which classes it will touch (model, view, controller) and which ones your design says should not need to change. You will compare this prediction to what actually happened.

* The change feature that is not in our MVP is a force add option for the students. This feature would be a way for the students to bypass the queue line and simply add the class they need to add to their schedule.
* This force add option will touch the model and controller. The Controller will handle when the force add option is triggered in the processMenuOptions(), and the Model will define the force add option as a method forceAdd(). This addition should not touch or change the View. 

Which stretch goals will you attempt? The ones from your scope you are committing to, and the ones you are dropping.

* The stretch goal that we are committing to is the stretch goal. We will not drop the future scheduling stretch goal yet as we will try to attempt it in these upcoming deliverables but if it becomes too messy we might need to end up dropping it. 

What new tests will you need? The methods and bad-input cases the added work must cover.
Who does what? Which team member owns each remaining item.

* We are making the forceAdd option a randomized variable so if a student triggers it, it should randomly accept or deny their request. We need to test it to make sure that when it triggers, it accepts/denies the student as we expect it to.
* Team Member: Andrew - In charge of additional tests, and forceAdd feature 
* Team Member: Emerson - In charge of helping with tests, additional menu options for forceAdd and admin portal features (such as making a secret key for the login that triggers the admin menu)
* Team Member: Suhana - In charge of helping with tests and additional menu design for the admin portal


# Attribution and GenAI note 

Model: Andrew
View: Suhana
Controller: Emerson

| Section | Member Work | GenAI note |   
| :--- | :--- | :--- |
| ConsoleView | Suhana: Drafted and Revised | --- |
| Course | Andrew: Drafted and Revised | --- |
| CourseCatalog | Andrew: Drafted and Revised | --- |
| Queue | Andrew: Drafted and Revised | --- |
| Student | Andrew: Drafted and Revised | --- |
| Test | Andrew: Drafted and revised | --- |
| RegistrationController | Emerson: Drafted, revised, and reviewed with AI | AI used in checking over work, feedback, and additions that were checked before use |
| Main | Emerson: Drafted, and revised | No GenAI used in this section |




