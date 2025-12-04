package Package.Exceptions;

public class InvalidType extends Exception{

    public InvalidType(){
        super(ExceptionsMessagesEnum.INVALID_SERVICE_TYPE.getMessage());
    }
}
