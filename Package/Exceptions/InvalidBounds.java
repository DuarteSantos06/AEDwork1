package Package.Exceptions;

public class InvalidBounds extends RuntimeException {
    public InvalidBounds() {
        super(ExceptionsMessagesEnum.INVALID_BOUNDS.getMessage());
    }
}
