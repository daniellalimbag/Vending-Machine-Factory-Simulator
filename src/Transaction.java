import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class serves as a template for transactions in a vending machine.
 * A transaction contains information about the item bought, the date of the transaction,
 * the cash inserted by the user, the change returned, and the restock index (if applicable).
 */
public class Transaction {
    private Item item;
    private String date;
    private ArrayList<Cash> cashInserted;
    private ArrayList<Cash> change;
    private int restockIndex;

    /**
     * Constructs a new Transaction object with the given attributes.
     * @param i      Item bought in the transaction.
     * @param d      Date of the transaction in string format.
     * @param r      Restock index, indicates the restock event associated with this transaction.
     * @param cI     ArrayList of Cash representing the cash inserted by the user.
     * @param c      ArrayList of Cash representing the change returned in the transaction.
     */
    public Transaction(Item i, String d, int r, ArrayList<Cash> cI, ArrayList<Cash> c)
    {
        this.item = i;
        this.date = d;
        this.restockIndex = r;
        this.cashInserted = cI;
        this.change = c;
    }

    /**
     * Retrieves date of transaction
     * @return Date of transaction
     */
    public String getDate(){return this.date;}

    /**
     * Retrieves the cash inserted by the user in the transaction.
     * @return ArrayList of Cash representing the cash inserted.
     */
    public ArrayList<Cash> getCashInserted(){return this.cashInserted;}

    /**
     * Retrieves the change returned in the transaction.
     * @return ArrayList of Cash representing the change.
     */
    public ArrayList<Cash> getChange(){return this.change;}

    /**
     * Retrieves item of transaction
     * @return Item of transaction
     */
    public Item getItem(){return this.item;}

    /**
     * Sets item of transaction
     * @param item Item of transaction
     */
    public void setItems(Item item) {
        this.item = item;
    }

    /**
     * Retrieves restock index
     * @return Restock index
     */
    public int getRestockIndex(){return restockIndex;}

    /**
     * Sets restock index
     * @param r Restock index
     */
    public void setRestockIndex(int r){this.restockIndex = r;}

    /**
     * Converts transaction to string format
     * @return String format of transaction
     */
    @Override
    public String toString(){
        String result = String.format("|%-37s|%19.2f|%19.2f|%22s|", this.getItem().getNAME(), VendingMachine.calculateValue(this.cashInserted), VendingMachine.calculateValue(this.change), this.getDate());
        return result;
    }
}