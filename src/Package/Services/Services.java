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
    protected int numberEvaluated;

    public Services(long latitude, long longitude, int price,String name,String type){
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.name=name;
        this.star=4;
        this.countEvaluations=1;
        this.type=type;
        reviews = new ListInArray<>(10) ;
    }

    public int getLastEvaluated(){
        return numberEvaluated;
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

    public abstract float getPrice();

    public void evaluate(int star,String description,int evaluateCounter){
        countEvaluations++;
        float newStar = ((this.star * (countEvaluations - 1)) + star) / countEvaluations;
        if(this.star>5){
            this.star=5;
        }else if(this.star<1){
            this.star=1;
        }
        if(Math.round(newStar)!=Math.round(this.star)) {
            this.numberEvaluated=evaluateCounter;
        }
        this.star=newStar;

        reviews.addLast(description.toLowerCase());
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
