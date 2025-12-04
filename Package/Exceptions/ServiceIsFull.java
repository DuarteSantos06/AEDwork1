package Package.Exceptions;

public class ServiceIsFull extends Exception{

    public ServiceIsFull(String type, String name){
        super(ExceptionsMessagesEnum.SERVICE_IS_FULL.withFormat(type,name));
    }
}
