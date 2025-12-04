package Package.Exceptions;

public class AreaAlreadyExists extends Exception{

    public AreaAlreadyExists(){
        super(ExceptionsMessagesEnum.AREA_ALREADY_EXISTS.getMessage());
    }
}
