package Package.Students;

import Package.Services.Lodging;
import Package.Services.Services;
import dataStructures.DoublyLinkedList;
import dataStructures.ListInArray;


public abstract class StudentsKeepVisited extends Students implements StudentsKeepVisitedInterface{

    protected ListInArray<Services> visitedLocations;

    public StudentsKeepVisited(String name, String country, String lodging, Lodging home, String location){
        super(name,country,lodging,home,location);
        visitedLocations=new ListInArray<>(10);
    }

    public void addVisited(Services service){
        visitedLocations.addLast(service);
    }

    public ListInArray<Services> getVisited(){
        return visitedLocations;
    }


}
