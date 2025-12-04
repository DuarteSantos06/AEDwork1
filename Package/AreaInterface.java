/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package;
import Package.Services.Services;
import Package.Students.Students;
import dataStructures.List;
import dataStructures.Map;
import Package.Exceptions.*;

/**
 * Interface representing a geographical area containing services and students.
 * An area is defined by its name and geographical boundaries (latitude and longitude).
 * Provides methods to manage services, students, and query them efficiently.
 */
public interface AreaInterface {

    /**
     * Returns the name of the area.
     * Time Complexity: O(1)
     * @return the area name
     */
    String getName();

    /**
     * Returns the top latitude boundary of the area.
     * Time Complexity: O(1)
     * @return the top latitude coordinate
     */
    long getTopLatitude();

    /**
     * Returns the left longitude boundary of the area.
     * Time Complexity: O(1)
     * @return the left longitude coordinate
     */
    long getLeftLongitude();

    /**
     * Returns the bottom latitude boundary of the area.
     * Time Complexity: O(1)
     * @return the bottom latitude coordinate
     */
    long getBottomLatitude();

    /**
     * Returns the right longitude boundary of the area.
     * Time Complexity: O(1)
     * @return the right longitude coordinate
     */
    long getRightLongitude();

    /**
     * Adds a service to this area.
     * Time Complexity: O(1)
     * @param service the service to be added
     */
    void addService(Services service);

    /**
     * Adds a student to this area.
     * Time Complexity: O(1)
     * @param student the student to be added
     */
    void addStudent(Students student);

    /**
     * Removes a student from this area.
     * Time Complexity: O(n)
     * @param student the student to be removed
     */
    void removeStudent(Students student);

    /**
     * Returns a list of all services in this area.
     * Time Complexity: O(1)
     * @return list of services
     * @throws NoServicesYet if no services have been added
     */
    List<Services> getServices() throws NoServicesYet;

    /**
     * Returns a map of all students in this area, keyed by student name.
     * Time Complexity: O(1)
     * @return map of students
     * @throws NoStudentsYet if no students exist
     */
    Map<String, Students> getStudentsMap() throws NoStudentsYet;

    /**
     * Returns a list of students from a specific country, ordered by registration.
     * Time Complexity: O(1) for lookup + O(k) for iteration, k = number of students from country
     * @param country the country to filter by
     * @return list of students from the specified country
     * @throws NoStudentsFromCountry if no students from that country exist
     */
    List<Students> getStudentsByRegistrationByCountry(String country) throws NoStudentsFromCountry;
}
