import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a controller for the Slot View in the vending machine application.
 * It displays the details of a specific slot in the vending machine.
 */
public class SlotController {
    private Slot slot;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label slotLabel;
    @FXML
    private Label caloriesLabel;
    @FXML
    private ImageView itemImage;
    private Map<String, String> itemImageMapping = new HashMap<>();

    /**
     * Initializes the SlotController.
     * This method is automatically called by JavaFX after loading the FXML file.
     */
    @FXML
    void initialize() {
        fillItemImageMapping();
    }

    /**
     * Sets the Slot instance for this controller and updates the displayed slot details.
     * @param slot The Slot instance to be set.
     */
    public void setSlot(Slot slot) {
        this.slot = slot;
        updateSlotDetails();
    }

    /**
     * Updates the displayed details of the slot based on the associated Item and quantity.
     * If the slot is empty, it clears the displayed details.
     */
    public void updateSlotDetails() {
        this.slotLabel.setText(String.valueOf(slot.getSlotNum()));
        if (slot != null && !slot.isEmpty() && this.slot.getItem() != null) {
            Item item = this.slot.getItem();
            String itemName = item.getNAME();
            String imageFileName = itemImageMapping.get(itemName);
            String imageUrl = "/image/food/" + imageFileName;
            this.nameLabel.setText(item.getNAME());
            this.priceLabel.setText(String.valueOf(item.getPrice()));
            this.caloriesLabel.setText(String.valueOf(item.getCalories()));
            this.quantityLabel.setText(String.valueOf(this.slot.getQuantity()));
            Image image;
            try {
                image = new Image(imageUrl);
            } catch (Exception e) {
                // If the URL is invalid, set the default image
                image = new Image("/image/food/questionmark.png");
            }
            this.itemImage.setImage(image);
        } else {
            this.nameLabel.setText("");
            this.priceLabel.setText("");
            this.caloriesLabel.setText("");
            this.quantityLabel.setText("");
            this.itemImage.setImage(null); // Clear the image if there is no item
        }
    }

    /**
     * Retrieves the Slot instance associated with this controller.
     * @return The Slot instance associated with this controller.
     */
    public Slot getSlot() {
        return this.slot;
    }

    /**
     * Fills the itemImageMapping with key-value pairs for mapping item names to their respective image file names.
     * This mapping is used to display the appropriate image for the item in the slot.
     */
    private void fillItemImageMapping() {
        itemImageMapping.put("Banana", "Banana.png");
        itemImageMapping.put("Berries", "Berries.png");
        itemImageMapping.put("Birchnut", "Birchnut.png");
        itemImageMapping.put("Bread", "Bread.png");
        itemImageMapping.put("Butter", "Butter.png");
        itemImageMapping.put("Cactus", "Cactus.png");
        itemImageMapping.put("Coconut", "Coconut.png");
        itemImageMapping.put("Corn", "Corn.png");
        itemImageMapping.put("Drumstick", "Drumstick.png");
        itemImageMapping.put("Durian", "Durian.png");
        itemImageMapping.put("Fish", "Fish.png");
        itemImageMapping.put("Ice", "Ice.png");
        itemImageMapping.put("Meat", "Meat.png");
        itemImageMapping.put("Milk", "Milk.png");
        itemImageMapping.put("Mushroom", "Mushroom.png");
        itemImageMapping.put("Pomegranate", "Pomegranate.png");
        itemImageMapping.put("Roe", "Roe.png");
        itemImageMapping.put("Taffy", "Taffy.png");
        itemImageMapping.put("Tomato", "Tomato.png");
        itemImageMapping.put("Honey", "Honey.png");
        itemImageMapping.put("Dressing", "Dressing.png");
        itemImageMapping.put("Rice", "Rice.png");
        itemImageMapping.put("Flour", "Flour.png");
        itemImageMapping.put("Egg", "Egg.png");
        itemImageMapping.put("Watermelon", "Watermelon.png");
    }
}
