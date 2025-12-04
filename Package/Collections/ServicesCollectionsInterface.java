package Package.Collections;
/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

import Package.Exceptions.NoServicesInTheSystem;
import Package.Exceptions.NoServicesYet;
import Package.Services.Services;
import dataStructures.List;
import dataStructures.Map;


/**
 * Interface for managing a collection of Services.
 * Provides methods for adding, querying, and updating services.
 */
public interface ServicesCollectionsInterface {

    /**
     * Adds a new service to the collection.
     * Time Complexity: O(log n) for BST + O(1) for list and hash table
     * @param service the service to add
     */
    void addService(Services service);

    /**
     * Returns a service by its name.
     * Time Complexity: O(1) average (hash table lookup)
     * @param name name of the service
     * @return the service object or null if not found
     */
    Services getServiceByName(String name);

    /**
     * Returns all services grouped by their star evaluation.
     * Time Complexity: O(1)
     * @return map of star rating -> list of services
     * @throws NoServicesInTheSystem if no services exist
     */
    Map<Integer, List<Services>> getServicesByStar() throws NoServicesInTheSystem;

    /**
     * Returns all services in the collection.
     * Time Complexity: O(1)
     * @return list of all services
     * @throws NoServicesYet if no services exist
     */
    List<Services> getServices() throws NoServicesYet;

    /**
     * Updates a service's evaluation from oldStar to newStar.
     * Time Complexity: O(n) to remove from list by index + O(log n) for BST update
     * @param service the service to update
     * @param newStar new evaluation value
     * @param oldStar old evaluation value
     */
    void updateEvaluation(Services service, int newStar, int oldStar);
}
