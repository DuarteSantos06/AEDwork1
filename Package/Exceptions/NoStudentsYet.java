package Package.Exceptions;

public class NoStudentsYet extends Exception {
    public NoStudentsYet() {
        super(ExceptionsMessagesEnum.NO_STUDENTS_YET.getMessage());
    }
}
