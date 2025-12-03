//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt

package Package.Services;

import Package.Exceptions.Empty;
import Package.Exceptions.InvalidLocation;
import Package.Students.Students;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServicesWithCapacity extends Services implements ServicesWithCapacityInterface{


    private transient int capacity;
    private transient DoublyLinkedList<Students> students;
    private transient int currentOccupation;
    private ServicesType type;


    public ServicesWithCapacity(long latitude, long longitude, float price, int capacity,String name,ServicesType type){
        super(latitude,longitude,price,name,type);
        this.capacity = capacity;
        students = new DoublyLinkedList<>();
        this.currentOccupation = 0;
        this.type=type;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(capacity);
        oos.writeInt(currentOccupation);
        Iterator<Students> it = students.iterator();
        while(it.hasNext()){
            oos.writeObject(it.next());
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.capacity = ois.readInt();
        this.currentOccupation = ois.readInt();
        this.students = new DoublyLinkedList<>();
        for(int i=0;i<currentOccupation;i++){
            students.addLast((Students)ois.readObject());
        }
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

    public DoublyLinkedList<Students> getStudents()throws Empty{
        if(students.isEmpty()){
            throw new Empty("No students on " + this.getName() + "!");
        }
        return students;
    }

    public boolean isFull(){
        return currentOccupation ==capacity;
    }

    public void addStudent(Students s)throws InvalidLocation{
        if(isFull()){
            throw new InvalidLocation(type +" "+ this.getName() + " is full!");
        }
        currentOccupation++;
        students.addLast(s);
    }

    public void removeStudent(Students s){
        currentOccupation--;
        students.remove(students.indexOf(s));
    }
}
