package Package.Services;

public class Lodging extends ServicesWithCapacity {

    public Lodging(long latitude, long longitude, int price, int capacity,String name){
        super(latitude,longitude,price,capacity,name,"lodging");
    }


}
