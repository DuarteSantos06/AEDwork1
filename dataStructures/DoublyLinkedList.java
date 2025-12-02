package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;
import java.io.Serializable;
/**
 * Doubly Linked List
 *
 * @author AED team
 * @version 1.0
 *
 * @param <E> Generic Element
 */
public class DoublyLinkedList<E> implements TwoWayList<E>,Serializable {
    /**
     *  Node at the head of the list.
     */
    private DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private DoublyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private int currentSize;

    /**
     * Constructor of an empty double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public DoublyLinkedList( ) {
        head=null;
        tail=null;
        currentSize=0;
    }

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */

    public int size() {

        return currentSize;
    }

    /**
     * Returns a two-way iterator of the elements in the list.
     *
     * @return Two-Way Iterator of the elements in the list
     */

    public TwoWayIterator<E> twoWayiterator() {
        return new TwoWayDoublyIterator<>(head, tail);
    }
    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Inserts the element at the first position in the list.
     * @param element - Element to be inserted
     */
    public void addFirst( E element ) {
        DoublyListNode<E> newNode = new DoublyListNode<>(element, null, head);

        if (head == null) {
            tail = newNode;
        } else {
            head.setPrevious(newNode);
        }

        head = newNode;
        currentSize++;
    }

    /**
     * Inserts the element at the last position in the list.
     * @param element - Element to be inserted
     */
    public void addLast( E element ) {
        DoublyListNode<E> newNode = new DoublyListNode<>(element, tail, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        currentSize++;
    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst( )throws NoSuchElementException {
        if(currentSize==0){
            throw new NoSuchElementException();
        }

        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getLast( )throws NoSuchElementException {
        if(currentSize==0){
            throw new NoSuchElementException();
        }

        return tail.getElement();
    }


    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    public E get( int position )throws InvalidPositionException {
        if(position<0 || position>=currentSize){
            throw new InvalidPositionException();
        }
        if(position==0){
            return getFirst();
        }else if(position==currentSize-1){
            return getLast();
        }
        DoublyListNode<E> current;
        if (position < currentSize / 2) {
            current = head;
            for (int i = 0; i < position; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = currentSize - 1; i > position; i--) {
                current = current.getPrevious();
            }
        }

        return current.getElement();
    }

    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    public int indexOf( E element ) {
        DoublyListNode<E> current = head;
        int index = 0;
        while (current != null) {
            if (current.getElement().equals(element)) {
                return index;
            }
            current = current.getNext();
            index++;
        }
        return -1;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     * @param position - position where to insert element
     * @param element - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public void add( int position, E element )throws InvalidPositionException {
        if(position<0 || position>currentSize){
            throw new InvalidPositionException();
        }
        if(position==0){
            addFirst(element);
            return;
        }else if(position==currentSize){
            addLast(element);
            return;
        }
        DoublyListNode<E> current;

        if (position < currentSize / 2) {
            current = head;
            for (int i = 0; i < position; i++) {
                current = current.getNext();
            }
        }else{
            current = tail;
            for (int i = currentSize - 1; i > position; i--) {
                current = current.getPrevious();
            }
        }

        DoublyListNode<E> previousNode = current.getPrevious();

        DoublyListNode<E> newNode = new DoublyListNode<>(element, previousNode, current);

        previousNode.setNext(newNode);
        current.setPrevious(newNode);

        currentSize++;


    }

    /**
     * Removes and returns the element at the first position in the list.
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst( )throws NoSuchElementException {
        if(currentSize==0){
            throw new NoSuchElementException();
        }
        E element=head.getElement();
        head=head.getNext();
        if(head==null){
            tail=null;
        }else{
            head.setPrevious(null);
        }
        currentSize--;
        return element;
    }

    /**
     * Removes and returns the element at the last position in the list.
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast( ) {
        if(currentSize==0){
            throw new NoSuchElementException();
        }
        E element=tail.getElement();
        tail=tail.getPrevious();
        if(tail==null){
            head=null;
        }else{
            tail.setNext(null);
        }
        currentSize--;
        return element;
    }


    /**
     *  Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public E remove( int position )throws InvalidPositionException {
        if (position < 0 || position >= currentSize) {
            throw new InvalidPositionException();
        }
        if (position == 0) {
            return removeFirst();
        } else if (position == currentSize - 1) {
            return removeLast();
        } else {
            DoublyListNode<E> current;
            if (position < currentSize / 2) {
                current = head;
                for (int i = 0; i < position; i++) {
                    current = current.getNext();
                }
            } else {
                current = tail;
                for (int i = currentSize - 1; i > position; i--) {
                    current = current.getPrevious();
                }
            }
            E element = current.getElement();
            DoublyListNode<E> previousNode = current.getPrevious();
            DoublyListNode<E> nextNode = current.getNext();
            previousNode.setNext(nextNode);
            nextNode.setPrevious(previousNode);
            currentSize--;
            return element;
        }
    }

}
