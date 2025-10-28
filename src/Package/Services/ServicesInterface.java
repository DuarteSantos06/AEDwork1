package Package.Services;

public interface ServicesInterface {

    String getName();

    long getLatitude();

    long getLongitude();

    float getPrice();

    void evaluate(int rating, String description, int evaluateCounter);

    int getEvaluation();

    String getType();

    int getLastEvaluated();
}
