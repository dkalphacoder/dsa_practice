package datastructures.binaryheap;

import java.util.ArrayList;

public class BinaryHeap<E extends Comparable<E>> {

    protected ArrayList<E> elements;
    protected boolean isMinHeap;

    public ArrayList<E> getElements() {
        return elements;
    }

    public void setElements(ArrayList<E> elements) {
        this.elements = elements;
    }

    public boolean isMinHeap() {
        return isMinHeap;
    }

    public void setMinHeap(boolean minHeap) {
        isMinHeap = minHeap;
    }

    private BinaryHeap() {
        setElements(new ArrayList<>());
    }

    public BinaryHeap(Boolean isMinHeap) {
        this();
        setMinHeap(isMinHeap);
    }

    public static void main(String[] args) {
        BinaryHeap<Integer> bh = new BinaryHeap<>(true);
        bh.getElements().add(1);
        bh.getElements().add(2);
        bh.getElements().add(4);
        bh.insert(3);

        System.out.println(bh.getElements());
        System.out.println(bh.getHighestPriorityElement() + " " + bh.extractHighestPriorityElement());
        System.out.println(bh.getElements());
    }

    public E getHighestPriorityElement() {
        return getElements().size() > 0 ? getElements().get(0) : null;
    }

    public boolean extractHighestPriorityElement() {
        if (getElements().size() == 0)
            return false;

        getElements().remove(0);
        if (getElements().size() <= 1) {
            return true;
        } else  {
            E lastElement = getElements().get(getElements().size() - 1);
            getElements().add(0, lastElement);
            getElements().remove(getElements().size() - 1);
            heapify(0);
            return true;
        }
    }

    public void insert(E e) {
        if (getElements().size() == 0) {
            getElements().add(e);
            return;
        }
        getElements().add(e);
        int addedElemIndex = getElements().size() - 1;
        int parentIndex = BinaryHeap.getParentIndex(addedElemIndex);
        while (parentIndex >= 0) {

            if(isMinHeap()) {
                if (getElements().get(parentIndex).compareTo(e) > 0) {
                    swapElements(parentIndex, addedElemIndex);
                    addedElemIndex = parentIndex;
                    parentIndex = BinaryHeap.getParentIndex(addedElemIndex);
                } else {
                    break;
                }

            } else {
                if (getElements().get(parentIndex).compareTo(e) < 0) {
                    swapElements(parentIndex, addedElemIndex);
                    addedElemIndex = parentIndex;
                    parentIndex = BinaryHeap.getParentIndex(addedElemIndex);
                } else {
                    break;
                }
            }
        }
    }

    public void swapElements(int index1, int index2) {
        E element1 = getElements().get(index1);
        E element2 = getElements().get(index2);
        getElements().set(index1, element2);
        getElements().set(index2, element1);
    }

    public void heapify(int index) {
        if (getElements().size() <= 1)
            return;

        if (2*index + 1 >= getElements().size()) {
            return;
        } else if (2*index + 2 >= getElements().size()) {
            swapElements(index, 2*index + 1);
            return;
        }

        E leftChild = getElements().get(2*index + 1);
        E rightChild = getElements().get(2*index + 2);
        if (isMinHeap()) {
            if (leftChild.compareTo(rightChild) < 0) {
                swapElements(index, 2*index + 1);
                heapify(2*index + 1);
            } else if (leftChild.compareTo(rightChild) > 0) {
                swapElements(index, 2*index + 2);
                heapify(2*index + 2);
            }
        } else {
            if (leftChild.compareTo(rightChild) > 0 ) {
                swapElements(index, 2*index + 1);
                heapify(2*index + 1);
            } else if (leftChild.compareTo(rightChild) < 0){
                swapElements(index, 2*index + 2);
                heapify(2*index + 2);
            }
        }
    }

    private static int getParentIndex(int i) {
        return (i-1)/2;
    }

}
