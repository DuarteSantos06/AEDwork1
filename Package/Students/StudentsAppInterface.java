/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package.Students;

import Package.Area;
import Package.Exceptions.*;
import Package.Services.Services;
import Package.Services.Lodging;
import dataStructures.Iterator;

/**
 * Interface that defines all operations related to students,
 * including creation, movement, listing and service queries.
 */
public interface StudentsAppInterface {

    /**
     * Creates a new student in the system.
     *
     * Time Complexity:
     * - O(1) to check if the student already exists (hash lookup)
     * - O(1) to insert the student into the area
     * - Overall: O(1)
     *
     * @param type    the student type (THRIFTY, OUTGOING, BOOKISH)
     * @param name    the student's name
     * @param country the student's country
     * @param home    the student's initial lodging
     *
     * @throws InvalidStudentType      if the student type is invalid
     * @throws StudentAlreadyExists    if the student already exists
     * @throws ServiceIsFull           if the lodging is full
     * @throws StudentNotFound         if the area has not been initialized
     */
    void createStudent(String type, String name, String country, Lodging home)
            throws InvalidStudentType, StudentAlreadyExists, ServiceIsFull, StudentNotFound;


    /**
     * Moves a student to a new service.
     *
     * Time Complexity:
     * - O(1) to find the student
     * - O(1) to perform the movement
     * - Overall: O(1)
     *
     * @param name     the student's name
     * @param service  the target service
     * @param location the target location name
     *
     * @return the name of the service where the student ended up
     *
     * @throws StudentNotFound if the student does not exist
     * @throws InvalidLocation if the location is invalid
     * @throws ServiceIsFull   if the service is full
     * @throws CantMove        if the student cannot move
     */
    String move(String name, Services service, String location)
            throws StudentNotFound, InvalidLocation, ServiceIsFull, CantMove;


    /**
     * Retrieves all services visited by a given student.
     *
     * Time Complexity:
     * - O(1) to find the student
     * - Overall: O(1)
     *
     * @param name the student's name
     *
     * @return an iterator over visited services
     *
     * @throws StudentNotFound                if the student does not exist
     * @throws ThriftyDoesNotStoreServices    if the student is Thrifty
     * @throws HasNotVisitedLocations         if the student has not visited any services
     */
    Iterator<Services> getVisited(String name)
            throws StudentNotFound, ThriftyDoesNotStoreServices, HasNotVisitedLocations;


    /**
     * Retrieves services of a given type and star rating, ordered by proximity.
     *
     * Time Complexity:
     * - O(1)   to find the student
     * - O(k)   to iterate over services with the given star value
     * - O(k)   to filter by type
     * - O(k)   to compute Manhattan distance
     * - O(k²)  final bubble-sort based on last-evaluated timestamp
     * - Overall: O(k²)
     *
     * @param type service type
     * @param star average star rating
     * @param name the student's name
     *
     * @return iterator over closest matching services
     *
     * @throws StudentNotFound        if the student does not exist
     * @throws InvalidType            if the type is invalid
     * @throws ServiceNotExists       if no matching service exists
     * @throws NoServicesInTheSystem  if the system has no services
     */
    Iterator<Services> getRanked(String type, int star, String name)
            throws StudentNotFound, InvalidType, ServiceNotExists, NoServicesInTheSystem;


    /**
     * Moves a student using the "go" rule.
     *
     * Time Complexity:
     * - O(1) to find the student
     * - O(1) to check rules and perform movement
     * - Overall: O(1)
     *
     * @param name     the student's name
     * @param location the target location
     * @param service  the service to visit
     *
     * @return true if special Thrifty rule applies, false otherwise
     *
     * @throws StudentNotFound if the student does not exist
     * @throws InvalidLocation if the location is invalid
     * @throws AlreadyThere    if the student is already there
     * @throws ServiceIsFull   if the service is full
     */
    boolean go(String name, String location, Services service)
            throws StudentNotFound, InvalidLocation, AlreadyThere, ServiceIsFull;


    /**
     * Lists all students in the system.
     *
     * Time Complexity:
     * - O(1)
     *
     * @return iterator over all students
     *
     * @throws NoStudentsYet        if no students exist
     * @throws NoBoundsInTheSystem  if no area exists
     */
    Iterator<Students> listAllStudents()
            throws NoStudentsYet, NoBoundsInTheSystem;


    /**
     * Removes a student from the system.
     *
     * Time Complexity:
     * - O(1) to find the student
     * - O(1) to remove the student from area
     *
     * @param name the student's name
     *
     * @return the name of the removed student
     *
     * @throws StudentNotFound if the student does not exist
     */
    String leave(String name) throws StudentNotFound;


    /**
     * Sets the current area managed by the system.
     *
     * Time Complexity: O(1)
     *
     * @param currentArea the area to assign
     */
    void setCurrentArea(Area currentArea);


    /**
     * Finds a student in the current area.
     *
     * Time Complexity:
     * - O(1) hash
     *
     * @param name the student's name
     *
     * @return the matching student
     *
     * @throws StudentNotFound if the student does not exist
     */
    Students findStudent(String name) throws StudentNotFound;
}
