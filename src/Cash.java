/**
 * This class represents a template for cash in the vending machine.
 * Each cash object has a denomination and quantity.
 */
public class Cash {
    private final int DENOMINATION;
    private int quantity;

    /**
     * Constructs a new Cash object with the given denomination and quantity
     * @param D The denomination of the cash.
     * @param q The initial quantity of the cash.
     */
    public Cash(int D, int q) {
        this.DENOMINATION = D;
        this.quantity = q;
    }

    /**
     * Constructs a new Cash object with the given denomination and initializes quantity to zero.
     * @param D The denomination of the cash.
     */
    public Cash(int D) {
        this.DENOMINATION = D;
        this.quantity = 0;
    }

    /**
     * Retrieves the denomination of the cash.
     * @return The denomination of the cash.
     */
    public int getDENOMINATION() {
        return DENOMINATION;
    }

    /**
     * Retrieves the current quantity of the cash.
     * @return The current quantity of the cash.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the cash.
     * @param q The quantity of the cash to be set.
     */
    public void setQuantity(int q) {
        this.quantity = q;
    }

    /**
     * Increments the quantity of the cash by one.
     */
    public void increment() {
        this.quantity++;
    }
}
