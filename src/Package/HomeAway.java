package Package;

import Package.Services.Eating;
import Package.Services.Lodging;

public class HomeAway {

    private Area currentArea;


    public void homeAway() {

    }

    public void createArea(String name, int minLongitude, int minLatitude, int maxLongitude, int maxLatitude) {
        //if(area já existir)

        if(minLatitude > maxLatitude || minLongitude > maxLongitude){

        }
        currentArea = new Area(name,minLongitude,minLatitude,maxLongitude,maxLatitude);
    }

    public String saveArea() {

        return currentArea.getName();
    }

    public void loadArea(String name){
        //if(area não existir)
        //else save
    }

    public void createEating(int latitude, int longitude, int price, int capacity, String name) {
        if (isInValidBounds(latitude, longitude)) {

        } else if (price <= 0) {
            //Invalid menu price
        } else if (capacity <= 0) {
            //Invalid capacity
        }//else if(alreadyEists(name){
        //name already exists
        //}
        Eating eating = new Eating(latitude,longitude,price,capacity,name);
    }

    public void createLodging(int latitude, int longitude, int price, int capacity, String name){
        if (isInValidBounds(latitude, longitude)) {

        } else if (price <= 0) {
            //Invalid room price
        } else if (capacity <= 0) {
            //Invalid capacity
        }//else if(alreadyEists(name){
        //name already exists
        //}
        Lodging lodging = new Lodging(latitude,longitude,price,capacity,name);
    }

    public void createLeisure(int latitude, int longitude,int price,int discount, String name){
        if (isInValidBounds(latitude, longitude)) {
            //Invalid location
        }else if(price<=0){
            //Invalid ticket Price
        }
    }

    private boolean isInValidBounds(int latitude, int longitude){
        return latitude >= currentArea.getMinLatitude() && latitude <= currentArea.getMaxLatitude() && longitude >= currentArea.getMinLongitude() && longitude <= currentArea.getMaxLongitude();
    }
}
