package Package.Services;

import Package.Students.Students;
import dataStructures.DoublyLinkedList;

public interface ServicesWithCapacityInterface extends ServicesInterface {

    int getCapacity();

    int getCurrentOccupation();

    DoublyLinkedList<Students> getStudents();
}
