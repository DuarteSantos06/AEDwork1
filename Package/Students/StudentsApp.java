/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package.Students;

import Package.Area;
import Package.Exceptions.*;
import Package.Services.*;
import dataStructures.*;

public class StudentsApp implements StudentsAppInterface{

    private Area currentArea;

    public StudentsApp(){
    }

    public void createStudent(String type, String name, String country,Lodging home) throws InvalidStudentType, StudentAlreadyExists,ServiceIsFull,StudentNotFound {
        StudentsType studentType = StudentsType.fromString(type);
        if (studentType == null) {
            throw new InvalidStudentType();
        } else if (alreadyExistsStudent(name)) {
            throw new StudentAlreadyExists(findStudent(name).getName());
        } else {
            Students student;
            if (studentType.equals(StudentsType.THRIFTY)) {
                student = new Thrifty(name, country, home);
            } else if (studentType.equals(StudentsType.OUTGOING)) {
                student = new Outgoing(name, country, home);
                ((Outgoing) student).addVisited(home);
            } else {
                student = new Bookish(name, country, home);
            }
            home.addStudent(student);
            currentArea.addStudent(student);
        }
    }

    public String move(String name, Services service,String location) throws StudentNotFound, InvalidLocation, ServiceIsFull, CantMove {
        Students s = findStudent(name);
        if (s.getHome().getName().equals(location)) {
            throw new AlreadyAtHome(s.getName());
        } else if (service instanceof Lodging && s instanceof Thrifty && s.getHome().getPrice() < service.getPrice()) {
            throw new CantMove(name);
        }
        s.move((Lodging) service);
        return service.getName();
    }

    public Iterator<Services> getVisited(String name) throws StudentNotFound, ThriftyDoesNotStoreServices,HasNotVisitedLocations {
        Students s = findStudent(name);
        if (s instanceof Thrifty) {
            throw new ThriftyDoesNotStoreServices(s.getName() );
        }
        List<Services> visited = ((StudentsKeepVisited) s).getVisited();
        return visited.iterator();
    }

    public Iterator<Services>getRanked(String type,int star,String name)throws StudentNotFound,InvalidType,ServiceNotExists,NoServicesInTheSystem{

        Students student = findStudent(name);
        ServicesType serviceType = ServicesType.fromString(type);
        if(serviceType==null){
            throw new InvalidType();
        }
        List<Services> servicesWithStar = currentArea.getServicesByStar().get(star);
        if (servicesWithStar == null || servicesWithStar.isEmpty()) {
            throw new NoServicesWithAverage( type );
        }
        FilterIterator<Services> it=new FilterIterator<>(servicesWithStar.iterator(), s->s.getType().equalsIgnoreCase(type));
        if(!it.hasNext()){
            throw new ServiceNotExists(type);
        }
        DoublyLinkedList<Services> result=closest(it,student);
        return result.iterator();
    }


    /**
     * @param itByStar - iterator of services by star
     * @param student  - student to find the closest services
     *Returns the closest services to the student
     */
    private DoublyLinkedList<Services> closest(FilterIterator<Services>itByStar,Students student){
        DoublyLinkedList<Services> closest = new DoublyLinkedList<>();
        long minDistance = Long.MAX_VALUE;

        while(itByStar.hasNext()){
            Services s = itByStar.next();
            long distance = Math.abs(s.getLatitude() - student.getLocation().getLatitude())
                    + Math.abs(s.getLongitude() - student.getLocation().getLongitude());

            if(distance < minDistance){
                closest = new DoublyLinkedList<>();
                closest.addLast(s);
                minDistance = distance;
            } else if(distance == minDistance){
                closest.addLast(s);
            }
        }
        for(int i = 0; i < closest.size() - 1; i++){
            for(int j = 0; j < closest.size() - 1 - i; j++){
                Services a = closest.get(j);
                Services b = closest.get(j+1);
                if(a.getLastEvaluated() > b.getLastEvaluated()){
                    closest.add(j, b);
                    closest.add(j+1, a);
                }
            }
        }
        return closest;
    }

    public boolean go(String name, String location,Services service) throws StudentNotFound, InvalidLocation, AlreadyThere,ServiceIsFull {
        Students s = findStudent(name);

        if (service == null) {
            throw new InvalidLocation(location);
        } else if (service instanceof Lodging) {
            throw new ServiceIsNotValid(location);
        } else if (s.getLocation().getName().equals(location)) {
            throw new AlreadyThere();
        }

        Eating eating = s.getCheapestEating();
        s.go(service);

        return eating != null && s instanceof Thrifty && eating.getPrice() < service.getPrice() && service instanceof Eating;

    }


    public Iterator<Students> listAllStudents() throws NoStudentsYet, NoBoundsInTheSystem {
        if (currentArea == null) {
            throw new NoBoundsInTheSystem();
        }
        return currentArea.getStudentsMap().values();
    }

    public String leave(String name) throws StudentNotFound {
        Students s = findStudent(name);
        String nameToReturn = s.getName();
        s.leave();
        currentArea.removeStudent(s);
        return nameToReturn;
    }

    public void setCurrentArea(Area currentArea){
        this.currentArea=currentArea;
    }

    public Students findStudent(String name)throws StudentNotFound {
        Students s=currentArea.getStudentByName(name.toLowerCase().trim());
        if (s == null) {
            throw new StudentNotFound(name);
        }
        return s;
    }


    /**
     * @param name
     * checks if a student already exists in the current area
     * return true if exists, false otherwise
     */
    private boolean alreadyExistsStudent(String name) {
        return currentArea.getStudentByName(name.toLowerCase().trim()) != null;
    }



}
