package Package.Exceptions;

public class NoStudentsFromCountry extends Exception {
    public NoStudentsFromCountry(String country) {
        super(ExceptionsMessagesEnum.NO_STUDENTS_FROM_COUNTRY.withFormat(country));
    }
}
