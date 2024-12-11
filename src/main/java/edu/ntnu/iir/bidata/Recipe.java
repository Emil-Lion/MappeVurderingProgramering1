package edu.ntnu.iir.bidata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a recipe with a name, a list of ingredients, and a description.
 */
public class Recipe implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private List<Ingredient> ingredients = new ArrayList<>();
  private String description;

  /**
   * Constructs a new Recipe with the specified name.

   * @param name the name of the recipe.
   */
  public Recipe(String name) {

    this.name = name;
  }

  /**
   * Gets the name of the recipe.

   * @return the name of the recipe.
   */
  public String getName() {
    return name;
  }

  /**
   * Adds an ingredient to the recipe.

   * @param ingredient the Ingredient object to add.
   */
  public void addIngredient(Ingredient ingredient) {

    ingredients.add(ingredient);
  }

  /**
   * Gets the list of ingredients for the recipe.

   * @return List of Ingredient objects.
   */
  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  /**
   * Gets the description of the recipe.

   * @return the description of the recipe.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the recipe.

   * @param description the description of the recipe.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Recipe: " + name + ", Ingredients: " + ingredients + ", Description: " + description;
  }
}