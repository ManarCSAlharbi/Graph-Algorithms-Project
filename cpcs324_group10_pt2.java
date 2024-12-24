/*
                CPCS324 - CPCS 324: Algorithms and Data Structures II
 ______________________________________________________________________________________________
Brief Description:
This program implements three fundamental algorithms in graph theory:

1. Prim's Algorithm - Finds the Minimum Spanning Tree (MST) of a weighted undirected graph using an unordered
   minimum-priority queue.
2. Kruskal's Algorithm - Finds the MST of a weighted undirected graph using the Union-Find data structure.
3. Dijkstra's Algorithm - Computes the shortest paths from a single source vertex to all other vertices in
   a weighted directed graph using a priority queue.

User can select which algorithm to execute through a menu. 
The program reads graph data from input files and displays the results along with the execution time of each algorithm.

 */
package cpcs324_group10_pt2;

import java.io.*;
import java.util.*;

public class cpcs324_group10_pt2 {

    static final int INF = Integer.MAX_VALUE;             // Represents infinite distance
    static int V;                                         // Number of vertices
    static double[][] graph;                                 // Adjacency matrix of the graph
    private static final String INPUT_FILE1 = "input1.txt";
    private static final String INPUT_FILE2 = "input2.txt";


    public static void main(String[] args) {
        displayMenu();
    }

    // Displays the main menu and handles user input
    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 4) {
            System.out.println("__________________________________________________________");
            System.out.println("__________________________________________________________");
            System.out.println("1. Finding minimum spanning tree using Prim’s algorithm");
            System.out.println("2. Finding minimum spanning tree using Kruskal’s algorithm");
            System.out.println("3. Finding shortest path using Dijkstra’s algorithm");
            System.out.println("4. Quit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    primMST();
                    break;
                case 2:
                    kruskalMST();
                    break;
                case 3:
                    dijkstraShortestPath();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice. Please select an option from 1 to 4.");
            }
            System.out.println();
        }
        scanner.close();
    }

    // Computes the Minimum Spanning Tree using Prim's Algorithm
    public static void primMST() {
        try {
            // Step 1: Reading the graph from input file
            Scanner fileScanner = new Scanner(new File(INPUT_FILE1));
            int vertices = Integer.parseInt(fileScanner.nextLine()); // Number of vertices
            int edges = Integer.parseInt(fileScanner.nextLine());    // Number of edges
            int[][] graph = new int[vertices][vertices];             // Adjacency matrix

            // Populating the adjacency matrix
            for (int i = 0; i < edges; i++) {
                String[] tokens = fileScanner.nextLine().split(" ");
                int src = Integer.parseInt(tokens[0]);
                int dest = Integer.parseInt(tokens[1]);
                int weight = Integer.parseInt(tokens[2]);
                graph[src][dest] = weight;
                graph[dest][src] = weight; // For undirected graph
            }
            fileScanner.close();

            // Step 2: Initialize data structures for Prim's algorithm
            boolean[] inMST = new boolean[vertices]; // Tracks vertices included in MST
            int[] key = new int[vertices];          // Holds minimum weights for vertices
            int[] parent = new int[vertices];       // Tracks MST structure

            // Initializing all keys to infinity and parent to -1
            Arrays.fill(key, Integer.MAX_VALUE);
            key[0] = 0; // Start with the first vertex
            parent[0] = -1;

            // Step 3: Measuring performance of the algorithm
            long startTime = System.nanoTime(); // Start timing

            // Step 4: Main loop to construct the MST
            for (int count = 0; count < vertices - 1; count++) {
                // Extract vertex with the minimum key value
                int u = extractMin(key, inMST);

                // Include this vertex in MST
                inMST[u] = true;

                // Update keys for adjacent vertices
                for (int v = 0; v < vertices; v++) {
                    if (graph[u][v] != 0 && !inMST[v] && graph[u][v] < key[v]) {
                        key[v] = graph[u][v];
                        parent[v] = u;
                    }
                }
            }

            // Step 5: Record performance and display the MST
            long endTime = System.nanoTime(); // End timing

            // Calculate total weight of MST
            double totalWeight = 0;
            System.out.println("The edges in the MST are:");
            for (int i = 1; i < vertices; i++) {
                System.out.println("Edge from " + parent[i] + " to " + i + " has weight " + graph[i][parent[i]]);
                totalWeight += graph[i][parent[i]];
            }
            System.out.println("Total weight of MST by Prim's algorithm: " + totalWeight);

            // Display the algorithm's execution time
            System.out.println("Running Time of Prim's algorithm using unordered Min-Priority Queue is:");
            System.out.println((endTime - startTime) + " Nano seconds.");

        } catch (FileNotFoundException e) {
            System.out.println("Error reading the input file for Prim's algorithm.");
            e.printStackTrace();
        }
    }

    // Extracts the vertex with the minimum key value not yet included in MST
    private static int extractMin(int[] key, boolean[] inMST) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int i = 0; i < key.length; i++) {
            if (!inMST[i] && key[i] < min) {
                min = key[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    // Computes the Minimum Spanning Tree using Kruskal's Algorithm
    public static void kruskalMST() {
        try {
            Scanner fileScanner = new Scanner(new File(INPUT_FILE1));
            int N = Integer.parseInt(fileScanner.nextLine());
            int E = Integer.parseInt(fileScanner.nextLine());
            Edge[] edges = new Edge[E];

            for (int i = 0; i < E; i++) {
                String[] tokens = fileScanner.nextLine().split(" ");
                int src = Integer.parseInt(tokens[0]);
                int dest = Integer.parseInt(tokens[1]);
                double weight = Double.parseDouble(tokens[2]);
                edges[i] = new Edge(src, dest, weight);
            }
            fileScanner.close();

            // Start timing
            long startTime = System.nanoTime();

            // Sort the edges by weight in increasing order with tie-breakers
            Arrays.sort(edges, new Comparator<Edge>() {
                @Override
                public int compare(Edge e1, Edge e2) {
                    int cmp = Double.compare(e1.weight, e2.weight);
                    if (cmp != 0) {
                        return cmp;
                    } else {
                        int srcCmp = Integer.compare(e1.src, e2.src);
                        if (srcCmp != 0) {
                            return srcCmp;
                        } else {
                            return Integer.compare(e1.dest, e2.dest);
                        }
                    }
                }
            });

            // Initialize Union-Find data structure
            int[] id = new int[N];
            for (int i = 0; i < N; i++) {
                id[i] = i;
            }

            // Initialize total weight and MST edges list
            double totalWeight = 0.0;
            List<Edge> mstEdges = new ArrayList<>();

            // Process the edges
            for (Edge edge : edges) {
                if (!findSet(id, edge.src, edge.dest)) {
                    union(id, edge.src, edge.dest);
                    mstEdges.add(edge);
                    totalWeight += edge.weight;
                    if (mstEdges.size() == N - 1) {
                        break;
                    }
                }
            }

            // End timing
            long endTime = System.nanoTime();
            long duration = endTime - startTime;

            // Output the result
            System.out.println("Total weight of MST by Kruskal's algorithm: " + totalWeight);
            System.out.println("The edges in the MST are:");
            for (Edge edge : mstEdges) {
                System.out.println("Edge from " + edge.src + " to " + edge.dest + " has weight " + edge.weight);
            }
            System.out.println("Running Time of Kruskal’s algorithm using Union-Find approach is");
            System.out.println(duration + " Nano seconds.");

        } catch (FileNotFoundException e) {
            System.out.println("Error reading the input file for Kruskal's algorithm.");
            e.printStackTrace();
        }
    }

    // Checks if two vertices are in the same set
    private static boolean findSet(int[] id, int p, int q) {
        return id[p] == id[q];
    }

    // Unites two sets containing two vertices
    private static void union(int[] id, int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }

    // Computes the shortest paths using Dijkstra's Algorithm

   
        // Method to read graph and perform Dijkstra's algorithm
        public static void dijkstraShortestPath() {
            try {
                // Step 1: Read the graph from the input file
                File file = new File(INPUT_FILE2);
                Scanner scanner = new Scanner(file);

                // Read the number of vertices
                V = scanner.nextInt();
                // Read the number of edges (not used but can be used for validation)
                int E = scanner.nextInt();

                // Initialize the graph matrix
                graph = new double[V][V];
                for (int i = 0; i < V; i++) {
                    for (int j = 0; j < V; j++) {
                        graph[i][j] = (i == j) ? 0 : 0; // 0 for self-loops, INF for others
                    }
                }

                // Read the edges and populate the graph matrix
                for (int i = 0; i < E; i++) {
                    int u = scanner.nextInt();
                    int v = scanner.nextInt();
                    double weight = scanner.nextDouble();
                    graph[u][v] = weight;
                }
                scanner.close();

                // Step 2: Print the weight matrix
                System.out.println("Weight Matrix:");
                for (int i = 0; i < V; i++) {
                    for (int j = 0; j < V; j++) {
                        if (graph[i][j] == INF) {
                            System.out.printf("%6s", "INF");
                        } else {
                            System.out.printf("%6.0f", graph[i][j]);
                        }
                    }
                    System.out.println();
                }

                // Step 3: Print the graph in the edge format
                System.out.println("\n# of vertices is: " + V + ", # of edges is: " + E);
                for (int i = 0; i < V; i++) {
                    System.out.print(i + ": ");
                    for (int j = 0; j < V; j++) {
                        if (graph[i][j] != INF && graph[i][j] != 0) {
                            System.out.print(i + "-" + j + " " + (int) graph[i][j] + "   ");
                        }
                    }
                    System.out.println();
                }

                // Step 4: Prompt the user for the source vertex
                Scanner userScanner = new Scanner(System.in);
                System.out.print("\nEnter Source vertex: ");
                int source = userScanner.nextInt();

                // Step 5: Initialize data structures
                double[] dist = new double[V]; // Distance array
                int[] prev = new int[V];       // Previous vertex array
                boolean[] visited = new boolean[V]; // Visited vertices array
                Arrays.fill(dist, Double.MAX_VALUE);
                Arrays.fill(prev, -1);
                dist[source] = 0;

                // Step 6: Perform Dijkstra's algorithm
                long startTime = System.nanoTime();
                for (int count = 0; count < V; count++) {
                    // Extract the vertex with the smallest distance that has not been visited
                    int u = extractMinUnordered(dist, visited);
                    if (u == -1) break; // No more reachable vertices
                    visited[u] = true;

                    // Update distances for adjacent vertices
                    for (int v = 0; v < V; v++) {
                        if (graph[u][v] != 0 && graph[u][v] != INF && !visited[v]) {
                            double alt = dist[u] + graph[u][v];
                            if (alt < dist[v]) {
                                dist[v] = alt;
                                prev[v] = u;
                            }
                        }
                    }
                }
                long endTime = System.nanoTime();

                // Step 7: Display the shortest paths and execution time
                System.out.println("\nDijkstra using unordered array:");
                System.out.println("Shortest paths from vertex " + source + ":");
                for (int i = 0; i < V; i++) {
                    if (dist[i] == Double.MAX_VALUE) {
                        System.out.println("A path from " + source + " to " + i + ": Unreachable");
                    } else {
                        String path = reconstructPath(prev, i);
                        System.out.println("A path from " + source + " to " + i + ": " + path + " (Length: " + dist[i] + ")");
                    }
                }

                System.out.println("\nRunning time of Dijkstra using unordered array is: " + (endTime - startTime) + " nano seconds");

            } catch (FileNotFoundException e) {
                System.err.println("Error reading the graph from input file: " + e.getMessage());
            }
        }

        // Extracts the vertex with the minimum distance from the array
        private static int extractMinUnordered(double[] dist, boolean[] visited) {
            int minIndex = -1;
            double minValue = Double.MAX_VALUE;

            for (int i = 0; i < dist.length; i++) {
                if (!visited[i] && dist[i] < minValue) {
                    minValue = dist[i];
                    minIndex = i;
                }
            }
            return minIndex;
        }

        // Reconstructs the path from the source to the given target vertex
        private static String reconstructPath(int[] prev, int target) {
            if (prev[target] == -1) {
                return String.valueOf(target);
            }
            return reconstructPath(prev, prev[target]) + " " + target;
        }


    }


    // Represents an edge in the graph
    class Edge {

        int src;
        int dest;
        double weight;

        Edge(int src, int dest, double weight) {
            // Ensure that src is less than dest for consistent ordering
            if (src > dest) {
                int temp = src;
                src = dest;
                dest = temp;
            }
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }}
    
    
    

