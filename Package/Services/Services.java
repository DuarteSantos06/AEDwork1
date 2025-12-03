//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt

package Package.Services;

import dataStructures.ListInArray;
import Package.Area;

import java.io.*;

public abstract class Services implements ServicesInterface, IReadOnlyService,Serializable {

    private final String name;
    private final long latitude;
    private final long longitude;
    protected float price;
    private float star;
    private int countEvaluations;
    private final ListInArray<String> reviews;
    private final ServicesType type;
    private int numberEvaluated;

    public Services(long latitude, long longitude, float price, String name, ServicesType type){
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.name = name;
        this.star = 4;
        this.countEvaluations = 1;
        this.type = type;
        reviews = new ListInArray<>(10);
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

    /**
     * Updates evaluation stats and adds a review description.
     *
     * @param star the star rating to add
     * @param description the review description
     * @param evaluateCounter counter for evaluation round
     */
    public void evaluate(int star, String description, int evaluateCounter, Area currentArea){
        countEvaluations++;
        float newStar = ((this.star * (countEvaluations - 1)) + star) / countEvaluations;
        if(newStar > 5){
            this.star = 5;
        } else if(newStar < 1){
            this.star = 1;
        }
        if(Math.round(newStar) != Math.round(this.star)) {
            this.numberEvaluated = evaluateCounter;
            currentArea.updateEvaluation(this, Math.round(newStar), Math.round(this.star));
        }

        this.star = newStar;

        reviews.addLast(description.toLowerCase());
    }

    public int getEvaluation(){
        return Math.round(star);
    }

    public ListInArray<String> getReviews(){
        return reviews;
    }

    public String getType(){
        return type.toString();
    }
}
