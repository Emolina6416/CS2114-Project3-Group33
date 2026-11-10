# CS2114-Project3-Group33


What changed from your specification? Classes, method signatures, where validation lives, test cases — what did the spec say, what did you build instead, and why?
Implementation of model classes were kept consistent with the specification. Node became an inner class of Queue because it only needed to be implemented with linked list. Removed paramter from addCredits() and removeCredits() since every class is worth 3 credits. Added Queue.contains(student) and Course.isEnrolled(Student) to check enrollment by student ID. Shortened field names (currentCredits, classYear, and studentData) for efficiency. Test cases for Queue remained consistent.
Did the model really stand alone? Was there anything you had to move across the model/view line to make it run, or anything in the model that still knows about the view?
Yes, the model does not reference ConsoleView or use input/output. The model only returns values or throws exceptions, which the controller then decides what will be displayed by the view.
What happened to your unknowns? Of the things you listed in your scope as not knowing how to do, which did you figure out, and which are still open?
