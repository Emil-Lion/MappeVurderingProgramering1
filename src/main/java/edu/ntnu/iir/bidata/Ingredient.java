package edu.ntnu.iir.bidata;

import java.io.Serializable;

/**
 * Represents an ingredient with a name, amount, and unit.
 */
public class Ingredient implements Serializable {
  private String name;
  private double amount;
  private String unit;

  /**
   * Constructs a new Ingredient with the specified details.

   * @param name the name of the ingredient.

   * @param amount the amount of the ingredient.

   * @param unit the unit of measurement for the amount.
   */
  public Ingredient(String name, double amount, String unit) {
    this.name = name;
    this.amount = amount;
    this.unit = unit;
  }

  /**
   * Gets the name of the ingredient.

   * @return the name of the ingredient.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the amount of the ingredient.

   * @return the amount of the ingredient.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Gets the unit of measurement for the ingredient.

   * @return the unit of measurement.
   */
  public String getUnit() {
    return unit;
  }

  @Override
  public String toString() {
    return name + ": " + amount + " " + unit;
  }
}