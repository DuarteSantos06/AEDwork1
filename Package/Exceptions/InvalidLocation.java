package Package.Exceptions;

public class InvalidLocation extends Exception{

    public InvalidLocation(String location){
        super(ExceptionsMessagesEnum.INVALID_LOCATION.withFormat(location));
    }
}
