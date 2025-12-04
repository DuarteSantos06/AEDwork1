package Package.Exceptions;

public class InvalidStar extends Exception{

    public InvalidStar(){
        super(ExceptionsMessagesEnum.INVALID_STAR.getMessage());
    }
}
