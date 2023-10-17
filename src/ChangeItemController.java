import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * This class is a controller for the Change Item View in the vending machine application.
 * It allows the user to change an existing item in the vending machine.
 */
public class ChangeItemController {
    private Stage stage;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField caloriesField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField slotField;
    DataSingleton data = DataSingleton.getInstance();

    /**
     * Initializes the ChangeItemController.
     * This method is automatically called by JavaFX after loading the FXML file.
     */
    @FXML
    void initialize() {
    }

    /**
     * Displays an error alert with the given message.
     *
     * @param message The error message to be displayed in the alert.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the submission of the form to change an existing item.
     * It updates the item details in the vending machine based on the user input.
     * Closes the current stage after successful submission.
     *
     * @param actionEvent The ActionEvent triggered by the submission button.
     */
    @FXML
    public void handleSubmission(javafx.event.ActionEvent actionEvent) {
        this.stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        String name = new String();
        float price = 0;
        float calories = 0;
        int quantity = 0;
        int slotNum = 0;
        try {
            name = nameField.getText();
            price = Float.parseFloat(priceField.getText());
            calories = Float.parseFloat(caloriesField.getText());
            quantity = Integer.parseInt(quantityField.getText());
            slotNum = Integer.parseInt(slotField.getText().trim());
            if(data.getRVMCurrent()) {
                if (data.getCurrentVM().findSlot(slotNum) == -1)
                    throw new InvalidSlotNumberException();
            }
            else {
                if (data.getCurrentSVM().findSlot(slotNum) == -1)
                    throw new InvalidSlotNumberException();
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        } catch (InvalidSlotNumberException e) {
            showAlert(e.getMessage());
        }
            Item item = new Item(name, price, calories);
            if (data.getRVMCurrent()) {
                data.getCurrentVM().stockItem(item, quantity, slotNum);
            } else {
                data.getCurrentSVM().stockItem(item, quantity, slotNum);
            }
            stage.close();
    }
}