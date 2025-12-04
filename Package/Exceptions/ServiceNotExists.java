package Package.Exceptions;

public class ServiceNotExists extends Exception{
    public ServiceNotExists(String name){
        super(ExceptionsMessagesEnum.SERVICE_DOES_NOT_EXISTS.withFormat(name));
    }
}
