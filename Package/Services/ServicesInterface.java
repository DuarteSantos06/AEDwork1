//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt

package Package.Services;

import Package.Exceptions.InvalidStar;
import Package.Area;

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
     * Adds an evaluation/rating for this service.
     *
     * @param rating the star rating given by the user
     * @param description the text description/review
     * @param evaluateCounter the counter for tracking evaluation order
     * @complexity O(1)
     */
     void evaluate(int rating, String description, int evaluateCounter,Area currentArea)throws InvalidStar;

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
