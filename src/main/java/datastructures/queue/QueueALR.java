package datastructures.queue;

import java.util.ArrayList;

public class QueueALR<E> {

    protected ArrayList<E> elements;

    public ArrayList<E> getElements() {
        return elements;
    }

    public void setElements(ArrayList<E> elements) {
        this.elements = elements;
    }

    public QueueALR() {
        setElements(new ArrayList<>());
    }

    public static void main(String[] args) {
        QueueALR<Integer> q = new QueueALR<>();

        System.out.println(q.isEmpty());
        q.enQueue(1);
        q.enQueue(2);
        q.enQueue(3);
        q.enQueue(4);
        q.enQueue(5);
        q.deQueue();
        q.deQueue();
        q.deQueue();
        System.out.println(q.isEmpty());
        q.enQueue(6);
        q.enQueue(7);
        q.traverseQueue();
        System.out.println("peek: " + q.peek() + ", rear: " + q.rear());
    }

    public boolean isEmpty() {
        return getElements().isEmpty();
    }

    public E enQueue(E e) {
        getElements().add(getElements().size(), e);
        return e;
    }

    public E deQueue() {
        E e = getElements().get(0);
        getElements().remove(0);

        return e;
    }

    public E peek() {
        return getElements().get(0);
    }

    public E rear() {
        return getElements().get(getElements().size() - 1);
    }

    public void traverseQueue() {
        System.out.println(getElements());
    }

}
