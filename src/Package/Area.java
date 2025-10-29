package Package;

import Package.Services.Services;
import Package.Students.Students;


import dataStructures.ListInArray;
import dataStructures.SortedDoublyLinkedList;
import dataStructures.SortedList;
import dataStructures.List;

import java.io.*;

public class Area implements AreaInterface, Serializable {

    private final String name;
    private final long topLatitude;
    private final long leftLongitude;
    private final long bottomLatitude;
    private final long rightLongitude;

    private transient List<Services> services;
    private transient SortedList<Students> students;
    private transient List<Students>studentsByRegistration;

    public Area(String name, long topLatitude, long leftLongitude, long bottomLatitude, long rightLongitude) {
        this.name = name;
        this.topLatitude = topLatitude;
        this.leftLongitude = leftLongitude;
        this.bottomLatitude = bottomLatitude;
        this.rightLongitude = rightLongitude;
        services = new ListInArray<>(2500);
        students = new SortedDoublyLinkedList<>(new StudentComparator());
        studentsByRegistration=new ListInArray<>(500);
    }





    public String getName() {
        return name;
    }

    public long getTopLatitude() {
        return topLatitude;
    }
    public long getLeftLongitude() {
        return leftLongitude;
    }

    public long getBottomLatitude() {
        return bottomLatitude;
    }
    public long getRightLongitude() {
        return rightLongitude;
    }

    public void addService(Services service){
        services.addLast(service);
    }


    public void addStudent(Students student){
        students.add(student);
        studentsByRegistration.addLast(student);
    }

    public void removeStudent(Students student){
        students.remove(student);
        studentsByRegistration.remove(studentsByRegistration.indexOf(student));
    }

    public List<Services> getServices(){
        return services;
    }

    public SortedList<Students> getStudents(){
        return students;
    }

    public List<Students> getStudentsByRegistration(){
        return studentsByRegistration;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(services.size());
        for (int i = 0; i < services.size(); i++) {
            oos.writeObject(services.get(i));
        }


        oos.writeInt(studentsByRegistration.size());
        for (int i = 0; i < studentsByRegistration.size(); i++) {
            oos.writeObject(studentsByRegistration.get(i));
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.services = new ListInArray<>(2500);
        this.students = new SortedDoublyLinkedList<>(new StudentComparator());
        this.studentsByRegistration = new ListInArray<>(500);


        int numServices = ois.readInt();
        for (int i = 0; i < numServices; i++) {
            Services s = (Services) ois.readObject();
            services.addLast(s);
        }


        int numByReg = ois.readInt();
        for (int i = 0; i < numByReg; i++) {
            Students st = (Students) ois.readObject();
            studentsByRegistration.addLast(st);
            students.add(st);
        }
    }
}
