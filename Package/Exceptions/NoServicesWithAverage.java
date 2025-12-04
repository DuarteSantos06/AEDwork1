package Package.Exceptions;

public class NoServicesWithAverage extends RuntimeException {
    public NoServicesWithAverage(String type) {
        super(ExceptionsMessagesEnum.NO_SERVICES_WITH_AVERAGE.withFormat(type));
    }
}
