package Package.Students;

import Package.Services.Lodging;
import Package.Services.Services;

public interface StudentsInterface {

    String getName();

    String getCountry();

    String getType();

    String getNameLocation();

    Services getLocation();

    void go(Services location);

    void move(Lodging home);
}
