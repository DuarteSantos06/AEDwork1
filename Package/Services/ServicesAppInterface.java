/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package.Services;

import Package.Area;
import Package.Exceptions.*;
import Package.Students.Students;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

/**
 * Interface that defines all operations related to services:
 * creation, search, evaluation, ranking and listing.
 */
public interface ServicesAppInterface {

    /**
     * Creates a new lodging service.
     *
     * Time Complexity:
     * - O(1) validate bounds
     * - O(1) validate price/capacity
     * - O(1) check if service exists
     * - O(1) insert into area
     * - Overall: O(1)
     *
     * @throws InvalidLocationArea if the location is outside bounds
     * @throws InvalidPrice if the price is invalid
     * @throws InvalidBounds if the area is not set
     * @throws InvalidCapacity if the capacity is invalid
     * @throws ServiceAlreadyExists if a service with that name already exists
     */
    void createLodging(long latitude, long longitude, int price, int capacity, String name)
            throws InvalidLocationArea, InvalidPrice, InvalidBounds, InvalidCapacity, ServiceAlreadyExists;


    /**
     * Creates a new eating service.
     * - O(1) validate bounds
     * - O(1) validate price/capacity
     * - O(1) check if service exists
     * - O(1) insert into area
     * Time Complexity: O(1)
     */
    void createEating(long latitude, long longitude, int price, int capacity, String name)
            throws InvalidLocationArea, InvalidPrice, InvalidBounds, InvalidCapacity, ServiceAlreadyExists;


    /**
     * Creates a new leisure service.
     * - O(1) validate bounds
     * - O(1) validate price/capacity
     * - O(1) check if service exists
     * - O(1) insert into area
     * Time Complexity: O(1)
     */
    void createLeisure(long latitude, long longitude, int price, int discount, String name)
            throws InvalidLocationArea, InvalidPrice, InvalidBounds, InvalidDiscount, ServiceAlreadyExists;


    /**
     * Finds a service by its name.
     * Time Complexity:
     * - O(1)
     *
     * @param name service name
     * @return the service or null if not found
     */
    Services findService(String name);


    /**
     * Finds the best service of a given type for a student.
     * Thrifty → cheapest
     * Others → highest ranked
     *
     * Time Complexity:
     * - O(n) scanning all services of that type (via FilterIterator)
     *
     * @param type service type
     * @param s student
     *
     * @return the selected service
     *
     * @throws NoServicesYet if no services exist in the system
     */
    Services find(String type, Students s) throws NoServicesYet;


    /**
     * Evaluates a service.
     * Time Complexity:
     * - O(1) find service
     * - O(1) evaluate (direct update)
     *
     * @param star star value (1–5)
     * @param nameService service name
     * @param description review text
     * @param evaluateCounter timestamp/counter
     *
     * @throws InvalidEvaluation if star is invalid
     * @throws ServiceNotExists if service does not exist
     */
    void evaluate(int star, String nameService, String description, int evaluateCounter)
            throws InvalidEvaluation, ServiceNotExists;


    /**
     * Returns all services ordered by average stars (5 → 1).
     * Time Complexity:
     * - O(1) iterating all services
     *
     * @return iterator of services sorted by ranking
     *
     * @throws NoServicesInTheSystem if no services exist
     */
    Iterator<Services> getRanking() throws NoServicesInTheSystem;


    /**
     * Finds a lodging by name.
     * Time Complexity:
     * - O(1)
     *
     * @throws LodgingDoesNotExist if name exists but is not a lodging
     * @throws NoServicesYet if system has no services
     */
    Lodging findLodging(String name) throws LodgingDoesNotExist, NoServicesYet;


    /**
     * Lists users currently inside a service (except leisure).
     * Time Complexity:
     * - O(1) to find the service
     * - O(1) to obtain its student list
     *
     * @param place service name
     *
     * @return a two-way iterator over students
     *
     * @throws ServiceNotExists if service does not exist
     * @throws Empty if student list is empty
     */
    TwoWayIterator<Students> listUsersByOrder(String place)
            throws ServiceNotExists, Empty;


    /**
     * Lists all services in the system.
     * Time Complexity:
     * - O(1)
     *
     * @return iterator of all services
     *
     * @throws NoServicesYet if no services exist
     */
    Iterator<Services> listAllServices() throws NoServicesYet;


    /**
     * Sets the current working area.
     * Time Complexity: O(1)
     */
    void setCurrentArea(Area currentArea);
}
