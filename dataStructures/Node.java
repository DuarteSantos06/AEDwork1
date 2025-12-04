/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */


package dataStructures;

import java.io.Serializable;
/**
 * Node Interface
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */

interface Node<E> extends Serializable{
    /**
     *  Returns the element of the node
     */
    E getElement();

    /**
     * Update the element
     * @param elem
     */
    void setElement(E elem);
}
