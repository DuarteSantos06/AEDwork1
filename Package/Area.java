//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt
package Package;

import Package.Collections.ServicesCollections;
import Package.Collections.StudentsCollections;
import Package.Exceptions.NoToList;
import Package.Services.Services;
import Package.Students.Students;
import dataStructures.*;


import java.io.*;


public class Area implements AreaInterface, Serializable {

    private final String name;
    private final long topLatitude;
    private final long leftLongitude;
    private final long bottomLatitude;
    private final long rightLongitude;

    StudentsCollections studentsCollections;
    ServicesCollections servicesCollections;


    public Area(String name, long topLatitude, long leftLongitude, long bottomLatitude, long rightLongitude) {
        this.name = name;
        this.topLatitude = topLatitude;
        this.leftLongitude = leftLongitude;
        this.bottomLatitude = bottomLatitude;
        this.rightLongitude = rightLongitude;
        studentsCollections = new StudentsCollections();
        servicesCollections = new ServicesCollections();
    }

    public void updateEvaluation(Services service, int newStar,int oldStar){
        servicesCollections.updateEvaluation(service,newStar,oldStar);
    }

    public String getName() {
        return name;
    }

    public Services getServiceByName(String name){
        return servicesCollections.getServiceByName(name);
    }

    public Students getStudentByName(String name){
        return studentsCollections.getStudentByName(name);
    }

    public Map<Integer,List<Services>> getServicesByStar()throws NoToList{
        return servicesCollections.getServicesByStar();
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
        servicesCollections.addService(service);
    }

    public void addStudent(Students student){
        studentsCollections.addStudent(student);
    }

    public void removeStudent(Students student){
        studentsCollections.removeStudent(student);
    }

    public List<Services> getServices(){
        return servicesCollections.getServices();
    }

    public Map<String,Students> getStudents()throws NoToList{
        return studentsCollections.getStudents();
    }

    public List<Students> getStudentsByRegistrationByCountry(String country)throws NoToList{
        return studentsCollections.getStudentsByRegistrationByCountry(country);
    }
}
