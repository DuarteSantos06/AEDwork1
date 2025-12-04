/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */
package Package;

import Package.Exceptions.*;
import Package.Services.*;
import Package.Students.*;
import dataStructures.*;

import java.io.*;

public class HomeAway implements HomeAwayInterface {

    private Area currentArea;
    private final int evaluateCounter;
    private final ServicesApp servicesApp;
    private final StudentsApp studentsApp;

    public HomeAway() {
        this.evaluateCounter = 0;
        servicesApp = new ServicesApp();
        studentsApp = new StudentsApp();
    }

    public void createArea(String name, long topLatitude, long leftLongitude, long bottomLatitude, long rightLongitude) throws AreaAlreadyExists, InvalidBounds {
        try {
            if (currentArea != null) {
                String saved = saveArea();
            }
        } catch (Exception ignored) {}
        String filename = "data/" + name.toLowerCase().replace(" ", "_") + ".ser";
        File file = new File(filename);


        if (file.exists()) {
            throw new AreaAlreadyExists();
        }
        if (leftLongitude >= rightLongitude || topLatitude <= bottomLatitude) {
            throw new InvalidBounds();
        }

        currentArea = new Area(name, topLatitude, leftLongitude, bottomLatitude, rightLongitude);
        servicesApp.setCurrentArea(currentArea);
        studentsApp.setCurrentArea(currentArea);
    }

    public String saveArea() throws NoBoundsInTheSystem {
        if (currentArea == null) {
            throw new NoBoundsInTheSystem();
        }
        File dataFolder = new File("data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        String fileName = getFileName(currentArea.getName());

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(currentArea);
        } catch (IOException ignored) {}
        return currentArea.getName();
    }

    public String loadArea(String name) throws AreaDoesNotExist,NoBoundsInTheSystem {
        if (currentArea != null) {
            saveArea();
        }

        String fileName = getFileName(name);
        File file = new File(fileName);

        if (!file.exists()) {
            throw new AreaDoesNotExist(name);
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            currentArea = (Area) in.readObject();
        } catch (Exception ignored) {}

        servicesApp.setCurrentArea(currentArea);
        studentsApp.setCurrentArea(currentArea);
        return currentArea.getName();
    }

    private String getFileName(String name){
        return "data/" + name.toLowerCase().replace(" ", "_") + ".ser";
    }

    public void createEating(long latitude, long longitude, int price, int capacity, String name) throws InvalidLocationArea,InvalidPrice, InvalidBounds, InvalidCapacity, ServiceAlreadyExists {
        servicesApp.createEating(latitude, longitude, price, capacity, name);
    }

    public void createLodging(long latitude, long longitude, int price, int capacity, String name) throws InvalidLocationArea,InvalidPrice, InvalidBounds, InvalidCapacity, ServiceAlreadyExists {
        servicesApp.createLodging(latitude, longitude, price, capacity, name);
    }

    public void createLeisure(long latitude, long longitude, int price, int discount, String name) throws InvalidLocationArea,InvalidPrice, InvalidBounds, InvalidDiscount, ServiceAlreadyExists {
        servicesApp.createLeisure(latitude, longitude, price, discount, name);
    }

    public Iterator<Services> listAllServices() throws NoServicesYet, NoBoundsInTheSystem {
        if (currentArea == null) {
            throw new NoBoundsInTheSystem();
        }
        return servicesApp.listAllServices();
    }

    public void createStudent(String type, String name, String country, String lodging)
            throws InvalidStudentType, LodgingDoesNotExist, StudentAlreadyExists,ServiceIsFull,StudentNotFound,NoServicesYet{

        Lodging home=servicesApp.findLodging(lodging);
        studentsApp.createStudent(type, name, country, home);
    }

    public String leave(String name) throws StudentNotFound {
        return studentsApp.leave(name);
    }

    public Iterator<Students> listAllStudents() throws NoStudentsYet, NoBoundsInTheSystem {
        return studentsApp.listAllStudents();
    }

    public Iterator<Students> listStudentsByCountry(String country) throws NoStudentsFromCountry {
        return currentArea.getStudentsByRegistrationByCountry(country).iterator();
    }

    public boolean go(String name, String location) throws StudentNotFound, InvalidLocation, AlreadyThere, ServiceIsFull {
        Services service = servicesApp.findService(location);
        return studentsApp.go(name, location, service);
    }

    public String getNameService(String location) {
        Services s = servicesApp.findService(location);
        return s.getName();
    }

    public String getNameStudent(String name)throws StudentNotFound {
        Students s = studentsApp.findStudent(name);
        return s.getName();
    }

    public String move(String name, String location) throws StudentNotFound, InvalidLocation, ServiceIsFull, CantMove,LodgingDoesNotExist {
        Services service = servicesApp.findService(location);
        if (service == null) {
            throw new LodgingDoesNotExist(location);
        }
        return studentsApp.move(name, service,location);
    }

    public TwoWayIterator<Students> listUsersByOrder(String place) throws ServiceNotExists, Empty {
        return servicesApp.listUsersByOrder(place);
    }

    public Services where(String name) throws StudentNotFound {
        return studentsApp.findStudent(name).getLocation();
    }

    public Iterator<Services> getVisited(String name) throws StudentNotFound, HasNotVisitedLocations {
        return studentsApp.getVisited(name);
    }

    public void evaluate(int star, String nameService, String description) throws ServiceNotExists {
        servicesApp.evaluate(star, nameService, description,evaluateCounter);
    }

    public Iterator<Services> getRanking() throws NoServicesInTheSystem {
        return servicesApp.getRanking();
    }

    public Iterator<Services>getRanked(String type,int star,String name)throws InvalidStar,StudentNotFound,InvalidType,ServiceNotExists,NoServicesInTheSystem{
        if(star<1||star>5){
            throw new InvalidStar();
        }
        return studentsApp.getRanked(type,star,name);
    }

    public Iterator<Services> getTag(String tag) throws NoServicesYet{
        return servicesApp.getTag(tag);
    }

    public Services find(String name,String type)throws StudentNotFound,NoServicesYet{
        Students student=studentsApp.findStudent(name);
        return servicesApp.find(type,student);
    }
}
