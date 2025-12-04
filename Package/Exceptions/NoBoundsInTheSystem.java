package Package.Exceptions;

public class NoBoundsInTheSystem extends Exception{

    public NoBoundsInTheSystem(){
        super(ExceptionsMessagesEnum.NO_BOUNDS_IN_THE_SYSTEM.getMessage());
    }
}
