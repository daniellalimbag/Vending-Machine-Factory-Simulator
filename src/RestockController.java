import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class is a controller for the Restock View in the vending machine application.
 * It allows the user to restock items in the vending machine by specifying the quantity and slot number.
 */
public class RestockController {
    private Stage stage;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField slotField;
    DataSingleton data = DataSingleton.getInstance();

    /**
     * Initializes the RestockController.
     * This method is automatically called by JavaFX after loading the FXML file.
     */
    @FXML
    void initialize() {
    }

    /**
     * Displays an error alert with the given message.
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
     * Handles the restocking of an item in the vending machine.
     * It updates the item's quantity in the vending machine based on the user input.
     * Closes the current stage after successful restock.
     * @param actionEvent The ActionEvent triggered by the restock button.
     */
    @FXML
    public void restockItem(javafx.event.ActionEvent actionEvent) {
        this.stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        int quantity = 0;
        int slotNum = 0;
        if(data.getRVMCurrent()) {
            try {
                quantity = Integer.parseInt(quantityField.getText());
                slotNum = Integer.parseInt(slotField.getText());
                Slot s = data.getCurrentVM().FindSlot(slotNum);
                if (data.getCurrentVM().findSlot(slotNum) == -1)
                    throw new RestockController.InvalidSlotNumberException();
                else if(s.getMaxCapacity()<quantity+s.getQuantity())
                    throw new ExceedsLimitException();
            } catch (NumberFormatException e) {
                System.out.println(e);
                return;
            } catch (RestockController.InvalidSlotNumberException e) {
                showAlert(e.getMessage());
                return;
            } catch (ExceedsLimitException e) {
                showAlert(e.getMessage());
            } finally {
                int index = data.getCurrentVM().findSlot(slotNum);
                data.getCurrentVM().restockItem(data.getCurrentVM().getItemSlots()[index], quantity);
                data.getCurrentVM().getItemSlots()[index].getSlotController().updateSlotDetails();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                data.setLastRVMrestock(formattedDateTime);
                stage.close();
            }
        }
        else{
            try {
                quantity = Integer.parseInt(quantityField.getText());
                slotNum = Integer.parseInt(slotField.getText());
                Slot s = data.getCurrentSVM().FindSlot(slotNum);
                if (data.getCurrentSVM().findSlot(slotNum) == -1)
                    throw new RestockController.InvalidSlotNumberException();
                else if(s.getMaxCapacity()<quantity+s.getQuantity())
                    throw new ExceedsLimitException();
            } catch (NumberFormatException e) {
                System.out.println(e);
                return;
            } catch (RestockController.InvalidSlotNumberException e) {
                showAlert(e.getMessage());
                return;
            } catch (ExceedsLimitException e) {
                showAlert(e.getMessage());
            } finally {
                int index = data.getCurrentSVM().findSlot(slotNum);
                data.getCurrentSVM().restockItem(data.getCurrentSVM().getItemSlots()[index], quantity);
                data.getCurrentSVM().getItemSlots()[index].getSlotController().updateSlotDetails();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                data.setLastSVMrestock(formattedDateTime);
                stage.close();
            }
        }
    }

    /**
     * Custom exception class to handle invalid slot numbers during restocking.
     */
    public class InvalidSlotNumberException extends Exception {
        public InvalidSlotNumberException() {
            super("Invalid slot number.");
        }
    }
    public class ExceedsLimitException extends Exception {
        public ExceedsLimitException() {
            super("Quantity exceeds slot limit.");
        }
    }
}
