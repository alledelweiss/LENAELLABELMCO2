import java.util.Scanner;

/**
 * MainMenu class handles the user interface and menu navigation for the Social Graph program.
 * It provides options to get friend list, find connections, or exit the program.
 */
public class mainMenu {
    private Graph graph; // to be renamed yes
    private Scanner scanner;
    
    /**
     * Constructor for mainMenu
     * @param graph The loaded graph data structure
     */
    public mainMenu (Graph graph) {
        this.graph = graph;
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
            if (!graph.hasVertex(id)) {
                System.out.println("Error: Person with ID " + id + " does not exist in the dataset.");
                return;
            }
            
            // Get and display the friend list
            java.util.List<Integer> friends = graph.getFriends(id);
            
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
     * Handles the connection feature
     * This is a placeholder method for the connection finder feature
     */
    private void handleConnection() {
        try {
            System.out.print("Enter ID of first person: ");
            int id1 = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Enter ID of second person: ");
            int id2 = Integer.parseInt(scanner.nextLine().trim());
            
            // Check if IDs exist in the dataset
            if (!graph.hasVertex(id1)) {
                System.out.println("Error: Person with ID " + id1 + " does not exist in the dataset.");
                return;
            }
            
            if (!graph.hasVertex(id2)) {
                System.out.println("Error: Person with ID " + id2 + " does not exist in the dataset.");
                return;
            }
            
            // Find and display the connection
            java.util.List<Integer> path = graph.findConnection(id1, id2);
            
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

// to be edited when source code is omki