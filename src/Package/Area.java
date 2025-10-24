package Package;

import Package.Services.Services;
import Package.Students.Students;

import dataStructures.ListInArray;
import dataStructures.SortedDoublyLinkedList;
import dataStructures.SortedList;
import dataStructures.List;
import java.io.Serializable;

public class Area implements AreaInterface, Serializable {

    private final String name;
    private final long topLatitude;
    private final long leftLongitude;
    private final long bottomLatitude;
    private final long rightLongitude;
    private final List<Services> services;
    private final SortedList<Students> students;
    private final List<Students>studentsByRegistration;

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

    public List<Students> getStudentsByRegistration(){
        return studentsByRegistration;
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
}
