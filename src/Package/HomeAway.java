package Package;

import Package.Exceptions.*;
import Package.Services.*;
import Package.Students.*;
import dataStructures.*;
import Package.Exceptions.Expensive;

import java.io.*;

public class HomeAway implements HomeAwayInterface{

    private static final String THRIFTY="thrifty";
    private static final String OUTGOING="outgoing";
    private static final String BOOKISH="bookish";
    private static final String LEISURE="leisure";
    private static final String EATING="eating";
    private static final String LODGING="lodging";


    private Area currentArea;
    private int evaluateCounter;

    public HomeAway() {
        this.evaluateCounter=0;
    }

    public void createArea(String name, long topLatitude, long leftLongitude, long bottomLatitude, long rightLongitude)throws AreaAlreadyExists,InvalidLocation {
        if(false){
            throw new AreaAlreadyExists("Bounds already exists. Please load it!");
        }
        if(leftLongitude >= rightLongitude || topLatitude <= bottomLatitude){
            throw new InvalidLocation("Invalid bounds.");

        }
        try{
            if(currentArea!=null){
                String saved=saveArea();
            }
        }catch(Exception e){
        }
        currentArea = new Area(name,topLatitude,leftLongitude,bottomLatitude,rightLongitude);
    }

    public String saveArea() throws NoBoundsInTheSystem {
        if (currentArea == null) {
            throw new NoBoundsInTheSystem("System bounds not defined.");
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/"+currentArea.getName().replace(" ", "_")+".ser"))) {
            out.writeObject(currentArea);
        } catch (IOException e) {

        }
        return currentArea.getName();
    }

    public String  loadArea(String name) throws NoBoundsInTheSystem {
        if (currentArea != null) {
            saveArea();
        }
        String fileName = "data/" + name.replace(" ", "_") + ".ser";
        File file = new File(fileName);

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            currentArea = (Area) in.readObject();;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return currentArea.getName();

    }
    
    public void createEating(long latitude, long longitude, int price, int capacity, String name)throws InvalidPrice,InvalidLocation,InvalidCapacity,ServiceAlreadyExists {
        if (isInValidBounds(latitude, longitude)) {
            throw new InvalidLocation("Invalid location!");
        } else if (price <= 0) {
            throw new InvalidPrice("Invalid menu price!");
        } else if (capacity <= 0) {
            throw new InvalidCapacity("Invalid capacity!");
        }else if(alreadyExists(name)){
            throw new ServiceAlreadyExists(findService(name).getName()+" already exists!");
        }
        Eating eating = new Eating(latitude,longitude,price,capacity,name);
        currentArea.addService(eating);


    }

    public void createLodging(long latitude, long longitude, int price, int capacity, String name)throws InvalidPrice,InvalidLocation,InvalidCapacity,ServiceAlreadyExists{
        if (isInValidBounds(latitude, longitude)) {
            throw new InvalidLocation("Invalid location!");
        } else if (price <= 0) {
            throw new InvalidPrice("Invalid room price!");
        } else if (capacity <= 0) {
            throw new InvalidCapacity("Invalid capacity!");
        }else if(alreadyExists(name)){
            throw new ServiceAlreadyExists(findService(name).getName()+" already exists!");
        }
        Lodging lodging = new Lodging(latitude,longitude,price,capacity,name);
        currentArea.addService(lodging);
    }

    public void createLeisure(long latitude, long longitude,int price,int discount, String name)throws InvalidPrice,InvalidLocation,InvalidDiscount,ServiceAlreadyExists{
        if (isInValidBounds(latitude, longitude)) {
            throw new InvalidLocation("Invalid location!");
        } else if (price <= 0) {
            throw new InvalidPrice("Invalid ticket price!");
        } else if (discount < 0 || discount > 100) {
            throw new InvalidDiscount("Invalid discount price!");
        }else if(alreadyExists(name)){
            throw new ServiceAlreadyExists(findService(name).getName()+" already exists!");
        }
        Leisure leisure=new Leisure(latitude,longitude,price,discount,name);
        currentArea.addService(leisure);
    }

    private boolean alreadyExists(String name){
        List<Services>services=currentArea.getServices();
        for(int i=0;i<services.size();i++){
            Services s=services.get(i);
            if(s.getName().equalsIgnoreCase(name.toUpperCase())){
                return true;
            }
        }
        return false;
    }

    private boolean isInValidBounds(long latitude, long longitude) {
        return !(longitude >= currentArea.getLeftLongitude() &&
                longitude <= currentArea.getRightLongitude() &&
                latitude <= currentArea.getTopLatitude() &&
                latitude >= currentArea.getBottomLatitude());
    }

    public Iterator<Services> listAllServices()throws NoToList{
        Iterator<Services>it=currentArea.getServices().iterator();
        if(!it.hasNext()){
            throw new NoToList("No services yet!");
        }
        return it;
    }

    public void createStudent(String type,String name,String country,String lodging)throws InvalidStudentType,LodgingNotExists,LodgingIsFull,StudentAlreadyExists{
        if(!type.equals(THRIFTY) && !type.equals(OUTGOING)&&!type.equals(BOOKISH)){
            throw new InvalidStudentType("Invalid student type!");
        }else if(!lodgingExists(lodging)){
            throw new LodgingNotExists("lodging "+lodging+" does not exist!");
        }else if(lodgingIsFull(lodging)){
            throw new LodgingIsFull("Lodging "+lodging+" is full!");
        }else if(alreadyExistsStudent(name)){
            Students s=findStudent(name);
            throw new StudentAlreadyExists(s.getName()+"already exists!");
        } else{
            Students student;
            Lodging home=findLodging(lodging);
            if(type.equals(THRIFTY)){
                student = new Thrifty(name,country,lodging,home);
            }else if(type.equals(OUTGOING)){
                student = new Outgoing(name,country,lodging,home);
                ((Outgoing)student).addVisited(home);
            }else {
                student = new Bookish(name, country, lodging, home);
            }
            currentArea.addStudent(student);
            home.addStudent(student);


        }
    }

    private boolean alreadyExistsStudent(String name){
        Iterator<Students>it=currentArea.getStudents().iterator();
        while(it.hasNext()){
            Students s=it.next();
            if(s.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public String leave(String name) throws StudentNotFound {
        Students s=findStudent(name);
        if(s==null){
            throw new StudentNotFound(name+"does not exist!");
        }
        String nameToReturn=s.getName();
        s.leave();
        currentArea.removeStudent(s);
        return nameToReturn;
    }

    private Lodging findLodging(String name){
        List<Services>services=currentArea.getServices();
        for(int i=0;i<services.size();i++){
            Services s=services.get(i);
            if(s.getName().equalsIgnoreCase(name.toUpperCase()) && s instanceof Lodging){
                return (Lodging)s;
            }
        }
        return null;
    }

    private boolean lodgingExists(String name){
        List<Services>services=currentArea.getServices();
        for(int i=0;i<services.size();i++){
            Services s=services.get(i);
            if(s.getName().equalsIgnoreCase(name) && s instanceof Lodging){
                return true;
            }
        }
        return false;
    }

    private boolean lodgingIsFull(String name){
        Lodging lodging=findLodging(name);
        return lodging.getCapacity()<=lodging.getCurrentOccupation();
    }


    public Iterator<Students> listAllStudents()throws NoToList {
        Iterator<Students>it=currentArea.getStudents().iterator();
        if(!it.hasNext()){
            throw new NoToList("No students yet!");
        }
        return it;
    }

    public Iterator<Students> listStudentsByCountry(String country)throws NoToList {
        FilterIterator<Students>it= new FilterIterator<>(currentArea.getStudentsByRegistration().iterator(),s->s.getCountry().equalsIgnoreCase(country));
        if(!it.hasNext()){
            throw new NoToList("No students from "+country+'!');
        }
        return it;
    }

    public String  go(String name,String location) throws StudentNotFound,InvalidLocation,AlreadyThere,Expensive{
        Students s=findStudent(name);
        Services service=findService(location);
        if(service==null){
            throw new InvalidLocation("Unknown "+location+ '!');
        }
        else if(s==null){
            throw new StudentNotFound(name+" does not exist!");
        }else if(service instanceof Lodging){
            throw new InvalidLocation(location+" is not a valid service!");
        }
        else if(s.getLocation().getName().equals(location)){
            throw new AlreadyThere("Already there!");
        }else if(service instanceof Eating){
            if(((Eating)service).isFull()){
                throw new InvalidLocation("eating "+location+ " is full!");
            }
        }

        Eating eating=s.getCheapestEating();
        s.go(service);

        if(eating!=null){
            if(s instanceof Thrifty && eating.getPrice()<service.getPrice() && service instanceof Eating){
                throw new Expensive(name+" is now at "+ location+". "+name+" is distracted!");
            }
        }
        return service.getName();

    }

    public String move(String name,String location) throws StudentNotFound,InvalidLocation,LodgingIsFull,CantMove{
        Students s=findStudent(name);
        Services service=findService(location);
        if(service==null){
            throw new InvalidLocation("lodging "+location+" does not exist!");
        }
        else if(s==null){
            throw new StudentNotFound(name+" does not exist!");
        }
        else if(s.getLocation().getName().equals(location)){
            throw new InvalidLocation("That is "+name+ "'s home!");
        }else if(service instanceof Lodging){
            if(((Lodging)service).isFull()){
                throw new LodgingIsFull("lodging "+location+ " is full!");
            }
            else if(s instanceof Thrifty && s.getHome().getPrice()<service.getPrice()){
                throw new CantMove("Move is not acceptable for "+name+'!');
            }
        }
        s.move((Lodging)service);
        return service.getName();


    }

    public TwoWayIterator<Students> listUsersByOrder(String place)throws ServiceNotExists,InvalidLocation,Empty{
        Services s=findService(place);
        if(s==null){
            throw new ServiceNotExists(place+" does not exist!");
        }else if(s instanceof Leisure){
            throw new InvalidLocation(s.getName()+" does not control student entry and exit!");
        }

        DoublyLinkedList<Students>list=((ServicesWithCapacity) s).getStudents();

        if(list.isEmpty()){
            throw new Empty("No students on "+s.getName()+"!");
        }
        return list.twoWayiterator();

    }

    public Services where(String name)throws StudentNotFound{
        Students s=findStudent(name);
        if(s==null){
            throw new StudentNotFound(name+" does not exist!");
        }

        return s.getLocation();
    }

    private Students findStudent(String name){
        if (name == null){
            return null;
        }

        String target = name.trim().toLowerCase();
        Iterator<Students> it = currentArea.getStudents().iterator();

        while (it.hasNext()) {
            Students s = it.next();
            if (s.getName().trim().toLowerCase().equals(target)) {
                return s;
            }
        }

        return null;
    }

    private Services findService(String name){
        int i=0;
        boolean foundService=false;
        Services s=null;
        List<Services>services=currentArea.getServices();
        while(i<services.size()&&!foundService){
            if(services.get(i).getName().equalsIgnoreCase(name)){
                s=services.get(i);
                foundService=true;
            }
            i++;
        }
        return s;
    }

    public Iterator<Services> getVisited(String name)throws StudentNotFound,InvalidStudentType,NoToList{
        Students s=findStudent(name);
        if(s==null){
            throw new StudentNotFound(name+" does not exist!");
        }else if(s instanceof Thrifty){
            throw new InvalidStudentType(s.getName()+" is thrifty!");
        }
        ListInArray<Services>visited= ((StudentsKeepVisited)s).getVisited();
        if(visited.isEmpty()){
            throw new NoToList(s.getName()+" has not visited any locations!");
        }
        return visited.iterator();

    }

    public void evaluate(int star,String nameService,String description)throws InvalidStar,ServiceNotExists{
        Services s=findService(nameService);
        if(star<1||star>5){
            throw new InvalidStar("Invalid evaluation!");
        }
        if(s==null){
            throw new ServiceNotExists(nameService+" does not exist!");
        }else{
            s.evaluate(star,description,evaluateCounter++);
        }
    }

    public Iterator<Services> getRanking(){
        return orderedServices().iterator();
    }

    private DoublyLinkedList<Services> orderedServices(){
        DoublyLinkedList<Services>ranking=new DoublyLinkedList<>();
        List<Services>services=currentArea.getServices();
        for(int i=0;i<services.size();i++){
            ranking.addLast(services.get(i));
        }
        for(int i=0;i<ranking.size()-1;i++){
            for(int j=0;j<ranking.size()-1-i;j++) {
                Services a = ranking.get(j);
                Services b = ranking.get(j + 1);
                if (a.getEvaluation() < b.getEvaluation()){
                    Services first = ranking.remove(j);
                    Services second = ranking.remove(j);
                    ranking.add(j, first);
                    ranking.add(j, second);
                }else if(a.getEvaluation()==b.getEvaluation() && a.getLastEvaluated() > b.getLastEvaluated()){
                    ranking.remove(j);
                    ranking.remove(j);
                    ranking.add(j, a);
                    ranking.add(j, b);
                }
            }
        }
        return ranking;
    }

    public Iterator<Services>getRanked(String type,int star,String name)throws InvalidStar,StudentNotFound,InvalidType,ServiceNotExists{
        Iterator<Services> it = currentArea.getServices().iterator();
        Students student = findStudent(name);
        if(star<1||star>5){
            throw new InvalidStar("Invalid stars!");
        }else if(student==null){
            throw new StudentNotFound(name+" does not exist!");
        }else if(!type.equals(LEISURE) && !type.equals(EATING) && !type.equals(LODGING) ){
            throw new InvalidType("Invalid service type!");
        }
        FilterIterator<Services>itByType=new FilterIterator<>(it,s->s.getType().equalsIgnoreCase(type));
        if(!itByType.hasNext()){
            throw new ServiceNotExists("No "+type+" services!");
        }
        FilterIterator<Services>itByStar=new FilterIterator<>(itByType,s->s.getEvaluation()==star);
        if(!itByStar.hasNext()){
            throw new ServiceNotExists("No "+type+" services with average!");
        }

        DoublyLinkedList<Services>result=closest(itByStar,student);



        return result.iterator();
    }

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

    public Iterator<Services>getTag(String tag){
        DoublyLinkedList<Services> result = new DoublyLinkedList<>();
        List<Services>services=currentArea.getServices();

        for(int i=0;i<services.size();i++){
            Services s=services.get(i);
            List<String>tags=s.getReviews();
            for(int j=0;j<tags.size();j++){
                String[] words = tags.get(j).split("\\s+");
                for (String word : words) {
                    if (word.equalsIgnoreCase(tag) && result.indexOf(s) == -1) {
                        result.addLast(s);
                        break;
                    }
                }
            }
        }
        return result.iterator();
    }

    public Services find(String name,String type){
        FilterIterator<Services>it= new FilterIterator<>(currentArea.getServices().iterator(),s->s.getType().equalsIgnoreCase(type));
        Students s=findStudent(name);

        if(s instanceof Thrifty){
            return findCheapest(it);
        }else {
            return findHighestRanked(it);
        }
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


}
