package Package.Exceptions;

public class AlreadyThere extends Exception{

    public AlreadyThere(){
        super(ExceptionsMessagesEnum.ALREADY_THERE.getMessage());
    }
}
