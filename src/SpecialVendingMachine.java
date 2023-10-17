import java.util.ArrayList;
import java.util.*;

/**
 * SpecialVendingMachine extends the VendingMachine class and represents a specialized vending machine
 * capable of handling additional functionalities, such as managing products, processes, and order.
 * It provides methods to add products, set add-on slots, manage processes, find items and slots,
 * handle orders, and set a controller to manage user interactions in the SVM interface.
 *
 * This class is designed to work with a graphical SVM interface managed by SVMcontroller
 */
public class SpecialVendingMachine extends VendingMachine{
    private ArrayList<Item> Products;
    private ArrayList<Item> Order;
    private ArrayList<Slot[]> Items;
    private Slot[] addOnSlots;
    private int maxAddOns;
    private int maxProducts;
    private ArrayList<String> Processes;
    private SVMcontroller svmController;
    private Item recentProduct;

    /**
     * Constructor for creating a SpecialVendingMachine object.
     *
     * @param N The name of the vending machine.
     * @param iS The array of item slots in the vending machine.
     * @param c The capacity of the vending machine.
     * @param maxA The array of add-on slots in the vending machine.
     * @param maxP The maximum number of products the vending machine can handle.
     */
    public SpecialVendingMachine(String N, Slot[] iS, int c, Slot[] maxA, int maxP) {
        super(N, iS, c);
        this.Products = new ArrayList<Item>();
        this.addOnSlots = maxA;
        this.maxProducts = maxP;
        this.Processes = new ArrayList<String>();
        this.Order = new ArrayList<Item>();
        this.Items = new ArrayList<>(2);
        this.Items.add(this.getItemSlots());
        this.Items.add(this.getAddOnSlots());
    }

    /**
     * Adds a product to the vending machine.
     *
     * @param item The Item object representing the product to be added.
     * @return True if the product is successfully added, false otherwise.
     */
    public boolean addProduct(Item item){
        if(item.isDish()){
            Products.add(item);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the list of products available in the vending machine.
     *
     * @return The ArrayList containing the available products.
     */
    public ArrayList<Item> getProducts(){
        return this.Products;
    }

    /**
     * Sets the array of add-on slots in the vending machine.
     *
     * @param slots The array of Slot objects representing the add-on slots.
     */
    public void setAddOnSlots(Slot[] slots){
        this.addOnSlots = slots;
    }

    /**
     * Retrieves the array of add-on slots in the vending machine.
     *
     * @return The array of Slot objects representing the add-on slots.
     */
    public Slot[] getAddOnSlots(){
        return this.addOnSlots;
    }

    /**
     * Sets the list of processes in the vending machine.
     *
     * @param processes The ArrayList containing the processes to be set.
     */
    public void setProcesses(ArrayList<String> processes){
        this.Processes = processes;
    }

    /**
     * Retrieves the list of processes in the vending machine.
     *
     * @return The ArrayList containing the processes.
     */
    public ArrayList<String> getProcesses(){
        return this.Processes;
    }

    /**
     * Sets the add-on sellable status for all add-on slots.
     * This method is used to mark all add-on slots as not sellable.
     */
    public void addOnSellableStatus(){
        for(int i = 0; i < maxAddOns; i++){
            this.addOnSlots[i].getItem().setaddOn(false);
        }
    }

    /**
     * Finds the Item object associated with the given slot number.
     *
     * @param slotNum The slot number for which to find the corresponding Item.
     * @return The Item object associated with the given slot number, or null if not found.
     */
    public Item findItem(int slotNum) {
        for(int i = 0; i < this.Items.size(); i++){
            for(int j = 0; j < this.Items.get(i).length; j++){
                if(this.Items.get(i)[j].getSlotNum() == slotNum)
                    return this.Items.get(i)[j].getItem();
            }
        }
        return null;
    }

    /**
     * Finds the Slot object associated with the given Item.
     *
     * @param item The Item object for which to find the corresponding Slot.
     * @return The Slot object associated with the given Item, or null if not found.
     */
    public Slot findSlot(Item item) {
        for(int i = 0; i < this.Items.size(); i++){
            for(int j = 0; j < this.Items.get(i).length; j++){
                if(this.Items.get(i)[j].getItem() == item)
                    return this.Items.get(i)[j];
            }
        }
        return null;
    }

    /**
     * Retrieves the current order in the vending machine.
     *
     * @return The ArrayList containing the current order.
     */
    public ArrayList<Item> getOrder(){
        return this.Order;
    }

    /**
     * Sets the order in the vending machine.
     *
     * @param order The ArrayList containing the order to be set.
     */
    public void setOrder(ArrayList<Item> order){
        this.Order = order;
    }

    /**
     * Clears the current order in the vending machine.
     */
    public void clearOrder(){
        this.Order = new ArrayList<Item>();
    }

    /**
     * Checks if there is any dish in the current order.
     *
     * @return True if there is at least one dish in the order, false otherwise.
     */
    public boolean hasDish() {
        for (Item dish : this.Products) {
            boolean isSubset = true;
            for (Item ingredient : dish.getIngredients()) {
                if (!this.Order.contains(ingredient)) {
                    isSubset = false;
                    break;
                }
            }
            if (isSubset) {
                return true;
            }
        }
        return false; // If no dish is found in the order, return false
    }

    /**
     * Finds a dish that is a subset of the current order.
     *
     * @return The Item object representing the dish found, or null if no dish is found.
     */
    public Item findDish() {
        for (Item dish : this.Products) {
            boolean isSubset = true;
            for (Item ingredient : dish.getIngredients()) {
                if (!this.Order.contains(ingredient)) {
                    isSubset = false;
                    break;
                }
            }
            if (isSubset) {
                return dish;
            }
        }
        return null; // If no dish is found in the order, return false
    }

    /**
     * Adds an item to the current order.
     *
     * @param item The Item object to be added to the order.
     * @return True if the item is successfully added to the order, false otherwise.
     */
    public boolean addToOrder(Item item){
        if(hasDish() && !item.isDish()){
            this.Order.add(item);
            svmController.updateDisplays();
            svmController.updateCustomProduct(item);
            return true;
        }
        else if(!hasDish()){
            if(item.isDish()){
                for(int i = 0; i < item.getIngredients().size(); i++){
                    this.Order.add(item.getIngredients().get(i));
                }
            }
            else {
                this.Order.add(item);
            }
            svmController.updateDisplays();
            svmController.updateCustomProduct(item);
            return true;
        }
        return false;
    }

    /**
     * Sets the controller for managing user interactions in the SVM interface.
     *
     * @param svmc The SVMcontroller object to be set as the controller.
     */
    public void setController(SVMcontroller svmc){
        this.svmController = svmc;
    }

    /**
     * Sets the most recently selected product in the vending machine.
     *
     * @param item The Item object representing the most recent product.
     */
    public void setRecentProduct(Item item){
        this.recentProduct = item;
    }

    /**
     * Retrieves the most recently selected product in the vending machine.
     *
     * @return The Item object representing the most recent product.
     */
    public Item getRecentProduct(){
        return this.recentProduct;
    }

    /**
     * Overrides the findSlot method from the VendingMachine class.
     * Finds the index of the Slot object associated with the given slot number.
     *
     * @param slotNum The slot number for which to find the corresponding Slot.
     * @return The index of the Slot object associated with the given slot number, or -1 if not found.
     */
    @Override
    public int findSlot(int slotNum) {
        for(int i = 0; i < this.Items.size(); i++){
            for(int j = 0; j < this.Items.get(i).length; j++){
                if(this.Items.get(i)[j].getSlotNum() == slotNum)
                    return j;
            }
        }
        return -1;
    }

}
