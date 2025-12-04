/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */


package dataStructures;
import java.io.Serializable;

/**
 * Singly List Node Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
class SinglyListNode<E> implements Serializable {
    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    /**
     * Element stored in the node.
     */
    private E element;

    /**
     * (Pointer to) the next node.
     */
    private SinglyListNode<E> next;

    /**
     *
     * @param theElement - The element to be contained in the node
     * @param theNext - the next node
     */
    public SinglyListNode( E theElement, SinglyListNode<E> theNext ) {
        element = theElement;
        next = theNext;
    }

    /**
     *
     * @param theElement to be contained in the node
     */
    public SinglyListNode( E theElement ) {
        this(theElement, null);
    }

    /**
     * Time complexity: O(1)
     * @return the element contained in the node
     */
    public E getElement( ) {
        return element;
    }

    /**
     * Time complexity: O(1)
     * @return the next node
     */
    public SinglyListNode<E> getNext( ) {
        return next;
    }

    /**
     * Time complexity: O(1)
     * @param newElement - New element to replace the current element
     */
    public void setElement( E newElement ) {
        element = newElement;
    }

    /**
     * Time complexity: O(1)
     * @param newNext - node to replace the next node
     */
    public void setNext( SinglyListNode<E> newNext ) {
        next = newNext;
    }
}
