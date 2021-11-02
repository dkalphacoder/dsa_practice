package datastructures.stack;

import datastructures.linkedlist.Node;

public class Stack<E> {
    protected Node<E> top;

    public Node<E> getTop() {
        return this.top;
    }

    public void setTop(Node<E> top) {
        this.top = top;
    }

    public Stack() {}

    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();

        s.push(1);
        System.out.println(s.peek());
        s.push(2);
        System.out.println(s.peek());
        s.push(3);
        s.push(45);
        s.push(4);
        s.traverseStack();
        s.pop();
        s.pop();
        s.pop();
        System.out.println("Size of the stack: " + s.size());
        s.traverseStack();
        s.pop();
        s.pop();
        s.traverseStack();
    }

    public boolean empty() {
        return top == null;
    }

    public E push(E e) {
        Node<E> newElement = new Node<>(e);

        if (empty()) {
            setTop(newElement);
            return e;
        }

        newElement.setNext(getTop());
        setTop(newElement);

        return e;
    }

    public E pop() {

        if (empty()) {
            System.out.println("No elements in stack. It is empty.");
            return null;
        }

        Node<E> newTop = getTop().getNext();
        Node<E> currentTop = getTop();

        getTop().setNext(null);
        setTop(newTop);

        return currentTop.getData();

    }

    public E peek() {
        if (empty()) {
            System.out.println("No elements in stack. It is empty.");
            return null;
        }

        return getTop().getData();
    }

    public void traverseStack() {
        if (this.empty()) {
            System.out.println("No elements in stack. It is empty.");
            return;
        }

        Node<E> currentNode = getTop();

        System.out.print("[");
        while (currentNode != null) {
            System.out.print(currentNode + ", ");
            currentNode = currentNode.getNext();
        }
        System.out.println("]");

    }

    public int size() {
        if (this.empty()) {
            System.out.println("No elements in stack. It is empty.");
            return 0;
        }

        Node<E> currentNode = getTop();

        int size = 0;
        while (currentNode != null) {
            currentNode = currentNode.getNext();
            size++;
        }

        return size;
    }

}
