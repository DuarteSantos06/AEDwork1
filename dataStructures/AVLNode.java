/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package dataStructures;
/**
 * AVL Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class AVLNode<E> extends BTNode<E> {
    // Height of the node
    protected int height;

    public AVLNode(E elem) {
        super(elem);
        height=0;
    }

    public AVLNode( E element, AVLNode<E> parent,
                    AVLNode<E> left, AVLNode<E> right ){
        super(element, parent, left, right);
        height=0;
    }
    public AVLNode( E element, AVLNode<E> parent){
        super(element, parent,null, null);
        height= 0;
    }

    private int height(AVLNode<E> no) {
        if (no==null)	return -1;
        return no.getHeight();
    }
    public int getHeight() {
        return height;
    }

    /**
     * Update the left child and height
     * @param node
     */
    public void setLeftChild(AVLNode<E> node) {
        super.setLeftChild(node);
        this.updateHeight();
    }

    /**
     * Update the right child and height
     * @param node
     */
    public void setRightChild(AVLNode<E> node) {
        super.setRightChild(node);
        this.updateHeight();
    }

    protected void updateHeight() {


        int leftHeight = this.height((AVLNode<E>) this.getLeftChild());
        int rightHeight = this.height((AVLNode<E>) this.getRightChild());

        this.height = 1 + Math.max(leftHeight, rightHeight);
    }
// others public methods
//TODO: Left as an exercise.




}
