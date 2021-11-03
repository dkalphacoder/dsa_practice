package datastructures.queue;

import datastructures.linkedlist.Node;

public class Queue<E> {

    protected Node<E> front;
    protected Node<E> rear;

    public Node<E> getFront() {
        return front;
    }

    public void setFront(Node<E> front) {
        this.front = front;
    }

    public Node<E> getRear() {
        return rear;
    }

    public void setRear(Node<E> rear) {
        this.rear = rear;
    }

    public static void main(String[] args) {
        Queue<Integer> q = new Queue<>();

        q.enQueue(1);
        q.enQueue(2);
        q.enQueue(3);
        q.enQueue(4);
        q.enQueue(5);
        q.deQueue();
        q.deQueue();
        q.deQueue();
        q.enQueue(6);
        q.enQueue(7);
        q.traverseQueue();
        System.out.println("peek: " + q.peek() + ", rear: " + q.rear());
    }

    public boolean isEmpty() {
        return getFront() == null;
    }

    public E enQueue(E e) {
        Node<E> newElement = new Node<>(e);
        if (isEmpty()) {
            setFront(newElement);
            setRear(newElement);

            return e;
        }

        getRear().setNext(newElement);
        setRear(newElement);

        return e;
    }

    public E deQueue() {
        if (isEmpty()) {
            System.out.print("Cannot dequeue. Queue is empty!");
            return null;
        }

        Node<E> deQueuedElement = getFront();

        if (getFront() == getRear()) {
            setFront(null);
            setRear(null);
            return deQueuedElement.getData();
        }

        setFront(getFront().getNext());
        return deQueuedElement.getData();
    }

    public E  peek() {
        if (isEmpty()) {
            return null;
        }

        return getFront().getData();
    }

    public E rear() {
        if (isEmpty()) {
            return null;
        }

        return getRear().getData();
    }

    public void traverseQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        Node<E> currentNode = getFront();

        System.out.print("[");
        while (currentNode != null) {
            System.out.print(currentNode.getData() + ", ");
            currentNode = currentNode.getNext();
        }

        System.out.println("]");
    }

}
