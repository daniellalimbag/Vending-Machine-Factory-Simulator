import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is a controller for the Product View in the vending machine application.
 * It displays the details of an item and allows the user to add the item to the current order.
 */
public class ProductController {
    private Item item;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label slotLabel;
    @FXML
    private Label caloriesLabel;
    @FXML
    private ImageView itemImage;
    private Map<String, String> itemImageMapping = new HashMap<>();
    DataSingleton data = DataSingleton.getInstance();

    /**
     * Initializes the ProductController.
     * This method is automatically called by JavaFX after loading the FXML file.
     * It also fills the itemImageMapping with the corresponding image file names for each item.
     */
    @FXML
    void initialize() {
        fillItemImageMapping();
    }

    /**
     * Sets the item to be displayed in the Product View.
     * It updates the item details displayed on the view.
     * @param i The item to be displayed.
     */
    public void setItem(Item i) {
        this.item = i;
        updateItemDetails();
    }

    /**
     * Updates the item details displayed on the view.
     * It sets the name, price, calories, and image of the item.
     * If the item is not a dish, it clears the details and image.
     */
    public void updateItemDetails() {
        this.slotLabel.setText(String.valueOf(item.getProductNum()));
        if (item.isDish()) {
            String itemName = item.getNAME().trim();
            String imageFileName = itemImageMapping.get(itemName);
            String imageUrl = "/image/food/" + imageFileName;
            this.nameLabel.setText(item.getNAME());
            this.priceLabel.setText(String.valueOf(item.getPrice()));
            this.caloriesLabel.setText(String.valueOf(item.getCalories()));
            Image image;
            try {
                image = new Image(imageUrl);
            } catch (Exception e) {
                // If the URL is invalid, set the default image (Coconut.png)
                image = new Image("/image/food/questionmark.png");
            }
            this.itemImage.setImage(image);
        } else {
            this.nameLabel.setText("");
            this.priceLabel.setText("");
            this.caloriesLabel.setText("");
            this.itemImage.setImage(null); // Clear the image if there is no item
        }
    }

    /**
     * Retrieves the currently displayed item.
     * @return The currently displayed item.
     */
    public Item getProduct() {
        return this.item;
    }

    /**
     * Fills the itemImageMapping with the image file names for each item.
     * The mapping is used to find the image file for each item when displaying its details.
     */
    private void fillItemImageMapping() {
        itemImageMapping.put("Waffles", "Waffles.png");
        itemImageMapping.put("FruitMedley", "FruitMedley.png");
        itemImageMapping.put("HoneyNuggets", "HoneyNuggets.png");
        itemImageMapping.put("Maki", "Maki.png");
        itemImageMapping.put("FlowerSalad", "FlowerSalad.png");
    }

    /**
     * Handles the action when the "Add" button is pressed.
     * It adds the current item to the current order in the SpecialVendingMachine.
     * @param event The ActionEvent triggered by pressing the "Add" button.
     * @throws IOException If an error occurs while adding the item to the order.
     */
    public void pressedAdd(ActionEvent event) throws IOException{
        data.getCurrentSVM().addToOrder(item);
        data.getCurrentSVM().setRecentProduct(item);
    }
}