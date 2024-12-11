package edu.ntnu.iir.bidata;

/**
 * The main entry point of the Waste_Less application.
 * This class is responsible for creating an instance of the UserInterface
 * and starting the application.
 */
public class Main {

  /**
   * The main method that launches the application.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    // Create a FoodStorage instance
    FoodStorage storage = new FoodStorage();

    // Add shutdown hook to save groceries on exit
    // FoodStorage.addShutdownHook();

    // Create a UserInterface instance to manage interactions
    UserInterface ui = new UserInterface(storage);

    // Start the application by calling the start method
    ui.start();
  }
}
