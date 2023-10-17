/**
 * This class represents a template for slots in the vending machine.
 * Each slot object has a slot number, an item, quantity, and other attributes.
 */
public class Slot {
    private int slotNum;
    private Item item;
    private boolean empty;
    private SlotController slotController;
    private int maxCapacity;
    private int quantity;

    /**
     * Constructs a new slot with the given slot number and maximum capacity.
     * @param num      The slot number.
     * @param capacity The maximum capacity of the slot.
     */
    public Slot(int num, int capacity) {
        this.slotNum = num;
        this.empty = true;
        this.maxCapacity = capacity;
        this.quantity = 0;
    }

    /**
     * Constructs a new slot with the given slot number, maximum capacity, and quantity.
     * @param num      The slot number.
     * @param capacity The maximum capacity of the slot.
     * @param q        The quantity of items in the slot.
     */
    public Slot(int num, int capacity, int q) {
        this.slotNum = num;
        this.empty = true;
        this.maxCapacity = capacity;
        this.quantity = q;
    }
    public int getMaxCapacity() {
        return this.maxCapacity;
    }
    /**
     * Retrieves the item in the slot.
     * @return The item in the slot.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the item in the slot and marks the slot as not empty.
     * @param item The item to be set in the slot.
     */
    public void setItem(Item item) {
        this.item = item;
        this.empty = false;
        notifyController();
    }

    /**
     * Removes the item from the slot and marks the slot as empty.
     */
    public void removeItem() {
        this.item = null;
        this.empty = true;
        notifyController();
    }

    /**
     * Sets the quantity of items in the slot.
     * @param q The quantity of items to be set.
     * @return True if the quantity is set successfully (less than or equal to the maximum capacity), otherwise false.
     */
    public boolean setQuantity(int q) {
        if (q <= maxCapacity) {
            this.quantity = q;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves the quantity of items in the slot.
     * @return The quantity of items in the slot.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Decrements the quantity of items in the slot when an item is dispensed.
     */
    public void dispenseItem() {
        this.quantity--;
    }

    /**
     * Retrieves the slot number.
     * @return The slot number.
     */
    public int getSlotNum() {
        return this.slotNum;
    }

    /**
     * Sets the slot number.
     * @param n The slot number to be set.
     */
    public void setSlotNum(int n) {
        this.slotNum = n;
    }

    /**
     * Checks if the slot is empty.
     * @return True if the slot is empty (quantity is zero), otherwise false.
     */
    public boolean isEmpty() {
        return this.quantity == 0;
    }

    /**
     * Sets the controller for the slot.
     * @param slotController The controller to be set for the slot.
     */
    public void setController(SlotController slotController) {
        this.slotController = slotController;
    }
    /**
     * Notifies the slot controller to update slot details.
     */
    private void notifyController() {
        if (slotController != null) {
            slotController.updateSlotDetails();
        }
    }
    /**
     * Retrieves the slot controller.
     * @return The slot controller.
     */
    public SlotController getSlotController() {
        return this.slotController;
    }
}
