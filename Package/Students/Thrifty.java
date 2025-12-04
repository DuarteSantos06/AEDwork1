/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package.Students;

import Package.Services.Lodging;

public class Thrifty extends Students {

    public Thrifty(String name, String country, Lodging home){
        super(name,country,home,StudentsType.THRIFTY);
    }
}
