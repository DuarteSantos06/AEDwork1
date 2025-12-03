package Package.Services;

import Package.Exceptions.*;
import Package.Area;
import Package.Students.Students;
import Package.Students.Thrifty;
import dataStructures.*;

public class ServicesApp {

    private Area currentArea;

    public ServicesApp(){}

    public void createLodging(long latitude, long longitude, int price, int capacity, String name) throws InvalidPrice, InvalidLocation, InvalidCapacity, ServiceAlreadyExists {
        validateBounds(latitude, longitude);
        validatePrice(price, "Invalid room price!");
        validateCapacity(capacity);
        serviceAlreadyExists(name);

        Lodging lodging = new Lodging(latitude, longitude, price, capacity, name);
        addServiceToCurrentArea(lodging);
    }

    public void createEating(long latitude, long longitude, int price, int capacity, String name) throws InvalidPrice, InvalidLocation, InvalidCapacity, ServiceAlreadyExists {
        validateBounds(latitude, longitude);
        validatePrice(price, "Invalid menu price!");
        validateCapacity(capacity);
        serviceAlreadyExists(name);

        Eating eating = new Eating(latitude, longitude, price, capacity, name);
        addServiceToCurrentArea(eating);
    }

    public void createLeisure(long latitude, long longitude, int price, int discount, String name) throws InvalidPrice, InvalidLocation, InvalidDiscount, ServiceAlreadyExists {
        validateBounds(latitude, longitude);
        validatePrice(price, "Invalid ticket price!");
        validateDiscount(discount);
        serviceAlreadyExists(name);

        Leisure leisure = new Leisure(latitude, longitude, price, discount, name);
        addServiceToCurrentArea(leisure);
    }

    public Services findService(String name) {
        return currentArea.getServiceByName(name.toLowerCase().trim());
    }

    public Services find(String type,Students s)throws NoToList{
        List<Services> services = currentArea.getServices();
        FilterIterator<Services> it= new FilterIterator<>(services.iterator(), service->service.getType().equalsIgnoreCase(type));

        if(s instanceof Thrifty){
            return findCheapest(it);
        }else {
            return findHighestRanked(it);
        }
    }

    public void evaluate(int star, String nameService, String description,int evaluateCounter) throws InvalidStar, ServiceNotExists {
        if (star < 1 || star > 5) {
            throw new InvalidStar("Invalid evaluation!");
        }
        servicesDoesNotExist(nameService);

        Services s = findService(nameService);
        s.evaluate(star, description, evaluateCounter++,currentArea);
    }

    public Iterator<Services> getRanking() throws NoToList {
        Map<Integer, List<Services>> servicesByStar = currentArea.getServicesByStar();

        DoublyLinkedList<Services> ranking = new DoublyLinkedList<>();
        for (int i = 5; i >= 1; i--) {
            List<Services> services = servicesByStar.get(i);
            if (services != null) {
                for (int j = 0; j < services.size(); j++) {
                    ranking.addLast(services.get(j));
                }
            }
        }
        return ranking.iterator();
    }

    public Lodging findLodging(String name)throws LodgingNotExists,NoToList {
        List<Services> services = currentArea.getServices();

        for (int i = 0; i < services.size(); i++) {
            Services s = services.get(i);
            if (s.getName().equalsIgnoreCase(name) && s instanceof Lodging) {
                return (Lodging) s;
            }
        }
        throw new LodgingNotExists("lodging " + name + " does not exist!");

    }

    public TwoWayIterator<Students> listUsersByOrder(String place) throws ServiceNotExists, InvalidLocation, Empty {
        servicesDoesNotExist(place);

        Services s = findService(place);
        if (s instanceof Leisure) {
            throw new InvalidLocation(s.getName() + " does not control student entry and exit!");
        }
        DoublyLinkedList<Students> list = ((ServicesWithCapacity) s).getStudents();
        return list.twoWayiterator();
    }

    public Iterator<Services> listAllServices() throws NoToList {
        return currentArea.getServices().iterator();
    }

    private Services findCheapest(FilterIterator<Services>it){
        Services cheapest=it.next();
        while(it.hasNext()){
            Services s=it.next();
            if(s.getPrice()<cheapest.getPrice()){
                cheapest=s;
            }
        }
        return cheapest;
    }

    private Services findHighestRanked(FilterIterator<Services>it){
        Services highest=it.next();
        while(it.hasNext()){
            Services s=it.next();
            if(s.getEvaluation()>highest.getEvaluation()){
                highest=s;
            }if(s.getEvaluation()==highest.getEvaluation() && s.getLastEvaluated()<highest.getLastEvaluated()){
                highest=s;
            }
        }
        return highest;
    }


    private void addServiceToCurrentArea(Services s){
        currentArea.addService(s);
    }
    private void validateBounds(long latitude, long longitude)throws InvalidLocation {
        if(!(longitude >= currentArea.getLeftLongitude() &&
                longitude <= currentArea.getRightLongitude() &&
                latitude <= currentArea.getTopLatitude() &&
                latitude >= currentArea.getBottomLatitude()) ){
                throw new InvalidLocation("Invalid location!");
        }
    }

    public void setCurrentArea(Area currentArea){
        this.currentArea=currentArea;
    }

    private void validateCapacity(int capacity)throws InvalidCapacity{
        if (capacity <= 0) {
            throw new InvalidCapacity("Invalid capacity!");
        }
    }

    private void validateDiscount(int discount)throws InvalidDiscount{
        if (discount < 0 || discount > 100) {
            throw new InvalidDiscount("Invalid discount price!");
        }
    }

    private void validatePrice(int price,String message)throws InvalidPrice{
         if (price <= 0) {
            throw new InvalidPrice(message);
        }
    }

    private void serviceAlreadyExists(String name)throws ServiceAlreadyExists{
        Services s=findService(name);
        if(s!=null){
            throw new ServiceAlreadyExists(s.getName()+" already exists!");
        }
    }

    private void servicesDoesNotExist(String name)throws ServiceNotExists{
        Services s=findService(name);
        if(s==null){
            throw new ServiceNotExists(name+" does not exist!");
        }
    }
}
