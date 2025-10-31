//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt

package Package.Students;

import Package.Services.*;

import java.io.DataOutputStream;
import java.io.Serializable;

public abstract class Students implements Comparable<Students>,StudentsInterface, Serializable {



    private String name;
    private String country;
    private String type;
    private Services location;
    private Lodging home;
    private Eating cheapestEating;


    public Students(String name, String country, String lodging, Lodging home,String type) {

        this.name = name;
        this.country = country;
        this.type=type;
        this.location=home;
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
        return type;
    }

    public String getNameLocation() {
        return location.getName();
    }

    public Services getLocation(){
        return location;
    }

    public Eating getCheapestEating(){
        return cheapestEating;
    }

    public void leave(){
        if(this.location instanceof ServicesWithCapacity){
            ((ServicesWithCapacity) this.location).removeStudent(this);
        }
        if(!location.getName().equals(home.getName())){
            home.removeStudent(this);
        }
    }

    public void go(Services location){
        if(this.location instanceof ServicesWithCapacity && !this.location.equals(home)){
            ((ServicesWithCapacity) this.location).removeStudent(this);
        }
        if(location instanceof ServicesWithCapacity){
            ((ServicesWithCapacity) location).addStudent(this);
        }
        this.location=location;

        if (location instanceof Eating eating) {
            if (cheapestEating == null || eating.getPrice() < cheapestEating.getPrice()) {
                cheapestEating = eating;
            }
        }
        if(!(this instanceof Thrifty)){
            ((StudentsKeepVisited)this).addVisited(location);
        }
    }

    public void move(Lodging home){
        home.addStudent(this);
        this.home.removeStudent(this);
        this.location=home;
        this.home =home;
        if(!(this instanceof Thrifty)){
            ((StudentsKeepVisited)this).addVisited(location);
        }

    }

}
