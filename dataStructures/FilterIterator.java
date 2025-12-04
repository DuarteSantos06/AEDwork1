/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator Abstract Data Type with Filter
 * Includes description of general methods for one way iterator.
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class FilterIterator<E> implements Iterator<E> {

    /**
     *  Iterator of elements to filter.
     */
    private Iterator<E> iterator;

    /**
     *  Filter.
     */
    private Predicate<E> filter;

    /**
     * Node with the next element in the iteration.
     */
    private E nextToReturn;

    /**
     *
     * @param list to be iterated
     * @param filter
     */
    public FilterIterator(Iterator<E> list, Predicate<E> filter) {
        iterator = list;
        this.filter = filter;
        nextToReturn = null;
    }

    /**
     * Returns true if next would return an element
     *Time Complexity: O(n)
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        while (nextToReturn == null && iterator.hasNext()) {
            E candidate = iterator.next();
            if (filter.check(candidate)) {
                nextToReturn = candidate;
            }
        }
        return nextToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     *Time Complexity: O(1)
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next()throws NoSuchElementException {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }
        E element = nextToReturn;
        nextToReturn = null;
        return element;
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     *Time Complexity: O(1)
     *
     */
    public void rewind() {
        nextToReturn = null;
        iterator.rewind();
    }

}
