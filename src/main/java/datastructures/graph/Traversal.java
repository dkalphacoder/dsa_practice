package datastructures.graph;

import java.util.*;

public class Traversal<E>{

    private static final String dfs = "dfs";
    private static final String bfs = "bfs";
    private static final String dfsRecursive = "dfsRecursive";

    public static void main(String[] args) {
//        GraphALR<String> g = new GraphALR<>();
//
//        g.addVertex("a");
//        g.addVertex("b");
//        g.addVertex("d");
//        g.addVertex("c");
//        g.addVertex("e");
//        g.addVertex("f");
//
//        g.addEdge("a","b",false, 3);
//        g.addEdge("a","c" ,false);
//        g.addEdge("b","d",false);
//        g.addEdge("c","e", false);
//        g.addEdge("d","f",false, 2);
//
//        System.out.println(g.getAdjList());
//
        Traversal<Integer> ga = new Traversal<>();
//        ga.depthFirst(g, "a", true);
//        ga.breadthFirst(g,"a");

        GraphALR<Integer> g = new GraphALR<>();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(5);
        g.addVertex(8);

        g.addEdge(0, 8);
        g.addEdge(0, 1);
        g.addEdge(0, 5);
        g.addEdge(5, 0);
        g.addEdge(5, 8);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 4);

        System.out.println("connected components: " + ga.connectedComponents(g,dfsRecursive));
        System.out.println("largest component: " + ga.largestComponent(g,dfsRecursive));
    }

    /**
     * depth first traversal implementations
     */
    public void depthFirst(GraphALR<E> graph, E src, boolean recursive) {
        System.out.println("dfs:");
        if (recursive) {
            depthFirstRecursive(graph, src, new HashSet<>());
            return;
        }

        Stack<E> vertices = new Stack<>();
        vertices.push(src);

        HashSet<E> visited = new HashSet<>(){{
            add(src);
        }};

        while (!vertices.empty()) {
            E current = vertices.pop();
            System.out.println(current);

            for ( VertexAndWeight<E> neighbour: graph.getAdjList().get(current)) {
                E n = neighbour.getVertex();
                if (!visited.contains(n)) {
                    vertices.push(n);
                    visited.add(n);
                }
            }
        }
    }

    public int depthFirst(GraphALR<E> graph, E src, HashSet<E> visited, boolean recursive) {
        System.out.println("dfs:");
        if (recursive) {
            return depthFirstRecursive(graph, src, visited);
        }

        int nodeCount = 0;
        Stack<E> vertices = new Stack<>();
        vertices.push(src);
        visited.add(src);
        nodeCount++;

        while (!vertices.empty()) {
            E current = vertices.pop();
            System.out.println(current);

            for ( VertexAndWeight<E> neighbour: graph.getAdjList().get(current)) {
                E n = neighbour.getVertex();
                if (!visited.contains(n)) {
                    vertices.push(n);
                    visited.add(n);
                    nodeCount++;
                }
            }
        }

        return nodeCount;
    }

    private int depthFirstRecursive(GraphALR<E> graph, E src, HashSet<E> visited) {
        int nodeCount = 0;
        System.out.println(src);
        visited.add(src);
        nodeCount++;

        for ( VertexAndWeight<E> neighbour: graph.getAdjList().get(src)) {
            E n = neighbour.getVertex();
            if (!visited.contains(n)) {
                nodeCount += depthFirstRecursive(graph, n, visited);
            }
        }
        return nodeCount;
    }

    /**
     * breadth first traversal implementations
     */
    public void breadthFirst(GraphALR<E> graph, E src) {
        Queue<E> vertices = new LinkedList<>();
        vertices.add(src);

        System.out.println("bfs:");
        HashSet<E> visited = new HashSet<>() {{
            add(src);
        }};

        while (!vertices.isEmpty()) {
            E current = vertices.remove();
            System.out.println(current);

            for ( VertexAndWeight<E> neighbour: graph.getAdjList().get(current)) {
                E n = neighbour.getVertex();
                if (!visited.contains(n)) {
                    vertices.add(n);
                    visited.add(n);
                }
            }
        }
    }

    private int breadthFirst(GraphALR<E> graph, E src, HashSet<E> visited) {
        int nodeCount = 0;
        System.out.println("bfs:");

        Queue<E> vertices = new LinkedList<>();
        vertices.add(src);
        visited.add(src);
        nodeCount++;

        while (!vertices.isEmpty()) {
            E current = vertices.remove();
            System.out.println(current);

            for ( VertexAndWeight<E> neighbour: graph.getAdjList().get(current)) {
                E n = neighbour.getVertex();
                if (!visited.contains(n)) {
                    vertices.add(n);
                    visited.add(n);
                    nodeCount++;
                }
            }
        }
        return nodeCount;
    }

    /**
     * checks if path is present between source & destination
     */
    public boolean hasPath(GraphALR<E> graph, E src, E dst, boolean isDFSApproach, boolean recursive) {
        if (isDFSApproach) {
            if (recursive) return hasPathDepthFirstSearch(graph, src, dst, new HashSet<>());

            Stack<E> v = new Stack<>();
            v.push(src);
            HashSet<E> visited = new HashSet<>() {{ add(src);}};
            while (!v.empty()) {
                E current = v.pop();
                if (current.equals(dst)) return true;

                for (VertexAndWeight<E> neighbour: graph.getAdjList().get(current)) {
                    E n = neighbour.getVertex();
                    if (!visited.contains(n)) {
                        v.push(n);
                        visited.add(n);
                    }
                }
            }
            return false;
        } else {

            Queue<E> q = new LinkedList<>();
            q.add(src);
            HashSet<E> visited = new HashSet<>();
            visited.add(src);
            while (!q.isEmpty()) {
                E current = q.remove();
                if (current.equals(dst)) return true;

                for (VertexAndWeight<E> neighbour: graph.getAdjList().get(current)) {
                    E n = neighbour.getVertex();
                    if (!visited.contains(n)) {
                        q.add(n);
                        visited.add(n);
                    }
                }
            }
            return false;
        }
    }

    private boolean hasPathDepthFirstSearch(GraphALR<E> graph, E src, E dst, HashSet<E> visited) {
        if (src.equals(dst)) return true;

        visited.add(src);
        for (VertexAndWeight<E> neighbour: graph.getAdjList().get(src)) {
            E n = neighbour.getVertex();
            if (!visited.contains(n) && hasPathDepthFirstSearch(graph, n, dst, visited)) return true;
        }
        return false;
    }

    /**
     * return all paths between source and destination
     */
    public List<List<E>> allPaths(GraphALR<E> graph, E src, E dst, String approach) {
        if (approach.equals(dfsRecursive)) {
            List<List<E>> allPaths = new ArrayList<>();
            //original appraoch by dkalphacoder
            ArrayList<E> currentPath = new ArrayList<>(){{ add(src); }};
            allPathsDFSRec(graph, src, dst, currentPath, allPaths);


            //using visited
            allPathsDFSRec(graph, src, dst, new HashSet<>(), new ArrayList<>(){{ add(src); }}, allPaths);

            return allPaths;
        } else if (approach.equals(dfs)) {

            List<List<E>> result = new ArrayList<>();

            Queue<ArrayList<E>> allPaths = new LinkedList<>();
            ArrayList<E> currentPath = new ArrayList<>(){{ add(src); }};

            allPaths.offer(currentPath);
            while (!allPaths.isEmpty()) {
                currentPath = allPaths.poll();

                E currentElement = currentPath.get( currentPath.size() - 1);
                if (currentElement.equals(dst)) {
                    result.add(currentPath);
                }

                for (VertexAndWeight<E> neighbour : graph.getAdjList().get(currentElement)) {
                    E n = neighbour.getVertex();
                    if (!currentPath.contains(n)) {
                        ArrayList<E> newPath = new ArrayList<>(currentPath);
                        newPath.add(n);

                        allPaths.offer(newPath);
                    }
                }
            }
            return result;

        } else {

            List<List<E>> result = new ArrayList<>();

            Stack<ArrayList<E>> allPaths = new Stack<>();
            ArrayList<E> currentPath = new ArrayList<>(){{ add(src); }};

            allPaths.push(currentPath);
            while (!allPaths.empty()) {
                currentPath = allPaths.pop();

                E currentElement = currentPath.get(currentPath.size() - 1);
                if (currentPath.equals(dst)) {
                    result.add(currentPath);
                }

                for (VertexAndWeight<E> neighbour: graph.getAdjList().get(currentElement)) {
                    E n = neighbour.getVertex();
                    if (!currentPath.contains(n)) {
                        ArrayList<E> newPath = new ArrayList<>(currentPath);
                        newPath.add(n);

                        allPaths.push(newPath);
                    }
                }
            }

            return result;
        }
    }

    /**
     *  3 dfs implementations to get all paths, 1 uses currentPath to keep track, 1 uses visited & 1 uses DP
     */
    private void allPathsDFSRec(GraphALR<E> graph, E src, E dst, ArrayList<E> currentPath, List<List<E>> allPaths) {
        if (src.equals(dst)) {
            allPaths.add(currentPath);
            return;
        }

        for (VertexAndWeight<E> neighbour: graph.getAdjList().get(src)) {
            E n = neighbour.getVertex();
            if (!currentPath.contains(n)) {
                ArrayList<E> newPath = new ArrayList<>(currentPath);
                newPath.add(n);

                allPathsDFSRec(graph, n, dst, newPath, allPaths);
            }
        }
        //@TODO modify method and add return statement
    }

    private void allPathsDFSRec(GraphALR<E> graph, E src, E dst, HashSet<E> visited, ArrayList<E> currentPath, List<List<E>> allPaths) {
        if (src.equals(dst)) {
            ArrayList<E> path = new ArrayList<>(currentPath);
            allPaths.add(path);
            return;
        }

        visited.add(src);

        for (VertexAndWeight<E> neighbour: graph.getAdjList().get(src)) {
            E n = neighbour.getVertex();
            if (!visited.contains(n)) {
                currentPath.add(n);
                allPathsDFSRec(graph, n, dst, visited, currentPath, allPaths);
                currentPath.remove(n);
            }
        }
        visited.remove(src);
        //@TODO modify method and add return statement
    }

    private List<List<E>> allPathsDFSRecDP(GraphALR<E> graph, E src, E dst, HashSet<E> visited, HashMap<String, List<List<E>>> memo) {
        String memoKey = src + "," + dst;
        if(memo.containsKey(memoKey)) {
            return memo.get(memoKey);
        }

        List<List<E>> allPaths = new ArrayList<>();
        if (src.equals(dst)) {
            ArrayList<E> path = new ArrayList<>() {{ add(dst); }};
            allPaths.add(path);
            return allPaths;
        }

        visited.add(src);

        for (VertexAndWeight<E> neighbour: graph.getAdjList().get(src)) {
            E n = neighbour.getVertex();
            if (!visited.contains(n)) {
                List<List<E>> neighbourPaths =  allPathsDFSRecDP(graph, n, dst, visited, memo);
                for (List<E> neighbourPath: neighbourPaths) {
                    List<E> pathWithCurrent = new ArrayList<>(neighbourPath);
                    pathWithCurrent.add(src);
                    allPaths.add(pathWithCurrent);
                }
            }
        }
        visited.remove(src);
        memo.put(memoKey, allPaths);

        return allPaths;
    }

    /**
     * no. of connected components in our UNDIRECTED graph
     */
    public int connectedComponents(GraphALR<E> graph, String approach) {

        int res = 0;

        if (Objects.equals(approach, dfsRecursive)) {
            HashSet<E> visited = new HashSet<>();

            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry : graph.getAdjList().entrySet()) {
                E vertex = entry.getKey();
                if (!visited.contains(vertex)) {
                    depthFirstRecursive(graph, vertex, visited);
                    res++;
                }
            }
        } else if (Objects.equals(approach, dfs)) {
            HashSet<E> visited = new HashSet<>();

            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry : graph.getAdjList().entrySet()) {
                E vertex = entry.getKey();
                if (!visited.contains(vertex)) {
                    depthFirst(graph, vertex, visited, false);
                    res++;
                }
            }
        } else if (Objects.equals(approach, bfs)) {
            HashSet<E> visited = new HashSet<>();

            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry : graph.getAdjList().entrySet()) {
                E vertex = entry.getKey();
                if (!visited.contains(vertex)) {
                    breadthFirst(graph, vertex, visited);
                    res++;
                }
            }
        }
        return res;
    }

    /**
     * size of the largest connected component in our UNDIRECTED graph
     */
    public int largestComponent(GraphALR<E> graph, String approach) {

        int res = 0;

        if (Objects.equals(approach, dfsRecursive)) {
            HashSet<E> visited = new HashSet<>();
            int maxNodeCount = 0;

            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry : graph.getAdjList().entrySet()) {
                E vertex = entry.getKey();
                int nodeCount = 0;
                if (!visited.contains(vertex)) {
                    nodeCount = depthFirstRecursive(graph, vertex, visited);
                }
                maxNodeCount = Math.max(nodeCount, maxNodeCount);
            }
            res = maxNodeCount;
        } else if (Objects.equals(approach, dfs)) {
            HashSet<E> visited = new HashSet<>();
            int maxNodeCount = 0;

            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry : graph.getAdjList().entrySet()) {
                E vertex = entry.getKey();
                int nodeCount = 0;
                if (!visited.contains(vertex)) {
                    nodeCount = depthFirst(graph, vertex, visited, false);
                }
                maxNodeCount = Math.max(nodeCount, maxNodeCount);
            }
            res = maxNodeCount;
        } else if (Objects.equals(approach, bfs)) {
            HashSet<E> visited = new HashSet<>();
            int maxNodeCount = 0;

            for (Map.Entry<E, ArrayList<VertexAndWeight<E>>> entry : graph.getAdjList().entrySet()) {
                E vertex = entry.getKey();
                int nodeCount = 0;
                if (!visited.contains(vertex)) {
                    nodeCount = breadthFirst(graph, vertex, visited);
                }
                maxNodeCount = Math.max(nodeCount, maxNodeCount);
            }
            res = maxNodeCount;
        }
        return res;
    }

}
