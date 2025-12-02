package dataStructures;

class RBNode<K extends Comparable<K>, V> extends BTNode<Map.Entry<K, V>> {
    boolean isRed;

    public RBNode(Map.Entry<K, V> element) {
        super(element);
        this.isRed = true;
    }

    public RBNode(Map.Entry<K, V> element, BTNode<Map.Entry<K, V>> parent) {
        super(element, (BTNode<Map.Entry<K, V>>) parent);
        this.isRed = true;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }
}
