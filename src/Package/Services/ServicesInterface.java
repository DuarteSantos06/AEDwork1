package Package.Services;

public interface ServicesInterface {

    String getName();

    long getLatitude();
    long getLongitude();

    int getPrice();

    void evaluate(int star, String description,int evaluateCounter);

    int getEvaluation();
}
