/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */


package dataStructures;

import dataStructures.exceptions.NoSuchElementException;
/**
 * Iterator of keys
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */
class KeysIterator<E> implements Iterator<E> {


    private Iterator<Map.Entry<E, ?>> entriesIterator;


    public KeysIterator(Iterator<Map.Entry<E,?>> it) {
       this.entriesIterator = it;
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * Time complexity: O(1)
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return entriesIterator.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     * Time complexity: O(1)
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();
        return entriesIterator.next().key();
    }

    /**
     * Restarts the iteration.
     * TIme complexity: O(1)
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        entriesIterator.rewind();
    }
}
