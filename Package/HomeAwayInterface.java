/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */
package Package;

import Package.Exceptions.*;
import Package.Services.Services;
import Package.Students.Students;
import dataStructures.*;

/**
 * Interface representing the HomeAway system.
 *
 * The system manages Areas, Services (Eating, Lodging, Leisure), and Students,
 * allowing the creation, management, and evaluation of services and student mobility.
 */
public interface HomeAwayInterface {

    /**
     * Creates a new geographical area with specified boundaries.
     * Time Complexity: O(1)
     * @param name the name of the area
     * @param minLongitude minimum longitude
     * @param minLatitude minimum latitude
     * @param maxLongitude maximum longitude
     * @param maxLatitude maximum latitude
     * @throws AreaAlreadyExists if an area with the same name already exists
     * @throws InvalidBounds if the coordinates are invalid
     */
    void createArea(String name, long minLongitude, long minLatitude, long maxLongitude, long maxLatitude)
            throws AreaAlreadyExists, InvalidBounds;

    /**
     * Saves the current area to persistent storage.
     * Time Complexity: O(n), where n is the serialized area size
     * @return confirmation message of save
     * @throws NoBoundsInTheSystem if no area is defined
     */
    String saveArea() throws NoBoundsInTheSystem;

    /**
     * Loads an area from persistent storage.
     * Time Complexity: O(n), where n is the serialized area size
     * @param name the name of the area to load
     * @return confirmation message of load
     * @throws NoBoundsInTheSystem if no area is defined
     * @throws AreaDoesNotExist if area file does not exist
     */
    String loadArea(String name) throws AreaDoesNotExist, NoBoundsInTheSystem;

    // ===== Services =====

    /**
     * Creates a new eating service.
     * Time Complexity: O(1) insertion,O(n) if list of services needs to be resized
     * @throws InvalidPrice if price < 0
     * @throws InvalidLocationArea if coordinates are outside current area
     * @throws InvalidBounds if area boundaries are invalid
     * @throws InvalidCapacity if capacity < 0
     * @throws ServiceAlreadyExists if service with same name exists
     */
    void createEating(long latitude, long longitude, int price, int capacity, String name)
            throws InvalidPrice, InvalidLocationArea, InvalidBounds, InvalidCapacity, ServiceAlreadyExists;

    /**
     * Creates a new lodging service.
     * Time Complexity: O(1) insertion,O(n) if list of services needs to be resized
     * @throws InvalidPrice if price < 0
     * @throws InvalidLocationArea if coordinates are outside current area
     * @throws InvalidBounds if area boundaries are invalid
     * @throws InvalidCapacity if capacity < 0
     * @throws ServiceAlreadyExists if service with same name exists
     */
    void createLodging(long latitude, long longitude, int price, int capacity, String name)
            throws InvalidPrice, InvalidLocationArea, InvalidBounds, InvalidCapacity, ServiceAlreadyExists;

    /**
     * Creates a new leisure service.
     * Time Complexity: O(1) insertion,O(n) if list of services needs to be resized
     * @throws InvalidPrice if price < 0
     * @throws InvalidLocationArea if coordinates are outside current area
     * @throws InvalidBounds if area boundaries are invalid
     * @throws InvalidDiscount if discount invalid
     * @throws ServiceAlreadyExists if service with same name exists
     */
    void createLeisure(long latitude, long longitude, int price, int discount, String name)
            throws InvalidPrice, InvalidLocationArea, InvalidBounds, InvalidDiscount, ServiceAlreadyExists;

    // ===== Students =====

    /**
     * Creates a new student assigned to a lodging.
     * Time Complexity: O(1)
     * @param type student type (regular, erasmus)
     * @param name student name
     * @param country student country
     * @param lodging name of lodging
     * @throws InvalidStudentType if type invalid
     * @throws LodgingDoesNotExist if lodging does not exist
     * @throws ServiceIsFull if lodging full
     * @throws StudentAlreadyExists if student exists
     */
    void createStudent(String type, String name, String country, String lodging)
            throws InvalidStudentType, LodgingDoesNotExist, ServiceIsFull, StudentAlreadyExists, InvalidLocation, StudentNotFound, NoServicesYet;

    /**
     * Removes a student from the system.
     * Time Complexity: O(1)
     * @param name student name
     * @return confirmation message
     * @throws StudentNotFound if student not found
     */
    String leave(String name) throws StudentNotFound;

    /**
     * Returns an iterator of all students.
     * Time Complexity: O(1) to return iterator
     * @throws NoStudentsYet if no students exist
     * @throws NoBoundsInTheSystem if area not defined
     */
    Iterator<Students> listAllStudents() throws NoStudentsYet, NoBoundsInTheSystem;

    /**
     * Returns an iterator of students from a specific country.
     * Time Complexity: O(1) lookup + O(k) iteration, k = students from country
     * @param country country name
     * @return iterator over students
     * @throws NoStudentsFromCountry if no students from country exist
     */
    Iterator<Students> listStudentsByCountry(String country) throws NoStudentsFromCountry;

    /**
     * Returns a two-way iterator over students at a location, ordered by arrival.
     * Time Complexity: O(1)
     * @param place location/service name
     * @return two-way iterator
     * @throws ServiceNotExists if service not found
     * @throws InvalidLocation if location invalid
     * @throws Empty if no students at location
     */
    TwoWayIterator<Students> listUsersByOrder(String place)
            throws ServiceNotExists, InvalidLocation, Empty;

    /**
     * Sends a student to a location.
     * Time Complexity: O(1)
     * @param name student name
     * @param location location/service name
     * @return true if successful
     * @throws StudentNotFound if student not found
     * @throws InvalidLocation if location invalid
     * @throws AlreadyThere if already at location
     */
    boolean go(String name, String location)
            throws StudentNotFound, InvalidLocation, AlreadyThere, ServiceIsFull;

    /**
     * Moves a student to a different lodging.
     * Time Complexity: O(1)
     * @param name student name
     * @param location new lodging name
     * @return confirmation message
     * @throws StudentNotFound if student not found
     * @throws InvalidLocation if location invalid
     * @throws ServiceIsFull if new lodging full
     * @throws CantMove if movement not allowed
     * @throws LodgingDoesNotExist if lodging does not exist
     */
    String move(String name, String location)
            throws StudentNotFound, InvalidLocation, ServiceIsFull, CantMove, LodgingDoesNotExist;

    /**
     * Returns the current service/location of a student.
     * Time Complexity: O(1)
     * @param name student name
     * @return current service
     * @throws StudentNotFound if student not found
     */
    Services where(String name) throws StudentNotFound;

    /**
     * Returns an iterator over all services visited by a student.
     * Time Complexity: O(1)
     * @param name student name
     * @return iterator over visited services
     * @throws StudentNotFound if not found
     * @throws InvalidStudentType if type does not track visits
     * @throws HasNotVisitedLocations if no visits
     */
    Iterator<Services> getVisited(String name)
            throws StudentNotFound, InvalidStudentType, HasNotVisitedLocations;

    /**
     * Adds an evaluation/review to a service.
     * Time Complexity: O(1)
     * @param star rating (1-5)
     * @param nameService service name
     * @param description review description
     * @throws InvalidStar if star out of bounds
     * @throws ServiceNotExists if service not found
     */
    void evaluate(int star, String nameService, String description)
            throws InvalidStar, ServiceNotExists;
}
