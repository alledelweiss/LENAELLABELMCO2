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
                       // handleConnection(); to be implemented
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
}
