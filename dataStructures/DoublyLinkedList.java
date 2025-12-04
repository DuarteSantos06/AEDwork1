/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */


package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;
import java.io.Serializable;

/**
 *
 * @author AED team
 * @version 1.0
 * @param <E> Generic element type
 */
public class DoublyLinkedList<E> implements TwoWayList<E>, Serializable {

    private DoublyListNode<E> head;
    private DoublyListNode<E> tail;
    private int currentSize;

    /**
     * Constructor: creates an empty doubly linked list.
     */
    public DoublyLinkedList() {
        head = null;
        tail = null;
        currentSize = 0;
    }

    /**
     * Checks if the list is empty.
     * Time Complexity: O(1)
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Returns the number of elements in the list.
     * Time Complexity: O(1)
     */
    public int size() {
        return currentSize;
    }

    /**
     * Returns a two-way iterator of the list.
     * Time Complexity: O(1) to create iterator
     */
    public TwoWayIterator<E> twoWayiterator() {
        return new TwoWayDoublyIterator<>(head, tail);
    }

    /**
     * Returns a forward iterator of the list.
     * Time Complexity: O(1) to create iterator
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Adds an element at the beginning of the list.
     * Time Complexity: O(1)
     */
    public void addFirst(E element) {
        DoublyListNode<E> newNode = new DoublyListNode<>(element, null, head);
        if (head == null) tail = newNode;
        else head.setPrevious(newNode);
        head = newNode;
        currentSize++;
    }

    /**
     * Adds an element at the end of the list.
     * Time Complexity: O(1)
     */
    public void addLast(E element) {
        DoublyListNode<E> newNode = new DoublyListNode<>(element, tail, null);
        if (tail == null) head = newNode;
        else tail.setNext(newNode);
        tail = newNode;
        currentSize++;
    }

    /**
     * Returns the first element.
     * Throws exception if list is empty.
     * Time Complexity: O(1)
     */
    public E getFirst() throws NoSuchElementException {
        if (currentSize == 0) throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element.
     * Throws exception if list is empty.
     * Time Complexity: O(1)
     */
    public E getLast() throws NoSuchElementException {
        if (currentSize == 0) throw new NoSuchElementException();
        return tail.getElement();
    }

    /**
     * Returns element at a specific position.
     * Time Complexity: O(n) worst case (middle element), O(1) for first/last
     */
    public E get(int position) throws InvalidPositionException {
        if (position < 0 || position >= currentSize) throw new InvalidPositionException();
        if (position == 0) return getFirst();
        if (position == currentSize - 1) return getLast();

        DoublyListNode<E> current;
        if (position < currentSize / 2) { // Start from head
            current = head;
            for (int i = 0; i < position; i++) current = current.getNext();
        } else { // Start from tail
            current = tail;
            for (int i = currentSize - 1; i > position; i--) current = current.getPrevious();
        }
        return current.getElement();
    }

    /**
     * Returns the index of the first occurrence of an element.
     * Returns -1 if element not found.
     * Time Complexity: O(n)
     */
    public int indexOf(E element) {
        DoublyListNode<E> current = head;
        int index = 0;
        while (current != null) {
            if (current.getElement().equals(element)) return index;
            current = current.getNext();
            index++;
        }
        return -1;
    }

    /**
     * Adds an element at a specific position.
     * Throws exception if position is invalid.
     * Time Complexity: O(n) worst case (middle position), O(1) for first/last
     */
    public void add(int position, E element) throws InvalidPositionException {
        if (position < 0 || position > currentSize) throw new InvalidPositionException();
        if (position == 0) { addFirst(element); return; }
        if (position == currentSize) { addLast(element); return; }

        DoublyListNode<E> current;
        if (position < currentSize / 2) {
            current = head;
            for (int i = 0; i < position; i++) current = current.getNext();
        } else {
            current = tail;
            for (int i = currentSize - 1; i > position; i--) current = current.getPrevious();
        }

        DoublyListNode<E> previousNode = current.getPrevious();
        DoublyListNode<E> newNode = new DoublyListNode<>(element, previousNode, current);
        previousNode.setNext(newNode);
        current.setPrevious(newNode);
        currentSize++;
    }

    /**
     * Removes the first element.
     * Throws exception if list is empty.
     * Time Complexity: O(1)
     */
    public E removeFirst() throws NoSuchElementException {
        if (currentSize == 0) throw new NoSuchElementException();
        E element = head.getElement();
        head = head.getNext();
        if (head == null) tail = null;
        else head.setPrevious(null);
        currentSize--;
        return element;
    }

    /**
     * Removes the last element.
     * Throws exception if list is empty.
     * Time Complexity: O(1)
     */
    public E removeLast() {
        if (currentSize == 0) throw new NoSuchElementException();
        E element = tail.getElement();
        tail = tail.getPrevious();
        if (tail == null) head = null;
        else tail.setNext(null);
        currentSize--;
        return element;
    }

    /**
     * Removes element at a specific position.
     * Throws exception if position invalid.
     * Time Complexity: O(n) worst case (middle element), O(1) for first/last
     */
    public E remove(int position) throws InvalidPositionException {
        if (position < 0 || position >= currentSize) throw new InvalidPositionException();
        if (position == 0) return removeFirst();
        if (position == currentSize - 1) return removeLast();

        DoublyListNode<E> current;
        if (position < currentSize / 2) {
            current = head;
            for (int i = 0; i < position; i++) current = current.getNext();
        } else {
            current = tail;
            for (int i = currentSize - 1; i > position; i--) current = current.getPrevious();
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