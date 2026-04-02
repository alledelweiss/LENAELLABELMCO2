import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MainMenu class handles the user interface and menu navigation for the Social Graph program.
 * It provides options to get friend list, find connections, or exit the program.
 */
public class mainMenu {
    private Scanner scanner;
    
    /**
     * Constructor for mainMenu
     */
    public mainMenu() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Displays the main menu and processes user choices
     * The menu will be displayed repeatedly until the user chooses to exit
     */
    public void displayMenu() {
        boolean running = true;
        
        while (running) {
            System.out.println("\nMAIN MENU");
            System.out.println("[1] Get friend list");
            System.out.println("[2] Get connection");
            System.out.println("[3] Exit");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        handleFriendList();
                        break;
                    case 2:
                        handleConnection();
                        break;
                    case 3:
                        System.out.println("Exiting program...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Handles the friend list feature
     * Prompts user for ID and displays the person's friend list and total number of friends
     */
    private void handleFriendList() {
        System.out.print("Enter ID of person: ");
        
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            
            // Check if the ID exists in the dataset
            if (!hasVertex(id)) {
                System.out.println("Error: Person with ID " + id + " does not exist in the dataset.");
                return;
            }
            
            // Get and display the friend list
            List<Integer> friends = getFriends(id);
            
            if (friends == null || friends.isEmpty()) {
                System.out.println("Person " + id + " has no friends.");
            } else {
                System.out.println("Person " + id + " has " + friends.size() + " friends!");
                System.out.print("List of friends: ");
                
                // Display the friends list
                for (int i = 0; i < friends.size(); i++) {
                    System.out.print(friends.get(i));
                    if (i < friends.size() - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format. Please enter a valid integer.");
        }
    }
    
    /**
     * Checks if a vertex exists in the graph
     * @param vertex The vertex ID to check
     * @return true if the vertex exists, false otherwise
     */
    private boolean hasVertex(int vertex) {
        if (Graph.graph == null) {
            return false;
        }
        return vertex >= 0 && vertex < Graph.n;
    }
    
    /**
     * Gets the list of friends for a given person
     * @param personId The ID of the person
     * @return List of friend IDs, or null if person doesn't exist
     */
    private List<Integer> getFriends(int personId) {
        if (!hasVertex(personId)) {
            return null;
        }
        
        ArrayList<Integer> friends = Graph.graph[personId];
        // Sort the list for consistent output
        java.util.Collections.sort(friends);
        return friends;
    }
    
    /**
     * Finds a connection (path) between two people using Breadth-First Search (BFS)
     * @param start The starting person ID
     * @param end The target person ID
     * @return List representing the path from start to end, or null if no path exists
     */
    private List<Integer> findConnection(int start, int end) {
        if (!hasVertex(start) || !hasVertex(end)) {
            return null;
        }
        
        if (start == end) {
            List<Integer> path = new ArrayList<>();
            path.add(start);
            return path;
        }
        
        // BFS setup
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        int[] parent = new int[Graph.n];
        boolean[] visited = new boolean[Graph.n];
        
        // Initialize parent array with -1
        for (int i = 0; i < Graph.n; i++) {
            parent[i] = -1;
        }
        
        queue.offer(start);
        visited[start] = true;
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            // Check all neighbors of the current vertex
            for (int neighbor : Graph.graph[current]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                    
                    if (neighbor == end) {
                        // Found the target, reconstruct the path
                        return reconstructPath(start, end, parent);
                    }
                    
                    queue.offer(neighbor);
                }
            }
        }
        
        // No path found
        return null;
    }
    
    /**
     * Reconstructs the path from start to end using the parent array from BFS
     * @param start The starting vertex
     * @param end The ending vertex
     * @param parent Array containing parent information for each visited vertex
     * @return List representing the path from start to end
     */
    private List<Integer> reconstructPath(int start, int end, int[] parent) {
        List<Integer> path = new ArrayList<>();
        int current = end;
        
        while (current != start) {
            path.add(current);
            current = parent[current];
        }
        path.add(start);
        
        // Reverse to get path from start to end
        java.util.Collections.reverse(path);
        return path;
    }
    
    /**
     * Handles the connection feature
     */
    private void handleConnection() {
        try {
            System.out.print("Enter ID of first person: ");
            int id1 = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Enter ID of second person: ");
            int id2 = Integer.parseInt(scanner.nextLine().trim());
            
            // Check if IDs exist in the dataset
            if (!hasVertex(id1)) {
                System.out.println("Error: Person with ID " + id1 + " does not exist in the dataset.");
                return;
            }
            
            if (!hasVertex(id2)) {
                System.out.println("Error: Person with ID " + id2 + " does not exist in the dataset.");
                return;
            }
            
            // Find and display the connection
            List<Integer> path = findConnection(id1, id2);
            
            if (path == null || path.isEmpty()) {
                System.out.println("Cannot find a connection between " + id1 + " and " + id2);
            } else {
                System.out.println("There is a connection from " + id1 + " to " + id2 + "!");
                // Display the path
                for (int i = 0; i < path.size() - 1; i++) {
                    System.out.println(path.get(i) + " is friends with " + path.get(i + 1));
                }
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format. Please enter valid integers.");
        }
    }
}
