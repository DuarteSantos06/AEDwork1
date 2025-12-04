package Package.Exceptions;

public class ThriftyDoesNotStoreServices extends RuntimeException {
    public ThriftyDoesNotStoreServices(String name) {
        super(ExceptionsMessagesEnum.THRIFTY_DOES_NOT_STORE_SERVICES.withFormat(name));
    }
}
