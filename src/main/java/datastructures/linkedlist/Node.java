package datastructures.linkedlist;

import java.util.Objects;

public class Node<T> {
    private T data;
    private Node<T> next;

    public Node(){}

    public Node(T d) {
        data = d;
        next = null;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return this.next;
    }

    public void setNext(Node<T> node) {
        this.next = node;
    }

    public String toString() {
        return data.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node<?> otherNode = (Node<?>) o;
        return otherNode.getData().equals(this.getData());
    }

    public int hashCode() {
        return Objects.hash(getData());
    }
}