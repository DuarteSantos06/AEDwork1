//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt
package Package.Students;

import Package.Exceptions.NoToList;
import Package.Services.Lodging;
import Package.Services.Services;

import dataStructures.ListInArray;


public abstract class StudentsKeepVisited extends Students implements StudentsKeepVisitedInterface{



    public StudentsKeepVisited(String name, String country, Lodging home,StudentsType type){
        super(name,country,home,type);

    }

    public abstract void addVisited(Services service);

    public abstract ListInArray<Services> getVisited()throws NoToList;


}
