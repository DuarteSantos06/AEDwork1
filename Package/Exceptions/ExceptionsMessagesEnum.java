package Package.Exceptions;

public enum ExceptionsMessagesEnum {

    INVALID_STUDENT_TYPE("Invalid student type!"),
    STUDENT_ALREADY_EXISTS("%s already exists!"),
    LODGING_DOES_NOT_EXIST("lodging %s does not exist!"),
    ALREADY_AT_HOME("That is %s's home!"),
    THRIFTY_DOES_NOT_STORE_SERVICES("%s is thrifty!"),
    INVALID_SERVICE_TYPE("Invalid service type!"),
    NO_SERVICES_WITH_AVERAGE("No %s services with average!"),
    INVALID_LOCATION("Unknown %s!"),
    SERVICE_IS_NOT_VALID("%s is not a valid service!"),
    INVALID_BOUNDS("Invalid bounds."),
    INVALID_LOCATION_AREA("Invalid location!"),
    SERVICE_IS_FULL("%s %s is full!"),
    DOES_NOT_CONTROL_STUDENT_ENTRY("%s does not control student entry and exit!"),
    ALREADY_THERE("Already there!"),
    NO_BOUNDS_IN_THE_SYSTEM("System bounds not defined."),
    AREA_DOES_NOT_EXIST("Bounds %s does not exist!"),
    STUDENT_NOT_FOUND("%s does not exist!"),
    INVALID_CAPACITY("Invalid capacity!"),
    INVALID_DISCOUNT("Invalid discount price!"),
    SERVICE_ALREADY_EXISTS("%s already exists!"),
    SERVICE_DOES_NOT_EXISTS("%s does not exist!"),
    AREA_ALREADY_EXISTS("Bounds already exists. Please load it!"),
    EMPTY("No students on %s!"),
    INVALID_PRICE_LODGING("Invalid room price!"),
    INVALID_PRICE_LEISURE("Invalid ticket price!"),
    INVALID_PRICE_EATING("Invalid menu price!"),
    INVALID_STAR("Invalid stars!"),
    INVALID_EVALUATION("Invalid evaluation!"),
    NO_SERVICES_IN_THE_SYSTEM("No services in the system."),
    NO_SERVICES_YET("No services yet!"),
    HAS_NOT_VISITED_LOCATIONS("%s has not visited any locations!"),
    NO_STUDENTS_YET("No students yet!"),
    NO_STUDENTS_FROM_COUNTRY("No students from %s!"),
    CANT_MOVE("Move is not acceptable for %s!");



    private final String msg;

    ExceptionsMessagesEnum(String msg){
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }

    public String withFormat(String s){
        return String.format(msg, s);
    }

    public String withFormat(String s,String j){
        return String.format(msg, s,j);
    }
}
