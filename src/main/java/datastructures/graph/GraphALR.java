package datastructures.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class GraphALR<E> {

    public static class VertexWithWeight<E> {
        E vertex;
        double weight;

        public VertexWithWeight() {}

        public VertexWithWeight(E vertex) {
            this.vertex = vertex;
        }

        public VertexWithWeight(E vertex, double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return vertex.toString() + ":"+ weight;
        }
    }

    protected HashMap<E, Integer> vertices;
    protected ArrayList<LinkedList<VertexWithWeight<E>>> adjList;

    public HashMap<E, Integer> getVertices() {
        return this.vertices;
    }

    public void setVertices(HashMap<E, Integer> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<LinkedList<VertexWithWeight<E>>> getAdjList() {
        return this.adjList;
    }

    public void setAdjList(ArrayList<LinkedList<VertexWithWeight<E>>> adjList) {
        this.adjList = adjList;
    }

    public GraphALR() {
        setVertices(new HashMap<>());
        setAdjList(new ArrayList<>());
    }

    public static void main(String[] args) {
        GraphALR<String> g = new GraphALR<>();

        g.addVertex("a");
        g.addVertex("b");
        g.addVertex("d");
        g.addVertex("f");
        g.addVertex("kl");

        g.addEdge("a","b",false, 3);
        g.addEdge("kl","d");

        System.out.println(g.getVertices());
        System.out.println(g.getAdjList());
    }

    public void addVertex(E vertex) {
        ArrayList<LinkedList<VertexWithWeight<E>>> adjList = getAdjList();
        adjList.add(new LinkedList<>());

        int verticesCount = adjList.size();
        adjList.get(verticesCount-1).add(new VertexWithWeight<>(vertex));

        getVertices().put(vertex, verticesCount-1);
    }

    public void addEdge(E vertexOne, E vertexTwo, boolean bidirectional, double weight) {

        int vertexOneIndex = getVertices().get(vertexOne) != null ? getVertices().get(vertexOne) : -1;
        int vertexTwoIndex = getVertices().get(vertexTwo) != null ? getVertices().get(vertexTwo) : -1;

        if (vertexOneIndex == -1 || vertexTwoIndex == -1) {
            System.out.println("One or both of the vertices dont exist, create vertex and try again");
        }

        ArrayList<LinkedList<VertexWithWeight<E>>> adjList = getAdjList();

        adjList.get(vertexOneIndex).add(new VertexWithWeight<>(vertexTwo, weight));
        if (bidirectional) {
            adjList.get(vertexTwoIndex).add(new VertexWithWeight<>(vertexOne, weight));
        }
    }

    public void addEdge(E vertexOne, E vertexTwo) {
        this.addEdge(vertexOne, vertexTwo, true, 1.0);
    }

    public void addEdge(E vertexOne, E vertexTwo, double weight) {
        this.addEdge(vertexOne, vertexTwo, true, weight);
    }

    public void addEdge(E vertexOne, E vertexTwo, boolean bidirectional) {
        this.addEdge(vertexOne, vertexTwo, bidirectional,1.0);
    }
}
