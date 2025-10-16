package Package.Services;

import dataStructures.ListInArray;

import java.io.Serializable;

public  abstract class Services implements ServicesInterface, Serializable {

    protected String name;
    protected long latitude;
    protected long longitude;
    protected int price;
    protected float star;
    protected int countEvaluations;
    protected ListInArray<String> reviews;
    protected String type;

    public Services(long latitude, long longitude, int price,String name,String type){
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.name=name;
        this.star=3;
        this.countEvaluations=1;
        this.type=type;
        reviews = new ListInArray<>(10) ;
    }

    public String getName() {
        return name;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public int getPrice() {
        return price;
    }

    public void evaluate(int star,String description){
        this.star=(star+this.star)/countEvaluations;
        reviews.addLast(description);
    }

    public int getEvaluation(){
        return Math.round(star);
    }

    public ListInArray<String> getReviews(){
        return reviews;
    }

    public String getType(){
        return type;
    }

}
