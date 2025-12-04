/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */
package Package.Services;

import Package.Exceptions.*;
import Package.Area;
import Package.Students.Students;
import Package.Students.Thrifty;
import dataStructures.*;

public class ServicesApp implements ServicesAppInterface{

    private Area currentArea;

    public ServicesApp(){}

    public void createLodging(long latitude, long longitude, int price, int capacity, String name) throws InvalidLocationArea,InvalidPrice, InvalidBounds, InvalidCapacity, ServiceAlreadyExists {
        validateBounds(latitude, longitude);
        validatePrice(price, ExceptionsMessagesEnum.INVALID_PRICE_LODGING.getMessage());
        validateCapacity(capacity);
        serviceAlreadyExists(name);

        Lodging lodging = new Lodging(latitude, longitude, price, capacity, name);
        addServiceToCurrentArea(lodging);
    }

    public void createEating(long latitude, long longitude, int price, int capacity, String name) throws InvalidLocationArea,InvalidPrice, InvalidBounds, InvalidCapacity, ServiceAlreadyExists {
        validateBounds(latitude, longitude);
        validatePrice(price, ExceptionsMessagesEnum.INVALID_PRICE_EATING.getMessage());
        validateCapacity(capacity);
        serviceAlreadyExists(name);

        Eating eating = new Eating(latitude, longitude, price, capacity, name);
        addServiceToCurrentArea(eating);
    }

    public void createLeisure(long latitude, long longitude, int price, int discount, String name) throws InvalidLocationArea,InvalidPrice, InvalidBounds, InvalidDiscount, ServiceAlreadyExists {
        validateBounds(latitude, longitude);
        validatePrice(price, ExceptionsMessagesEnum.INVALID_PRICE_LEISURE.getMessage());
        validateDiscount(discount);
        serviceAlreadyExists(name);

        Leisure leisure = new Leisure(latitude, longitude, price, discount, name);
        addServiceToCurrentArea(leisure);
    }

    public Services findService(String name) {
        return currentArea.getServiceByName(name.toLowerCase().trim());
    }

    public Services find(String type,Students s)throws NoServicesYet{
        List<Services> services = currentArea.getServices();
        FilterIterator<Services> it= new FilterIterator<>(services.iterator(), service->service.getType().equalsIgnoreCase(type));

        if(s instanceof Thrifty){
            return findCheapest(it);
        }else {
            return findHighestRanked(it);
        }
    }

    public void evaluate(int star, String nameService, String description,int evaluateCounter) throws InvalidEvaluation, ServiceNotExists {
        if (star < 1 || star > 5) {
            throw new InvalidEvaluation();
        }
        checkIfServiceExists(nameService);

        Services s = findService(nameService);
        s.evaluate(star, description, evaluateCounter++,currentArea);
    }

    public Iterator<Services> getRanking() throws NoServicesInTheSystem {
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

    public Lodging findLodging(String name)throws LodgingDoesNotExist {
        Services s=findService(name);
        if(s instanceof Lodging){
            return (Lodging) s;
        }
        throw new LodgingDoesNotExist(name);
    }

    public TwoWayIterator<Students> listUsersByOrder(String place) throws ServiceNotExists, Empty {
        checkIfServiceExists(place);

        Services s = findService(place);
        if (s instanceof Leisure) {
            throw new DoesNotControlStudentEntry(s.getName());
        }
        DoublyLinkedList<Students> list = ((ServicesWithCapacity) s).getStudents();
        return list.twoWayiterator();
    }

    public Iterator<Services> listAllServices() throws NoServicesYet {
        return currentArea.getServices().iterator();
    }

    public void setCurrentArea(Area currentArea){
        this.currentArea=currentArea;
    }

    public Iterator<Services> getTag(String tag) throws NoServicesYet{
        DoublyLinkedList<Services> result = new DoublyLinkedList<>();
        List<Services> services = currentArea.getServices();

        char[] tagChars = tag.toLowerCase().toCharArray();

        for (int i = 0; i < services.size(); i++) {
            Services s = services.get(i);
            for (int j = 0; j < s.getReviews().size(); j++) {
                String review = s.getReviews().get(j);
                char[] reviewChars = review.toLowerCase().toCharArray();
                if (kmpWholeWord(reviewChars, tagChars)) {
                    result.addLast(s);
                    break;
                }
            }
        }
        return result.iterator();
    }

    private boolean kmpWholeWord(char[] text, char[] pattern) {
        if (pattern.length == 0) return true;
        if (text.length < pattern.length) return false;
        int[] lps = buildLps(pattern);
        int i = 0;
        int j = 0;
        while (i < text.length) {
            if (text[i] == pattern[j]) {
                i++;
                j++;
                if (j == pattern.length) {
                    int start = i - j;
                    int end = i - 1;
                    if (isWordBoundary(text, start - 1) && isWordBoundary(text, end + 1)) {
                        return true;
                    }
                    j = lps[j - 1];
                }
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return false;
    }

    private boolean isWordBoundary(char[] text, int index) {
        if (index < 0) return true;
        if (index >= text.length) {
            return true;
        }
        char c = text[index];
        return !Character.isLetterOrDigit(c);
    }

    private int[] buildLps(char[] pattern) {
        int[] lps = new int[pattern.length];
        int len = 0;
        int i = 1;

        while (i < pattern.length) {
            if (pattern[i] == pattern[len]) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }

    /**
     * Validates that the capacity of a service is greater than zero.
     *
     * @param capacity the capacity to be validated
     * @throws InvalidCapacity if the capacity is less than or equal to zero
     *
     * Time Complexity: O(1)
     */
    private void validateCapacity(int capacity) throws InvalidCapacity {
        if (capacity <= 0) {
            throw new InvalidCapacity();
        }
    }

    /**
     * Validates that the discount value is within the allowed range [0, 100].
     *
     * @param discount the discount percentage
     * @throws InvalidDiscount if the discount is outside the valid range
     *
     * Time Complexity: O(1)
     */
    private void validateDiscount(int discount) throws InvalidDiscount {
        if (discount < 0 || discount > 100) {
            throw new InvalidDiscount();
        }
    }

    /**
     * Validates that the price of a service is greater than zero.
     *
     * @param price the service price
     * @param message the error message to use in the exception
     * @throws InvalidPrice if the price is less than or equal to zero
     *
     * Time Complexity: O(1)
     */
    private void validatePrice(int price, String message) throws InvalidPrice {
        if (price <= 0) {
            throw new InvalidPrice(message);
        }
    }

    /**
     * Checks whether a service already exists in the current area.
     * If it exists, throws an exception.
     *
     * @param name the name of the service
     * @throws ServiceAlreadyExists if a service with the same name already exist
     * Time Complexity: O(1) average
     * (depends on currentArea.getServiceByName(), typically O(1) with hash tables)
     */
    private void serviceAlreadyExists(String name) throws ServiceAlreadyExists {
        Services s = findService(name);
        if (s != null) {
            throw new ServiceAlreadyExists(s.getName());
        }
    }

    /**
     * Checks whether a service exists in the current area.
     * If it does not exist, throws an exception.
     *
     * @param name the name of the service
     * @throws ServiceNotExists if the service does not exist
     *
     * Time Complexity: O(1) average
     */
    private void checkIfServiceExists(String name) throws ServiceNotExists {
        Services s = findService(name);
        if (s == null) {
            throw new ServiceNotExists(name);
        }
    }

    /**
     * Finds the cheapest service from a filtered iterator.
     * Assumes the iterator contains at least one element.
     *
     * @param it filtered iterator of services
     * @return the service with the lowest price
     * Time Complexity: O(n)
     * (must iterate through all filtered services)
     */
    private Services findCheapest(FilterIterator<Services> it) {
        Services cheapest = it.next();
        while (it.hasNext()) {
            Services s = it.next();
            if (s.getPrice() < cheapest.getPrice()) {
                cheapest = s;
            }
        }
        return cheapest;
    }

    /**
     * Finds the highest-ranked service from a filtered iterator.
     * If two services have the same evaluation, returns the one
     * evaluated least recently.
     *
     * @param it filtered iterator of services
     * @return the highest-ranked service
     *
     * Time Complexity: O(n)
     */
    private Services findHighestRanked(FilterIterator<Services> it) {
        Services highest = it.next();
        while (it.hasNext()) {
            Services s = it.next();
            if (s.getEvaluation() > highest.getEvaluation()) {
                highest = s;
            }
            if (s.getEvaluation() == highest.getEvaluation()
                    && s.getLastEvaluated() < highest.getLastEvaluated()) {
                highest = s;
            }
        }
        return highest;
    }

    /**
     * Adds a service to the current area.
     *
     * @param s the service to add
     * Time Complexity: depends on underlying collection
     * Typically O(1) if ArrayList or hash table.
     */
    private void addServiceToCurrentArea(Services s) {
        currentArea.addService(s);
    }

    /**
     * Validates that a latitude/longitude combination is inside the area bounds.
     *
     * @param latitude the service latitude
     * @param longitude the service longitude
     * @throws InvalidLocationArea if the coordinates are outside the area's limits
     *
     * Time Complexity: O(1)
     */
    private void validateBounds(long latitude, long longitude)
            throws InvalidBounds, InvalidLocationArea {

        if (!(longitude >= currentArea.getLeftLongitude() &&
                longitude <= currentArea.getRightLongitude() &&
                latitude <= currentArea.getTopLatitude() &&
                latitude >= currentArea.getBottomLatitude())) {

            throw new InvalidLocationArea();
        }
    }

}
