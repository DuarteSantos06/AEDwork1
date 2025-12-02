package dataStructures;

public class RBSortedMap<K extends Comparable<K>, V> extends AdvancedBSTree<K,V> {

    public RBSortedMap() {
        super();
    }

    @Override
    public V put(K key, V value) {
        Entry<K,V> entry = new Entry<>(key, value);

        if (isEmpty()) {
            root = new RBNode<>(entry);
            ((RBNode<K,V>) root).setRed(false); // raiz preta
            currentSize++;
            return null;
        }

        RBNode<K,V> parent = null;
        RBNode<K,V> current = (RBNode<K,V>) root;

        while (current != null) {
            int cmp = key.compareTo(current.getElement().key());
            if (cmp == 0) {
                V oldValue = current.getElement().value();
                current.setElement(entry);
                return oldValue;
            }
            parent = current;
            if (cmp < 0)
                current = (RBNode<K,V>) current.getLeftChild();
            else
                current = (RBNode<K,V>) current.getRightChild();
        }

        RBNode<K,V> newNode = new RBNode<>(entry, parent);
        if (key.compareTo(parent.getElement().key()) < 0)
            parent.setLeftChild(newNode);
        else
            parent.setRightChild(newNode);

        fixInsert(newNode);
        currentSize++;
        return null;
    }

    private void fixInsert(RBNode<K,V> node) {
        while (node != root && ((RBNode<K,V>) node.getParent()).isRed()) {
            RBNode<K,V> parent = (RBNode<K,V>) node.getParent();
            RBNode<K,V> grandparent = (RBNode<K,V>) parent.getParent();

            if (grandparent == null) {
                break; // Se não houver grandparent, terminamos o fix
            }

            if (parent == grandparent.getLeftChild()) {
                RBNode<K,V> uncle = (RBNode<K,V>) grandparent.getRightChild();
                if (uncle != null && uncle.isRed()) {
                    parent.setRed(false);
                    uncle.setRed(false);
                    ((RBNode<K,V>) grandparent).setRed(true);
                    node = (RBNode<K,V>) grandparent;
                } else {
                    if (node == parent.getRightChild()) {
                        node = parent;
                        rotateLeft(node);
                    }
                    parent.setRed(false);
                    ((RBNode<K,V>) grandparent).setRed(true);
                    rotateRight((RBNode<K,V>) grandparent);
                }
            } else {
                RBNode<K,V> uncle = (RBNode<K,V>) grandparent.getLeftChild();
                if (uncle != null && uncle.isRed()) {
                    parent.setRed(false);
                    uncle.setRed(false);
                    ((RBNode<K,V>) grandparent).setRed(true);
                    node = (RBNode<K,V>) grandparent;
                } else {
                    if (node == parent.getLeftChild()) {
                        node = parent;
                        rotateRight(node);
                    }
                    parent.setRed(false);
                    ((RBNode<K,V>) grandparent).setRed(true);
                    rotateLeft((RBNode<K,V>) grandparent);
                }
            }
        }
        ((RBNode<K,V>) root).setRed(false);
    }
}
