package Package.Students;

import Package.Services.Lodging;
import Package.Services.Services;

import dataStructures.ListInArray;


public abstract class StudentsKeepVisited extends Students implements StudentsKeepVisitedInterface{



    public StudentsKeepVisited(String name, String country, String lodging, Lodging home, String location){
        super(name,country,lodging,home,location);

    }

    public abstract void addVisited(Services service);

    public abstract ListInArray<Services> getVisited();


}
