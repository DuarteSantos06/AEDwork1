package Package.Exceptions;

public class InvalidStudentType extends Exception{

    public InvalidStudentType(){
        super(ExceptionsMessagesEnum.INVALID_STUDENT_TYPE.getMessage());
    }
}
