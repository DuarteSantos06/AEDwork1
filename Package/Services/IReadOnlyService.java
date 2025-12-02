package Package.Services;



public interface IReadOnlyService {

    /**
     * Returns the name of the service.
     *
     * @return the service name
     * @complexity O(1)
     */
    String getName();


    /**
     * Returns the latitude coordinate of the service location.
     *
     * @return the latitude coordinate
     * @complexity O(1)
     */
    long getLatitude();

    /**
     * Returns the longitude coordinate of the service location.
     *
     * @return the longitude coordinate
     * @complexity O(1)
     */
    long getLongitude();

    /**
     * Returns the price of the service.
     *
     * @return the price per visit or usage
     * @complexity O(1)
     */
    float getPrice();

    /**
     * Returns the aggregated evaluation score of the service.
     * This typically represents the average or total of all ratings.
     *
     * @return the evaluation score
     * @complexity O(1)
     */
    int getEvaluation();

    /**
     * Returns the type of service (e.g., "eating", "lodging", "leisure").
     *
     * @return the service type
     * @complexity O(1)
     */
    String getType();

    /**
     * Returns the last evaluation counter value.
     * This tracks when the service was last evaluated.
     *
     * @return the last evaluation counter
     * @complexity O(1)
     */
    int getLastEvaluated();
}
