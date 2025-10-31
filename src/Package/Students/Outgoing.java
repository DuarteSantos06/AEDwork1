//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt
package Package.Students;

import Package.Services.Lodging;
import Package.Services.Services;

import dataStructures.ListInArray;

public class Outgoing extends StudentsKeepVisited {

    private ListInArray<Services> services;

    public Outgoing(String name, String country, String lodging, Lodging home){
        super(name,country,lodging,home,"outgoing");
        services=new ListInArray<>(10);
    }

    public void addVisited(Services service){
        if(!alreadyVisited(service)){
            services.addLast(service);
        }
    }

    /**
     * Checks if the service has already been visited.
     *
     * @param service the service to check
     * @return true if service is already visited, false otherwise
     */
    private boolean alreadyVisited(Services service){
        for(int i=0;i<services.size();i++){
            if(services.get(i).equals(service)){
                return true;
            }
        }
        return false;
    }

    public ListInArray<Services> getVisited(){
        return services;
    }
}
