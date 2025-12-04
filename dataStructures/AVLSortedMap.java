/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */
package dataStructures;
/**
 * AVL Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class AVLSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{
    /**
     * Inserts a new (key, value) pair into the tree.
     * If the key already exists, replaces the old value.
     *
     * @param key key to insert
     * @param value value to insert
     * @return old value if key existed, otherwise null
     * Time Complexity: O(log n)
     */
    public V put(K key, V value) {
        if (isEmpty()) {
            root = new AVLNode<>(new Map.Entry<>(key, value));
            currentSize++;
            return null;
        }

        AVLNode<Map.Entry<K, V>> parent = null;
        AVLNode<Map.Entry<K, V>> current = (AVLNode<Map.Entry<K, V>>) root;

        while (current != null) {
            int cmp = key.compareTo(current.getElement().key());

            if (cmp == 0) {
                V oldValue = current.getElement().value();
                current.setElement(new Map.Entry<>(key, value));
                return oldValue;
            }

            parent = current;
            current = (cmp < 0 ? (AVLNode<Map.Entry<K, V>>) current.getLeftChild()
                    : (AVLNode<Map.Entry<K, V>>) current.getRightChild());
        }

        AVLNode<Map.Entry<K, V>> newNode = new AVLNode<>(new Map.Entry<>(key, value), parent);

        if (key.compareTo(parent.getElement().key()) < 0) {
            parent.setLeftChild(newNode);
        }
        else {
            parent.setRightChild(newNode);
        }
        this.rebalance(parent);

        currentSize++;
        return null;
    }

    /**
     * Removes the entry with the given key.
     *
     * @param key key to remove
     * @return removed value, or null if key does not exist
     * Time Complexity: O(log n)
     */
    @Override
    public V remove(K key) {
        AVLNode<Map.Entry<K, V>> current = (AVLNode<Map.Entry<K, V>>) this.root;

        while (current != null) {
            int cmp = key.compareTo(current.getElement().key());
            if (cmp == 0) {
                V removedValue = current.getElement().value();
                AVLNode<Map.Entry<K, V>> rebalanceStartNode = null;
                if (current.getLeftChild() != null && current.getRightChild() != null) {
                    AVLNode<Map.Entry<K, V>> successor =
                            (AVLNode<Map.Entry<K, V>>) ((BTNode<Map.Entry<K, V>>) current.getRightChild()).furtherLeftElement();
                    rebalanceStartNode = (AVLNode<Map.Entry<K,V>>) successor.getParent();
                    current.setElement(successor.getElement());
                    current = successor;
                }
                AVLNode<Map.Entry<K, V>> child =
                        (AVLNode<Map.Entry<K, V>>) (current.getLeftChild() != null ?
                                current.getLeftChild() : current.getRightChild());

                if (!current.isRoot()) {
                    if (rebalanceStartNode == null || rebalanceStartNode == current)
                        rebalanceStartNode = (AVLNode<Map.Entry<K,V>>) current.getParent();
                }
                this.splice(current, child);

                if (current.isRoot()) {
                    this.root = child;
                }
                currentSize--;
                if (rebalanceStartNode != null) {
                    this.rebalance(rebalanceStartNode);
                }

                return removedValue;
            }

            current = (cmp < 0 ? (AVLNode<Map.Entry<K, V>>) current.getLeftChild()
                    : (AVLNode<Map.Entry<K, V>>) current.getRightChild());
        }

        return null;
    }

    /**
     * Rebalances the tree starting from a node up to the root.
     *
     * @param node starting node
     * Time Complexity: O(log n)
     */
    protected void rebalance(AVLNode<Map.Entry<K,V>> node) {
        AVLNode<Map.Entry<K,V>> current = node;

        while (current != null) {
            current.updateHeight();
            int leftHeight = this.getHeightSafe(current.getLeftChild());
            int rightHeight = this.getHeightSafe(current.getRightChild());
            int bf = leftHeight - rightHeight; // balance factor

            if (bf > 1 || bf < -1) {
                AVLNode<Map.Entry<K,V>> y = (AVLNode<Map.Entry<K,V>>) (bf > 1 ? current.getLeftChild() : current.getRightChild());
                int yBf = this.getHeightSafe(y.getLeftChild()) - this.getHeightSafe(y.getRightChild());
                AVLNode<Map.Entry<K,V>> x = (AVLNode<Map.Entry<K,V>>) (
                        (bf > 0 && yBf >= 0) || (bf < 0 && yBf <= 0)
                                ? (bf > 1 ? y.getLeftChild() : y.getRightChild())
                                : (bf > 1 ? y.getRightChild() : y.getLeftChild())
                );
            }
            current = (AVLNode<Map.Entry<K,V>>) current.getParent();
        }
    }

    /**
     * Returns height of a node safely (returns -1 if null).
     * Time Complexity: O(1)
     */
    private int getHeightSafe(Node<Map.Entry<K,V>> node) {
        if (node == null) return -1;
        return ((AVLNode<Map.Entry<K,V>>) node).getHeight();
    }

    /**
     * Removes node u and links its child v to the parent.
     * Time Complexity: O(1)
     */
    protected void splice(BTNode<Entry<K,V>> u, BTNode<Entry<K,V>> v) {
        BTNode<Entry<K, V>> parentOfU = (BTNode<Entry<K, V>>) u.getParent();
        if (u.isRoot()) this.root = v;
        else if (parentOfU.getLeftChild() == u) parentOfU.setLeftChild(v);
        else parentOfU.setRightChild(v);

        if (v != null) v.setParent(parentOfU);
    }
}







