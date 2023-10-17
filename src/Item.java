import java.util.ArrayList;

/**
 *  This class serves as a template for items
 */
public class Item {
    private String NAME;
    private float price;
    private float calories;
    private boolean addOn;
    private ArrayList<Item> ingredients;
    private int productNum;
    private boolean dish;
    /**
     * Constructs a new item object and assigns the attributes: name, price, calories, and quantity
     * @param N Name
     * @param p Price
     * @param C Calories
     */
    Item(String N, float p, float C){
        this.NAME = N;
        this.price = p;
        this.calories = C;
        this.ingredients = new ArrayList<Item>();
        this.addOn = false;
        this.dish = false;
    }
    Item(String N){
        this.NAME = N;
        this.price = 0;
        this.calories = 0;
        this.ingredients = new ArrayList<Item>();
        this.addOn = false;
        this.dish = true;
    }

    /**
     * Returns name of item
     * @return Name of item
     */
    public String getNAME(){return NAME;}

    /**
     * Returns price of item
     * @return Price of item
     */
    public float getPrice() {
        return price;
    }

    /**
     * Returns calories of item
     * @return Calories of item
     */
    public float getCalories() {
        return this.calories;
    }

    /**
     * Returns whether the item is addOn.
     * @return True if the item is addOn, otherwise false.
     */
    public boolean isaddOn() {
        return this.addOn;
    }


    /**
     * Sets whether the item is addOn.
     * @param b Boolean value indicating if the item is addOn.
     */
    public void setaddOn(boolean b) {
        this.addOn = b;
    }

    /**
     * Returns ingredients of item
     * @return Ingredients
     */
    public ArrayList<Item> getIngredients(){
        return ingredients;
    }

    /**
     * Sets ingredients of item
     * @param i Ingredients
     */
    public void setIngredients(ArrayList<Item> i){
        this.ingredients = i;
    }

    /**
     * Sets price of item
     * @param p Price
     */
    public void setPrice(float p){
        this.price = p;
    }


    /**
     * Checks if item is available
     * @return Boolean value if item is available
     */
    public void addIngredient(Item item) {
        this.ingredients.add(item);
        this.calories += item.calories;
        this.price += item.getPrice();
        this.dish = true;
    }

    /**
     * Checks if the item is a dish (has more than one ingredient).
     * @return True if the item is a dish, otherwise false.
     */
    public Boolean isDish() {
        return getIngredients().size() >= 2;
    }

    /**
     * Sets the product number of the item (applicable only for dishes).
     * @param num The product number to be set for the item.
     */
    public void setProductNum(int num) {
        if (this.isDish())
            this.productNum = num;
    }

    /**
     * Returns the product number of the item (applicable only for dishes).
     * @return The product number of the item.
     */
    public int getProductNum() {
        return this.productNum;
    }

}