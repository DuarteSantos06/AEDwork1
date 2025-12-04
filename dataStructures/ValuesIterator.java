/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package dataStructures;

import dataStructures.exceptions.NoSuchElementException;
/**
 * Iterator of values
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */
class ValuesIterator<E> implements Iterator<E> {

    private Iterator<Map.Entry<?,E>> it;
   

    public ValuesIterator(Iterator<Map.Entry<?,E>> it) {
        this.it = it;
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * Time complexity: O(1)
     * @return true iff the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     * Time complexity: O(1)
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    @Override
    public E next() {
        if(!it.hasNext()){
            throw new NoSuchElementException();
        }
        return it.next().value();
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     * Time complexity: O(1)
     */
    @Override
    public void rewind() {
       it.rewind();
    }
}
