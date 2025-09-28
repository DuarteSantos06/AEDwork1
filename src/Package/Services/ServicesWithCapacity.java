package Package.Services;

public class ServicesWithCapacity extends Services {

    private int latitude;
    private int longitude;
    private int money;
    private int capacity;
    private String name;

    public ServicesWithCapacity(int latitude, int longitude, int money, int capacity,String name){
        this.latitude = latitude;
        this.longitude = longitude;
        this.money = money;
        this.capacity = capacity;
        this.name=name;

    }
}
