package Package.Students;
import Package.Services.Eating;
import Package.Services.Lodging;
import Package.Services.Services;

/**
 * Interface representing a student in the home away from home system.
 * Students have a name, country of origin, type (e.g., regular, erasmus),
 * and can move between different services and locations.
 */
public interface StudentsInterface {
    
    /**
     * Returns the name of the student.
     * 
     * @return the student's name
     */
    String getName();
    
    /**
     * Returns the country of origin of the student.
     * 
     * @return the student's country
     */
    String getCountry();
    
    /**
     * Returns the type of student (e.g., "bookish", "outgoing", "thrifty").
     * 
     * @return the student type
     */
    String getType();
    
    /**
     * Returns the name of the student's current location.
     * 
     * @return the name of the current location/service
     */
    String getNameLocation();
    
    /**
     * Returns the service where the student is currently located.
     * 
     * @return the current location service
     */
    Services getLocation();
    
    /**
     * Returns the lodging where the student is staying.
     * This is the student's home base in the system.
     * 
     * @return the student's home lodging
     */
    Lodging getHome();
    
    /**
     * Returns the cheapest eating service available to this student.
     * The cheapest option may vary depending on the student's type,
     * location, and available discounts.
     * 
     * @return the cheapest available eating service
     */
    Eating getCheapestEating();
    
    /**
     * Moves the student to a specified service location.
     * 
     * @param location the service where the student will go
     */
    void go(Services location);
    
    /**
     * Changes the student's home lodging to a new location.
     * 
     * @param home the new lodging to become the student's home
     */
    void move(Lodging home);
    
    /**
     * Removes the student from the system.
     * This typically includes removing them from their current location
     * and lodging before they leave the program.
     */
    void leave();
}
