package Package.Exceptions;

public class LodgingDoesNotExist extends Exception{

    public LodgingDoesNotExist(String name){
        super(ExceptionsMessagesEnum.LODGING_DOES_NOT_EXIST.withFormat(name));
    }
}
