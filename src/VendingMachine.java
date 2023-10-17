import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a template for creating a vending machine.
 * The vending machine contains cash, item slots, and keeps track of transactions.
 */
public class VendingMachine {
    private final String NAME;
    private ArrayList<Cash> cashAmount;
    private ArrayList<Cash> cashInserted;
    private Slot[] itemSlots;
    private ArrayList<Transaction> transactions;
    private int cashCapacity;
    private int restockCount;
    private List<String> restockDates;
    /**
     * Constructs a new VendingMachine object with the given name, item slots, and cash capacity.
     * @param N Name of the vending machine.
     * @param iS Item slots of the vending machine.
     * @param c Cash capacity of the vending machine.
     */
    public VendingMachine(String N, Slot[] iS, int c){
        this.NAME = N;
        this.cashAmount = prepareMoney();
        this.cashInserted = prepareMoney();
        this.itemSlots = iS;
        this.transactions = new ArrayList<>();
        this.restockCount = 0;
        this.restockDates = new ArrayList<>();
        this.cashCapacity = c;
    }

    /**
     * Resets cash collected by the machine
     * @param
     */
    public void resetCashCollected(){
        ArrayList<Cash> c = prepareMoney();
        this.cashAmount = c;
    }

    /**
     * Retrieves the cash capacity of the vending machine
     * @return Cash capacity of the vending machine.
     */
    public int getCashCapacity(){return this.cashCapacity;}


    /**
     * Organizes cash into an array list of cash collected by the machine.
     * @param c Cash to be organized.
     */
    public void organizeMoney(ArrayList<Cash> c){
        int i = 0;
        while(i < c.size()){
            if(c.get(i).getQuantity() > 0) {
                this.cashAmount.get(i).setQuantity(this.cashAmount.get(i).getQuantity() + c.get(i).getQuantity());
            }
            i++;
        }
    }

    /**
     * Organizes cash into an array list of cash inserted into the machine.
     * @param c Cash to be inserted.
     */
    public void insertMoney(Cash c){
        boolean organized = false;
        int i = 0;
        while(!organized && i < this.cashInserted.size()){
            if(c.getDENOMINATION() == this.cashInserted.get(i).getDENOMINATION()){
                this.cashInserted.get(i).increment();
                organized = true;
            }
            i++;
        }
    }

    /**
     * Retrieves the name of the vending machine
     * @return Name of the vending machine
     */
    public String getNAME(){return this.NAME;}

    /**
     * Collects money from the user and organizes it in the cashInserted list.
     * If the denomination of the cash matches any existing denomination in the cashInserted list,
     * it increments the quantity of that denomination.
     * @param c Cash collected from the user.
     */
    public void organizePayment(Cash c){
        for (int i = 0; i < this.cashInserted.size(); i++) {
            if (c.getDENOMINATION() == this.cashInserted.get(i).getDENOMINATION()) {
                this.cashInserted.get(i).setQuantity(this.cashInserted.get(i).getQuantity() + 1);
            }
        }
    }

    /**
     * Clears the cashInserted list by setting the quantity of every denomination to 0.
     * It also organizes the collected cash into the cashCollected list to keep track of the total cash collected.
     */
    public void collectCash(){
        // Create a copy of the cashInserted ArrayList
        ArrayList<Cash> c = this.cashInserted;
        organizeMoney(c);
        // Clear the original cashInserted ArrayList without affecting the Transaction
        for (int i = 0; i < this.cashInserted.size(); i++) {
            this.cashInserted.get(i).setQuantity(0);
        }
    }

    /**
     * Sets the cash inserted into the vending machine.
     * @param c The ArrayList of Cash to be set as the amount of cash inserted into the machine.
     */
    public void setCashInserted(ArrayList<Cash> c){
        this.cashInserted = c;
    }

    /**
     * Creates an initialized array list of cash with the denominations accepted by the machine.
     * @return A Cash ArrayList that has been initialized.
     */
    public ArrayList<Cash> prepareMoney(){
        ArrayList<Cash> money = new ArrayList<Cash>();
        money.add(new Cash(1, 0));
        money.add(new Cash(5, 0));
        money.add(new Cash(10, 0));
        money.add(new Cash(20, 0));
        money.add(new Cash(50, 0));
        money.add(new Cash(100, 0));
        money.add(new Cash(200, 0));
        money.add(new Cash(500, 0));
        return money;
    }

    /**
     * Calculates the total value of the given array list of cash.
     * @param cI Array list of cash.
     * @return The total value of cash in the array list.
     */
    public static float calculateValue(ArrayList<Cash> cI){
        float value = 0;
        int i;

        for(i=0;i<cI.size();i++){
            value += cI.get(i).getQuantity() * cI.get(i).getDENOMINATION();
        }
        return value;
    }
    /**
     * Calculates and returns the change in denominations to be given to the user after a purchase.
     * @param price Price of the item bought.
     * @param cashVal Cash inserted by the user.
     * @return ArrayList of Cash representing the change in denominations.
     */
    public ArrayList<Cash> giveChange(float price, ArrayList<Cash> cashVal) {
        float change = calculateValue(cashVal) - price;
        System.out.println("change: " + change);
        ArrayList<Cash> changeArr = prepareMoney();
        int i = this.cashAmount.size() - 1;
        while (i >= 0) {
            int denomination = this.cashAmount.get(i).getDENOMINATION();
            int quantity = this.cashAmount.get(i).getQuantity();
            if (denomination <= change && change > 0) {
                int numMoney = (int)change / denomination; // Consider the quantity of cash denominations
                changeArr.get(i).setQuantity(numMoney);
                change -= denomination * numMoney;
                this.cashAmount.get(i).setQuantity(quantity - numMoney); // Update the quantity of cash denominations
                System.out.println(denomination + " " + numMoney);
            }
            i--;
        }
        return changeArr;
    }

    /**
     * Retrieves the cash available in the machine
     * @return Cash available in the machine
     */
    public ArrayList<Cash> getCashAmount(){
        return cashAmount;
    }

    /**
     * Retrieves the cash inserted in the machine
     * @return Cash inserted in the machine
     */
    public ArrayList<Cash> getCashInserted() {
        return cashInserted;
    }

    /**
     * Retrieves the item slots of the vending machine
     * @return Item slots of the vending machine
     */
    public Slot[] getItemSlots() {
        return itemSlots;
    }

    /**
     * Displays text to dispense item
     * @param iS Item to be dispensed
     */
    public void dispenseItem(Slot iS){
        iS.dispenseItem();
        System.out.println("Dispensing Item...\tDone!");
    }

    /**
     * Restocks an item in the specified slot with the given quantity.
     * @param s Slot with the item to be restocked.
     * @param q Quantity of items to be restocked.
     */
    public void restockItem(Slot s, int q){
        s.setQuantity(s.getQuantity()+q);
        this.transactions.clear();
        this.restockCount++;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        this.restockDates.add(formattedDateTime);
    }

    /**
     * Retrieves the restock count of the vending machine.
     * @return Restock count of the vending machine.
     */
    public int getRestockCount(){return this.restockCount;}

    /**
     * Replenishes the cash in the machine by adding the specified cash to the existing cash.
     * @param c Cash to be added to the machine.
     */
    public void replenishMoney(ArrayList<Cash> c){
        int i;
        for(i = 0; i < c.size(); i++)
        {
            if(c.get(i).getDENOMINATION() == this.cashAmount.get(i).getDENOMINATION()){
                int original = this.cashAmount.get(i).getQuantity();
                int add = c.get(i).getQuantity();
                this.cashAmount.get(i).setQuantity(original + add);
            }
        }
    }

    /**
     * Adds a new transaction to the list of transactions in the vending machine.
     * @param t Transaction to be added.
     */
    public void addTransaction(Transaction t){
        this.transactions.add(t);
    }

    /**
     * Searches for the slot index based on the given slot number.
     * @param slotNum Slot number to search for.
     * @return The index of the slot if found, otherwise -1.
     */
    public int findSlot(int slotNum){
        int i = 0;
        while(i < this.getItemSlots().length){
            if(this.getItemSlots()[i].getSlotNum() == slotNum)
                return i;
            i++;
        }
        return -1;
    }
    public Slot FindSlot(int slotNum){
        for(int i = 0; i < this.getItemSlots().length; i++){
                if(this.getItemSlots()[i].getSlotNum() == slotNum)
                    return getItemSlots()[i];
        }
        return null;
    }
    /**
     * Stocks an item in the specified slot with the given quantity.
     * @param item Item to be stocked in the slot.
     * @param quantity Quantity of items to be stocked.
     * @param slotNum Slot number to stock the item.
     * @return True if the item is successfully stocked, otherwise false.
     */
    public boolean stockItem(Item item, int quantity, int slotNum){
        int index = findSlot(slotNum);
        if(index >= 0) {
            this.getItemSlots()[index].setItem(item);
            this.getItemSlots()[index].setQuantity(quantity);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the list of transactions in the vending machine.
     * @return List of transactions in the vending machine.
     */
    public ArrayList<Transaction> getTransactions(){
        return this.transactions;
    }
}