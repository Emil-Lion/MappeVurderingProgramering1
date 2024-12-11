package edu.ntnu.iir.bidata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of recipes, including adding new recipes
 * and suggesting recipes based on available ingredients.
 */
public class RecipeBook {
  private List<Recipe> recipes = new ArrayList<>();
  private static final String FILE_PATH = "recipes.dat";

  /**
   * Constructs a new RecipeBook object and loads existing recipes from file if available.
   */
  public RecipeBook() {
    loadFromFile();
  }

  /**
   * Adds a new recipe to the recipe book and saves the updated list to file.

   * @param recipe the Recipe object to add.
   */
  public void addRecipe(Recipe recipe) {
    recipes.add(recipe);
    saveToFile();
  }

  /**
   * Returns a list of recipes that can be made with the available ingredients in the storage.

   * @param storage the FoodStorage object to check against.

   * @return a list of recipes that can be made.
   */
  public List<Recipe> suggestRecipes(FoodStorage storage) {
    List<Recipe> suggestedRecipes = new ArrayList<>();
    for (Recipe recipe : recipes) {
      if (storage.hasIngredients(recipe)) {
        suggestedRecipes.add(recipe);
      }
    }
    return suggestedRecipes;
  }

  /**
   * Returns a list of recipes that can be made partially
   * with the available ingredients in the storage.

   * @param storage the FoodStorage object to check against.

   * @return a list of recipes that can be made partially.
   */
  public List<Recipe> suggestPartialRecipes(FoodStorage storage) {
    List<Recipe> partialRecipes = new ArrayList<>();
    for (Recipe recipe : recipes) {
      boolean canMakePartially = false;
      for (Ingredient ingredient : recipe.getIngredients()) {
        if (storage.searchGrocery(ingredient.getName()) != null) {
          canMakePartially = true;
          break;
        }
      }
      if (canMakePartially) {
        partialRecipes.add(recipe);
      }
    }
    return partialRecipes;
  }

  /**
   * Returns the list of all recipes in the recipe book.

   * @return List of Recipe objects.
   */
  public List<Recipe> getRecipes() {
    return recipes;
  }

  /**
   * Saves the list of recipes to a file to ensure data is retained across sessions.
   */
  private void saveToFile() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
      oos.writeObject(recipes);
      System.out.println("Recipes have been saved successfully.");
    } catch (IOException e) {
      System.out.println("Couldn't save recipes: " + e.getMessage());
    }
  }

  /**
   * Loads the list of recipes from a file if it exists.
   */
  private void loadFromFile() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
      recipes = (List<Recipe>) ois.readObject();
      System.out.println("Recipes loaded successfully.");
    } catch (FileNotFoundException e) {
      System.out.println("No saved recipes found. Starting with an empty list.");
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Error loading recipes: " + e.getMessage());
    }
  }
}