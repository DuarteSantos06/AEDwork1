package Package;

public class Area {

    private String name;
    private int minLongitude ;
    private int minLatitude ;
    private int maxLongitude ;
    private int maxLatitude;

    public Area(String name, int minLongitude, int minLatitude, int maxLongitude, int maxLatitude) {
        this.name = name;
        this.minLongitude = minLongitude;
        this.minLatitude = minLatitude;
        this.maxLongitude = maxLongitude;
        this.maxLatitude = maxLatitude;
    }

    public String getName() {
        return name;
    }

    public int getMinLongitude() {
        return minLongitude;
    }
    public int getMinLatitude() {
        return minLatitude;
    }

    public int getMaxLongitude() {
        return maxLongitude;
    }
    public int getMaxLatitude() {
        return maxLatitude;
    }
}
