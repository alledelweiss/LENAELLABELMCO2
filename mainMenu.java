import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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
            System.out.println("[3] Exit\n");
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

            System.out.println();
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
     * Finds and displays the shortest connection path between two users using BFS traversal.
     */
    private void handleConnection() {
        try {
            System.out.print("Enter ID of first person: ");
            int start = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter ID of second person: ");
            int end = Integer.parseInt(scanner.nextLine().trim());

            if(!hasVertex(start) || !hasVertex(end)) {
                System.out.println("Error: One or both IDs do not exist in the dataset.");
                return;
            }

            //BFS setup
            boolean[] visited = new boolean[Graph.n];
            int[] parent = new int[Graph.n]; //to reconstruct path
            for(int i = 0; i < Graph.n; i++){
                parent[i] = -1; //initialize parent
            }

            Queue<Integer> queue = new java.util.LinkedList<>();
            visited[start] = true;
            queue.add(start);

            boolean found = false;

            //BFS Traversal
            while(!queue.isEmpty() && ! found){
                int current = queue.poll();
                ArrayList<Integer> neighbors = Graph.graph[current];
                for(int i = 0; i < neighbors.size(); i++){
                    int neighbor = neighbors.get(i);

                    if(!visited[neighbor]) {
                        visited[neighbor] = true;
                        parent[neighbor] = current;
                        queue.add(neighbor);

                        if(neighbor == end){
                            found = true;
                        }
                    }
                }
            }

            // if connection is found, reconstruct path
            if(found){
                System.out.println();
                System.out.println("There is a connection from " + start + " to " + end + "!");
                ArrayList<Integer> path = new ArrayList<>();
                int current = end;
                while (current != -1){
                    path.add(0, current); //add to the front of the path
                    current = parent[current];
                }

                // Print connection steps
                for(int i = 0; i < path.size() - 1; i++){
                    System.out.println(path.get(i) + " is friends with " + path.get(i + 1));
                }
            } else {
                System.out.println("Cannot find a connection between " + start + " and " + end + ".");
            }
        } catch (NumberFormatException e){
            System.out.println("Error: Invalid ID format. Please enter valid integers.");
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
}
