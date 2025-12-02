package Package.Collections;

import Package.Exceptions.NoToList;
import Package.Students.Students;
import dataStructures.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class StudentsCollections implements Serializable {


    private transient Map<String, Students> students;
    private transient Map<String, List<Students>>studentsByRegistration;

    public StudentsCollections(){
        students = new RBSortedMap<>();
        studentsByRegistration = new SepChainHashTable<>(500);
    }

    public Students getStudentByName(String name){
        return students.get(name);
    }

    public Map<String,Students> getStudents()throws NoToList {
        if(students==null||students.isEmpty()){
            throw new NoToList("No students yet!");
        }
        return students;
    }

    public List<Students> getStudentsByRegistrationByCountry(String country)throws NoToList{
        List<Students> students=studentsByRegistration.get(country.toLowerCase());
        if(students ==null){
            throw new NoToList("No students from " + country + '!');
        }
        return students;
    }

    public void removeStudent(Students student){
        students.remove(student.getName().toLowerCase());
        List<Students> list = studentsByRegistration.get(student.getCountry().toLowerCase());

        if (list != null) {
            list.remove(list.indexOf(student));
            if (list.isEmpty()) {
                studentsByRegistration.remove(student.getCountry().toLowerCase());
            }
        }
    }
    public void addStudent(Students student){
        students.put(student.getName().toLowerCase(),student);
        String country = student.getCountry().toLowerCase();
        List<Students> list = studentsByRegistration.get(country);
        if(list==null){
            list = new ListInArray<>(100);
            studentsByRegistration.put(country, list);
        }
        list.addLast(student);
    }

    private void writeObject(ObjectOutputStream oos)throws IOException{
        oos.defaultWriteObject();
        oos.writeInt(studentsByRegistration.size());
        for (Iterator<Map.Entry<String, List<Students>>> it = studentsByRegistration.iterator(); it.hasNext(); ) {
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

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.students = new BSTSortedMap<>();
        this.studentsByRegistration = new SepChainHashTable<>();


        int numCountries = ois.readInt();
        for (int i = 0; i < numCountries; i++) {
            String country = (String) ois.readObject();
            int listSize = ois.readInt();
            List<Students> list = new ListInArray<>(listSize);

            for (int j = 0; j < listSize; j++) {
                Students st = (Students) ois.readObject();
                list.addLast(st);
                students.put(st.getName().toLowerCase(),st);
            }

            studentsByRegistration.put(country, list);
        }
    }
}
