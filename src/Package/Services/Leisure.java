package Package.Services;

public class Leisure extends Services{

    protected int discount;

    public Leisure(long latitude, long longitude, float price, int discount,String name){
        super(latitude,longitude,price,name,"leisure");
        this.discount = discount;

    }

    public int getDiscount(){
        return discount;
    }

    public float getPrice(){
        return price * (1 - (float)discount / 100.0f);
    }
}
