import java.util.Scanner;
import Package.HomeAway;
import Package.Services.Services;
import Package.Students.Students;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

public class Main  {



    private static final String GO_OUTPUT__DISTRACTED="%s is now at %s. %s is distracted!\n";
    private static final String LEAVE_OUTPUT="%s has left.\n";
    private static final String RANKED_PHRASE="%s services closer with %d average\n";
    private static final String NO_SERVICES_TAGGED="There are no services with this tag!";
    private static final String TAG_OUTPUT="%s %s\n";
    private static final String SERVICES_DESCENDING= "Services sorted in descending order";
    private static final String RANKING_OUTPUT="%s: %d\n";
    private static final String EVALUATION_OUTPUT="Your evaluation has been registered!";
    private static final String LIST_USERS="%s: %s\n";
    private static final String MOVE_OUTPUT="lodging %s is now %s's home. %s is at home.\n";
    private static final String GO_OUTPUT="%s is now at %s.\n";
    private static final String STUDENT_CREATED="%s added.\n";
    private static final String SERVICE_CREATED="%s %s added.\n";
    private static final String AREA_CREATED="%s created.\n";
    private static final String WHERE_OUTPUT="%s is at %s %s (%d, %d).\n";
    private static final String LIST_STUDENTS="%s: %s at %s.\n";
    private static final String EXIT_OUTPUT="Bye!";
    private static final String INVALID_SERVICE_TYPE="Invalid service type!";
    private static final String SAVED="%s saved.\n";
    private static final String UNKNOWN_COMMAND="Unknown command. Type help to see available commands.";
    private static final String HELP_OUTPUT = "bounds - Defines the new geographic bounding rectangle\n" +
            "save - Saves the current geographic bounding rectangle to a text file\n" +
            "load - Load a geographic bounding rectangle from a text file\n" +
            "service - Adds a new service to the current geographic bounding rectangle. The service may be eating, lodging or leisure\n" +
            "services - Displays the list of services in current geographic bounding rectangle, in order of registration\n" +
            "student - Adds a student to the current geographic bounding rectangle\n" +
            "students - Lists all the students or those of a given country in the current geographic bounding rectangle, in alphabetical order of the student's name\n" +
            "leave - Removes a student from the the current geographic bounding rectangle\n" +
            "go - Changes the location of a student to a leisure service, or eating service\n" +
            "move - Changes the home of a student\n" +
            "users - List all students who are in a given service (eating or lodging)\n" +
            "star - Evaluates a service\n" +
            "where - Locates a student\n" +
            "visited - Lists locations visited by one student\n" +
            "ranking - Lists services ordered by star\n" +
            "ranked - Lists the service(s) of the indicated type with the given score that are closer to the student location\n" +
            "tag - Lists all services that have at least one review whose description contains the specified word\n" +
            "find - Finds the most relevant service of a certain type, for a specific student\n" +
            "help - Shows the available commands\n" +
            "exit - Terminates the execution of the program";

    private static final String EATING="eating";
    private static final String LODGING="lodging";
    private static final String LEISURE="leisure";


    private static Command getCommand(Scanner input) {
        try {
            String comm = input.next().toUpperCase();
            return Command.valueOf(comm);
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HomeAway homeAway = new HomeAway();
        Command cmd;

        do{
            cmd = getCommand(in);
            switch(cmd) {
                case BOUNDS-> createArea(in,homeAway);
                case SAVE-> saveArea(homeAway);
                case LOAD-> loadArea(in,homeAway);
                case SERVICE -> createService(in,homeAway);
                case SERVICES-> listServices(homeAway);
                case STUDENT-> createStudent(in,homeAway);
                case LEAVE-> leave(in,homeAway);
                case STUDENTS-> listStudents(in,homeAway);
                case GO->go(in,homeAway);
                case MOVE-> move(in,homeAway);
                case USERS-> listUsers(in,homeAway);
                case WHERE-> where(in,homeAway);
                case VISITED-> listVisited(in,homeAway);
                case STAR-> evaluate(in,homeAway);
                case RANKING-> ranking(homeAway);
                case RANKED-> ranked(in,homeAway);
                case TAG-> tag(in,homeAway);
                case FIND-> find(in,homeAway);
                case HELP->executeHelp();
                case EXIT->exit(homeAway);
                default-> System.out.println(UNKNOWN_COMMAND);
            }

        }while(!cmd.equals(cmd.EXIT));
        in.close();

    }
    private static void executeHelp() {
        Command[] help=Command.values();
        for(int i=0; i<help.length-1;i++)
            System.out.println(help[i].getMsg());
    }

    private static void exit(HomeAway homeAway) {
        try{
            String name=homeAway.saveArea();
            System.out.println(EXIT_OUTPUT);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void createArea(Scanner in, HomeAway homeAway) {
        try {
            long topLatitude = in.nextLong();
            long leftLongitude = in.nextLong();
            long bottomLatitude = in.nextLong();
            long rightLongitude = in.nextLong();
            String name = in.next();
            name += in.nextLine();
            name = name.trim();

            homeAway.createArea(name, topLatitude, leftLongitude, bottomLatitude, rightLongitude);
            System.out.printf(AREA_CREATED,name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void saveArea( HomeAway homeAway) {
        try {
            String name=homeAway.saveArea();
            System.out.printf(SAVED,name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadArea(Scanner in, HomeAway homeAway) {
        try{
            String name=in.nextLine().trim();

            System.out.println(homeAway.loadArea(name)+" loaded.");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void createService(Scanner in, HomeAway homeAway) {
        String type=in.next().trim().toLowerCase();
        if(type.equals(LEISURE)){
            createLeisure(in,homeAway);
        }
        else if(type.equals(LODGING)||type.equals(EATING)){
            createServicesWithCapacity(in,homeAway,type);
        }else{
        System.out.println(INVALID_SERVICE_TYPE);
        in.nextLine();
        }
    }

    private static void createServicesWithCapacity(Scanner in, HomeAway homeAway,String type) {
        try{
            long latitude=in.nextLong();
            long longitude=in.nextLong();
            int price=in.nextInt();
            int capacity=in.nextInt();
            String name = in.next();
            name += in.nextLine();
            name = name.trim();
            if(type.equals(EATING)){
                homeAway.createEating(latitude,longitude,price,capacity,name);
            }
            else if(type.equals(LODGING)){
                homeAway.createLodging(latitude,longitude,price,capacity,name);
            }
            System.out.printf(SERVICE_CREATED,type,name);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void createLeisure(Scanner in, HomeAway homeAway) {
        try{
            long latitude=in.nextLong();
            long longitude=in.nextLong();
            int price=in.nextInt();
            int discount=in.nextInt();
            String name = in.next();
            name += in.nextLine();
            name = name.trim();
            homeAway.createLeisure(latitude,longitude,price,  discount,name);
            System.out.printf(SERVICE_CREATED,LEISURE,name);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void listServices( HomeAway homeAway) {
        try{
            Iterator<Services> it=homeAway.listAllServices();
            while(it.hasNext()){
                Services s=it.next();
                System.out.printf("%s: %s (%d, %d).\n",s.getName(),s.getType(),s.getLatitude(),s.getLongitude());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void createStudent(Scanner in, HomeAway homeAway) {
        try{
            String type=in.next().trim();
            in.nextLine();
            String name=in.nextLine();
            String country=in.nextLine();
            String lodging=in.nextLine();
            homeAway.createStudent(type,name,country,lodging);
            System.out.printf(STUDENT_CREATED,name);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void leave(Scanner in, HomeAway homeAway) {
        try{
            String name=in.nextLine().trim();
            String nameLeft=homeAway.leave(name);
            System.out.printf(LEAVE_OUTPUT,nameLeft);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void listStudents(Scanner in, HomeAway homeAway) {
        try{
            String country=in.nextLine().trim();
            if(country.equals("all")){
                Iterator<Students> it=homeAway.listAllStudents();
                while(it.hasNext()){
                    Students s=it.next();
                    System.out.printf(LIST_STUDENTS,s.getName(),s.getType(),s.getNameLocation());
                }
            }else{
                Iterator<Students> it=homeAway.listStudentsByCountry(country);
                while(it.hasNext()){
                    Students s=it.next();
                    System.out.printf(LIST_STUDENTS,s.getName(),s.getType(),s.getNameLocation());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void go(Scanner in, HomeAway homeAway) {
        try{
            String name=in.nextLine().trim();
            String location=in.nextLine();
            boolean is_distracted=homeAway.go(name,location);
            String nameOutput=homeAway.getNameStudent(name);
            String locationOutput=homeAway.getNameService(location);
            if(!is_distracted){
                System.out.printf(GO_OUTPUT,nameOutput,locationOutput);
            }else {
            System.out.printf(GO_OUTPUT__DISTRACTED,nameOutput,locationOutput,nameOutput);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void move(Scanner in, HomeAway homeAway) {
        try{
            String nameRead=in.nextLine().trim();
            String location=in.nextLine().trim();
            String newHome=homeAway.move(nameRead,location);
            String name=homeAway.getNameStudent(nameRead);
            System.out.printf(MOVE_OUTPUT,newHome,name,name);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void listUsers(Scanner in, HomeAway homeAway) {
        try{
            String order=in.next().trim();
            String place=in.nextLine().trim();
            TwoWayIterator<Students> it=homeAway.listUsersByOrder(place);
            if(order.equals(">")){
                while(it.hasNext()){
                    Students s=it.next();
                    System.out.printf(LIST_USERS,s.getName(),s.getType());
                }
            }else if(order.equals("<")){
                it.fullForward();
                while(it.hasPrevious()){
                    Students s=it.previous();
                    System.out.printf(LIST_USERS,s.getName(),s.getType());
                }
            }else{
                System.out.println("This order does not exists!");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void where(Scanner in, HomeAway homeAway) {
        try{
            String name= in.nextLine().trim();
            Services location=homeAway.where(name);
            name=capitalizeTheName(name);
            System.out.printf(WHERE_OUTPUT,capitalizeTheName(name),location.getName(),location.getType(),location.getLatitude(),location.getLongitude());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void listVisited(Scanner in, HomeAway homeAway) {
        try{
            String name=in.nextLine().trim();
            Iterator<Services> it=homeAway.getVisited(name);
            while(it.hasNext()){
                Services s=it.next();
                System.out.println(s.getName());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void evaluate(Scanner in, HomeAway homeAway) {
        try{
            int star=in.nextInt();
            String nameService= capitalizeTheName(in.nextLine().trim());
            String description=in.nextLine();
            homeAway.evaluate(star,nameService,description);
            System.out.println(EVALUATION_OUTPUT);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void ranking(HomeAway homeAway) {
        try{
            System.out.println(SERVICES_DESCENDING);
            Iterator<Services> it=homeAway.getRanking();
            while(it.hasNext()){
                Services s=it.next();
                System.out.printf(RANKING_OUTPUT,s.getName(),s.getEvaluation());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void ranked(Scanner in, HomeAway homeAway) {
        try{
            String type=in.next().trim();
            int star=in.nextInt();
            String name=in.nextLine().trim();
            Iterator<Services> it=homeAway.getRanked(type,star,name);
            System.out.printf(RANKED_PHRASE,type,star);
            while(it.hasNext()){
                Services s=it.next();
                System.out.println(s.getName());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void tag(Scanner in, HomeAway homeAway) {
        try{
            String tag=in.nextLine().trim();
            Iterator<Services> it=homeAway.getTag(tag);
            if(!it.hasNext()){
            System.out.println(NO_SERVICES_TAGGED);
            }
            while(it.hasNext()){
                Services s=it.next();
                System.out.printf(TAG_OUTPUT,s.getType(),s.getName());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void find (Scanner in, HomeAway homeAway){
        try{
            String name=in.nextLine().trim();
            String type=in.nextLine();
            Services s=homeAway.find(name,type);
            System.out.println(s.getName());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //chatgpt
    private static String capitalizeTheName(String name){
        String[] words = name.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String w : words) {
            if (!w.isEmpty()) {
                sb.append(Character.toUpperCase(w.charAt(0)))
                        .append(w.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }
//chatgpt



}
