/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package dataStructures;
/**
 * Advanced Binary Search Tree
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
abstract class AdvancedBSTree <K extends Comparable<K>,V> extends BSTSortedMap<K,V>{
    /**
     * Performs a single left rotation rooted at z node.
     * Node y was a  right  child  of z before the  rotation,
     * then z becomes the left child of y after the rotation.
     *
     * Time complexity: O(1)
     * A rotation only adjusts a constant number of pointers.
     * @param z - root of the rotation
     * @pre: z has a right child
     */
    protected void rotateLeft( BTNode<Entry<K,V>> z){

        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) z.getRightChild();
        BTNode<Entry<K,V>> t2 = (BTNode<Entry<K,V>>) y.getLeftChild();
        y.setLeftChild(z);
        y.setParent(z.getParent());
        if (z.isRoot()) {
            this.root = y;
        } else {
            BTNode<Entry<K,V>> parentOfZ = (BTNode<Entry<K,V>>) z.getParent();
            if (parentOfZ.getLeftChild() == z) {
                parentOfZ.setLeftChild(y);
            } else {
                parentOfZ.setRightChild(y);
            }
        }
        z.setRightChild(t2);
        if (t2 != null) {
            t2.setParent(z);
        }
        z.setParent(y);
    }

    /**
     * Performs a single right rotation rooted at z node.
     * Node y was a left  child  of z before the  rotation,
     * then z becomes the right child of y after the rotation.
     * Time Complexity: O(1)
     * Also adjusts only a fixed number of pointers.
     * @param z - root of the rotation
     * @pre: z has a left child
     */
    protected void rotateRight( BTNode<Entry<K,V>> z){
        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) z.getLeftChild();
        BTNode<Entry<K,V>> t2 = (BTNode<Entry<K,V>>) y.getRightChild();
        y.setRightChild(z);
        y.setParent(z.getParent());

        if (z.isRoot()) {
            this.root = y;
        } else {
            BTNode<Entry<K,V>> parentOfZ = (BTNode<Entry<K,V>>) z.getParent();
            if (parentOfZ.getLeftChild() == z) {
                parentOfZ.setLeftChild(y);
            } else {
                parentOfZ.setRightChild(y);
            }
        }
        z.setLeftChild(t2);
        if (t2 != null) {
            t2.setParent(z);
        }
        z.setParent(y);
    }
}
