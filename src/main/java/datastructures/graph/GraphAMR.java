package datastructures.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphAMR<E>{
    
    protected HashMap<E, Integer> vertices;
    protected ArrayList<ArrayList<Double>> adjMatrix;

    public HashMap<E, Integer> getVertices() {
        return this.vertices;
    }

    public void setVertices(HashMap<E, Integer> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<ArrayList<Double>> getAdjMatrix() {
        return this.adjMatrix;
    }

    public void setAdjMatrix(ArrayList<ArrayList<Double>> adjMatrix) {
        this.adjMatrix = adjMatrix;
    }

    public GraphAMR () {
        setVertices(new HashMap<>());
        setAdjMatrix(new ArrayList<>());
    }

    public static void main(String[] args) {

        GraphAMR<String> graph = new GraphAMR<>();
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");

        graph.addEdge("c","d", false);
        graph.addEdge("a","c", true, 2.951);
        graph.addEdge("b", "d");

        System.out.println("vertices: " + graph.getVertices() );
        System.out.println("edges: "+ graph.getAdjMatrix());
    }

    public void addVertex(E vertex) {

        HashMap<E, Integer> vertices = getVertices();
        vertices.put(vertex, vertices.size());

        this.initializeEdges();
    }

    private void initializeEdges() {
        ArrayList<ArrayList<Double>> adjMatrix = getAdjMatrix();

        for (ArrayList<Double> edgesOfVertex: adjMatrix) {
            edgesOfVertex.add(0.0);
        }

        adjMatrix.add(new ArrayList<>());

        int verticesCount = getVertices().size();
        for (int i = 0; i < verticesCount; i++) {
            adjMatrix.get(verticesCount-1).add(0.0);
        }
    }

    public void addEdge(E vertexOne, E vertexTwo, boolean bidirectional, double weight) {
        ArrayList<ArrayList<Double>> adjMatrix = getAdjMatrix();

        int vertexOneIndex = getVertices().get(vertexOne) != null ? getVertices().get(vertexOne) : -1;
        int vertexTwoIndex = getVertices().get(vertexTwo) != null ? getVertices().get(vertexTwo) : -1;

        if (vertexOneIndex == -1 || vertexTwoIndex == -1) {
            System.out.println("One or both of the vertices dont exist, create vertex and try again");
        }

        adjMatrix.get(vertexOneIndex).set(vertexTwoIndex, weight);

        if (bidirectional) {
            adjMatrix.get(vertexTwoIndex).set(vertexOneIndex, weight);
        }
    }

    public void addEdge(E vertexOne, E vertexTwo) {
        this.addEdge(vertexOne, vertexTwo, true,1.0);
    }

    public void addEdge(E vertexOne, E vertexTwo, double weight) {
        this.addEdge(vertexOne, vertexTwo, true, weight);
    }

    public void addEdge(E vertexOne, E vertexTwo, boolean bidirectional) {
        this.addEdge(vertexOne, vertexTwo, bidirectional, 1.0);
    }

}
