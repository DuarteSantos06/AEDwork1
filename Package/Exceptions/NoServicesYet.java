package Package.Exceptions;

public class NoServicesYet extends Exception{
    public NoServicesYet() {
        super(ExceptionsMessagesEnum.NO_SERVICES_YET.getMessage());
    }
}
