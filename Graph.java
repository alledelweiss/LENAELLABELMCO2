/*
 * Graph class reads a social network data file and stores it as a bidirectional graph using an adjacency list. 
 *
 * The input file format:
 *  (1) First line: two integers n and e (number of accounts and number of friendships)
 *  (2) Following e lines: pairs of integers a b, representing that person a is friends with person b
 *
 * After reading the file, the graph is stored as an ArrayList of integers for each person.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;;

public class Graph {
    public static ArrayList<Integer>[] graph; // stores the graph in an adjacenty list
    public static int n; // number of people in the network

    // READ FILE (user inputs filename)
    public static int[][] readFile() {
        int[][] edges = null;

        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Input file path: ");
            String fileName = input.nextLine();

            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);

            n = fileReader.nextInt();
            int e = fileReader.nextInt(); // number of friendships

            edges = new int[e][2];

            // reading each pair of friends
            for (int i = 0; i < e; i++) {
                edges[i][0] = fileReader.nextInt(); // person1
                edges[i][1] = fileReader.nextInt(); // person2
            }

            fileReader.close();

        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            e.printStackTrace();
        }

        return edges;
    }

    // BUILD GRAPH
    public static void loadGraph(int[][] edges){
        graph = new ArrayList[n];

        // initialization
        for (int i = 0; i < n; i++){
            graph[i] = new ArrayList<>();
        }

        int numOfEdges = edges.length;

        for (int i = 0; i < numOfEdges; i++) {
            int nodeA = edges[i][0];
            int nodeB = edges[i][1];

            // add edge if not already in the list
            if (!graph[nodeA].contains(nodeB)) {
                graph[nodeA].add(nodeB);
            }
            if (!graph[nodeB].contains(nodeA)) {
                graph[nodeB].add(nodeA); 
            }
        }
    }

    // CALL BOTH FUNCTIONS
    public static void initializeGraph() {
        int[][] edges = readFile(); 

        if (edges != null) {
            loadGraph(edges);  
            System.out.println("Graph loaded!");     

            /***********************************************
            prints out the adjaceny list for double checking
            ************************************************

            for (int i = 0; i < n; i++) {
                System.out.print(i + ": ");
                for (int friend : graph[i]) {
                    System.out.print(friend + " ");
                }
                System.out.println();
            }
            */
        }
    }
}
