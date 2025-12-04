package Package.Exceptions;

public class HasNotVisitedLocations extends Exception {
    public HasNotVisitedLocations(String name) {
        super(ExceptionsMessagesEnum.HAS_NOT_VISITED_LOCATIONS.withFormat(name));
    }
}
