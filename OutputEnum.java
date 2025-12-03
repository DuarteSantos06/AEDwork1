public enum OutputEnum {

    ORDER_NOT_EXISTS("This order does not exists!"),
    LIST_OUTPUT("%s: %s (%d, %d).\n"),
    GO_OUTPUT_DISTRACTED("%s is now at %s. %s is distracted!\n"),
    LEAVE_OUTPUT("%s has left.\n"),
    RANKED_PHRASE("%s services closer with %d average\n"),
    NO_SERVICES_TAGGED("There are no services with this tag!"),
    TAG_OUTPUT("%s %s\n"),
    SERVICES_DESCENDING("Services sorted in descending order"),
    RANKING_OUTPUT("%s: %d\n"),
    EVALUATION_OUTPUT("Your evaluation has been registered!"),
    LIST_USERS("%s: %s\n"),
    MOVE_OUTPUT("lodging %s is now %s's home. %s is at home.\n"),
    GO_OUTPUT("%s is now at %s.\n"),
    STUDENT_CREATED("%s added.\n"),
    SERVICE_CREATED("%s %s added.\n"),
    AREA_CREATED("%s created.\n"),
    WHERE_OUTPUT("%s is at %s %s (%d, %d).\n"),
    LIST_STUDENTS("%s: %s at %s.\n"),
    EXIT_OUTPUT("Bye!"),
    INVALID_SERVICE_TYPE("Invalid service type!"),
    SAVED("%s saved.\n"),
    UNKNOWN_COMMAND("Unknown command. Type help to see available commands.");

    private final String msg;

    OutputEnum(String msg){
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }
}
