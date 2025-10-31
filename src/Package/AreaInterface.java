package Package;
import Package.Services.Services;
import Package.Students.Students;
import dataStructures.List;
import dataStructures.SortedList;

/**
 * Interface representing a geographical area that contains services and students.
 * An area is defined by its name and geographical boundaries (latitude and longitude coordinates).
 */
public interface AreaInterface {

    /**
     * Returns the name of the area.
     *
     * @return the area name
     * @complexity O(1)
     */
    String getName();

    /**
     * Returns the top latitude boundary of the area.
     *
     * @return the top latitude coordinate
     * @complexity O(1)
     */
    long getTopLatitude();

    /**
     * Returns the left longitude boundary of the area.
     *
     * @return the left longitude coordinate
     * @complexity O(1)
     */
    long getLeftLongitude();

    /**
     * Returns the bottom latitude boundary of the area.
     *
     * @return the bottom latitude coordinate
     * @complexity O(1)
     */
    long getBottomLatitude();

    /**
     * Returns the right longitude boundary of the area.
     *
     * @return the right longitude coordinate
     * @complexity O(1)
     */
    long getRightLongitude();

    /**
     * Adds a service to this area.
     *
     * @param service the service to be added
     * @complexity O(1) — assuming insertion at end or fixed position
     */
    void addService(Services service);

    /**
     * Adds a student to this area.
     *
     * @param student the student to be added
     * @complexity O(1) — assuming insertion into list
     */
    void addStudent(Students student);

    /**
     * Removes a student from this area.
     *
     * @param student the student to be removed
     * @complexity O(n) — where n is number of students (search and removal)
     */
    void removeStudent(Students student);

    /**
     * Returns a list of all services in this area.
     *
     * @return a list containing all services
     * @complexity O(1)
     */
    List<Services> getServices();

    /**
     * Returns a sorted list of all students in this area.
     *
     * @return a sorted list containing all students
     * @complexity O(n log n) — if sorting on retrieval
     */
    SortedList<Students> getStudents();

    /**
     * Returns a list of all students in this area ordered by registration number.
     *
     * @return a list of students sorted by registration
     * @complexity O(n log n) — if sorting required
     */
    List<Students> getStudentsByRegistration();
}
