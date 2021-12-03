package datastructures.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphALR<E> {

    protected HashMap<E, ArrayList<VertexAndWeight<E>>> adjList;

    public HashMap<E, ArrayList<VertexAndWeight<E>>> getAdjList() {
        return this.adjList;
    }

    public void setAdjList(HashMap<E, ArrayList<VertexAndWeight<E>>> adjList) {
        this.adjList = adjList;
    }

    public GraphALR() {
        setAdjList(new HashMap<>());
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
        g.addEdge("b","f",false);
        g.addEdge("f","d",2);

        System.out.println(g.getAdjList());
    }

    public void addVertex(E vertex) {
        getAdjList().put(vertex, new ArrayList<>());
    }

    public void addEdge(E vertexOne, E vertexTwo, boolean bidirectional, double weight) {

        if (!getAdjList().containsKey(vertexOne) || !getAdjList().containsKey(vertexTwo)) {
            System.out.println("One or both of the vertices dont exist, create vertex and try again");
        }

        getAdjList().get(vertexOne).add(new VertexAndWeight<>(vertexTwo, weight));

        if (bidirectional) {
            getAdjList().get(vertexTwo).add(new VertexAndWeight<>(vertexOne, weight));
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
