/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

/**
 * Tree
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */
package dataStructures;
import java.io.Serializable;

abstract class Tree<E> implements Serializable {

    /**
     * Root
     */
    protected Node<E> root;

    /**
     * Number of elements
     */
    protected int currentSize;

    public Tree(){
        root=null;
        currentSize=0;
    }

    /**
     * Returns true iff the dictionary contains no entries.
     * Time complexity: O(1)
     * @return true if dictionary is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of entries in the dictionary.
     * Time complexity: O(1)
     * @return number of elements in the dictionary
     */
    public int size() {
        return currentSize;
    }


    /**
     * Time complexity: O(1)
     * Return the root of the tree
     * @return
     */
    Node<E> root(){ return root;}

}