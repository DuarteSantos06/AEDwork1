/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */
package dataStructures;
/**
 * Array Iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
class ArrayIterator<E> implements Iterator<E> {
    private E[] elems;
    private int counter;
    private int current;

    /**
     * Constructor
     * @param elems Array of elements
     * @param counter Number of elements in the array
     */
    public ArrayIterator(E[] elems, int counter) {
        this.elems = elems;
        this.counter = counter;
        rewind();
    }

    /**
     * Reset the iterator
     * time complexity: O(1) on best and worst case
     */
    @Override
    public void rewind() {
        current=0;
    }

    /**
     * Returns true if next would return an element
     * Time complexity: O(1) on best and worst case
     * @return
     */
    @Override
    public boolean hasNext() {
        return current<counter;
    }

    @Override
    /**
     * Returns the next element in the iteration.
     * Time complexity: O(1) on best and worst case
     */
    public E next() {
        if(hasNext()){
            return elems[current++];
        }
        return null;
    }

}
