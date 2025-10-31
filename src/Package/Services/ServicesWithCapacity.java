//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt
package Package.Services;

import Package.Students.Students;
import dataStructures.DoublyLinkedList;

public class ServicesWithCapacity extends Services implements ServicesWithCapacityInterface{

    private int capacity;
    private DoublyLinkedList<Students> students;
    private int currentOccupation;
    private String type;

    public ServicesWithCapacity(long latitude, long longitude, float price, int capacity,String name,String type){
        super(latitude,longitude,price,name,type);
        this.capacity = capacity;
        students = new DoublyLinkedList<>();
        this.currentOccupation = 0;
        this.type=type;
    }

    public void setCapacity(int capacity){
        this.capacity=capacity;
    }

    public void setCurrentOccupation(int currentOccupation){
        this.currentOccupation=currentOccupation;
    }

    public float getPrice(){
        return price;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentOccupation() {
        return currentOccupation;
    }

    public DoublyLinkedList<Students> getStudents(){
        return students;
    }

    public boolean isFull(){
        return currentOccupation == capacity;
    }

    /**
     * Adds a student to this service and increases occupation count.
     *
     * @param s the student to add
     */
    public void addStudent(Students s){
        currentOccupation++;
        students.addLast(s);
    }

    /**
     * Removes a student from this service and decreases occupation count.
     *
     * @param s the student to remove
     */
    public void removeStudent(Students s){
        currentOccupation--;
        students.remove(students.indexOf(s));
    }
}
