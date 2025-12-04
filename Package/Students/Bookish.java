/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package.Students;

import Package.Exceptions.HasNotVisitedLocations;
import Package.Services.Leisure;
import Package.Services.Lodging;
import Package.Services.Services;

import dataStructures.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

public class Bookish extends StudentsKeepVisited{

    public transient List<Services> services;
    private transient Map<String,Services> servicesMap;

    public Bookish(String name, String country, Lodging home){
        super(name,country,home,StudentsType.BOOKISH);
        services=new SinglyLinkedList<>();
        servicesMap=new ClosedHashTable<>(100);
    }

    public void addVisited(Services service){
        if(service instanceof Leisure && !alreadyVisited(service)){
            services.addLast(service);
            servicesMap.put(service.getName(),service);
        }
    }

    private boolean alreadyVisited(Services service){
        Services s=servicesMap.get(service.getName());
        return s!=null;
    }

    public List<Services> getVisited()throws HasNotVisitedLocations {
        if(services.isEmpty()){
            throw new HasNotVisitedLocations(this.getName());
        }
        return services;
    }

    /**
     * Custom serialization for Bookish.
     * Reconstructs transient fields:
     * - services
     * @param oos
     * @throws IOException
     */
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(services.size());
        Iterator<Services> it = services.iterator();
        while(it.hasNext()){
            oos.writeObject(it.next());
        }
    }

    /**
     * Custom deserialization for Bookish.
     * Reconstructs transient fields:
     * - services
     * - servicesMap
     * @param ois
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        services=new SinglyLinkedList<>();
        servicesMap=new ClosedHashTable<>(100);
        int numServices=ois.readInt();
        for(int i=0;i<numServices;i++){
            services.addLast((Services)ois.readObject());
            servicesMap.put((services.getLast()).getName(),services.getLast());
        }
    }
}
