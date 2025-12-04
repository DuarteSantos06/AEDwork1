/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package.Collections;

import Package.Exceptions.NoStudentsFromCountry;
import Package.Exceptions.NoStudentsYet;
import Package.Students.Students;
import dataStructures.List;
import dataStructures.Map;

/**
 * Interface for managing student collections.
 * Provides methods to add, remove and query students.
 *
 * Time Complexity notes assume:
 * - `students` is an AVL map: O(log n) for get/put/remove by name
 * - `studentsByRegistration` is a hash table: O(1) average for get/put/remove
 */
public interface StudentsCollectionsInterface {

    /**
     * Returns a student by their name.
     *
     * @param name student name
     * @return the student object or null if not found
     *
     * Time Complexity: O(log n) for AVL lookup
     */
    Students getStudentByName(String name);

    /**
     * Returns the map of all students.
     *
     * @return map of students (name -> student)
     * @throws NoStudentsYet if there are no students
     *
     * Time Complexity: O(1)
     */
    Map<String, Students> getStudents() throws NoStudentsYet;

    /**
     * Returns a list of students registered in a given country.
     *
     * @param country country name
     * @return list of students
     * @throws NoStudentsFromCountry if no students exist from that country
     *
     * Time Complexity: O(1) average hash table lookup + O(k) to copy list if needed
     */
    List<Students> getStudentsByRegistrationByCountry(String country) throws NoStudentsFromCountry;

    /**
     * Adds a student to the collections.
     *
     * @param student the student to add
     *
     * Time Complexity:
     * - O(log n) for AVL insertion
     * - O(1) average for hash table update
     */
    void addStudent(Students student);

    /**
     * Removes a student from the collections.
     *
     * @param student the student to remove
     *
     * Time Complexity:
     * - O(log n) for AVL removal
     * - O(1) average for hash table lookup + O(k) to remove from list (k = number of students from that country)
     */
    void removeStudent(Students student);
}
