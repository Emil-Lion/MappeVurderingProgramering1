import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import edu.ntnu.iir.bidata.Grocery;

/**
 * Tests the Grocery class. This class contains unit tests to ensure
 * the Grocery class behaves as expected in both positive and negative scenarios.
 *
 * Positive tests:
 * - Creating Grocery with a valid name
 * - Setting a positive amount for the grocery
 * - Specifying a valid unit (e.g., liters, grams, pieces)
 * - Setting a valid best before date
 * - Setting a positive price per unit
 *
 * Negative tests:
 * - Creating Grocery with an invalid name (empty or null)
 * - Setting a negative amount
 * - Specifying an invalid unit (null or empty)
 * - Setting an invalid best before date
 * - Setting a negative price per unit
 */
public class GroceryTest {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    // Positive Tests

    /**
     * Tests creating a Grocery with a valid name.
     */
    @Test
    public void testValidName() {
        Grocery milk = new Grocery("Milk", 1, "liters", new Date(), 20.0);
        assertEquals("Milk", milk.getName(), "Name should be 'Milk'");
    }

    /**
     * Tests setting a positive amount for the grocery.
     */
    @Test
    public void testValidAmount() {
        Grocery bread = new Grocery("Bread", 2, "pieces", new Date(), 10.0);
        assertEquals(2, bread.getAmount(), "Amount should be 2");
    }

    /**
     * Tests specifying a valid unit for the grocery.
     */
    @Test
    public void testValidUnit() {
        Grocery juice = new Grocery("Juice", 1, "liters", new Date(), 15.0);
        assertEquals("liters", juice.getUnit(), "Unit should be 'liters'");
    }

    /**
     * Tests setting a valid best before date for the grocery.
     */
    @Test
    public void testValidBestBeforeDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1); // Adds 1 day
        Date futureDate = calendar.getTime();

        Grocery cheese = new Grocery("Cheese", 1, "kg", futureDate, 30);
        assertEquals(DATE_FORMAT.format(futureDate), DATE_FORMAT.format(cheese.getBestBeforeDate()), "Best before date should be set correctly");
    }

    /**
     * Tests setting a positive price per unit for the grocery.
     */
    @Test
    public void testValidPricePerUnit() {
        Grocery apple = new Grocery("Apple", 5, "kg", new Date(), 30.0);
        assertEquals(30.0, apple.getPricePerUnit(), 0.01, "Price per unit should be 30.0 NOK");
    }

    // Negative Tests

    /**
     * Tests creating a Grocery with a negative amount.
     */
    @Test
    public void testInvalidAmountNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Grocery("Bread", -1, "pieces", new Date(), 10.0);
        });
        assertEquals("Amount must be greater than 0", exception.getMessage());
    }

    /**
     * Tests creating a Grocery with an amount of zero.
     */
    @Test
    public void testInvalidAmountZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Grocery("Bread", 0, "pieces", new Date(), 10.0);
        });
        assertEquals("Amount must be greater than 0", exception.getMessage());
    }

    /**
     * Tests creating a Grocery with an empty unit.
     */
    @Test
    public void testInvalidUnitEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Grocery("Milk", 1, "", new Date(), 20.0);
        });
        assertEquals("Unit cannot be null or empty", exception.getMessage());
    }

    /**
     * Tests creating a Grocery with a null unit.
     */
    @Test
    public void testInvalidUnitNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Grocery("Milk", 1, null, new Date(), 20.0);
        });
        assertEquals("Unit cannot be null or empty", exception.getMessage());
    }

    /**
     * Tests creating a Grocery with a negative price per unit.
     */
    @Test
    public void testInvalidPricePerUnitNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Grocery("Juice", 1, "liters", new Date(), -10.0);
        });
        assertEquals("Price per unit must be greater than 0", exception.getMessage());
    }
}
