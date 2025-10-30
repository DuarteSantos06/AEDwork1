package Package;
import Package.Exceptions.*;
import Package.Services.Services;
import Package.Students.Students;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;
import Package.Exceptions.Expensive;

/**
 * Interface representing the main home away from home system.
 * This system manages areas, services (eating, lodging, leisure), and students.
 * It provides functionality for student mobility, service management, and tracking.
 */
public interface HomeAwayInterface {
    
    /**
     * Creates a new geographical area with specified boundaries.
     * 
     * @param name the name of the area
     * @param minLongitude the minimum (left) longitude boundary
     * @param minLatitude the minimum (bottom) latitude boundary
     * @param maxLongitude the maximum (right) longitude boundary
     * @param maxLatitude the maximum (top) latitude boundary
     * @throws AreaAlreadyExists if an area with the same name already exists
     * @throws InvalidLocation if the location coordinates are invalid
     */
    void createArea(String name, long minLongitude, long minLatitude, long maxLongitude, long maxLatitude)
            throws AreaAlreadyExists, InvalidLocation;
    
    /**
     * Saves the current area to persistent storage.
     * 
     * @return confirmation message of the save operation
     * @throws NoBoundsInTheSystem if no area boundaries are defined in the system
     */
    String saveArea() throws NoBoundsInTheSystem;
    
    /**
     * Loads an area from persistent storage.
     * 
     * @param name the name of the area to load
     * @return confirmation message of the load operation
     * @throws NoBoundsInTheSystem if no area boundaries are defined in the system
     */
    String loadArea(String name) throws NoBoundsInTheSystem;
    
    // Services
    
    /**
     * Creates a new eating service with specified location, price, and capacity.
     * 
     * @param latitude the latitude coordinate of the service
     * @param longitude the longitude coordinate of the service
     * @param price the price per visit
     * @param capacity the maximum capacity of the service
     * @param name the name of the eating service
     * @throws InvalidPrice if the price is negative or invalid
     * @throws InvalidLocation if the location coordinates are invalid
     * @throws InvalidCapacity if the capacity is negative or invalid
     * @throws ServiceAlreadyExists if a service with the same name already exists
     */
    void createEating(long latitude, long longitude, int price, int capacity, String name)
            throws InvalidPrice, InvalidLocation, InvalidCapacity, ServiceAlreadyExists;
    
    /**
     * Creates a new lodging service with specified location, price, and capacity.
     * 
     * @param latitude the latitude coordinate of the service
     * @param longitude the longitude coordinate of the service
     * @param price the price per stay
     * @param capacity the maximum capacity of the lodging
     * @param name the name of the lodging service
     * @throws InvalidPrice if the price is negative or invalid
     * @throws InvalidLocation if the location coordinates are invalid
     * @throws InvalidCapacity if the capacity is negative or invalid
     * @throws ServiceAlreadyExists if a service with the same name already exists
     */
    void createLodging(long latitude, long longitude, int price, int capacity, String name)
            throws InvalidPrice, InvalidLocation, InvalidCapacity, ServiceAlreadyExists;
    
    /**
     * Creates a new leisure service with specified location, price, and discount.
     * 
     * @param latitude the latitude coordinate of the service
     * @param longitude the longitude coordinate of the service
     * @param price the price per visit
     * @param discount the discount percentage for eligible students
     * @param name the name of the leisure service
     * @throws InvalidPrice if the price is negative or invalid
     * @throws InvalidLocation if the location coordinates are invalid
     * @throws InvalidDiscount if the discount percentage is invalid
     * @throws ServiceAlreadyExists if a service with the same name already exists
     */
    void createLeisure(long latitude, long longitude, int price, int discount, String name)
            throws InvalidPrice, InvalidLocation, InvalidDiscount, ServiceAlreadyExists;
    
    // Students
    
    /**
     * Creates a new student with specified type, name, country, and lodging.
     * 
     * @param type the type of student (e.g., "regular" or "erasmus")
     * @param name the name of the student
     * @param country the country of origin
     * @param lodging the name of the lodging where the student will stay
     * @throws InvalidStudentType if the student type is not recognized
     * @throws LodgingNotExists if the specified lodging does not exist
     * @throws LodgingIsFull if the lodging has reached maximum capacity
     * @throws StudentAlreadyExists if a student with the same name already exists
     */
    void createStudent(String type, String name, String country, String lodging)
            throws InvalidStudentType, LodgingNotExists, LodgingIsFull, StudentAlreadyExists;
    
    /**
     * Removes a student from the system.
     * 
     * @param name the name of the student to remove
     * @return confirmation message of the leave operation
     * @throws StudentNotFound if the student does not exist
     */
    String leave(String name) throws StudentNotFound;
    
    /**
     * Returns an iterator over all students in the system.
     * 
     * @return an iterator over all students
     * @throws NoToList if there are no students to list
     * @throws NoBoundsInTheSystem if no area boundaries are defined
     */
    Iterator<Students> listAllStudents() throws NoToList, NoBoundsInTheSystem;
    
    /**
     * Returns an iterator over students from a specific country.
     * 
     * @param country the country to filter by
     * @return an iterator over students from the specified country
     * @throws NoToList if there are no students from the specified country
     */
    Iterator<Students> listStudentsByCountry(String country) throws NoToList;
    
    /**
     * Returns a two-way iterator over users at a specific location, ordered by arrival.
     * 
     * @param place the name of the place/service
     * @return a two-way iterator over users at the location
     * @throws ServiceNotExists if the service does not exist
     * @throws InvalidLocation if the location is invalid
     * @throws Empty if no users are at the location
     */
    TwoWayIterator<Students> listUsersByOrder(String place)
            throws ServiceNotExists, InvalidLocation, Empty;
    
    /**
     * Sends a student to a specific location.
     * 
     * @param name the name of the student
     * @param location the name of the location/service
     * @return true if the student successfully went to the location
     * @throws StudentNotFound if the student does not exist
     * @throws InvalidLocation if the location is invalid
     * @throws AlreadyThere if the student is already at that location
     * @throws Expensive if the student cannot afford the service
     */
    boolean go(String name, String location) 
            throws StudentNotFound, InvalidLocation, AlreadyThere, Expensive;
    
    /**
     * Moves a student to a different lodging.
     * 
     * @param name the name of the student
     * @param location the name of the new lodging
     * @return confirmation message of the move operation
     * @throws StudentNotFound if the student does not exist
     * @throws InvalidLocation if the location is invalid
     * @throws LodgingIsFull if the new lodging is at capacity
     * @throws CantMove if the student cannot move to the specified lodging
     */
    String move(String name, String location) 
            throws StudentNotFound, InvalidLocation, LodgingIsFull, CantMove;
    
    /**
     * Returns the current location of a student.
     * 
     * @param name the name of the student
     * @return the service where the student is currently located
     * @throws StudentNotFound if the student does not exist
     */
    Services where(String name) throws StudentNotFound;
    
    /**
     * Returns an iterator over all services visited by a student.
     * Only applicable for students that keep track of visited locations.
     * 
     * @param name the name of the student
     * @return an iterator over visited services
     * @throws StudentNotFound if the student does not exist
     * @throws InvalidStudentType if the student type does not track visits
     * @throws NoToList if the student has not visited any services
     */
    Iterator<Services> getVisited(String name) 
            throws StudentNotFound, InvalidStudentType, NoToList;
    
    /**
     * Adds an evaluation/review for a service.
     * 
     * @param star the star rating (typically 1-5)
     * @param nameService the name of the service being evaluated
     * @param description the review description
     * @throws InvalidStar if the star rating is out of valid range
     * @throws ServiceNotExists if the service does not exist
     */
    void evaluate(int star, String nameService, String description) 
            throws InvalidStar, ServiceNotExists;
}
