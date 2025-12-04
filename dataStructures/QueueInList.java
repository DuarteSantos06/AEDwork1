/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */


package dataStructures;

import dataStructures.exceptions.*;

public class QueueInList<E> implements Queue<E> {

    // Memory of the queue: a list.
    private List<E> list;

    public QueueInList( ){
        list = new SinglyLinkedList<E>();
    }

    /**
     * Returns true iff the queue contains no elements.
     *Time complexity O(1)
     * @return true if the queue contains no elements, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns the number of elements in the queue.
     *Time complexity O(1)
     * @return number of elements in the queue
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Inserts the specified element at the rear of the queue.
     *Time complexity O(1)
     * @param element
     */
    @Override
    public void enqueue(E element) {
        list.addLast(element);
    }

    /**
     * Removes and returns the element at the front of the queue.
     *Time complexity O(1)
     * @return element removed from the front of the queue
     * @throws EmptyQueueException
     */
    @Override
    public E dequeue() {

        if(isEmpty()){
            throw new EmptyQueueException();
        }
        return list.removeFirst();
    }
    /**
     * Returns the element at the front of the queue.
     *Time complexity O(1)
     * @return element at the front of the queue
     * @throws EmptyQueueException
     */
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        return list.getFirst();
    }
}
