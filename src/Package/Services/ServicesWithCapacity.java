package Package.Services;

import Package.Students.Students;
import dataStructures.DoublyLinkedList;
import dataStructures.ListInArray;

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

    public void setCurrentOccupation(int capacity){
        this.currentOccupation=capacity;
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
        return currentOccupation ==capacity;
    }

    public void addStudent(Students s){
        currentOccupation++;
        students.addLast(s);
    }

    public void removeStudent(Students s){
        currentOccupation--;
        students.remove(students.indexOf(s));
    }
}
