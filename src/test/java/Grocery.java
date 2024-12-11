package edu.ntnu.iir.bidata;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a grocery item stored in the Wasteless app.
 * This class validates input data and ensures grocery items have valid properties.
 * Key features:
 * - Validates name, amount, unit, best-before date, and price per unit.
 * - Provides methods for calculating total value and formatting output.
 */
public class Grocery implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private double amount;
  private String unit;
  private Date bestBeforeDate;
  private double pricePerUnit;
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

  /**
   * Constructs a Grocery object with specified properties.
   *
   * @param name the name of the grocery item
   * @param amount the quantity of the grocery item
   * @param unit the unit of measurement (e.g., kg, liters)
   * @param bestBeforeDate the expiration date of the item
   * @param pricePerUnit the cost per unit
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public Grocery(String name, double amount, String unit,
                 Date bestBeforeDate, double pricePerUnit) {
    setName(name);
    setAmount(amount);
    setUnit(unit);
    setBestBeforeDate(bestBeforeDate);
    setPricePerUnit(pricePerUnit);
  }

  /**
   * Gets the name of the grocery item.
   *
   * @return the name of the grocery item
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the grocery item.
   *
   * @param name the name to set
   * @throws IllegalArgumentException if the name is null or blank
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or blank");
    }
    this.name = name;
  }

  /**
   * Gets the amount of the grocery item.
   *
   * @return the amount of the grocery item
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Sets the amount of the grocery item.
   *
   * @param amount the amount to set
   * @throws IllegalArgumentException if the amount is less than or equal to 0
   */
  public void setAmount(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0");
    }
    this.amount = amount;
  }

  /**
   * Gets the unit of measurement for the grocery item.
   *
   * @return the unit of measurement
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Sets the unit of measurement for the grocery item.
   *
   * @param unit the unit to set
   * @throws IllegalArgumentException if the unit is null or blank
   */
  public void setUnit(String unit) {
    if (unit == null || unit.isBlank()) {
      throw new IllegalArgumentException("Unit cannot be null or blank");
    }
    this.unit = unit;
  }

  /**
   * Gets the best-before date of the grocery item.
   *
   * @return the best-before date
   */
  public Date getBestBeforeDate() {
    return bestBeforeDate;
  }

  /**
   * Sets the best-before date of the grocery item.
   *
   * @param bestBeforeDate the best-before date to set
   * @throws IllegalArgumentException if the date is null or not in the future
   */
  public void setBestBeforeDate(Date bestBeforeDate) {
    if (bestBeforeDate == null || bestBeforeDate.before(new Date())) {
      throw new IllegalArgumentException("Best before date must be a valid future date");
    }
    this.bestBeforeDate = bestBeforeDate;
  }

  /**
   * Gets the price per unit of the grocery item.
   *
   * @return the price per unit
   */
  public double getPricePerUnit() {
    return pricePerUnit;
  }

  /**
   * Sets the price per unit of the grocery item.
   *
   * @param pricePerUnit the price per unit to set
   * @throws IllegalArgumentException if the price is less than or equal to 0
   */
  public void setPricePerUnit(double pricePerUnit) {
    if (pricePerUnit <= 0) {
      throw new IllegalArgumentException("Price per unit must be greater than 0");
    }
    this.pricePerUnit = pricePerUnit;
  }

  /**
   * Calculates the total value of the grocery based on the amount and price per unit.
   *
   * @return the total value as a double
   */
  public double calculateTotalValue() {
    return amount * pricePerUnit;
  }

  /**
   * Returns a formatted string representation of the grocery item.
   *
   * @return a string representation of the grocery item
   */
  @Override
  public String toString() {
    return String.format(
        "Grocery: %s, Amount: %.2f %s, Best Before: %s, Price: %.2f NOK, Total: %.2f NOK",
        name, amount, unit, DATE_FORMAT.format(bestBeforeDate),
        pricePerUnit, calculateTotalValue());
  }
}
