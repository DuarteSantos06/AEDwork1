/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */


package dataStructures;

import dataStructures.exceptions.NoSuchElementException;
/**
 * Singly list Iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */
class SinglyIterator<E>  implements Iterator<E> {

    /**
     * First node of the list.
     */
    private SinglyListNode<E> first;

    /**
     * Node with the next element in the iteration.
     */
    private SinglyListNode<E> nextToReturn;


    /**
     * SinglyIterator constructor
     * @param first - Node with the first element of the iteration
     */
    public SinglyIterator(SinglyListNode<E> first) {
        this.first=first;
        nextToReturn=first;
    }

    @Override
    public boolean hasNext( ) {
        return nextToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     * Time complexity: O(1)
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    @Override
    public E next( ){
        if ( !this.hasNext() )
            throw new NoSuchElementException();
        E element = nextToReturn.getElement();
        nextToReturn = nextToReturn.getNext();
        return element;
    }

    /**
     * Restart the iterator
     * Time complexity: O(1)
     */
    @Override
    public void rewind() {
        nextToReturn=first;
    }

}
