package Package.Exceptions;

public class DoesNotControlStudentEntry extends RuntimeException {
    public DoesNotControlStudentEntry(String name) {
        super(ExceptionsMessagesEnum.DOES_NOT_CONTROL_STUDENT_ENTRY.withFormat(name));
    }
}
