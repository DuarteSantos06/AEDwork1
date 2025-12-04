package Package.Exceptions;

public class InvalidCapacity extends Exception{

    public InvalidCapacity(){
        super(ExceptionsMessagesEnum.INVALID_CAPACITY.getMessage());
    }
}
