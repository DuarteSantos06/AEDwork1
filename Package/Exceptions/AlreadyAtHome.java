package Package.Exceptions;

public class AlreadyAtHome extends RuntimeException {
    public AlreadyAtHome(String name) {
        super(ExceptionsMessagesEnum.ALREADY_AT_HOME.withFormat(name));
    }
}
