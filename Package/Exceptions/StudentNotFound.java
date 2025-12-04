package Package.Exceptions;

public class StudentNotFound extends Exception{

    public StudentNotFound(String name){
        super(ExceptionsMessagesEnum.STUDENT_NOT_FOUND.withFormat (name));
    }
}
