package datastructures.graph;

import java.util.*;

public class ShortestPath<E> {

    private static final String dfs = "dfs";
    private static final String bfs = "bfs";
    private static final String dfsRecursive = "dfsRecursive";

    public static void main(String[] args) {
        ShortestPath<Integer> sp = new ShortestPath<>();

        GraphALR<Integer> g = new GraphALR<>();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(5);
        g.addVertex(6);
        g.addVertex(7);
        g.addVertex(8);

        g.addEdge(0, 1,4);
        g.addEdge(0, 7,8);
        g.addEdge(1, 2,8);
        g.addEdge(1, 7,11);
        g.addEdge(2, 3,7);
        g.addEdge(2, 5,4);
        g.addEdge(2, 8,2);
        g.addEdge(3, 4,2);
        g.addEdge(3, 5,14);
        g.addEdge(4, 5,10);
        g.addEdge(5, 6,2);
        g.addEdge(6, 7,1);
        g.addEdge(6, 8,6);
        g.addEdge(7, 8,7);

        ShortestPathUnweighted<Integer> spu = new ShortestPathUnweighted<>();
        System.out.println("naive shortest paths bfs: " + spu.naiveShortestPaths(g,0, 4,bfs) + "\n");
        System.out.println("naive shortest paths dfs: " + spu.naiveShortestPaths(g,0, 4,dfs) + "\n");
        System.out.println("naive shortest paths dfs recursive: " + spu.naiveShortestPaths(g,0, 4,dfsRecursive) + "\n");

        System.out.println("shortest paths dijkstra by finding nearest node: " + sp.dijkstraShortestPath(g,0, 4,1) + "\n");
        System.out.println("shortest paths dijkstra using priority queue: " + sp.dijkstraShortestPath(g,0, 4,2) + "\n");
        System.out.println("shortest paths bellman ford: " + sp.bellmanFord(g,0, 4) + "\n");
        System.out.println("all shortest paths: " + sp.floydWarshall(g) + "\n");
    }

    public double dijkstraShortestPath(GraphALR<E> graph, E src, E dst, int approach) {
        /**
         * approach using visited & finding nearest neighbour using List
         */
        if (approach == 1) {

            List<E> vertices = new ArrayList<>();
            HashSet<E> visited = new HashSet<>();
            HashMap<E, Double> distances = new HashMap<>();
            HashMap<E, List<VertexAndWeight<E>>> predecessors = new HashMap<>(); //add shortest path parent

            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry: graph.getAdjList().entrySet()) {
                distances.put(entry.getKey(), Double.MAX_VALUE);
                vertices.add(entry.getKey());

                predecessors.put(entry.getKey(), new ArrayList<>());
            }
            distances.put(src, (double) 0);

            while (visited.size() < vertices.size()) {
                E current = nearestNeighbour(vertices, visited, distances);
                visited.add(current);

                for (VertexAndWeight<E> neighbour: graph.getAdjList().get(current)) {
                    E n = neighbour.getVertex();
                    double edgeWeight = neighbour.getWeight();
                    if (!visited.contains(n) && distances.get(current) + edgeWeight <= distances.get(n)) {

                        if (distances.get(current) + edgeWeight < distances.get(n))
                            predecessors.get(n).clear();

                        distances.put(n, distances.get(current) + edgeWeight);
                        predecessors.get(n).add(new VertexAndWeight<>(current, distances.get(current)));
                    }
                }
            }

            List<List<VertexAndWeight<E>>> allShortestPaths = findPaths(predecessors, src, dst, distances, new HashSet<>());
            System.out.println("allShortestPaths using dijkstra: " + allShortestPaths);
            System.out.println("distances: "+ distances);

            return distances.get(dst);
        } else {

            /**
             * priority queue approach
             */

            HashMap<E, Double> distances = new HashMap<>();
            HashMap<E, List<VertexAndWeight<E>>> predecessors = new HashMap<>(); //add shortest path parent

            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry: graph.getAdjList().entrySet()) {
                distances.put(entry.getKey(), Double.MAX_VALUE);
                predecessors.put(entry.getKey(), new ArrayList<>());
            }

            Comparator<VertexAndWeight<E>> c1 = Comparator.comparing(VertexAndWeight::getWeight);
            PriorityQueue<VertexAndWeight<E>> pq = new PriorityQueue<>(c1);

            pq.offer(new VertexAndWeight<>(src, 0));
            distances.put(src, (double) 0);

            while (!pq.isEmpty()) {
                VertexAndWeight<E> current = pq.poll();

                E currentVertex = current.getVertex();

                for (VertexAndWeight<E> neighbour: graph.getAdjList().get(currentVertex)) {
                    E n = neighbour.getVertex();
                    double edgeWeight = neighbour.getWeight();
                    if (current.getWeight() + edgeWeight <= distances.get(n)) {

                        if (current.getWeight() + edgeWeight < distances.get(n))
                            predecessors.get(n).clear();
                        predecessors.get(n).add(new VertexAndWeight<>(currentVertex, distances.get(currentVertex)));

                        distances.put(n, distances.get(currentVertex) + edgeWeight);
                        pq.offer(new VertexAndWeight<>(n, distances.get(n)));
                    }
                }
            }

            List<List<VertexAndWeight<E>>> allShortestPaths = findPaths(predecessors, src, dst, distances, new HashSet<>());
            System.out.println("allShortestPaths using dijkstra: " + allShortestPaths);
            System.out.println("distances: "+ distances);

            return distances.get(dst);
        }
    }

    private E nearestNeighbour(List<E> vertices, HashSet<E> visited, HashMap<E, Double> distances) {
        double smallestDistance = Double.MAX_VALUE;
        E nearestVertex = null;
//        System.out.println(distances);
        for (E vertex: vertices) {
            if (!visited.contains(vertex) && distances.get(vertex) < smallestDistance) {
                nearestVertex = vertex;
                smallestDistance = distances.get(vertex);
            }
        }
        return nearestVertex;
    }

    /**
     * you find paths from destination to source, so code may seems a bit weird here
     */
    private List<List<VertexAndWeight<E>>> findPaths(HashMap<E, List<VertexAndWeight<E>>> predecessors, E src, E dst,
                                                     HashMap<E, Double> distances, HashSet<E> visited) {
        List<List<VertexAndWeight<E>>> allPaths = new ArrayList<>();
        if (dst.equals(src)) {
            List<VertexAndWeight<E>> srcList = new ArrayList<>();
            srcList.add(new VertexAndWeight<>(src,0));
            allPaths.add(srcList);
            return allPaths;
        }

        if (predecessors.get(dst).size() == 0) {
            return allPaths;
        }

        visited.add(dst);
        for (VertexAndWeight<E> predecessor: predecessors.get(dst)) {

            for (List<VertexAndWeight<E>> path:  findPaths(predecessors, src, predecessor.getVertex(), distances, visited)) {
                List<VertexAndWeight<E>> newPath = new ArrayList<>(path);
                newPath.add(new VertexAndWeight<>(dst, distances.get(dst)));
                allPaths.add(newPath);
            }
        }
        visited.remove(dst);

        return allPaths;
    }

    private void findPaths(HashMap<E, List<VertexAndWeight<E>>> predecessors, E src, E dst, List<List<VertexAndWeight<E>>> allShortestPaths,
                           List<VertexAndWeight<E>> currentPath) {
        if (dst.equals(src)) {
            ArrayList<VertexAndWeight<E>> newPath = new ArrayList<>(currentPath);
            Collections.reverse(newPath);
            allShortestPaths.add(newPath);
            return;
        }

        if (predecessors.get(dst).size() == 0) {
            return;
        }

        /**
         * there wont be any cycles here, otherwise we can just maintain a visited, its not a big deal (add visited before loop & remove visited after loop)
         * this is a modified implementation of all paths DFS
         */
        for (VertexAndWeight<E> predecessor: predecessors.get(dst)) {
            currentPath.add(new VertexAndWeight<>(predecessor.getVertex(), predecessor.getWeight()));

            findPaths(predecessors, src, predecessor.getVertex(), allShortestPaths, currentPath);

            currentPath.remove( currentPath.size() - 1);
        }
    }

    public double bellmanFord(GraphALR<E> graph, E src, E dst) {
        HashMap<E, Double> distances = new HashMap<>();
        HashMap<E, List<VertexAndWeight<E>>> predecessors = new HashMap<>();

        for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry: graph.getAdjList().entrySet()) {
            distances.put(entry.getKey(), Double.MAX_VALUE);
            predecessors.put(entry.getKey(), new ArrayList<>());
        }

        distances.put(src, (double) 0);

        for (int i=1; i <= graph.getAdjList().size()-1; i++) {
            // iterate over all edges
            boolean update = false;
            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry: graph.getAdjList().entrySet()) {
                E currentVertex = entry.getKey();
                for (VertexAndWeight<E> neighbour: graph.getAdjList().get(currentVertex)) {
                    double edgeWeight = neighbour.getWeight();
                    E neighbourVertex = neighbour.getVertex();

                    if (distances.get(currentVertex) != Double.MAX_VALUE && distances.get(currentVertex) + edgeWeight <= distances.get(neighbourVertex)) {

                        if (distances.get(currentVertex) + edgeWeight < distances.get(neighbourVertex))
                            predecessors.get(neighbourVertex).clear();

                        distances.put(neighbourVertex, distances.get(currentVertex) + edgeWeight);
                        update = true;

                        VertexAndWeight<E> parentVertex = new VertexAndWeight<>(currentVertex, distances.get(currentVertex));
                        if (!predecessors.get(neighbourVertex).contains(parentVertex)) predecessors.get(neighbourVertex).add(parentVertex);
                    }
                }
            }
            if (!update) break; //if there's no update in 1 of the iterations, then we can break the loop & we're done
        }

        /**
         * to detect negative weight cycle
         */
        for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry: graph.getAdjList().entrySet()) {
            E currentVertex = entry.getKey();
            for (VertexAndWeight<E> neighbour: graph.getAdjList().get(currentVertex)) {
                double edgeWeight = neighbour.getWeight();
                E neighbourVertex = neighbour.getVertex();

                if (distances.get(currentVertex) != Double.MAX_VALUE && distances.get(currentVertex) + edgeWeight < distances.get(neighbourVertex)) {
                    System.out.println("there is a negative weight cycle, ugh");
                    break;
                }
            }
        }

        List<List<VertexAndWeight<E>>> allShortestPaths = findPaths(predecessors, src, dst, distances, new HashSet<>());
        System.out.println("allShortestPaths using bellman ford: " + allShortestPaths);
        System.out.println("distances: "+ distances);

        return distances.get(dst);
    }

    public HashMap<String, Double> floydWarshall(GraphALR<E> graph) {
        HashMap<String, Double> distances = new HashMap<>();
        // assign distances in these for loops
        for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry: graph.getAdjList().entrySet()) {
            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry1: graph.getAdjList().entrySet()) {
                E vertexOne = entry.getKey();
                E vertexTwo = entry1.getKey();
                if (vertexOne.equals(vertexTwo)) {
                    distances.put(getMapKey(vertexOne, vertexTwo), (double) 0);
                } else {
                    distances.put(getMapKey(vertexOne, vertexTwo), Double.MAX_VALUE);
                }
            }
        }

        for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry: graph.getAdjList().entrySet()) {
            E currentVertex = entry.getKey();
            for (VertexAndWeight<E> neighbour: graph.getAdjList().get(currentVertex)) {
                distances.put(getMapKey(currentVertex, neighbour.getVertex()), neighbour.getWeight());
            }
        }

        for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry3: graph.getAdjList().entrySet()) {
            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry1: graph.getAdjList().entrySet()) {
                for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry2: graph.getAdjList().entrySet()) {
                    E v3 = entry3.getKey();
                    E v1 = entry1.getKey();
                    E v2 = entry2.getKey();

                    if (distances.get(getMapKey(v1,v3)) != Double.MAX_VALUE && distances.get(getMapKey(v3,v2)) != Double.MAX_VALUE
                            && distances.get(getMapKey(v1,v3)) + distances.get(getMapKey(v3,v2)) < distances.get(getMapKey(v1,v2))) {
                        distances.put(getMapKey(v1,v2), distances.get(getMapKey(v1,v3)) + distances.get(getMapKey(v3,v2)));
                    }
                }
            }
        }
        return distances;
    }

    private String getMapKey(E vertexOne, E vertexTwo) {
        return vertexOne+ ","+ vertexTwo;
    }
}
