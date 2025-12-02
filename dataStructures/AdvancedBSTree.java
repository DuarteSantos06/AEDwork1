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

    /**
     * Performs a tri-node restructuring (a single or double rotation rooted at X node).
     * Assumes the nodes are in one of following configurations:
     *
     * @param x - root of the rotation
     * <pre>
     *          z=c       z=c        z=a         z=a
     *          /  \      /  \       /  \        /  \
     *        y=b  t4   y=a  t4    t1  y=c     t1  y=b
     *       /  \      /  \           /  \         /  \
     *     x=a  t3    t1 x=b        x=b  t4       t2 x=c
     *    /  \          /  \       /  \             /  \
     *   t1  t2        t2  t3     t2  t3           t3  t4
     * </pre>
     * @return the new root of the restructured subtree
     */
    protected BTNode<Entry<K,V>> restructure (BTNode<Entry<K,V>> x) {

        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) x.getParent();
        BTNode<Entry<K,V>> z = (BTNode<Entry<K,V>>) y.getParent();


        if ((z.getLeftChild() == y && y.getLeftChild() == x) ||
                (z.getRightChild() == y && y.getRightChild() == x)) {


            if (z.getLeftChild() == y) {
                this.rotateRight(z);
                return y;
            }


            else {
                this.rotateLeft(z);
                return y;
            }
        }


        else {

            if (z.getLeftChild() == y) {
                this.rotateLeft(y);
                this.rotateRight(z);
                return x;
            }


            else {
                this.rotateRight(y);
                this.rotateLeft(z);
                return x;
            }
        }
    }
}
