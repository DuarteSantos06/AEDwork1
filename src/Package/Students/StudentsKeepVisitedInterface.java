package Package.Students;
import Package.Services.Services;
import dataStructures.ListInArray;

/**
 * Interface representing a student that keeps track of visited services.
 * This extends the basic StudentsInterface to add functionality for recording
 * and retrieving the history of services that this student has visited.
 * Only certain types of students (e.g., erasmus students) maintain visit history.
 */
public interface StudentsKeepVisitedInterface extends StudentsInterface {
    
    /**
     * Adds a service to the list of visited services for this student.
     * This method should be called whenever the student visits a new service.
     * 
     * @param service the service that was visited
     */
    void addVisited(Services service);
    
    /**
     * Returns a list of all services that this student has visited.
     * The list maintains the order in which services were visited.
     * 
     * @return a list containing all visited services in chronological order
     */
    ListInArray<Services> getVisited();
}
