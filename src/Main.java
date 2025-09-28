import java.util.Scanner;
import Package.HomeAway;

public class Main {

    private static final String BOUNDS="BOUNDS";
    private static final String EXIT="EXIT";
    private static final String HELP="HELP";
    private static final String SAVE="SAVE";
    private static final String LOAD="LOAD";
    private static final String SERVICE="SERVICE";

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
            "tag - Lists all services that have at least one review whose description contains the specified word. This list is from the most recent review to the oldest\n" +
            "find - Finds the most relevant service of a certain type, for a specific student\n" +
            "help - Shows the available commands\n" +
            "exit - Terminates the execution of the program";

    private static final String EATING="eating";
    private static final String LODGING="lodging";
    private static final String LEISURE="leisure";




    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String comm;
        HomeAway homeAway = new HomeAway();

        do{
            comm=in.next().toUpperCase();
            switch(comm) {
                case BOUNDS-> createArea(in,homeAway);
                case SAVE-> saveArea(in,homeAway);
                case LOAD-> loadArea(in,homeAway);
                case SERVICE -> createService(in,homeAway);
                case HELP->System.out.println(HELP_OUTPUT);
                case EXIT->System.out.println(EXIT_OUTPUT);
                default-> System.out.println(UNKNOWN_COMMAND);
            }
        }while(!comm.equals(EXIT));
    }

    private static void createArea(Scanner in, HomeAway homeAway) {
        try {
            int minLongitude = in.nextInt();
            int minLatitude = in.nextInt();
            int maxLongitude = in.nextInt();
            int maxLatitude = in.nextInt();
            String name = in.nextLine();
            homeAway.createArea(name, minLongitude, minLatitude, maxLongitude, maxLatitude);
        } catch (Exception e) {
            System.out.println();
        }
    }

    private static void saveArea(Scanner in, HomeAway homeAway) {
        try {
            String name=homeAway.saveArea();
            System.out.printf(SAVED,name);
        } catch (Exception e) {
            System.out.println();
        }
    }

    private static void loadArea(Scanner in, HomeAway homeAway) {
        try{
            String name=in.nextLine();
            homeAway.loadArea(name);
        }catch (Exception e){
            System.out.println();
        }
    }

    private static void createService(Scanner in, HomeAway homeAway) {
        String type=in.next();

        if(type.equals(LEISURE)){
            createLeisure(in,homeAway);
        }
        else if(type.equals(LODGING)||type.equals(EATING)){
            createServicesWithCapacity(in,homeAway);
        }else{
        System.out.println(INVALID_SERVICE_TYPE);

        }
    }

    private static void createServicesWithCapacity(Scanner in, HomeAway homeAway) {
        try{
            String type=in.next();
            int latitude=in.nextInt();
            int longitude=in.nextInt();
            int price=in.nextInt();
            int capacity=in.nextInt();
            String name=in.nextLine();
            if(type.equals(EATING)){
                homeAway.createEating(latitude,longitude,price,capacity,name);
            }
            else if(type.equals(LODGING)){
                homeAway.createLodging(latitude,longitude,price,capacity,name);
            }
        }catch (Exception e){}
    }

    private static void createLeisure(Scanner in, HomeAway homeAway) {
        try{
            int latitude=in.nextInt();
            int longitude=in.nextInt();
            int price=in.nextInt();
            int discount=in.nextInt();
            String name=in.nextLine();
            homeAway.createLeisure(latitude,longitude,price,  discount,name);
        }catch (Exception e){}
    }




}
