package Package.Exceptions;

public class ServiceAlreadyExists extends Exception{

    public ServiceAlreadyExists(String name){
        super(ExceptionsMessagesEnum.SERVICE_ALREADY_EXISTS.withFormat(name));
    }
}
