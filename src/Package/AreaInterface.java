package Package;

import Package.Services.Services;
import Package.Students.Students;
import dataStructures.List;
import dataStructures.SortedList;

public interface AreaInterface {

    String getName();

    long getTopLatitude();
    long getLeftLongitude();
    long getBottomLatitude();
    long getRightLongitude();

    void addService(Services service);
    void addStudent(Students student);
    void removeStudent(Students student);

    List<Services> getServices();
    SortedList<Students> getStudents();
    List<Students> getStudentsByRegistration();
}
