package Package.Exceptions;

public class InvalidLocationArea extends Exception {
    public InvalidLocationArea() {
        super(ExceptionsMessagesEnum.INVALID_LOCATION_AREA.getMessage());
    }
}
