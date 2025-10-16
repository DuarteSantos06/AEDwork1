package Package.Students;

import Package.Services.Lodging;

public class Outgoing extends StudentsKeepVisited{

    public Outgoing(String name, String country, String lodging, Lodging home){
        super(name,country,lodging,home,"outgoing");
    }
}
