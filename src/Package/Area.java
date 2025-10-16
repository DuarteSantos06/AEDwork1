package Package;

import Package.Services.Services;
import Package.Students.Students;
import dataStructures.ListInArray;
import dataStructures.SortedDoublyLinkedList;
import dataStructures.SortedList;
import dataStructures.List;

import java.io.Serializable;

public class Area implements AreaInterface, Serializable {

    private String name;
    private long topLatitude;
    private long leftLongitude;
    private long bottomLatitude;
    private long rightLongitude;
    private List<Services> services;
    private SortedList<Students> students;
    private List<Students>studentsByRegistration;

    public Area(String name, long topLatitude, long leftLongitude, long bottomLatitude, long rightLongitude) {
        this.name = name;
        this.topLatitude = topLatitude;
        this.leftLongitude = leftLongitude;
        this.bottomLatitude = bottomLatitude;
        this.rightLongitude = rightLongitude;
        services = new ListInArray<>(10);
        students = new SortedDoublyLinkedList<>(new StudentComparator());
        studentsByRegistration=new ListInArray<>(10);
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

    public List<Services> getServices(){
        return services;
    }

    public SortedList<Students> getStudents(){
        return students;
    }
}
