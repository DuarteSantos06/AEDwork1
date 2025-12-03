package Package.Students;

import Package.Area;
import Package.Exceptions.*;
import Package.Services.*;
import dataStructures.*;

public class StudentsApp {

    private Area currentArea;

    public StudentsApp(){
    }

    /**
     * @param type
     * @param name
     * @param country
     * @throws InvalidStudentType
     * @throws StudentAlreadyExists
     */
    public void createStudent(String type, String name, String country,Lodging home) throws InvalidStudentType, StudentAlreadyExists, InvalidLocation,StudentNotFound {
        StudentsType studentType = StudentsType.fromString(type);
        if (studentType == null) {
            throw new InvalidStudentType("Invalid student type!");
        } else if (alreadyExistsStudent(name)) {
            throw new StudentAlreadyExists(findStudent(name).getName() + " already exists!");
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

    /**
     * @param name
     * @param location
     * @return
     * @throws StudentNotFound
     * @throws InvalidLocation
     * @throws LodgingIsFull
     * @throws CantMove
     */
    public String move(String name, Services service,String location) throws StudentNotFound, InvalidLocation, LodgingIsFull, CantMove {
        Students s = findStudent(name);
        if (service == null) {
            throw new InvalidLocation("lodging " + location + " does not exist!");
        } else if (s.getHome().getName().equals(location)) {
            throw new InvalidLocation("That is " + s.getName() + "'s home!");
        } else if (service instanceof Lodging && s instanceof Thrifty && s.getHome().getPrice() < service.getPrice()) {
            throw new CantMove("Move is not acceptable for " + name + '!');
        }
        s.move((Lodging) service);
        return service.getName();
    }

    public Iterator<Services> getVisited(String name) throws StudentNotFound, InvalidStudentType, NoToList {
        Students s = findStudent(name);
        if (s instanceof Thrifty) {
            throw new InvalidStudentType(s.getName() + " is thrifty!");
        }
        ListInArray<Services> visited = ((StudentsKeepVisited) s).getVisited();
        return visited.iterator();
    }

    public Iterator<Services>getRanked(String type,int star,String name)throws StudentNotFound,InvalidType,ServiceNotExists,NoToList{

        Students student = findStudent(name);
        ServicesType serviceType = ServicesType.fromString(type);
        if(serviceType==null){
            throw new InvalidType("Invalid service type!");
        }
        List<Services> servicesWithStar = currentArea.getServicesByStar().get(star);
        if (servicesWithStar == null || servicesWithStar.isEmpty()) {
            throw new ServiceNotExists("No " + type + " services with average!");
        }
        FilterIterator<Services> it=new FilterIterator<>(servicesWithStar.iterator(), s->s.getType().equalsIgnoreCase(type));
        if(!it.hasNext()){
            throw new ServiceNotExists("No "+type+" services!");
        }
        DoublyLinkedList<Services> result=closest(it,student);
        return result.iterator();
    }

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

    public boolean go(String name, String location,Services service) throws StudentNotFound, InvalidLocation, AlreadyThere, Expensive {
        Students s = findStudent(name);

        if (service == null) {
            throw new InvalidLocation("Unknown " + location + '!');
        } else if (service instanceof Lodging) {
            throw new InvalidLocation(location + " is not a valid service!");
        } else if (s.getLocation().getName().equals(location)) {
            throw new AlreadyThere("Already there!");
        }

        Eating eating = s.getCheapestEating();
        s.go(service);

        return eating != null && s instanceof Thrifty && eating.getPrice() < service.getPrice() && service instanceof Eating;

    }

    /**
     * @return
     * @throws NoToList
     * @throws NoBoundsInTheSystem
     */
    public Iterator<Students> listAllStudents() throws NoToList, NoBoundsInTheSystem {
        if (currentArea == null) {
            throw new NoBoundsInTheSystem("System bounds not defined.");
        }
        return currentArea.getStudents().values();
    }

    /**
     * @param name
     * @return
     * @throws StudentNotFound
     */
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
            throw new StudentNotFound(name + " does not exist!");
        }
        return s;
    }

    private boolean alreadyExistsStudent(String name) {
        return currentArea.getStudentByName(name.toLowerCase().trim()) != null;
    }



}
