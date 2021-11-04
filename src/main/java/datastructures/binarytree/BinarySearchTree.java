package datastructures.binarytree;

import utils.Constants;

import java.util.HashMap;

public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E>{

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        BinaryTreeNode<Integer> ten = new BinaryTreeNode<>(10);
        BinaryTreeNode<Integer> twenty = new BinaryTreeNode<>(20);
        BinaryTreeNode<Integer> thirty = new BinaryTreeNode<>(30);
        BinaryTreeNode<Integer> forty = new BinaryTreeNode<>(40);
        BinaryTreeNode<Integer> fifty = new BinaryTreeNode<>(50);
        BinaryTreeNode<Integer> sixty = new BinaryTreeNode<>(60);
        BinaryTreeNode<Integer> seventy = new BinaryTreeNode<>(70);

        bst.setRoot(forty);
        System.out.println(bst.preAndSuc(40, true) + " " + bst.preAndSuc(40, false));
        forty.setLeft(twenty);
        System.out.println(bst.preAndSuc(20, true) + " " + bst.preAndSuc(20, false));
        System.out.println(bst.preAndSuc(40, true) + " " + bst.preAndSuc(40, false));
        twenty.setLeft(ten);
        twenty.setRight(thirty);
        forty.setRight(sixty);
        sixty.setLeft(fifty);
        sixty.setRight(seventy);

        System.out.println(bst.search(71, false));
        System.out.println(bst.search(50, false));
        System.out.println(bst.search(30, true));
        System.out.println(bst.extrema(true));
        System.out.println(bst.extrema(false));

        System.out.println(bst.preAndSuc(70, true) + " " + bst.preAndSuc(70, false));
        System.out.println(bst.preAndSuc(10, true) + " " + bst.preAndSuc(10, false));
        System.out.println(bst.preAndSuc(60,  true) + " " + bst.preAndSuc(60, false));
        bst.insert(55);
        bst.inOrderTraversal();
        System.out.println(bst.preAndSuc(55,  true) + " " + bst.preAndSuc(55, false)); //@TODO fix bug


    }

    public boolean search(E e, boolean isRecursive) {
        if (isRecursive) {
            return searchSubTree(e, getRoot());
        } else {

            if (getRoot() == null || e == null) {
                return false;
            }

            BinaryTreeNode<E> currentNode = getRoot();

            while (currentNode != null) {
                if (e.compareTo(currentNode.getData()) == 0) {
                    return true;
                } else if (e.compareTo(currentNode.getData()) > 0) {
                    currentNode = currentNode.getRight();
                } else {
                    currentNode = currentNode.getLeft();
                }
            }
            return false;
        }

    }

    public boolean searchSubTree(E e, BinaryTreeNode<E> node) {
        if (node == null) {
            return false;
        }

        if (e.compareTo(node.getData()) == 0) {
            return true;
        } else if (e.compareTo(node.getData()) > 0) {
            return searchSubTree(e, node.getRight());
        } else {
            return searchSubTree(e, node.getLeft());
        }
    }

    public HashMap<String, E> extrema(boolean isRecursive) {
        if (isRecursive) {

            return new HashMap<>(){{
                put(Constants.MIN, extremaInSubTree(getRoot(), Constants.MIN));
                put(Constants.MAX, extremaInSubTree(getRoot(), Constants.MAX));
            }};

        } else {

            if (getRoot() == null) {
                return null;
            }

            HashMap<String, E> extrema = new HashMap<>();
            BinaryTreeNode<E> currentNode = getRoot();

            while (currentNode != null) {
                if (currentNode.getLeft() == null) {
                    extrema.put(Constants.MIN, currentNode.getData());
                    break;
                } else {
                    currentNode = currentNode.getLeft();
                }
            }

            currentNode = getRoot();
            while (currentNode != null) {
                if (currentNode.getRight() == null) {
                    extrema.put(Constants.MAX, currentNode.getData());
                    break;
                } else {
                    currentNode = currentNode.getRight();
                }
            }

            return extrema;
        }
    }

    public E extremaInSubTree(BinaryTreeNode<E> node, String minOrMax) {
        if (node == null) {
            return null;
        }

        if (minOrMax.equals(Constants.MIN)) {

            if (node.getLeft() == null) {
                return node.getData();
            } else {
                return extremaInSubTree(node.getLeft(), minOrMax);
            }

        } else {

            if (node.getRight() == null) {
                return node.getData();
            } else {
                return extremaInSubTree(node.getRight(), minOrMax);
            }

        }

    }

    public HashMap<String, E> preAndSuc(E e, boolean isRecursive) {

        if (isRecursive) {
            return preAndSucForSubTree(getRoot(), e, null, null);
        } else {
            if (getRoot() == null || e == null) {
                return null;
            }

            BinaryTreeNode<E> currentNode = getRoot();
            BinaryTreeNode<E> preTemp = null, sucTemp = null;
            while (currentNode != null) {

                if (e.compareTo(currentNode.getData()) == 0) {

                    E predecessor = currentNode.getLeft() != null ? extremaInSubTree(currentNode.getLeft(), Constants.MAX)
                                : preTemp != null ? preTemp.getData() : null;

                    E successor = currentNode.getRight() != null ? extremaInSubTree(currentNode.getRight(), Constants.MIN)
                                : sucTemp != null ? sucTemp.getData() : null;

                    return new HashMap<>() {{
                        put(Constants.PREDECESSOR, predecessor);
                        put(Constants.SUCCESSOR, successor);
                    }};


                } else if (e.compareTo(currentNode.getData()) > 0) {
                    preTemp = currentNode;
                    currentNode = currentNode.getRight();
                } else {
                    sucTemp = currentNode;
                    currentNode = currentNode.getLeft();
                }
            }
            return null;
        }
    }

    public HashMap<String, E> preAndSucForSubTree(BinaryTreeNode<E> node, E e, BinaryTreeNode<E> preTemp, BinaryTreeNode<E> sucTemp) {
        if (e == null) {
            return null;
        }

        if (node.getData().compareTo(e) == 0) {

                E predecessor =  node.getLeft() != null ? extremaInSubTree(node.getLeft(), Constants.MAX) : preTemp != null ? preTemp.getData() : null;
                E successor = node.getRight() != null ? extremaInSubTree(node.getRight(), Constants.MIN) : sucTemp != null ? sucTemp.getData() : null;

            return new HashMap<>() {{
                put(Constants.PREDECESSOR, predecessor);
                put(Constants.SUCCESSOR, successor);
            }};

        } else if (e.compareTo(node.getData()) > 0) {
            return preAndSucForSubTree(node.getRight(), e, node, sucTemp);
        } else {
            return preAndSucForSubTree(node.getLeft(), e, preTemp, node);
        }
    }

    public void insert(E e) {

        if (getRoot() == null || e == null) {
            System.out.println("Invalid insertion");
            return;
        }

        BinaryTreeNode<E> currentNode = getRoot();

        while (currentNode != null) {
            if (e.compareTo(currentNode.getData()) == 0) {
                System.out.println("Element already exists");
                return;
            } else if (e.compareTo(currentNode.getData()) > 0) {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(new BinaryTreeNode<>(e));
                    return;
                } else {
                    currentNode = currentNode.getRight();
                }
            } else {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(new BinaryTreeNode<>(e));
                    return;
                } else {
                    currentNode = currentNode.getLeft();
                }
            }
        }
    }

}
