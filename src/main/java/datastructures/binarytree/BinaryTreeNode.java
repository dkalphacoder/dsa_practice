package datastructures.binarytree;

public class BinaryTreeNode<E> {

    protected E data;
    protected BinaryTreeNode<E> left;
    protected BinaryTreeNode<E> right;

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public BinaryTreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<E> left) {
        this.left = left;
    }

    public BinaryTreeNode<E> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<E> right) {
        this.right = right;
    }

    public BinaryTreeNode() {}

    public BinaryTreeNode(E data) {
        setData(data);
    }
}
