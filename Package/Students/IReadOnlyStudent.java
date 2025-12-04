/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package.Students;

import Package.Exceptions.InvalidLocation;
import Package.Exceptions.ServiceIsFull;
import Package.Services.Eating;
import Package.Services.Lodging;
import Package.Services.Services;

public interface IReadOnlyStudent {


    /**
     * Returns the name of the student.
     *
     * @return the student's name
     * @complexity O(1)
     */
    String getName();

    /**
     * Returns the country of origin of the student.
     *
     * @return the student's country
     * @complexity O(1)
     */
    String getCountry();

    /**
     * Returns the type of student (e.g., "bookish", "outgoing", "thrifty").
     *
     * @return the student type
     * @complexity O(1)
     */
    String getType();

    /**
     * Returns the name of the student's current location.
     *
     * @return the name of the current location/service
     * @complexity O(1)
     */
    String getNameLocation();

    /**
     * Returns the service where the student is currently located.
     *
     * @return the current location service
     * @complexity O(1)
     */
    Services getLocation();

    /**
     * Returns the lodging where the student is staying.
     * This is the student's home base in the system.
     *
     * @return the student's home lodging
     * @complexity O(1)
     */
    Lodging getHome();

    /**
     * Returns the cheapest eating service available to this student.
     * The cheapest option may vary depending on the student's type,
     * location, and available discounts.
     *
     * @return the cheapest available eating service
     * @complexity O(1)
     */
    Eating getCheapestEating();

    /**
     * Moves the student to a specified service location.
     *
     * @param location the service where the student will go
     * @complexity O(1) — location change and array updates
     */
    void go(Services location)throws InvalidLocation, ServiceIsFull;

    /**
     * Changes the student's home lodging to a new location.
     *
     * @param home the new lodging to become the student's home
     * @complexity O(1)
     */
    void move(Lodging home)throws InvalidLocation,ServiceIsFull;

    /**
     * Removes the student from the system.
     * This typically includes removing them from their current location
     * and lodging before they leave the program.
     * @complexity O(1) — assuming single remove
     */
    void leave();
}
