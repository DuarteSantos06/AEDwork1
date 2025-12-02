//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt
package Package.Services;

public class Lodging extends ServicesWithCapacity {

    public Lodging(long latitude, long longitude, float price, int capacity,String name){
        super(latitude,longitude,price,capacity,name,"lodging");
    }


}
