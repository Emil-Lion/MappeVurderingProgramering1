package edu.ntnu.iir.bidata;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages grocery storage, including adding, removing, clearing,
 * and saving groceries for data persistence.
 */
public class FoodStorage {
  private List<Grocery> groceries = new ArrayList<>();
  private static final String FILE_PATH = "groceries.dat";

  /**
   * Constructs a new FoodStorage object and loads existing groceries from file if available.
   */
  public FoodStorage() {
    loadFromFile();
  }

  /**
   * Adds a grocery to the storage.
   * If a grocery with the same name already exists, it updates the amount.
   *
   * @param grocery the Grocery object to add.
   */
  public void addGrocery(Grocery grocery) {
    for (Grocery g : groceries) {
      if (g.getName().equalsIgnoreCase(grocery.getName())) {
        g.setAmount(g.getAmount() + grocery.getAmount());
        return;
      }
    }
    groceries.add(grocery);
  }

  /**
   * Searches for a grocery by name.
   *
   * @param name the name of the grocery to search for.
   * @return the Grocery object if found, null otherwise.
   */
  public Grocery searchGrocery(String name) {
    return groceries.stream()
            .filter(g -> g.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
  }

  /**
   * Removes a specified amount from a grocery.
   * If the grocery amount becomes zero or less, it is removed from the list.
   *
   * @param name the name of the grocery to update.
   * @param amount the amount to remove.
   * @return true if the grocery was found and updated, false otherwise.
   */
  public boolean removeGrocery(String name, double amount) {
    Grocery grocery = searchGrocery(name);
    if (grocery != null) {
      double newAmount = grocery.getAmount() - amount;
      if (newAmount <= 0) {
        groceries.remove(grocery);
      } else {
        grocery.setAmount(newAmount);
      }
      return true;
    }
    return false;
  }

  /**
   * Retrieves the list of all groceries in storage.
   *
   * @return List of Grocery objects.
   */
  public List<Grocery> getGroceries() {
    return groceries;
  }

  /**
   * Clears all groceries from the storage.
   */
  public void clearAllGroceries() {
    groceries.clear();
  }

  /**
   * Saves the list of groceries to a file to ensure data is retained across sessions.
   */
  public void saveToFile() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
      oos.writeObject(groceries);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads the list of groceries from a file if it exists.
   */
  private void loadFromFile() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
      groceries = (List<Grocery>) ois.readObject();
    } catch (FileNotFoundException e) {
      System.out.println("No saved groceries found. Starting with an empty list.");
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if the necessary ingredients for a recipe are available in the storage.
   *
   * @param recipe the Recipe object to check.
   * @return true if all ingredients are available, false otherwise.
   */
  public boolean hasIngredients(Recipe recipe) {
    for (Ingredient ingredient : recipe.getIngredients()) {
      Grocery grocery = searchGrocery(ingredient.getName());
      if (grocery == null || grocery.getAmount() < ingredient.getAmount()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Converts units to handle different measurement units.
   *
   * @param amount the amount to convert.
   * @param fromUnit the unit to convert from.
   * @param toUnit the unit to convert to.
   * @return the converted amount.
   */
  public double convertUnits(double amount, String fromUnit, String toUnit) {
    // Implement unit conversion logic here
    return amount; // Placeholder
  }

  /**
   * Deducts the amount of an ingredient after use in a recipe.
   *
   * @param ingredient the Ingredient object to deduct.
   */
  public void useIngredient(Ingredient ingredient) {
    Grocery grocery = searchGrocery(ingredient.getName());
    if (grocery != null) {
      double newAmount = grocery.getAmount() - ingredient.getAmount();
      if (newAmount <= 0) {
        groceries.remove(grocery);
      } else {
        grocery.setAmount(newAmount);
      }
    }
  }
}