//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt

package Package.Students;

import Package.Exceptions.NoToList;
import Package.Services.Leisure;
import Package.Services.Lodging;
import Package.Services.Services;

import dataStructures.ListInArray;

public class Bookish extends StudentsKeepVisited{

    public ListInArray<Services> services;

    public Bookish(String name, String country, String lodging, Lodging home){
        super(name,country,lodging,home,"bookish");
        services=new ListInArray<>(10);
    }

    public void addVisited(Services service){
        if(service instanceof Leisure && !alreadyVisited(service)){
            services.addLast(service);
        }
    }

    private boolean alreadyVisited(Services service){
        for(int i=0;i<services.size();i++){
            if(services.get(i).equals(service)){
                return true;
            }
        }
        return false;
    }

    public ListInArray<Services> getVisited()throws NoToList {
        if(services.isEmpty()){
            throw new NoToList(this.getName()+" has not visited any locations!");
        }
        return services;
    }
}
