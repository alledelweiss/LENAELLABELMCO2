/**
 * Main class for the Social Network Graph project.
<<<<<<< HEAD
 * This program loads a Facebook social network dataset and provides
 * functionalities to view friend lists and find connections between people.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Social Graph Analysis Program ===");
        System.out.println("This program analyzes Facebook social network data.");
        System.out.println();
        
        // initialize the graph (loads file and builds the graph)
        Graph.initializeGraph();
        
        // check if graph was loaded successfully
        if (Graph.graph == null) {
            System.out.println("Error: Failed to load graph. Program terminating.");
            return;
        }
        
        System.out.println("Number of accounts: " + Graph.n);
        System.out.println("Graph ready for queries!");
        
        // create and display the main menu
        mainMenu menu = new mainMenu();
        menu.displayMenu();
        
        System.out.println("Program terminated.");
    }
}
