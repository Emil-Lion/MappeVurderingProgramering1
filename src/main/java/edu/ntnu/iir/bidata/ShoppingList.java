package edu.ntnu.iir.bidata;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a shopping list of ingredients needed for recipes.
 */
public class ShoppingList {
  private List<Ingredient> items = new ArrayList<>();

  /**
   * Adds an ingredient to the shopping list.

   * @param ingredient the Ingredient object to add.
   */
  public void addItem(Ingredient ingredient) {
    items.add(ingredient);
  }

  /**
   * Generates a shopping list for a recipe based on missing ingredients in the storage.

   * @param recipe the Recipe object to generate the list for.

   * @param storage the FoodStorage object to check against.
   */
  public void generateShoppingList(Recipe recipe, FoodStorage storage) {
    for (Ingredient ingredient : recipe.getIngredients()) {
      Grocery grocery = storage.searchGrocery(ingredient.getName());
      if (grocery == null || grocery.getAmount() < ingredient.getAmount()) {
        double neededAmount = ingredient.getAmount() - (grocery != null ? grocery.getAmount() : 0);
        items.add(new Ingredient(ingredient.getName(), neededAmount, ingredient.getUnit()));
      }
    }
  }

  /**
   * Returns the list of items in the shopping list.

   * @return List of Ingredient objects.
   */
  public List<Ingredient> getItems() {
    return items;
  }
}