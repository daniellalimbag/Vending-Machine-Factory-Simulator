import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is a controller for the Dispense Item View in the vending machine application.
 * It displays the details of the item to be dispensed, including its name, calories, and an image.
 */
public class DispenseItemController implements Initializable {
    private Item item;
    @FXML
    private Label itemName;
    @FXML
    private Label itemCalories;
    @FXML
    private ImageView itemImage;
    DataSingleton data = DataSingleton.getInstance();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    /**
     * Initializes the DispenseItemController.
     * This method is automatically called by JavaFX after loading the FXML file.
     */
    @FXML
    void initialize() {
    }

    /**
     * Sets the item to be dispensed and updates the display with its details.
     * @param i The item to be dispensed.
     */
    public void setItem(Item i) {
        this.item = i;
        updateDisplay();
    }

    /**
     * Updates the display with the details of the current item.
     * It sets the text for the item name and calories labels, and loads the image of the item.
     */
    public void updateDisplay() {
        if (this.item != null) {
            this.itemCalories.setText(Float.toString(this.item.getCalories()));
            this.itemName.setText(this.item.getNAME());
            String imageUrl = "/image/food/" + this.item.getNAME() + ".png";
            Image image = new Image(imageUrl);
            this.itemImage.setImage(image);
        }
    }

    /**
     * Initializes the view with the details of the current item.
     * This method is automatically called by JavaFX after loading the FXML file.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateDisplay();
    }
}
