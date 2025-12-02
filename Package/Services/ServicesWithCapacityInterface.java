//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt
package Package.Services;
import Package.Exceptions.Empty;
import Package.Exceptions.NoToList;
import Package.Students.Students;
import dataStructures.DoublyLinkedList;

/**
 * Interface representing a service with capacity constraints.
 * This extends the basic ServicesInterface to add functionality for services
 * that have a maximum capacity and can track current occupancy (e.g., lodging, eating areas).
 */
public interface ServicesWithCapacityInterface extends ServicesInterface {

    /**
     * Returns the maximum capacity of this service.
     * This represents the total number of students that can use this service simultaneously.
     *
     * @return the maximum capacity
     * @complexity O(1)
     */
    int getCapacity();

    /**
     * Returns the current number of students occupying this service.
     * This value should always be less than or equal to the maximum capacity.
     *
     * @return the current occupation count
     * @complexity O(1)
     */
    int getCurrentOccupation();

    /**
     * Returns a list of all students currently at this service.
     * The list maintains the order in which students arrived at the service.
     *
     * @return a doubly linked list of students currently using this service
     * @complexity O(1) — returning reference; O(n) if copying
     */
    DoublyLinkedList<Students> getStudents()throws Empty;
}
