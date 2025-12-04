/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */
package Package.Services;

import Package.Exceptions.Empty;
import Package.Exceptions.ServiceIsFull;
import Package.Students.Students;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

public class ServicesWithCapacity extends Services implements ServicesWithCapacityInterface{


    private transient int capacity;
    private transient DoublyLinkedList<Students> students;
    private transient int currentOccupation;
    private final ServicesType type;


    public ServicesWithCapacity(long latitude, long longitude, float price, int capacity,String name,ServicesType type){
        super(latitude,longitude,price,name,type);
        this.capacity = capacity;
        students = new DoublyLinkedList<>();
        this.currentOccupation = 0;
        this.type=type;
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
            throw new Empty(this.getName());
        }
        return students;
    }

    public boolean isFull(){
        return currentOccupation ==capacity;
    }

    public void addStudent(Students s)throws ServiceIsFull{
        if(isFull()){
            throw new ServiceIsFull(type.toString() ,this.getName());
        }
        currentOccupation++;
        students.addLast(s);
    }

    public void removeStudent(Students s){
        currentOccupation--;
        students.remove(students.indexOf(s));
    }

    /**
     * Custom serialization method
     * Reconstructs transient fields:
     * - students
     * @param oos
     * @throws IOException
     */
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(capacity);
        oos.writeInt(currentOccupation);
        Iterator<Students> it = students.iterator();
        while(it.hasNext()){
            oos.writeObject(it.next());
        }
    }

    /**
     * ´
     * Custom deserialization method
     * Reconstructs transient fields:
     * - students
     * @param ois
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.capacity = ois.readInt();
        this.currentOccupation = ois.readInt();
        this.students = new DoublyLinkedList<>();
        for(int i=0;i<currentOccupation;i++){
            students.addLast((Students)ois.readObject());
        }
    }
}
