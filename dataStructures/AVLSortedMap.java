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
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {



        if (isEmpty()) {
            // Usa o construtor do AVLNode
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
            if (cmp < 0)
                current = (AVLNode<Map.Entry<K, V>>) current.getLeftChild();
            else
                current = (AVLNode<Map.Entry<K, V>>) current.getRightChild();
        }



        AVLNode<Map.Entry<K, V>> newNode = new AVLNode<>(new Map.Entry<>(key, value), parent);


        if (key.compareTo(parent.getElement().key()) < 0)
            parent.setLeftChild(newNode);
        else
            parent.setRightChild(newNode);


        this.rebalance(parent);

        currentSize++;
        return null;
    }

    /**
     *
     * @param key whose entry is to be removed from the map
     * @return
     */
    /**
     * Remove a entrada associada à chave especificada do mapa.
     *
     * @param key cuja entrada deve ser removida do mapa
     * @return o valor anterior associado à chave, ou null se a chave não for encontrada.
     */

    /**
     * Remove a entrada associada à chave especificada do mapa.
     *
     * @param key cuja entrada deve ser removida do mapa
     * @return o valor anterior associado à chave, ou null se a chave não for encontrada.
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
                        (AVLNode<Map.Entry<K, V>>) (current.getLeftChild() != null ? current.getLeftChild() : current.getRightChild());


                if (current.isRoot()) {

                    rebalanceStartNode = null;
                } else {
                    if (rebalanceStartNode == null) {
                        rebalanceStartNode = (AVLNode<Map.Entry<K,V>>) current.getParent();
                    }

                    else if (rebalanceStartNode == current) {
                        rebalanceStartNode = (AVLNode<Map.Entry<K,V>>) current.getParent();
                    }
                }



                this.splice(current, child);


                if (current.isRoot()) {
                    this.root = child;
                }

                this.currentSize--;


                if (rebalanceStartNode != null) {
                    this.rebalance(rebalanceStartNode);
                }

                return removedValue;
            }


            if (cmp < 0) {
                current = (AVLNode<Map.Entry<K, V>>) current.getLeftChild();
            } else {
                current = (AVLNode<Map.Entry<K, V>>) current.getRightChild();
            }
        }


        return null;
    }

    protected void rebalance(AVLNode<Map.Entry<K,V>> node) {

        AVLNode<Map.Entry<K,V>> current = node;

        while (current != null) {


            current.updateHeight();


            int leftHeight = this.getHeightSafe(current.getLeftChild());
            int rightHeight = this.getHeightSafe(current.getRightChild());
            int bf = leftHeight - rightHeight;


            if (bf > 1 || bf < -1) {


                AVLNode<Map.Entry<K,V>> y;
                if (bf > 1) {
                    y = (AVLNode<Map.Entry<K,V>>) current.getLeftChild();
                } else {
                    y = (AVLNode<Map.Entry<K,V>>) current.getRightChild();
                }


                int yBf = this.getHeightSafe(y.getLeftChild()) - this.getHeightSafe(y.getRightChild());

                AVLNode<Map.Entry<K,V>> x;
                if ((bf > 0 && yBf >= 0) || (bf < 0 && yBf <= 0)) {

                    x = (AVLNode<Map.Entry<K,V>>) (bf > 1 ? y.getLeftChild() : y.getRightChild());
                } else {

                    x = (AVLNode<Map.Entry<K,V>>) (bf > 1 ? y.getRightChild() : y.getLeftChild());
                }


                if (x == null) {

                    current = (AVLNode<Map.Entry<K,V>>) current.getParent();
                    continue;
                }
            }


            current = (AVLNode<Map.Entry<K,V>>) current.getParent();
        }
    }
    private int getHeightSafe(Node<Map.Entry<K,V>> node) {
        if (node == null) {
            return -1;
        }

        return ((AVLNode<Map.Entry<K,V>>) node).getHeight();
    }

    protected void splice(BTNode<Entry<K,V>> u, BTNode<Entry<K,V>> v) {


        BTNode<Entry<K, V>> parentOfU = (BTNode<Entry<K, V>>) u.getParent();


        if (u.isRoot()) {

            this.root = v;
        } else {

            if (parentOfU.getLeftChild() == u) {
                parentOfU.setLeftChild(v);
            } else {
                parentOfU.setRightChild(v);
            }
        }


        if (v != null) {
            v.setParent(parentOfU);
        }
    }







}
