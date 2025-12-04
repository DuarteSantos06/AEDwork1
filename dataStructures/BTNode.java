/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */
package dataStructures;

/**
 * Binary Tree Node
 * Represents a node in a binary tree.
 * @author AED
 * @version 1.0
 * @param <E> Generic Element stored in the node
 */
class BTNode<E> implements Node<E> {

    private E element;            // Element stored in the node
    private Node<E> parent;       // Pointer to parent node
    private Node<E> leftChild;    // Pointer to left child
    private Node<E> rightChild;   // Pointer to right child

    // Constructor with only element
    BTNode(E elem){
        this(elem, null, null, null);
    }

    // Constructor with element and parent
    BTNode(E elem, BTNode<E> parent) {
        this(elem, parent, null, null);
    }

    // Constructor with element, parent, left and right children
    BTNode(E elem, BTNode<E> parent, BTNode<E> leftChild, BTNode<E> rightChild){
        this.element = elem;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /** Returns the element stored in this node. O(1) */
    public E getElement() {
        return element;
    }

    /** Returns the left child node. O(1) */
    public Node<E> getLeftChild(){
        return leftChild;
    }

    /** Returns the right child node. O(1) */
    public Node<E> getRightChild(){
        return rightChild;
    }

    /** Returns the parent node. O(1) */
    public Node<E> getParent(){
        return parent;
    }

    /** Returns true if the node has no children. O(1) */
    boolean isLeaf() {
        return getLeftChild() == null && getRightChild() == null;
    }

    /** Sets the element of the node. O(1) */
    public void setElement(E elem) {
        element = elem;
    }

    /** Sets the left child of the node. O(1) */
    public void setLeftChild(Node<E> node) {
        leftChild = node;
    }

    /** Sets the right child of the node. O(1) */
    public void setRightChild(Node<E> node) {
        rightChild = node;
    }

    /** Sets the parent of the node. O(1) */
    public void setParent(Node<E> node) {
        parent = node;
    }

    /** Returns true if this node is the root (has no parent). O(1) */
    boolean isRoot() {
        return getParent() == null;
    }

    /**
     * Computes the height of the subtree rooted at this node.
     * O(n)
     * because it recursively computes height of left and right subtrees.
     */
    public int getHeight() {
        int leftHeight = (leftChild == null) ? -1 : ((BTNode<E>) leftChild).getHeight();
        int rightHeight = (rightChild == null) ? -1 : ((BTNode<E>) rightChild).getHeight();
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Returns the left-most descendant of this subtree (smallest element in BST).
     * O(h), where h is the height of the subtree, because it traverses down left children.
     */
    BTNode<E> furtherLeftElement() {
        BTNode<E> current = this;
        while (current.getLeftChild() != null) {
            current = (BTNode<E>) current.getLeftChild();
        }
        return current;
    }

    /**
     * Returns the right-most descendant of this subtree (largest element in BST).
     * O(h), where h is the height of the subtree, because it traverses down right children.
     */
    BTNode<E> furtherRightElement() {
        BTNode<E> current = this;
        while (current.getRightChild() != null) {
            current = (BTNode<E>) current.getRightChild();
        }
        return current;
    }
}
