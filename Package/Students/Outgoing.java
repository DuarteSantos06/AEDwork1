//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt
package Package.Students;

import Package.Exceptions.NoToList;
import Package.Services.Lodging;
import Package.Services.Services;

import dataStructures.ListInArray;
import dataStructures.Map;
import dataStructures.SepChainHashTable;

public class Outgoing extends StudentsKeepVisited {

    private final ListInArray<Services> services;
    private final Map<String,Services> servicesMap;

    public Outgoing(String name, String country, String lodging, Lodging home){
        super(name,country,lodging,home,"outgoing");
        services=new ListInArray<>(10);
        servicesMap=new SepChainHashTable<>();
    }

    public void addVisited(Services service){
        if(!alreadyVisited(service)){
            services.addLast(service);
            servicesMap.put(service.getName(),service);
        }
    }

    /**
     * Checks if the service has already been visited.
     *
     * @param service the service to check
     * @return true if service is already visited, false otherwise
     */
    private boolean alreadyVisited(Services service){
        Services s=servicesMap.get(service.getName());
        return s!=null;
    }

    public ListInArray<Services> getVisited()throws NoToList{
        if(services.isEmpty()){
            throw  new NoToList(this.getName()+ " has not visited any locations!");
        }
        return services;
    }
}
