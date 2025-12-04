package Package.Exceptions;

public class Empty extends Exception{

    public Empty(String name){
        super(ExceptionsMessagesEnum.EMPTY.withFormat(name));
    }
}
