package Package.Students;

import Package.Services.Lodging;

public class Thrifty extends Students {

    public Thrifty(String name, String country, Lodging home){
        super(name,country,home,StudentsType.THRIFTY);
    }
}
