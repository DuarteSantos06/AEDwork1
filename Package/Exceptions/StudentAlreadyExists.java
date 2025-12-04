package Package.Exceptions;

public class StudentAlreadyExists extends Exception{

    public StudentAlreadyExists(String name){
        super(ExceptionsMessagesEnum.STUDENT_ALREADY_EXISTS.withFormat(name));
    }
}
