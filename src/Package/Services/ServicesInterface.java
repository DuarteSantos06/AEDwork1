package Package.Services;

/**
 * Interface representing a service within the home away from home system.
 * A service is a location that students can visit, with properties such as
 * name, geographical coordinates, and price. Services can be evaluated by users.
 */
public interface ServicesInterface {
    
    /**
     * Returns the name of the service.
     * 
     * @return the service name
     */
    String getName();
    
    /**
     * Returns the latitude coordinate of the service location.
     * 
     * @return the latitude coordinate
     */
    long getLatitude();
    
    /**
     * Returns the longitude coordinate of the service location.
     * 
     * @return the longitude coordinate
     */
    long getLongitude();
    
    /**
     * Returns the price of the service.
     * 
     * @return the price per visit or usage
     */
    float getPrice();
    
    /**
     * Adds an evaluation/rating for this service.
     * 
     * @param rating the star rating given by the user
     * @param description the text description/review
     * @param evaluateCounter the counter for tracking evaluation order
     */
    void evaluate(int rating, String description, int evaluateCounter);
    
    /**
     * Returns the aggregated evaluation score of the service.
     * This typically represents the average or total of all ratings.
     * 
     * @return the evaluation score
     */
    int getEvaluation();
    
    /**
     * Returns the type of service (e.g., "eating", "lodging", "leisure").
     * 
     * @return the service type
     */
    String getType();
    
    /**
     * Returns the last evaluation counter value.
     * This tracks when the service was last evaluated.
     * 
     * @return the last evaluation counter
     */
    int getLastEvaluated();
}
