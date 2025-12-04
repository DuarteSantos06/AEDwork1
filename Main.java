/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */
import java.util.Scanner;
import Package.HomeAway;
import Package.Services.IReadOnlyService;
import Package.Services.Services;
import Package.Services.ServicesType;
import Package.Students.IReadOnlyStudent;
import Package.Students.Students;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;
//comments done with the help of AI
/**
 * Main class to handle the application logic for managing students, services and areas
 * in the HomeAway system. It handles user input, invokes the correct operations in
 * the HomeAway class, and prints formatted output.
 */
public class Main  {

    /**
     * Reads the next command from input and returns its respective Command enum.
     * @param input Scanner with user input
     * @return parsed Command or UNKNOWN if not valid
     * @complexity O(1)
     */
    private static Command getCommand(Scanner input) {
        try {
            String comm = input.next().toUpperCase();
            return Command.valueOf(comm);
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }
    }

    /**
     * Entry point of the application. Processes user commands in a loop until EXIT is received.
     * @param args command-line arguments (not used)
     * @complexity O(n) em relação ao número de comandos lidos até o comando EXIT
     */
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
                default-> System.out.println(OutputEnum.UNKNOWN_COMMAND.getMessage());
            }

        }while(!cmd.equals(Command.EXIT));
        in.close();
    }

    /**
     * Prints the help menu by iterating over all commands and displaying their messages.
     * @complexity O(m), where m is the number of commands
     */
    private static void executeHelp() {
        Command[] help=Command.values();
        for(int i=0; i<help.length-1;i++)
            System.out.println(help[i].getMsg());
    }

    /**
     * Handles program termination, saving current area before exiting.
     * @param homeAway, App class
     * @complexity O(1)
     */
    private static void exit(HomeAway homeAway) {
        try{
            String name=homeAway.saveArea();
            System.out.println(OutputEnum.EXIT_OUTPUT.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads area bounds and name from input and creates a new area in HomeAway.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(1)
     */
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
            System.out.printf(OutputEnum.AREA_CREATED.getMessage(),name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves current area in HomeAway and prints confirmation.
     * @param homeAway ,App class
     * @complexity O(1)
     */
    private static void saveArea( HomeAway homeAway) {
        try {
            String name=homeAway.saveArea();
            System.out.printf(OutputEnum.SAVED.getMessage(),name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Loads an area from user input name in HomeAway.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(1)
     */
    private static void loadArea(Scanner in, HomeAway homeAway) {
        try{
            String name=in.nextLine().trim();

            System.out.println(homeAway.loadArea(name)+" loaded.");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a new service (eating, lodging or leisure) depending on user input.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(1)
     */
    private static void createService(Scanner in, HomeAway homeAway) {
        String type=in.next().trim().toLowerCase();
        if(type.equals(ServicesType.LEISURE.toString())){
            createLeisure(in,homeAway);
        }
        else if(type.equals(ServicesType.LODGING.toString())||type.equals(ServicesType.EATING.toString())){
            createServicesWithCapacity(in,homeAway,type);
        }else{
            System.out.println(OutputEnum.INVALID_SERVICE_TYPE.getMessage());
            in.nextLine();
        }
    }

    /**
     * Creates a service with capacity and price (eating or lodging).
     * @param in Scanner input
     * @param homeAway ,App class
     * @param type Type of service (eating or lodging)
     * @complexity O(1)
     */
    private static void createServicesWithCapacity(Scanner in, HomeAway homeAway,String type) {
        try{
            long latitude=in.nextLong();
            long longitude=in.nextLong();
            int price=in.nextInt();
            int capacity=in.nextInt();
            String name = in.next();
            name += in.nextLine();
            name = name.trim();
            if(type.equals(ServicesType.EATING.toString())){
                homeAway.createEating(latitude,longitude,price,capacity,name);
            }
            else if(type.equals(ServicesType.LODGING.toString())){
                homeAway.createLodging(latitude,longitude,price,capacity,name);
            }
            System.out.printf(OutputEnum.SERVICE_CREATED.getMessage(),type,name);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a leisure service with a discount and price.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(1)
     */
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
            System.out.printf(OutputEnum.SERVICE_CREATED.getMessage(),ServicesType.LEISURE,name);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists all services registered in the current area of HomeAway.
     * @param homeAway ,App class
     * @complexity O(s), where s is the number of services
     */
    private static void listServices( HomeAway homeAway) {
        try{
            Iterator<Services> it=homeAway.listAllServices();
            while(it.hasNext()){
                IReadOnlyService s=it.next();
                System.out.printf(OutputEnum.LIST_OUTPUT.getMessage(),s.getName(),s.getType(),s.getLatitude(),s.getLongitude());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a student with all details from input to the current area.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(log n)
     */
    private static void createStudent(Scanner in, HomeAway homeAway) {
        try{
            String type=in.next().trim();
            in.nextLine();
            String name=in.nextLine();
            String country=in.nextLine();
            String lodging=in.nextLine();
            homeAway.createStudent(type,name,country,lodging);
            System.out.printf(OutputEnum.STUDENT_CREATED.getMessage(),name);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes a student (by name) from the current area.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(log n)
     */
    private static void leave(Scanner in, HomeAway homeAway) {
        try{
            String name=in.nextLine().trim();
            String nameLeft=homeAway.leave(name);
            System.out.printf(OutputEnum.LEAVE_OUTPUT.getMessage(),nameLeft);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists all students, or students from a specific country, in the current area.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(n), where n is the number of students
     */
    private static void listStudents(Scanner in, HomeAway homeAway) {
        try{
            String country=in.nextLine().trim();
            if(country.equals("all")){
                Iterator<Students> it=homeAway.listAllStudents();
                while(it.hasNext()){
                    IReadOnlyStudent s=it.next();
                    System.out.printf(OutputEnum.LIST_STUDENTS.getMessage(),s.getName(),s.getType(),s.getNameLocation());
                }
            }else{
                Iterator<Students> it=homeAway.listStudentsByCountry(country);
                while(it.hasNext()){
                    IReadOnlyStudent s=it.next();
                    System.out.printf(OutputEnum.LIST_STUDENTS.getMessage(),s.getName(),s.getType(),s.getNameLocation());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Changes the location of a student to a new service (leisure or eating).
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(1)
     */
    private static void go(Scanner in, HomeAway homeAway) {
        try{
            String name=in.nextLine().trim();
            String location=in.nextLine();
            boolean is_distracted=homeAway.go(name,location);
            String nameOutput=homeAway.getNameStudent(name);
            String locationOutput=homeAway.getNameService(location);
            if(!is_distracted){
                System.out.printf(OutputEnum.GO_OUTPUT.getMessage(),nameOutput,locationOutput);
            }else {
                System.out.printf(OutputEnum.GO_OUTPUT_DISTRACTED.getMessage(),nameOutput,locationOutput,nameOutput);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Changes the designated home (lodging) for a student.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(1)
     */
    private static void move(Scanner in, HomeAway homeAway) {
        try{
            String nameRead=in.nextLine().trim();
            String location=in.nextLine().trim();
            String newHome=homeAway.move(nameRead,location);
            String name=homeAway.getNameStudent(nameRead);
            System.out.printf(OutputEnum.MOVE_OUTPUT.getMessage(),newHome,name,name);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists users in a specific service, forwards or backwards.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(u), where u is the number of users
     */
    private static void listUsers(Scanner in, HomeAway homeAway) {
        try{
            String order=in.next().trim();
            String place=in.nextLine().trim();
            TwoWayIterator<Students> it=homeAway.listUsersByOrder(place);
            if(order.equals(">")){
                while(it.hasNext()){
                    IReadOnlyStudent s=it.next();
                    System.out.printf(OutputEnum.LIST_USERS.getMessage(),s.getName(),s.getType());
                }
            }else if(order.equals("<")){
                it.fullForward();
                while(it.hasPrevious()){
                    IReadOnlyStudent s=it.previous();
                    System.out.printf(OutputEnum.LIST_USERS.getMessage(),s.getName(),s.getType());
                }
            }else{
                System.out.println(OutputEnum.ORDER_NOT_EXISTS.getMessage());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints the current location of a student.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(1)
     */
    private static void where(Scanner in, HomeAway homeAway) {
        try{
            String name= in.nextLine().trim();
            IReadOnlyService location=homeAway.where(name);
            name=homeAway.getNameStudent(name);
            System.out.printf(OutputEnum.WHERE_OUTPUT.getMessage(),name,location.getName(),
                    location.getType(),location.getLatitude(),location.getLongitude());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists all places visited by a given student.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(v)
     */
    private static void listVisited(Scanner in, HomeAway homeAway) {
        try{
            String name=in.nextLine().trim();
            Iterator<Services> it=homeAway.getVisited(name);
            while(it.hasNext()){
                IReadOnlyService s=it.next();
                System.out.println(s.getName());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds an evaluation (star and description) to a service.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(1)
     */
    private static void evaluate(Scanner in, HomeAway homeAway) {
        try{
            int star=in.nextInt();
            String nameService= in.nextLine().trim();
            String description=in.nextLine();
            homeAway.evaluate(star,nameService,description);
            System.out.println(OutputEnum.EVALUATION_OUTPUT.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays all services sorted by their star evaluations, descending.
     * @param homeAway ,App class
     * @complexity O(m)
     */
    private static void ranking(HomeAway homeAway) {
        try{
            System.out.println(OutputEnum.SERVICES_DESCENDING.getMessage());
            Iterator<Services> it=homeAway.getRanking();
            while(it.hasNext()){
                IReadOnlyService s=it.next();
                System.out.printf(OutputEnum.RANKING_OUTPUT.getMessage(),s.getName(),s.getEvaluation());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists services matching type and star evaluation, closest to the student's location.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(n+m)
     */
    private static void ranked(Scanner in, HomeAway homeAway) {
        try{
            String type=in.next().trim();
            int star=in.nextInt();
            String name=in.nextLine().trim();
            Iterator<Services> it=homeAway.getRanked(type,star,name);
            System.out.printf(OutputEnum.RANKED_PHRASE.getMessage(),type,star);
            while(it.hasNext()){
                IReadOnlyService s=it.next();
                System.out.println(s.getName());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists all services with at least one review containing a specified word.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(m*r),where m is the number of services and r is the number of reviews per service
     */
    private static void tag(Scanner in, HomeAway homeAway) {
        try{
            String tag=in.nextLine().trim();
            Iterator<Services> it=homeAway.getTag(tag);
            if(!it.hasNext()){
                System.out.println(OutputEnum.NO_SERVICES_TAGGED.getMessage());
            }
            while(it.hasNext()){
                IReadOnlyService s=it.next();
                System.out.printf(OutputEnum.TAG_OUTPUT.getMessage(),s.getType(),s.getName());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Finds the most relevant service of a certain type for a specific student.
     * @param in Scanner input
     * @param homeAway ,App class
     * @complexity O(s),where s is the number of services
     */
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
}
