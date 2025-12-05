/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */
package Package.Services;

import dataStructures.*;
import Package.Area;

import java.io.*;

public abstract class Services implements ServicesInterface, IReadOnlyService,Serializable {

    private final String name;
    private final long latitude;
    private final long longitude;
    protected float price;
    private float star;
    private int countEvaluations;
    private transient List<String> reviews;
    private final ServicesType type;
    private int numberEvaluated;
    @Serial
    private static final long serialVersionUID = 1L;

    public Services(long latitude, long longitude, float price, String name, ServicesType type){
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.name = name;
        this.star = 4;
        this.countEvaluations = 1;
        this.type = type;
        reviews = new SinglyLinkedList<>();
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

    public List<String> getReviews(){
        return reviews;
    }

    public String getType(){
        return type.toString();
    }

    /**
     * Custom serialization method
     * Reconstructs transient fields:
     * - reviews
     * @param oos
     * @throws IOException
     */
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(reviews.size());
        Iterator<String> it = reviews.iterator();
        while(it.hasNext()){
            oos.writeObject(it.next());
        }
    }

    /**
     * Custom deserialization method
     * Reconstructs transient fields:
     * - reviews
     * @param ois
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        reviews=new SinglyLinkedList<>();
        int numServices=ois.readInt();
        for(int i=0;i<numServices;i++){
            reviews.addLast((String)ois.readObject());
        }
    }
}
