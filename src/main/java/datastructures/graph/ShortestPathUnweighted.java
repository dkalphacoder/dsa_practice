package datastructures.graph;

import java.util.*;

public class ShortestPathUnweighted<E> {

    private static final String dfs = "dfs";
    private static final String bfs = "bfs";
    private static final String dfsRecursive = "dfsRecursive";

    public static void main(String[] args) {
//
        ShortestPathUnweighted<Integer> sp = new ShortestPathUnweighted<>();

        GraphALR<Integer> g = new GraphALR<>();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(5);
        g.addVertex(6);

        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(4, 6);
        g.addEdge(5, 6);

        System.out.println("shortest paths all, own naive dfs approach " + sp.naiveShortestPaths(g,0, 6, dfs));
        System.out.println("shortest paths all, own naive dfs recursive approach " + sp.naiveShortestPaths(g,0, 6, dfsRecursive));
        System.out.println("shortest paths all, own naive bfs approach " + sp.naiveShortestPaths(g,0, 6, bfs));
        System.out.println("shortest paths all, own bfs approach " + sp.allBfsShortestPathsUnweighted(g,0, 6, "own"));
        System.out.println("shortest paths all, other bfs approach " + sp.allBfsShortestPathsUnweighted(g,0, 6, "other"));
        System.out.println("shortest paths normal: " + sp.bfsShortestPathUnweighted(g,0, 6));
    }

    /**
     * should work for any kind of graph,
     * we can only have multiple/one shortest paths, we have to do full traversal & keep adding paths (only if they are shortest), when we find destination
     * @return
     */
    public List<List<VertexAndWeight<E>>> naiveShortestPaths(GraphALR<E> graph, E src, E dst, String approach) {

        List<List<VertexAndWeight<E>>> allShortestPaths = new ArrayList<>();
        double minDistance = Double.MAX_VALUE;

        if (approach.equals(dfsRecursive)) {
            allShortestPathsDFSRec(graph, src, dst, new HashSet<>(), new ArrayList<>(){{ add(new VertexAndWeight<>(src, 0)); }}, allShortestPaths, 0);
        } else if (approach.equals(dfs)) {

//            System.out.println("naive DFS shortest path:");

            Stack<ArrayList<VertexAndWeight<E>>> s = new Stack<>();
            double currentDistance = 0;
            ArrayList<VertexAndWeight<E>> currentPath = new ArrayList<>(){{ add(new VertexAndWeight<>(src, 0)); }};
            s.push(currentPath);

            while (!s.empty()) {

                currentPath = s.pop();
                E currentVertex = currentPath.get(currentPath.size() - 1).getVertex();
                currentDistance = currentPath.get(currentPath.size() - 1).getWeight();

                if (currentVertex.equals(dst)) {

                    if (currentDistance < minDistance) {
                        minDistance = currentDistance;

                        allShortestPaths.clear();
                        allShortestPaths.add(currentPath);
                    } else if (minDistance == currentDistance) {
                        allShortestPaths.add(currentPath);
                    }
                }

                for (VertexAndWeight<E> neighbour : graph.getAdjList().get(currentVertex)) {

                    boolean skipNeighbour = false;
                    for (VertexAndWeight<E> element: currentPath) {
                        if (element.getVertex().equals(neighbour.getVertex())) {
                            skipNeighbour = true;
                            break;
                        }
                    }

                    if (!skipNeighbour) {
                        ArrayList<VertexAndWeight<E>> newPath = new ArrayList<>(currentPath);
                        newPath.add(new VertexAndWeight<>(neighbour.getVertex(), currentDistance + neighbour.getWeight()));

                        s.push(newPath);
                    }
                }
            }

        } else {

            /**
             * bfs implementation, just use queue instead of stack
             */
            Queue<ArrayList<VertexAndWeight<E>>> q = new LinkedList<>();
            double currentDistance = 0;
            ArrayList<VertexAndWeight<E>> currentPath = new ArrayList<>(){{ add(new VertexAndWeight<>(src, 0)); }};
            q.offer(currentPath);

            while (!q.isEmpty()) {

                currentPath = q.poll();
                E currentVertex = currentPath.get(currentPath.size() - 1).getVertex();
                currentDistance = currentPath.get(currentPath.size() - 1).getWeight();

                if (currentVertex.equals(dst)) {

                    if (currentDistance < minDistance) {
                        minDistance = currentDistance;

                        allShortestPaths.clear();
                        allShortestPaths.add(currentPath);
                    } else if (minDistance == currentDistance) {
                        allShortestPaths.add(currentPath);
                    }
                }

                for (VertexAndWeight<E> neighbour : graph.getAdjList().get(currentVertex)) {

                    boolean skipNeighbour = false;
                    for (VertexAndWeight<E> element: currentPath) {
                        if (element.getVertex().equals(neighbour.getVertex())) {
                            skipNeighbour = true;
                            break;
                        }
                    }

                    if (!skipNeighbour) {
                        ArrayList<VertexAndWeight<E>> newPath = new ArrayList<>(currentPath);
                        newPath.add(new VertexAndWeight<>(neighbour.getVertex(), currentDistance + neighbour.getWeight()));

                        q.offer(newPath);
                    }
                }
            }
        }

        /**
         *  shortest distance below, uncomment
         */
//        System.out.println(minDistance);
        return allShortestPaths;
    }

    private void allShortestPathsDFSRec(GraphALR<E> graph, E src, E dst, HashSet<E> visited, ArrayList<VertexAndWeight<E>> currentPath,
                                        List<List<VertexAndWeight<E>>> allPaths, double distance) {
        if (src.equals(dst)) {
            if (allPaths.size() == 0) {
                ArrayList<VertexAndWeight<E>> path = new ArrayList<>(currentPath);
                allPaths.add(path);
            } else {
                if (currentPath.size() < allPaths.get(0).size()) {
                    ArrayList<VertexAndWeight<E>> path = new ArrayList<>(currentPath);
                    allPaths.clear();
                    allPaths.add(path);
                } else if (currentPath.size() == allPaths.get(0).size()) {
                    ArrayList<VertexAndWeight<E>> path = new ArrayList<>(currentPath);
                    allPaths.add(path);
                }
            }
            return;
        }

        visited.add(src);

        for (VertexAndWeight<E> neighbour: graph.getAdjList().get(src)) {
            E n = neighbour.getVertex();
            if (!visited.contains(n)) {
                VertexAndWeight<E> vw = new VertexAndWeight<>(n,distance + neighbour.getWeight());
                currentPath.add(vw);
                allShortestPathsDFSRec(graph, n, dst, visited, currentPath, allPaths, vw.getWeight());
                currentPath.remove(vw);
            }
        }
        visited.remove(src);
        //@TODO add a return statement instead of void
    }

    /**
     * shortest path for unweighted graph bfs
     */
    public List<E> bfsShortestPathUnweighted(GraphALR<E> graph, E src, E dst) {
        double minDistance = Double.MAX_VALUE;

        Queue<VertexAndWeight<E>> q = new LinkedList<>();
        double currentDistance = 0;
        q.offer(new VertexAndWeight<>(src, currentDistance));

        HashMap<E, E> predecessors = new HashMap<>();
        HashSet<E> visited = new HashSet<>();
        visited.add(src);

        while (!q.isEmpty()) {
            VertexAndWeight<E> current = q.poll();
            E currentVertex = current.getVertex();
            currentDistance = current.getWeight();

            if (currentVertex.equals(dst)) {
                minDistance = current.getWeight();
                break;
            }

            for (VertexAndWeight<E> neighbour: graph.getAdjList().get(currentVertex)) {
                if (!visited.contains(neighbour.getVertex())) {
                    visited.add(neighbour.getVertex());
                    q.offer(new VertexAndWeight<>(neighbour.getVertex(), currentDistance+1 ));

                    predecessors.put(neighbour.getVertex(), currentVertex);
                }
            }
        }
        List<E> shortestPath = new ArrayList<>();
        E next = dst;
        while (next != null) {
            shortestPath.add(next);
            next = predecessors.get(next);
        }
        Collections.reverse(shortestPath);

        /**
         *  shortest distance below, uncomment
         */
//        System.out.println(minDistance);
        return shortestPath;
    }

    /**
     * all shortest paths for unweighted graph bfs
     */
    public List<List<VertexAndWeight<E>>> allBfsShortestPathsUnweighted(GraphALR<E> graph, E src, E dst, String approach) {
        /**
         *  my own approach, BFS while storing all shortest paths
         */
        if (approach.equals("own")) {

            List<List<VertexAndWeight<E>>> allShortestPaths = new ArrayList<>();
            double minDistance = Double.MAX_VALUE;

            Queue<ArrayList<VertexAndWeight<E>>> q = new LinkedList<>();
            double currentDistance = 0;
            ArrayList<VertexAndWeight<E>> currentPath = new ArrayList<>(){{ add(new VertexAndWeight<>(src, 0)); }};
            q.offer(currentPath);


            while (!q.isEmpty()) {
                currentPath = q.poll();
                E currentVertex = currentPath.get(currentPath.size() - 1).getVertex();
                currentDistance = currentPath.get(currentPath.size() - 1).getWeight();

                if (minDistance < currentDistance) break;

                if (currentVertex.equals(dst)) {
                    if (currentDistance < minDistance) {
                        minDistance = currentDistance;

                        allShortestPaths.clear();
                        allShortestPaths.add(currentPath);
                    } else if (minDistance == currentDistance) {
                        allShortestPaths.add(currentPath);
                    }
                }

                for (VertexAndWeight<E> neighbour: graph.getAdjList().get(currentVertex)) {
                    boolean skipNeighbour = false;
                    for (VertexAndWeight<E> element: currentPath) {
                        if (element.getVertex().equals(neighbour.getVertex())) {
                            skipNeighbour = true;
                            break;
                        }
                    }

                    if (!skipNeighbour) {
                        ArrayList<VertexAndWeight<E>> newPath = new ArrayList<>(currentPath);
                        newPath.add(new VertexAndWeight<>(neighbour.getVertex(), currentDistance+1));

                        q.offer(newPath);
                    }
                }
            }

            /**
             *  shortest distance below, uncomment
             */
//            System.out.println(minDistance);
            return allShortestPaths;

        } else {

            /**
             *  store all the successors while doing DFS
             */
            double minDistance = Double.MAX_VALUE;

            HashMap<E, ArrayList<E>> predecessors = new HashMap<>();
            HashMap<E, Double> distances = new HashMap<>();
            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry : graph.getAdjList().entrySet()) {
                E vertex = entry.getKey();
                predecessors.put(vertex, new ArrayList<>());
                distances.put(vertex, Double.MAX_VALUE);
            }

            Queue<VertexAndWeight<E>> q = new LinkedList<>();
            q.offer(new VertexAndWeight<>(src, 0));
            distances.put(src, (double) 0);

            while (!q.isEmpty()) {
                VertexAndWeight<E> current = q.poll();
                E currentVertex = current.getVertex();
                double currentDistance = current.getWeight();

                if (minDistance < currentDistance) break;

                if (currentVertex.equals(dst)) {
                    minDistance = current.getWeight();
                }

                for (VertexAndWeight<E> neighbour: graph.getAdjList().get(currentVertex)) {
                    E n = neighbour.getVertex();
                    if (distances.get(n) > distances.get(currentVertex) + 1) {
                        distances.put(n, distances.get(currentVertex) + 1);
                        q.offer(new VertexAndWeight<>(n, distances.get(n)));

                        predecessors.get(n).clear();
                        predecessors.get(n).add(currentVertex);
                    } else if (distances.get(n) == distances.get(currentVertex) + 1) {
                        predecessors.get(n).add(currentVertex);
                    }
                }
            }

//            System.out.println(predecessors);
            List<List<VertexAndWeight<E>>> allShortestPaths = new ArrayList<>();
            List<VertexAndWeight<E>> currentPath = new ArrayList<>();
            currentPath.add( new VertexAndWeight<>(dst,minDistance) );
            findPaths(predecessors, src, dst, allShortestPaths, currentPath, minDistance);

            /**
             *  shortest distance below, uncomment
             */
//            System.out.println(minDistance);
            return allShortestPaths;
        }
    }

    /**
     * you find paths from destination to source, so code may seems a bit weird here
     */
    private void findPaths(HashMap<E, ArrayList<E>> predecessors, E src, E dst, List<List<VertexAndWeight<E>>> allShortestPaths,
                           List<VertexAndWeight<E>> currentPath, double level) {
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
        for (E predecessor: predecessors.get(dst)) {
            currentPath.add(new VertexAndWeight<>(predecessor, level-1));

            findPaths(predecessors, src, predecessor, allShortestPaths, currentPath, level-1);

            currentPath.remove( currentPath.size() - 1);
        }
        //@TODO add a return statement instead of void
    }
}
