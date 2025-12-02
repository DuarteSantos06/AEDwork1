package Package.Collections;

import Package.Exceptions.NoToList;
import Package.Services.Services;
import dataStructures.*;

import java.io.*;


public class ServicesCollections implements Serializable {

    private transient Map<Integer, List<Services>> servicesByStar;
    private transient Map<String,Services> servicesByName;
    private transient List<Services> services;

    public ServicesCollections(){
        services = new ListInArray<>(2500);
        servicesByStar = new RBSortedMap<>();
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

    public Map<Integer,List<Services>> getServicesByStar()throws NoToList{
        if(servicesByStar==null||servicesByStar.isEmpty()){
            throw new NoToList("No services in the system.");
        }
        return servicesByStar;
    }

    public List<Services> getServices(){
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
     * Serializes custom fields: services and studentsByRegistration.
     * This is a private helper for Java serialization.
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(services.size());
        for (int i = 0; i < services.size(); i++) {
            Services s = services.get(i);
            oos.writeObject(s);
        }
    }

    /**
     * Deserializes custom fields: services and studentsByRegistration.
     * This is a private helper for Java serialization.
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.services = new ListInArray<>(2500);
        this.servicesByStar = new BSTSortedMap<>();
        this.servicesByName = new SepChainHashTable<>();

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
