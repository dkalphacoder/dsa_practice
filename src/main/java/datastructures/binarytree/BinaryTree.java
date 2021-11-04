package datastructures.binarytree;

public class BinaryTree<E extends Comparable<E>> {

    protected BinaryTreeNode<E> root;

    public BinaryTreeNode<E> getRoot() {
        return root;
    }

    public void setRoot(BinaryTreeNode<E> root) {
        this.root = root;
    }

    public static void main(String[] args) {
        BinaryTree<Integer> bt = new BinaryTree<>();
        BinaryTreeNode<Integer> ten = new BinaryTreeNode<>(10);
        bt.setRoot(ten);

        BinaryTreeNode<Integer> twenty = new BinaryTreeNode<>(20);
        BinaryTreeNode<Integer> thirty = new BinaryTreeNode<>(30);
        BinaryTreeNode<Integer> forty = new BinaryTreeNode<>(40);
        BinaryTreeNode<Integer> fifty = new BinaryTreeNode<>(50);
        BinaryTreeNode<Integer> sixty = new BinaryTreeNode<>(60);
        BinaryTreeNode<Integer> seventy = new BinaryTreeNode<>(70);
//        BinaryTreeNode<Integer> l4 = new BinaryTreeNode<>(80);
//        BinaryTreeNode<Integer> r4 = new BinaryTreeNode<>(90);

        ten.setLeft(twenty);
        ten.setRight(thirty);

        twenty.setLeft(forty);
        twenty.setRight(fifty);
        thirty.setLeft(sixty);
        thirty.setRight(seventy);
//        l2.setRight(r4);
//        r3.setLeft(l4);

        bt.inOrderTraversal();
        bt.preOrderTraversal();
        bt.postOrderTraversal();
    }

    public void inOrderTraversal() {
        inOrderTraverseHelper(getRoot());
        System.out.println(" ");
    }

    private void inOrderTraverseHelper(BinaryTreeNode<E> node) {
        if (node == null) {
            return;
        }

        inOrderTraverseHelper(node.getLeft());
        System.out.print(node.getData() + ", ");
        inOrderTraverseHelper(node.getRight());
    }

    public void preOrderTraversal() {
        preOrderTraverseHelper(getRoot());
        System.out.println(" ");
    }

    private void preOrderTraverseHelper(BinaryTreeNode<E> node) {
        if (node == null) {
            return;
        }

        System.out.print(node.getData() + ", ");
        preOrderTraverseHelper(node.getLeft());
        preOrderTraverseHelper(node.getRight());
    }

    public void postOrderTraversal() {
        postOrderTraverseHelper(getRoot());
        System.out.println(" ");
    }

    private void postOrderTraverseHelper(BinaryTreeNode<E> node) {
        if (node == null) {
            return;
        }

        postOrderTraverseHelper(node.getLeft());
        postOrderTraverseHelper(node.getRight());
        System.out.print(node.getData() + ", ");
    }

}
