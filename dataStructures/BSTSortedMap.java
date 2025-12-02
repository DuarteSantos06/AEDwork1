package dataStructures;

import dataStructures.exceptions.EmptyMapException;
/**
 * Binary Search Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTSortedMap<K extends Comparable<K>,V> extends BTree<Map.Entry<K,V>> implements SortedMap<K,V> {

    /**
     * Constructor
     */
    public BSTSortedMap(){
        super();
    }
    /**
     * Returns the entry with the smallest key in the dictionary.
     *
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Map.Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the dictionary.
     *
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Map.Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        Node<Map.Entry<K,V>> node=getNode((BTNode<Map.Entry<K,V>>)root,key);
        if (node!=null)
            return node.getElement().value();
        return null;
    }

    private BTNode<Map.Entry<K,V>> getNode(BTNode<Map.Entry<K,V>> node, K key) {
        if (node == null)
            return null;

        int cmp = key.compareTo(node.getElement().key());

        if (cmp == 0)
            return node; // Found it
        else if (cmp < 0)
            return getNode((BTNode<Map.Entry<K,V>>) node.getLeftChild(), key);
        else
            return getNode((BTNode<Map.Entry<K,V>>) node.getRightChild(), key);
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        if (isEmpty()) {
            root = new BTNode<>(new Map.Entry<>(key, value));
            currentSize++;
            return null;
        }
        BTNode<Map.Entry<K, V>> parent = null;
        BTNode<Map.Entry<K, V>> current = (BTNode<Map.Entry<K, V>>) root;

        while (current != null) {
            int cmp = key.compareTo(current.getElement().key());
            if (cmp == 0) {
                V oldValue = current.getElement().value();
                current.setElement(new Map.Entry<>(key, value));
                return oldValue;
            }
            parent = current;
            if (cmp < 0)
                current = (BTNode<Map.Entry<K, V>>) current.getLeftChild();
            else
                current = (BTNode<Map.Entry<K, V>>) current.getRightChild();
        }


        BTNode<Map.Entry<K, V>> newNode = new BTNode<>(new Map.Entry<>(key, value), parent);

        if (key.compareTo(parent.getElement().key()) < 0)
            parent.setLeftChild(newNode);
        else
            parent.setRightChild(newNode);

        currentSize++;
        return null;
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    @Override
    public V remove(K key) {
        BTNode<Map.Entry<K,V>> node = getNode((BTNode<Map.Entry<K,V>>) root, key);
        if (node == null)
            return null;

        V oldValue = node.getElement().value();
        deleteNode(node);
        currentSize--;
        return oldValue;
    }

    /**
     * Removes the given node from the BST.
     */
    private void deleteNode(BTNode<Map.Entry<K,V>> node) {

        if (node.getLeftChild() == null && node.getRightChild() == null) {
            if (node.isRoot()) {
                root = null;
            } else {
                BTNode<Map.Entry<K,V>> parent = (BTNode<Map.Entry<K,V>>) node.getParent();
                if (parent.getLeftChild() == node)
                    parent.setLeftChild(null);
                else
                    parent.setRightChild(null);
            }
        }

        else if (node.getLeftChild() == null || node.getRightChild() == null) {
            BTNode<Map.Entry<K,V>> child = (BTNode<Map.Entry<K,V>>)
                    (node.getLeftChild() != null ? node.getLeftChild() : node.getRightChild());

            if (node.isRoot()) {
                root = child;
                child.setParent(null);
            } else {
                BTNode<Map.Entry<K,V>> parent = (BTNode<Map.Entry<K,V>>) node.getParent();
                if (parent.getLeftChild() == node)
                    parent.setLeftChild(child);
                else
                    parent.setRightChild(child);
                child.setParent(parent);
            }
        }

        else {

            BTNode<Map.Entry<K,V>> successor = ((BTNode<Map.Entry<K,V>>) node.getRightChild()).furtherLeftElement();

            node.setElement(successor.getElement());

            deleteNode(successor);
        }
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Map.Entry<K,V>>) root);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
    @Override
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
    @Override
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }
}
