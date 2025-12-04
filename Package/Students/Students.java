/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package.Students;

import Package.Exceptions.InvalidLocation;
import Package.Exceptions.ServiceIsFull;
import Package.Services.*;

import java.io.Serializable;

public abstract class Students implements Comparable<Students>,StudentsInterface,IReadOnlyStudent, Serializable {



    private final String name;
    private final String country;
    private final StudentsType type;
    private Services actualLocation;
    private Lodging home;
    private Eating cheapestEating;


    public Students(String name, String country, Lodging home,StudentsType type) {
        this.name = name;
        this.country = country;
        this.type=type;
        this.actualLocation=home;
        this.home =home;
    }

    public Lodging getHome(){
        return home;
    }

    public int compareTo(Students other) {
        return this.name.compareTo(other.name);
    }

    public String getName() {
        return name;
    }

    public String getCountry(){
        return country;
    }

    public String getType(){
        return type.toString();
    }

    public String getNameLocation() {
        return actualLocation.getName();
    }

    public Services getLocation(){
        return actualLocation;
    }

    public Eating getCheapestEating(){
        return cheapestEating;
    }

    public void leave(){
        if(this.actualLocation instanceof ServicesWithCapacity){
            ((ServicesWithCapacity) this.actualLocation).removeStudent(this);
        }
        if(!actualLocation.getName().equals(home.getName())){
            home.removeStudent(this);
        }
    }

    public void go(Services location)throws InvalidLocation, ServiceIsFull {
        if(this.actualLocation instanceof ServicesWithCapacity && !this.actualLocation.equals(home)){
            ((ServicesWithCapacity) this.actualLocation).removeStudent(this);
        }
        if(location instanceof ServicesWithCapacity){
            ((ServicesWithCapacity) location).addStudent(this);
        }
        this.actualLocation=location;

        if (location instanceof Eating eating) {
            if (cheapestEating == null || eating.getPrice() < cheapestEating.getPrice()) {
                cheapestEating = eating;
            }
        }
        if(!(this instanceof Thrifty)){
            ((StudentsKeepVisited)this).addVisited(location);
        }
    }

    public void move(Lodging newHome)throws InvalidLocation,ServiceIsFull{
        newHome.addStudent(this);
        this.home.removeStudent(this);
        this.actualLocation=newHome;
        this.home =newHome;
        if(!(this instanceof Thrifty)){
            ((StudentsKeepVisited)this).addVisited(actualLocation);
        }

    }

}
