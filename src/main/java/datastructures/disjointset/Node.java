package datastructures.disjointset;

import java.util.Objects;

public class Node<E> {

    private E data;
    private E parent;
    private int rank;
    private int size;

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public E getParent() {
        return parent;
    }

    public void setParent(E parent) {
        this.parent = parent;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Node() {}

    public Node(E data) {
        setData(data);
        setParent(data);
        setRank(0);
        setSize(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(data, node.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", parent=" + parent +
                ", rank=" + rank +
                ", size=" + size +
                '}';
    }
}
