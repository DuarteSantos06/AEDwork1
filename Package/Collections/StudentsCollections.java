/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package.Collections;

import Package.Exceptions.NoStudentsFromCountry;
import Package.Exceptions.NoStudentsYet;
import Package.Students.Students;
import dataStructures.*;

import java.io.*;

public class StudentsCollections implements Serializable,StudentsCollectionsInterface {


    private transient Map<String, Students> students;
    private transient Map<String, List<Students>>studentsByCountry;
    @Serial
    private static final long serialVersionUID = 4L;

    public StudentsCollections(){
        students = new AVLSortedMap<>();
        studentsByCountry = new SepChainHashTable<>(500);
    }

    public Students getStudentByName(String name){
        return students.get(name);
    }

    public Map<String,Students> getStudents()throws NoStudentsYet {
        if(students==null||students.isEmpty()){
            throw new NoStudentsYet();
        }
        return students;
    }

    public List<Students> getStudentsByRegistrationByCountry(String country)throws NoStudentsFromCountry{
        List<Students> students=studentsByCountry.get(country.toLowerCase());
        if(students ==null){
            throw new NoStudentsFromCountry( country);
        }
        return students;
    }

    public void removeStudent(Students student){
        students.remove(student.getName().toLowerCase());
        List<Students> list = studentsByCountry.get(student.getCountry().toLowerCase());

        if (list != null) {
            list.remove(list.indexOf(student));
            if (list.isEmpty()) {
                studentsByCountry.remove(student.getCountry().toLowerCase());
            }
        }
    }

    public void addStudent(Students student){
        students.put(student.getName().toLowerCase(),student);
        String country = student.getCountry().toLowerCase();
        List<Students> list = studentsByCountry.get(country);
        if(list==null){
            list = new ListInArray<>(100);
            studentsByCountry.put(country, list);
        }
        list.addLast(student);
    }

    /**
     * Custom serialization method for StudentsCollections.
     * <p>
     * This method handles the serialization of transient fields:
     * - `studentsByRegistration`: Map of country -> list of students
     * - `students` map is reconstructed during deserialization
     * </p>
     * @param oos the ObjectOutputStream to write data to
     * @throws IOException if an I/O error occurs
     * Time Complexity: O(n)
     * - n = total number of students in all countries
     * - Iterates through every student once
     */
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(studentsByCountry.size());

        for (Iterator<Map.Entry<String, List<Students>>> it = studentsByCountry.iterator(); it.hasNext(); ) {
            Map.Entry<String, List<Students>> entry = it.next();
            String country = entry.key();
            List<Students> list = entry.value();

            oos.writeObject(country);
            oos.writeInt(list.size());

            for (int i = 0; i < list.size(); i++) {
                Students st = list.get(i);
                oos.writeObject(st);
            }
        }
    }

    /**
     * Custom deserialization method for StudentsCollections.
     * <p>
     * This method reconstructs the transient fields:
     * - `students`: AVL map of student names -> student objects
     * - `studentsByRegistration`: hash table mapping country -> list of students
     * </p>
     *
     * @param ois the ObjectInputStream to read data from
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if a class of a serialized object cannot be found
     * Time Complexity: O(n)
     * - n = total number of students
     * - Each student is read and inserted once
     */
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();

        this.students = new AVLSortedMap<>();
        this.studentsByCountry = new SepChainHashTable<>(500);

        int numCountries = ois.readInt();
        for (int i = 0; i < numCountries; i++) {
            String country = (String) ois.readObject();
            int listSize = ois.readInt();
            List<Students> list = new ListInArray<>(listSize);

            for (int j = 0; j < listSize; j++) {
                Students st = (Students) ois.readObject();
                list.addLast(st);
                students.put(st.getName().toLowerCase(), st);
            }

            studentsByCountry.put(country, list);

        }
    }

}
