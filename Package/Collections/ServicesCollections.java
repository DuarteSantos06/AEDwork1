/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package.Collections;

import Package.Exceptions.NoServicesInTheSystem;
import Package.Exceptions.NoServicesYet;
import Package.Services.Services;
import dataStructures.*;

import java.io.*;


public class ServicesCollections implements Serializable,ServicesCollectionsInterface {

    private transient Map<Integer, List<Services>> servicesByStar;
    private transient Map<String,Services> servicesByName;
    private transient List<Services> services;
    @Serial
    private static final long serialVersionUID = 3L;

    public ServicesCollections(){
        services = new DoublyLinkedList<>();
        servicesByStar = new BSTSortedMap<>();
        servicesByName = new ClosedHashTable<>(2500);

    }


    public void updateEvaluation(Services service, int newStar,int oldStar){
        List<Services> list = servicesByStar.get(oldStar);
        list.remove(list.indexOf(service));
        List<Services> newList = servicesByStar.get(newStar);
        if(newList==null){
            newList = new DoublyLinkedList<>();
            servicesByStar.put(newStar,newList);
        }
        newList.addLast(service);
    }

    public Services getServiceByName(String name){
        return servicesByName.get(name);
    }

    public Map<Integer,List<Services>> getServicesByStar()throws NoServicesInTheSystem{
        if(servicesByStar==null||servicesByStar.isEmpty()){
            throw new NoServicesInTheSystem();
        }
        return servicesByStar;
    }

    public List<Services> getServices()throws NoServicesYet{
        if(services.isEmpty()||services==null){
            throw new NoServicesYet();
        }
        return services;
    }

    public void addService(Services service){
        services.addLast(service);
        List<Services>list=servicesByStar.get(service.getEvaluation());
        if(list==null){
            list=new DoublyLinkedList<>();
            servicesByStar.put(service.getEvaluation(),list);
        }
        list.addLast(service);
        servicesByName.put(service.getName().toLowerCase(),service);
    }


    /**
     * Custom serialization for ServicesCollections.
     * Serializes the list of services; other maps are reconstructed on deserialization.
     * @param oos ObjectOutputStream
     * @throws IOException if an I/O error occurs
     * Time Complexity: O(n), n = number of services
     */
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(services.size());
        for (int i = 0; i < services.size(); i++) {
            Services s = services.get(i);
            oos.writeObject(s);
        }
    }

    /**
     * Custom deserialization for ServicesCollections.
     * Reconstructs transient fields:
     * - servicesByStar
     * - servicesByName
     *
     * @param ois ObjectInputStream
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if a class cannot be found
     * Time Complexity: O(n log n) worst case
     * - O(n) to read all services
     * - O(log n) per service to insert in BSTSortedMap
     */
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.services = new ListInArray<>(2500);
        this.servicesByStar = new BSTSortedMap<>();
        this.servicesByName = new ClosedHashTable<>(2500);

        int numServices = ois.readInt();
        for (int i = 0; i < numServices; i++) {
            Services s = (Services) ois.readObject();
            services.addLast(s);
        }

        for (int i = 0; i < services.size(); i++) {
            List<Services> list = servicesByStar.get(services.get(i).getEvaluation());
            if (list == null) {
                list = new DoublyLinkedList<>();
                servicesByStar.put(services.get(i).getEvaluation(), list);
            }
            list.addLast(services.get(i));
            servicesByName.put(services.get(i).getName().toLowerCase(), services.get(i));
        }
    }
}
