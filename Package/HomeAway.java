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

    private Area currentArea;
    private final int evaluateCounter;
    private final ServicesApp servicesApp;
    private final StudentsApp studentsApp;

    public HomeAway() {
        this.evaluateCounter = 0;
        servicesApp = new ServicesApp();
        studentsApp = new StudentsApp();
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
        } catch (Exception ignored) {}
        String filename = "data/" + name.toLowerCase().replace(" ", "_") + ".ser";
        File file = new File(filename);


        if (file.exists()) {
            throw new AreaAlreadyExists("Bounds already exists. Please load it!");
        }
        if (leftLongitude >= rightLongitude || topLatitude <= bottomLatitude) {
            throw new InvalidLocation("Invalid bounds.");

        }

        currentArea = new Area(name, topLatitude, leftLongitude, bottomLatitude, rightLongitude);
        servicesApp.setCurrentArea(currentArea);
        studentsApp.setCurrentArea(currentArea);
    }

    public String saveArea() throws NoBoundsInTheSystem {
        if (currentArea == null) {
            throw new NoBoundsInTheSystem("System bounds not defined.");
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

    public String loadArea(String name) throws NoBoundsInTheSystem {
        if (currentArea != null) {
            saveArea();
        }

        String fileName = getFileName(name);
        File file = new File(fileName);

        if (!file.exists()) {
            throw new NoBoundsInTheSystem("Bounds " + name + " does not exist!");
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

    public void createEating(long latitude, long longitude, int price, int capacity, String name) throws InvalidPrice, InvalidLocation, InvalidCapacity, ServiceAlreadyExists {
        servicesApp.createEating(latitude, longitude, price, capacity, name);
    }

    public void createLodging(long latitude, long longitude, int price, int capacity, String name) throws InvalidPrice, InvalidLocation, InvalidCapacity, ServiceAlreadyExists {
        servicesApp.createLodging(latitude, longitude, price, capacity, name);
    }

    public void createLeisure(long latitude, long longitude, int price, int discount, String name) throws InvalidPrice, InvalidLocation, InvalidDiscount, ServiceAlreadyExists {
        servicesApp.createLeisure(latitude, longitude, price, discount, name);
    }

    public Iterator<Services> listAllServices() throws NoToList, NoBoundsInTheSystem {
        if (currentArea == null) {
            throw new NoBoundsInTheSystem("System bounds not defined.");
        }
        return servicesApp.listAllServices();
    }

    public void createStudent(String type, String name, String country, String lodging)
            throws InvalidStudentType, LodgingNotExists, StudentAlreadyExists,InvalidLocation,StudentNotFound,NoToList{

        Lodging home=servicesApp.findLodging(lodging);
        studentsApp.createStudent(type, name, country, home);
    }

    public String leave(String name) throws StudentNotFound {
        return studentsApp.leave(name);
    }

    public Iterator<Students> listAllStudents() throws NoToList, NoBoundsInTheSystem {
        return studentsApp.listAllStudents();
    }

    public Iterator<Students> listStudentsByCountry(String country) throws NoToList {
        return currentArea.getStudentsByRegistrationByCountry(country).iterator();
    }

    public boolean go(String name, String location) throws StudentNotFound, InvalidLocation, AlreadyThere, Expensive {
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

    public String move(String name, String location) throws StudentNotFound, InvalidLocation, LodgingIsFull, CantMove {
        Services service = servicesApp.findService(location);
        return studentsApp.move(name, service,location);
    }

    public TwoWayIterator<Students> listUsersByOrder(String place) throws ServiceNotExists, InvalidLocation, Empty {
        return servicesApp.listUsersByOrder(place);
    }

    public Services where(String name) throws StudentNotFound {
        Students s = studentsApp.findStudent(name);
        return s.getLocation();
    }

    public Iterator<Services> getVisited(String name) throws StudentNotFound, InvalidStudentType, NoToList {
        return studentsApp.getVisited(name);
    }

    public void evaluate(int star, String nameService, String description) throws InvalidStar, ServiceNotExists {
        servicesApp.evaluate(star, nameService, description,evaluateCounter);
    }

    public Iterator<Services> getRanking() throws NoToList {
        return servicesApp.getRanking();
    }

    public Iterator<Services>getRanked(String type,int star,String name)throws InvalidStar,StudentNotFound,InvalidType,ServiceNotExists,NoToList{
        if(star<1||star>5){
            throw new InvalidStar("Invalid stars!");
        }
        return studentsApp.getRanked(type,star,name);
    }

    public Iterator<Services> getTag(String tag) throws NoToList{
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

    public Services find(String name,String type)throws StudentNotFound,NoToList{
        Students student=studentsApp.findStudent(name);
        return servicesApp.find(type,student);
    }
}
