/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package dataStructures;

import java.io.Serializable;

/**
 * Two-Way List
 *
 * @author AED team
 * @version 1.0
 *
 * @param <E> Generic Element
 */
public interface TwoWayList<E> extends List<E>, Serializable {
    /**
     * Returns a two-way iterator of the elements in the list.
     *
     * @return Two-Way Iterator of the elements in the list
     */
    TwoWayIterator<E> twoWayiterator();
}