/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */


package dataStructures;

import dataStructures.exceptions.*;



/**
 * Sorted Doubly linked list Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class SortedDoublyLinkedList<E> implements SortedList<E> {

    /**
     *  Node at the head of the list.
     */
    private DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private DoublyListNode<E> tail;
    /**     * Number of elements in the list.
     */
    private int currentSize;
    /**
     * Comparator of elements.
     */
    private final Comparator<E> comparator;
    /**
     * Constructor of an empty sorted double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SortedDoublyLinkedList(Comparator<E> comparator) {
        currentSize = 0;
        this.comparator = comparator;
    }

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMin( ) throws NoSuchElementException {
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
    public E getMax( ) throws NoSuchElementException {
        if(currentSize==0){
            throw new NoSuchElementException();
        }
        return tail.getElement();
    }
    /**
     * Returns the first occurrence of the element equals to the given element in the list.
     * @return element in the list or null
     * Time complexity: O(n)
     */
    public E get(E element) {
        DoublyListNode<E> current = head;

        while(current!=null) {
            int cmp = comparator.compare(element, current.getElement());
            if(cmp==0){
                return current.getElement();

            }else if(cmp<0){
                return null;
            }
            current=current.getNext();
        }
        return null;
    }

    /**
     * Returns true iff the element exists in the list.
     *Time complexity: O(n)
     * @param element to be found
     * @return true iff the element exists in the list.
     */
    public boolean contains(E element) {
        return get(element)!=null;
    }

    /**
     * Inserts the specified element at the list, according to the natural order.
     * If there is an equal element, the new element is inserted after it.
     * @param element to be inserted
     * Time complexity: O(n)
     */
    public void add(E element) {
        DoublyListNode<E> newNode = new DoublyListNode<>(element);

        if(head==null){
            head=tail=newNode;
            currentSize++;
            return;
        }

        DoublyListNode<E> current = head;

        while(current!=null) {
            int cmp = comparator.compare(element, current.getElement());

            if(cmp==0){
                newNode.setNext(current.getNext());
                newNode.setPrevious(current);
                if(current.getNext()==null){
                    tail=newNode;
                }else{
                    current.getNext().setPrevious(newNode);
                }
                currentSize++;
                current.setNext(newNode);
                return;

            }else if(cmp<0){
                newNode.setNext(current);
                newNode.setPrevious(current.getPrevious());
                if(current.getPrevious()==null){
                    head=newNode;
                }else{
                    current.getPrevious().setNext(newNode);
                }
                current.setPrevious(newNode);
                currentSize++;
                return;
            }
            current=current.getNext();
        }
        tail.setNext(newNode);
        newNode.setPrevious(tail);
        tail = newNode;
        currentSize++;
    }

    /**
     * Removes and returns the first occurrence of the element equals to the given element in the list.
     * @return element removed from the list or null if !belongs(element)
     * Time complexity: O(n)
     */
    public E remove(E element) {
        if(head==null){
            return null;
        }

        DoublyListNode<E> current = head;
        E elementRemoved=null;
        while(current!=null) {
            int cmp = comparator.compare(element, current.getElement());
            if(cmp==0){
                elementRemoved = current.getElement();
                if(current==head){
                    head=current.getNext();
                    if(head!=null){
                        head.setPrevious(null);
                    }else{
                        tail=null;
                    }
                }else if(current==tail){
                    tail=current.getPrevious();
                    tail.setNext(null);
                }else{
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                }
            }
            currentSize--;
            current=current.getNext();
        }
        return elementRemoved;
    }
}
