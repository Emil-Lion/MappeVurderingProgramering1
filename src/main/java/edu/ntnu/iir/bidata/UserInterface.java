package edu.ntnu.iir.bidata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

/**
 * This class represents the user interface for the Waste_Less app.
 * It handles user interactions and manages the list of groceries.
 * Provides a text-based menu for adding, removing, and viewing groceries.
 */
public class UserInterface {
  private Scanner scanner;
  private final FoodStorage storage;
  private final RecipeBook recipeBook;
  private final ShoppingList shoppingList;
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

  /**
   * Constructs a new UserInterface with the given FoodStorage.
   *
   * @param storage the FoodStorage object to manage groceries
   */
  public UserInterface(FoodStorage storage) {
    this.storage = storage;
    this.recipeBook = new RecipeBook();
    this.shoppingList = new ShoppingList();
    this.scanner = new Scanner(System.in);
  }

  /**
   * Starts the main loop of the user interface and displays the main menu.
   * From the main menu allowing the user to choose actions.
   */
  public void start() {
    System.out.println("");
    System.out.println("*****************************************************************");
    System.out.println("*                    Welcome to Wasteless                       *");
    System.out.println("* Your partner in saving Food, Money and making Planet greener! *");
    System.out.println("*****************************************************************");
    while (true) {
      System.out.println(
          "\nFood Storage:\n"
              +
          "1. Add a new grocery\n"
              +
          "2. Search for a grocery\n"
              +
          "3. Remove some amount\n"
              +
          "4. Show all groceries sorted alphabetically\n"
              +
          "5. Show expires soon\n"
              +
          "6. Show expired groceries and wasted value\n"
              +
          "7. Show total storage value\n"
              +
          "8. Clear all groceries\n"
              +
          "9. Add a new recipe\n"
              +
          "10. Show all recipes\n"
              +
          "11. Generate shopping list for recipe\n"
              +
          "12. Suggest recipes based on storage\n"
              +
          "13. Show shopping list\n"
              +
          "14. About the app\n"
              +
          "0. Save & Exit\n"
              +
          "Choose an option: "
      );
      try {
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
          case 1 -> addGrocery();
          case 2 -> searchGrocery();
          case 3 -> removeGrocery();
          case 4 -> showGroceriesSortedAlphabetically();
          case 5 -> showExpiresSoon();
          case 6 -> showExpiredGroceriesAndWastedValue();
          case 7 -> showTotalValue();
          case 8 -> clearAllGroceries();
          case 9 -> addRecipe();
          case 10 -> showAllRecipes();
          case 11 -> generateShoppingList();
          case 12 -> suggestRecipes();
          case 13 -> showShoppingList();
          case 14 -> aboutApp();
          case 0 ->
            {
            saveFridgeData();
            System.out.println(
                "Take care of your self, and have a wasteless day, and eat well!");
            return;
            }
          default -> System.out.println("Invalid option. Please try again.");
        }
      } catch (NumberFormatException e) {
        System.out.println(
                "Invalid input. Please enter a number corresponding to the menu options.");
      }
    }
  }

  /**
   * Adds a new grocery to the storage.
   */
  private void addGrocery() {
    System.out.print("Enter name (or type 'Return' to Cancel): ");
    String name = scanner.nextLine();
    if (name.isBlank()) {
      System.out.println("Operation cancelled.");
      return;
    }

    // Capitalize the first letter of the name
    name = Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();

    System.out.print("Enter amount: ");
    String amountInput = scanner.nextLine();
    double amount;
    try {
      amount = Double.parseDouble(amountInput.replace(",", "."));
      if (amount <= 0) {
        System.out.println("Amount must be greater than 0. Operation cancelled.");
        return;
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid amount format. Operation cancelled.");
      return;
    }

    System.out.print("Enter unit (liters, kg, grams, pieces): ");
    String unit = scanner.nextLine();
    if (unit.isBlank()) {
      System.out.println("Unit cannot be empty. Operation cancelled.");
      return;
    }

    System.out.print("Enter price per unit: ");
    String priceInput = scanner.nextLine();
    double pricePerUnit;
    try {
      pricePerUnit = Double.parseDouble(priceInput.replace(",", "."));
      if (pricePerUnit <= 0) {
        System.out.println("Price per unit must be greater than 0. Operation cancelled.");
        return;
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid price format. Operation cancelled.");
      return;
    }

    System.out.print("Enter best before date (dd.MM.yyyy): ");
    String dateInput = scanner.nextLine();
    Date bestBeforeDate;
    try {
      bestBeforeDate = dateFormat.parse(dateInput);
      Calendar todayCal = Calendar.getInstance();
      todayCal.set(Calendar.HOUR_OF_DAY, 0);
      todayCal.set(Calendar.MINUTE, 0);
      todayCal.set(Calendar.SECOND, 0);
      todayCal.set(Calendar.MILLISECOND, 0);
      Date today = todayCal.getTime();

      if (bestBeforeDate.before(today)) {
        System.out.println("Best before date cannot be in the past. Operation cancelled.");
        return;
      }
    } catch (ParseException e) {
      System.out.println("Invalid date format. Operation cancelled.");
      return;
    }

    try {
      Grocery grocery = new Grocery(name, amount, unit, bestBeforeDate, pricePerUnit);
      storage.addGrocery(grocery);
      storage.saveToFile(); // Save the data after adding a new grocery
      System.out.println("Grocery added successfully.");
    } catch (IllegalArgumentException e) {
      System.out.println("Error adding grocery: " + e.getMessage());
    }
  }

  /**
   * Searches for a grocery by name.
   */
  private void searchGrocery() {
    System.out.print("Enter grocery name to search: ");
    String name = scanner.nextLine();
    Grocery grocery = storage.searchGrocery(name);
    if (grocery != null) {
      System.out.println("Grocery found: " + grocery);
    } else {
      System.out.println("Grocery not found.");
    }
  }

  /**
   * Removes a specified amount from a grocery.
   */
  private void removeGrocery() {
    System.out.print("Enter grocery name to remove amount from: ");
    String name = scanner.nextLine();
    System.out.print("Enter amount to remove: ");
    double amount = Double.parseDouble(scanner.nextLine());
    if (storage.removeGrocery(name, amount)) {
      System.out.println("Amount removed successfully.");
    } else {
      System.out.println("Grocery not found or insufficient amount.");
    }
  }

  /**
   * Shows all groceries sorted alphabetically.
   */
  private void showGroceriesSortedAlphabetically() {
    storage.getGroceries().stream()
             .sorted(Comparator.comparing(Grocery::getName))
             .forEach(System.out::println);
  }

  /**
   * Shows groceries that are expiring soon.
   */
  private void showExpiresSoon() {
    Calendar todayCal = Calendar.getInstance();
    todayCal.set(Calendar.HOUR_OF_DAY, 0);
    todayCal.set(Calendar.MINUTE, 0);
    todayCal.set(Calendar.SECOND, 0);
    todayCal.set(Calendar.MILLISECOND, 0);
    Date today = todayCal.getTime();

    storage.getGroceries().stream()
             .filter(grocery -> grocery.getBestBeforeDate().after(today))
             .sorted(Comparator.comparing(Grocery::getBestBeforeDate))
             .forEach(System.out::println);
  }

  /**
   * Shows expired groceries and their wasted value.
   */
  private void showExpiredGroceriesAndWastedValue() {
    Calendar todayCal = Calendar.getInstance();
    todayCal.set(Calendar.HOUR_OF_DAY, 0);
    todayCal.set(Calendar.MINUTE, 0);
    todayCal.set(Calendar.SECOND, 0);
    todayCal.set(Calendar.MILLISECOND, 0);
    Date today = todayCal.getTime();

    storage.getGroceries().stream()
                .filter(grocery -> grocery.getBestBeforeDate().before(today))
                .sorted(Comparator.comparing(Grocery::getBestBeforeDate).reversed())
                .forEach(System.out::println);
  }

  /**
   * Shows the total value of all groceries in storage.
   */
  private void showTotalValue() {
    double totalValue = storage.getGroceries().stream()
            .mapToDouble(Grocery::calculateTotalValue)
            .sum();
    System.out.println("Total storage value: " + totalValue + " NOK");
  }

  /**
   * Clears all groceries from the storage.
   */
  private void clearAllGroceries() {
    System.out.println("WARNING: You are about to delete all groceries from the food storage.");
    System.out.println("This action cannot be undone.");
    System.out.print("Type \"YES\" to confirm, or any other key to cancel: ");
    String confirmation = scanner.nextLine();
    if (confirmation.equalsIgnoreCase("YES")) {
      storage.clearAllGroceries();
      System.out.println("All groceries have been cleared.");
    } else {
      System.out.println("Operation cancelled.");
    }
  }

  /**
   * Saves the current state of the fridge data to a file.
   */
  private void saveFridgeData() {
    storage.saveToFile();
    System.out.println("Data saved successfully.");
  }

  /**
   * Adds a new recipe to the recipe book.
   */
  private void addRecipe() {
    System.out.print("Enter recipe name: ");
    String name = scanner.nextLine();
    Recipe recipe = new Recipe(name);
    while (true) {
      System.out.print("Enter ingredient name (or type 'done' to finish): ");
      String ingredientName = scanner.nextLine();
      if (ingredientName.equalsIgnoreCase("done")) {
        break;
      }
      System.out.print("Enter amount: ");
      double amount = Double.parseDouble(scanner.nextLine());
      System.out.print("Enter unit: ");
      String unit = scanner.nextLine();
      recipe.addIngredient(new Ingredient(ingredientName, amount, unit));
    }
    System.out.print("Enter description and recipe: ");
    String description = scanner.nextLine();
    recipe.setDescription(description);
    recipeBook.addRecipe(recipe);
    System.out.println("Recipe added successfully.");
  }

  /**
   * Shows all stored recipes in alphabetical order.
   */
  private void showAllRecipes() {
    System.out.println("Stored recipes:");
    recipeBook.getRecipes().stream()
            .sorted(Comparator.comparing(Recipe::getName))
            .forEach(System.out::println);
  }

  /**
     * Generates a shopping list for a recipe based on missing ingredients in the storage.
     */
  private void generateShoppingList() {
    System.out.print("Enter recipe name to generate shopping list: ");
    String name = scanner.nextLine();
    Recipe recipe = recipeBook.getRecipes().stream()
            .filter(r -> r.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);

    if (recipe == null) {
      System.out.println("Recipe not found.");
      return;
    }

    shoppingList.generateShoppingList(recipe, storage);
    System.out.println("Shopping list generated:");
    shoppingList.getItems().forEach(System.out::println);
  }

  /**
   * Suggests recipes based on the available ingredients in the storage.
   * Adds missing groceries to the shopping list for partially available recipes.
   */
  private void suggestRecipes() {
    System.out.println("Recipes that can be made with available ingredients:");
    recipeBook.suggestRecipes(storage).forEach(System.out::println);
    System.out.println("Recipes that can be made partially with available ingredients:");
    recipeBook.suggestPartialRecipes(storage).forEach(recipe -> {
      System.out.println(recipe);
      recipe.getIngredients().forEach(ingredient -> {
        Grocery grocery = storage.searchGrocery(ingredient.getName());
        if (grocery == null || grocery.getAmount() < ingredient.getAmount()) {
          double neededAmount = ingredient.getAmount() - (grocery != null
              ? grocery.getAmount() : 0);
          shoppingList.addItem(new Ingredient(ingredient.getName(), neededAmount,
                    ingredient.getUnit()));
        }
      });
    });
  }

  /**
   * Shows the shopping list with groceries sorted alphabetically.
   */
  private void showShoppingList() {
    System.out.println("Shopping list:");
    shoppingList.getItems().stream()
            .sorted(Comparator.comparing(Ingredient::getName))
            .forEach(System.out::println);
  }

  /**
   * Displays information about the app and instructions on how to use it.
   */
  private void aboutApp() {
    System.out.println("About the Waste_Less App:");
    System.out.println("This app helps you manage your food storage, recipes, "
        +
        "and shopping lists to reduce food waste.");
    System.out.println("Instructions:");
    System.out.println("1. Add a new grocery: Allows you to add a new grocery item"
        +
        " to your storage.");
    System.out.println("2. Search for a grocery: Search for a specific grocery item"
        +
        " in your storage.");
    System.out.println("3. Remove some amount: Remove a specified amount from a grocery item.");
    System.out.println("4. Show all groceries sorted alphabetically: Display all "
        +
        "groceries in alphabetical order.");
    System.out.println("5. Show expires soon: Display groceries that are expiring soon.");
    System.out.println("6. Show expired groceries and wasted value: Display expired "
        +
        "groceries and their wasted value.");
    System.out.println("7. Show total storage value: Display the total value of all "
        +
        "groceries in storage.");
    System.out.println("8. Clear all groceries: You get WARNING: that You are about to "
        +
        "delete all groceries from the food storage, by confirming YES you Remove all "
        +
        "groceries from storage.");
    System.out.println("9. Add a new recipe: Add a new recipe to the recipe book.");
    System.out.println("10. Show all recipes: Display all stored recipes in alphabetical order.");
    System.out.println("11. Generate shopping list for recipe: Generate a shopping list "
        +
        "for a recipe based on missing ingredients.");
    System.out.println("12. Suggest recipes based on storage: Suggest recipes that can be "
        +
        "made with available ingredients.");
    System.out.println("13. Show shopping list: Display the shopping list with groceries "
        +
        "sorted alphabetically.");
    System.out.println("14. About the app: Display information about the app and instructions "
        +
        "on how to use it.");
    System.out.println("0. Save & Exit: Save the current state and exit the app.");
  }
}