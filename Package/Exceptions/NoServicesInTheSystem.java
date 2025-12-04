package Package.Exceptions;

public class NoServicesInTheSystem extends Exception {
    public NoServicesInTheSystem() {
        super(ExceptionsMessagesEnum.NO_SERVICES_IN_THE_SYSTEM.getMessage());
    }
}
