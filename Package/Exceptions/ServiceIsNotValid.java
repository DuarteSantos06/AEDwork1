package Package.Exceptions;

public class ServiceIsNotValid extends RuntimeException {
    public ServiceIsNotValid(String location) {
        super(ExceptionsMessagesEnum.SERVICE_IS_NOT_VALID.withFormat(location));
    }
}
