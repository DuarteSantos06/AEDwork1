package Package.Exceptions;

public class CantMove extends Exception{

    public CantMove(String name){
        super(ExceptionsMessagesEnum.CANT_MOVE.withFormat(name));
    }
}
