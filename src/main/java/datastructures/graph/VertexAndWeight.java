package datastructures.graph;

import java.util.Objects;

public class VertexAndWeight<E> {
    E vertex;
    double weight;

    public E getVertex() {
        return vertex;
    }

    public void setVertex(E vertex) {
        this.vertex = vertex;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    private VertexAndWeight() {}

    public VertexAndWeight(E vertex) {
        this.vertex = vertex;
        this.weight = 1; //By default we can assume each weight is 1
    }

    public VertexAndWeight(E vertex, double weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return vertex.toString() + ":"+ weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VertexAndWeight<?> other = (VertexAndWeight<?>) o;
        return getVertex().equals(other.getVertex()) && getWeight() == other.getWeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex, weight);
    }
}
