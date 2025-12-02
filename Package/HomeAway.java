//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt
package Package;

import Package.Exceptions.*;
import Package.Services.*;
import Package.Students.*;
import Package.Exceptions.Expensive;
import dataStructures.*;

import java.io.*;

public class HomeAway implements HomeAwayInterface {

    private static final String THRIFTY = "thrifty";
    private static final String OUTGOING = "outgoing";




    private StudentsType studentsType;
    private ServicesType servicesType;
    private Area currentArea;
    private int evaluateCounter;

    public HomeAway() {
        this.evaluateCounter = 0;
    }

    /**
     * @param name           the name of the area
     * @param topLatitude    northern latitude of the area
     * @param leftLongitude  western longitude of the area
     * @param bottomLatitude southern latitude of the area
     * @param rightLongitude eastern longitude of the area
     * @throws AreaAlreadyExists if an area file already exists with this name
     * @throws InvalidLocation   if the given boundaries are invalid
     */
    public void createArea(String name, long topLatitude, long leftLongitude, long bottomLatitude, long rightLongitude) throws AreaAlreadyExists, InvalidLocation {

        try {
            if (currentArea != null) {
                String saved = saveArea();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String filename = "data/" + name.toLowerCase().replace(" ", "_") + ".ser";
        File file = new File(filename);


        if (file.exists()) {
            throw new AreaAlreadyExists("Bounds already exists. Please load it!");
        }
        if (leftLongitude >= rightLongitude || topLatitude <= bottomLatitude) {
            throw new InvalidLocation("Invalid bounds.");

        }

        currentArea = new Area(name, topLatitude, leftLongitude, bottomLatitude, rightLongitude);
    }

    /**
     * @return the name of the saved area
     * @throws NoBoundsInTheSystem if no current area exists to save
     */
    public String saveArea() throws NoBoundsInTheSystem {
        if (currentArea == null) {
            throw new NoBoundsInTheSystem("System bounds not defined.");
        }

        File dataFolder = new File("data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        String fileName = "data/" + currentArea.getName().toLowerCase().replace(" ", "_") + ".ser";

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(currentArea);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currentArea.getName();
    }

    /**
     * @param name the name of the area to load
     * @return the name of the loaded area
     * @throws NoBoundsInTheSystem if the requested area does not exist
     */
    public String loadArea(String name) throws NoBoundsInTheSystem {
        if (currentArea != null) {
            saveArea();
        }

        String fileName = "data/" + name.toLowerCase().replace(" ", "_") + ".ser";
        File file = new File(fileName);

        if (!file.exists()) {
            throw new NoBoundsInTheSystem("Bounds " + name + " does not exist!");
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {

            currentArea = (Area) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return currentArea.getName();
    }


    /**
     * @param latitude  latitude of the new service
     * @param longitude longitude of the new service
     * @param price     menu price for the eating service
     * @param capacity  service capacity
     * @param name      name of the service
     * @throws InvalidPrice         if price is not positive
     * @throws InvalidLocation      if the coordinates are outside the area
     * @throws InvalidCapacity      if capacity is not positive
     * @throws ServiceAlreadyExists if a service with the same name exists
     */
    public void createEating(long latitude, long longitude, int price, int capacity, String name) throws InvalidPrice, InvalidLocation, InvalidCapacity, ServiceAlreadyExists {
        Services s = findService(name);
        if (isInValidBounds(latitude, longitude)) {
            throw new InvalidLocation("Invalid location!");
        } else if (price <= 0) {
            throw new InvalidPrice("Invalid menu price!");
        } else if (capacity <= 0) {
            throw new InvalidCapacity("Invalid capacity!");
        } else if (s != null) {
            throw new ServiceAlreadyExists(s.getName() + " already exists!");
        }
        Eating eating = new Eating(latitude, longitude, price, capacity, name);
        currentArea.addService(eating);
    }


    /**
     * @param latitude  latitude of the new service
     * @param longitude longitude of the new service
     * @param price     room price for the lodging service
     * @param capacity  service capacity
     * @param name      name of the service
     * @throws InvalidPrice         if price is not positive
     * @throws InvalidLocation      if the coordinates are outside the area
     * @throws InvalidCapacity      if capacity is not positive
     * @throws ServiceAlreadyExists if a service with the same name exists
     */
    public void createLodging(long latitude, long longitude, int price, int capacity, String name) throws InvalidPrice, InvalidLocation, InvalidCapacity, ServiceAlreadyExists {
        Services s = findService(name);
        if (isInValidBounds(latitude, longitude)) {
            throw new InvalidLocation("Invalid location!");
        } else if (price <= 0) {
            throw new InvalidPrice("Invalid room price!");
        } else if (capacity <= 0) {
            throw new InvalidCapacity("Invalid capacity!");
        } else if (s != null) {
            throw new ServiceAlreadyExists(s.getName() + " already exists!");
        }
        Lodging lodging = new Lodging(latitude, longitude, price, capacity, name);
        currentArea.addService(lodging);
    }

    /**
     * @param latitude  latitude of the new service
     * @param longitude longitude of the new service
     * @param price     ticket price for the leisure service
     * @param discount  percentage discount (0-100)
     * @param name      name of the service
     * @throws InvalidPrice         if price is not positive
     * @throws InvalidLocation      if the coordinates are outside the area
     * @throws InvalidDiscount      if discount is out of bounds
     * @throws ServiceAlreadyExists if a service with the same name exists
     */
    public void createLeisure(long latitude, long longitude, int price, int discount, String name) throws InvalidPrice, InvalidLocation, InvalidDiscount, ServiceAlreadyExists {
        Services s = findService(name);
        if (isInValidBounds(latitude, longitude)) {
            throw new InvalidLocation("Invalid location!");
        } else if (price <= 0) {
            throw new InvalidPrice("Invalid ticket price!");
        } else if (discount < 0 || discount > 100) {
            throw new InvalidDiscount("Invalid discount price!");
        } else if (s != null) {
            throw new ServiceAlreadyExists(s.getName() + " already exists!");
        }
        Leisure leisure = new Leisure(latitude, longitude, price, discount, name);
        currentArea.addService(leisure);
    }

    /**
     * @param latitude
     * @param longitude
     * @return
     */
    private boolean isInValidBounds(long latitude, long longitude) {
        return !(longitude >= currentArea.getLeftLongitude() &&
                longitude <= currentArea.getRightLongitude() &&
                latitude <= currentArea.getTopLatitude() &&
                latitude >= currentArea.getBottomLatitude());
    }

    /**
     * @return
     * @throws NoToList
     * @throws NoBoundsInTheSystem
     */
    public Iterator<Services> listAllServices() throws NoToList, NoBoundsInTheSystem {
        if (currentArea == null) {
            throw new NoBoundsInTheSystem("System bounds not defined.");
        }
        Iterator<Services> it = currentArea.getServices().iterator();
        if (!it.hasNext()) {
            throw new NoToList("No services yet!");
        }
        return it;
    }

    /**
     * @param type
     * @param name
     * @param country
     * @param lodging
     * @throws InvalidStudentType
     * @throws LodgingNotExists
     * @throws LodgingIsFull
     * @throws StudentAlreadyExists
     */
    public void createStudent(String type, String name, String country, String lodging) throws InvalidStudentType, LodgingNotExists, StudentAlreadyExists,InvalidLocation{
        Lodging home = findLodging(lodging);
        Students s = findStudent(name);
        StudentsType studentType = StudentsType.fromString(type);
        if (studentType == null) {
            throw new InvalidStudentType("Invalid student type!");
        } else if (home == null) {
            throw new LodgingNotExists("lodging " + lodging + " does not exist!");
        } else if (s != null) {
            throw new StudentAlreadyExists(s.getName() + " already exists!");
        } else {
            Students student;
            if (studentType.equals(StudentsType.THRIFTY)) {
                student = new Thrifty(name, country, lodging, home);
            } else if (studentType.equals(StudentsType.OUTGOING)) {
                student = new Outgoing(name, country, lodging, home);
                ((Outgoing) student).addVisited(home);
            } else {
                student = new Bookish(name, country, lodging, home);
            }
            home.addStudent(student);
            currentArea.addStudent(student);
        }
    }


    /**
     * @param name
     * @return
     * @throws StudentNotFound
     */
    public String leave(String name) throws StudentNotFound {
        Students s = findStudent(name);
        if (s == null) {
            throw new StudentNotFound(name + " does not exist!");
        }
        String nameToReturn = s.getName();
        s.leave();
        currentArea.removeStudent(s);
        return nameToReturn;
    }

    /**
     * @param name
     * @return
     */
    private Lodging findLodging(String name) {
        List<Services> services = currentArea.getServices();
        for (int i = 0; i < services.size(); i++) {
            Services s = services.get(i);
            if (s.getName().equalsIgnoreCase(name) && s instanceof Lodging) {
                return (Lodging) s;
            }
        }
        return null;
    }

    /**
     * @return
     * @throws NoToList
     * @throws NoBoundsInTheSystem
     */
    public Iterator<Students> listAllStudents() throws NoToList, NoBoundsInTheSystem {
        if (currentArea == null) {
            throw new NoBoundsInTheSystem("System bounds not defined.");
        }
        return currentArea.getStudents().values();
    }

    /**
     * @param country
     * @return
     * @throws NoToList
     */
    public Iterator<Students> listStudentsByCountry(String country) throws NoToList {
        return currentArea.getStudentsByRegistrationByCountry(country).iterator();
    }

    /**
     * @param name
     * @param location
     * @return
     * @throws StudentNotFound
     * @throws InvalidLocation
     * @throws AlreadyThere
     * @throws Expensive
     */
    public boolean go(String name, String location) throws StudentNotFound, InvalidLocation, AlreadyThere, Expensive {
        Students s = findStudent(name);
        Services service = findService(location);
        if (service == null) {
            throw new InvalidLocation("Unknown " + location + '!');
        } else if (s == null) {
            throw new StudentNotFound(name + " does not exist!");
        } else if (service instanceof Lodging) {
            throw new InvalidLocation(location + " is not a valid service!");
        } else if (s.getLocation().getName().equals(location)) {
            throw new AlreadyThere("Already there!");
        }

        Eating eating = s.getCheapestEating();
        s.go(service);

        return eating != null && s instanceof Thrifty && eating.getPrice() < service.getPrice() && service instanceof Eating;

    }


    public String getNameService(String location) {
        Services s = findService(location);
        if (s == null) {
            return null;
        }
        return s.getName();
    }

    public String getNameStudent(String name) {
        Students s = findStudent(name);
        if (s == null) {
            return null;
        }
        return s.getName();
    }

    /**
     * @param name
     * @param location
     * @return
     * @throws StudentNotFound
     * @throws InvalidLocation
     * @throws LodgingIsFull
     * @throws CantMove
     */
    public String move(String name, String location) throws StudentNotFound, InvalidLocation, LodgingIsFull, CantMove {
        Students s = findStudent(name);
        Services service = findService(location);
        if (service == null) {
            throw new InvalidLocation("lodging " + location + " does not exist!");
        } else if (s == null) {
            throw new StudentNotFound(name + " does not exist!");
        } else if (s.getHome().getName().equals(location)) {
            throw new InvalidLocation("That is " + s.getName() + "'s home!");
        } else if (service instanceof Lodging && s instanceof Thrifty && s.getHome().getPrice() < service.getPrice()) {
            throw new CantMove("Move is not acceptable for " + name + '!');
        }

        s.move((Lodging) service);
        return service.getName();
    }

    /**
     * @param place
     * @return
     * @throws ServiceNotExists
     * @throws InvalidLocation
     * @throws Empty
     */
    public TwoWayIterator<Students> listUsersByOrder(String place) throws ServiceNotExists, InvalidLocation, Empty {
        Services s = findService(place);
        if (s == null) {
            throw new ServiceNotExists(place + " does not exist!");
        } else if (s instanceof Leisure) {
            throw new InvalidLocation(s.getName() + " does not control student entry and exit!");
        }
        DoublyLinkedList<Students> list = ((ServicesWithCapacity) s).getStudents();
        return list.twoWayiterator();
    }

    /**
     * @param name
     * @return
     * @throws StudentNotFound
     */
    public Services where(String name) throws StudentNotFound {
        Students s = findStudent(name);
        if (s == null) {
            throw new StudentNotFound(name + " does not exist!");
        }
        return s.getLocation();
    }



    /**
     * @param name
     * @return
     */
    private Students findStudent(String name) {
        if (name == null) {
            return null;
        }
        return currentArea.getStudentByName(name.toLowerCase().trim());
    }

    /**
     * @param name
     * @return
     */
    private Services findService(String name) {
        return currentArea.getServiceByName(name.toLowerCase().trim());
    }

    /**
     * @param name
     * @return
     * @throws StudentNotFound
     * @throws InvalidStudentType
     * @throws NoToList
     */
    public Iterator<Services> getVisited(String name) throws StudentNotFound, InvalidStudentType, NoToList {
        Students s = findStudent(name);
        if (s == null) {
            throw new StudentNotFound(name + " does not exist!");
        } else if (s instanceof Thrifty) {
            throw new InvalidStudentType(s.getName() + " is thrifty!");
        }
        ListInArray<Services> visited = ((StudentsKeepVisited) s).getVisited();
        return visited.iterator();
    }

    /**
     * @param star        the star value of the evaluation (1-5)
     * @param nameService the service name
     * @param description review description
     * @throws InvalidStar      if the star rating is out of bounds
     * @throws ServiceNotExists if the service doesn't exist
     */
    public void evaluate(int star, String nameService, String description) throws InvalidStar, ServiceNotExists {
        Services s = findService(nameService);
        if (star < 1 || star > 5) {
            throw new InvalidStar("Invalid evaluation!");
        }
        if (s == null) {
            throw new ServiceNotExists(nameService + " does not exist!");
        }
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


    public Iterator<Services>getRanked(String type,int star,String name)throws InvalidStar,StudentNotFound,InvalidType,ServiceNotExists,NoToList{

        Students student = findStudent(name);
        ServicesType serviceType = ServicesType.fromString(type);
        if(star<1||star>5){
            throw new InvalidStar("Invalid stars!");
        }else if(student==null){
            throw new StudentNotFound(name+" does not exist!");
        }else if(serviceType==null){
            throw new InvalidType("Invalid service type!");
        }
        List<Services> servicesWithStar = currentArea.getServicesByStar().get(star);
        if (servicesWithStar == null || servicesWithStar.isEmpty()) {
            throw new ServiceNotExists("No " + type + " services with average!");
        }
        FilterIterator<Services>it=new FilterIterator<>(servicesWithStar.iterator(),s->s.getType().equalsIgnoreCase(type));
        if(!it.hasNext()){
            throw new ServiceNotExists("No "+type+" services!");
        }



        DoublyLinkedList<Services>result=closest(it,student);



        return result.iterator();
    }

    /**
     *
     * @param itByStar
     * @param student
     * @return
     */
    private DoublyLinkedList<Services> closest(FilterIterator<Services>itByStar,Students student){
        DoublyLinkedList<Services> closest = new DoublyLinkedList<>();
        long minDistance = Long.MAX_VALUE;

        while(itByStar.hasNext()){
            Services s = itByStar.next();
            long distance = Math.abs(s.getLatitude() - student.getLocation().getLatitude())
                    + Math.abs(s.getLongitude() - student.getLocation().getLongitude());

            if(distance < minDistance){
                closest = new DoublyLinkedList<>();
                closest.addLast(s);
                minDistance = distance;
            } else if(distance == minDistance){
                closest.addLast(s);
            }
        }
        for(int i = 0; i < closest.size() - 1; i++){
            for(int j = 0; j < closest.size() - 1 - i; j++){
                Services a = closest.get(j);
                Services b = closest.get(j+1);
                if(a.getLastEvaluated() > b.getLastEvaluated()){
                    closest.add(j, b);
                    closest.add(j+1, a);
                }
            }
        }
        return closest;
    }

    public Iterator<Services> getTag(String tag) {
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

                    if (isWordBoundary(text, start - 1) &&
                            isWordBoundary(text, end + 1)) {
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
        if (index >= text.length) return true;

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
     *
     * @param name
     * @param type
     * @return
     */
    public Services find(String name,String type){
        FilterIterator<Services>it= new FilterIterator<>(currentArea.getServices().iterator(),s->s.getType().equalsIgnoreCase(type));
        Students s=findStudent(name);

        if(s instanceof Thrifty){
            return findCheapest(it);
        }else {
            return findHighestRanked(it);
        }
    }

    /**
     *
     * @param it
     * @return
     */
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

    /**
     *
     * @param it
     * @return
     */
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


}
