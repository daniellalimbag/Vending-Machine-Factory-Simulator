import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class is a controller for the Replenish Money View in the vending machine application.
 * It allows the user to replenish the money in the vending machine with various denominations of currency.
 */
public class ReplenishMoneyController {
    private Stage stage;
    private VMcontroller controller;
    private SVMcontroller svmcontroller;
    @FXML
    private TextField PHP1;
    @FXML
    private TextField PHP5;
    @FXML
    private TextField PHP10;
    @FXML
    private TextField PHP20;
    @FXML
    private TextField PHP50;
    @FXML
    private TextField PHP100;
    @FXML
    private TextField PHP200;
    @FXML
    private TextField PHP500;
    DataSingleton data = DataSingleton.getInstance();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    /**
     * Initializes the ReplenishMoneyController.
     * This method is automatically called by JavaFX after loading the FXML file.
     */
    @FXML
    void initialize() {
    }

    /**
     * Sets the VMcontroller instance for this controller.
     * @param vmc The VMcontroller instance to be set.
     */
    public void setVMController(VMcontroller vmc){
        this.controller = vmc;
    }

    public void setSVMController(SVMcontroller vmc){
        this.svmcontroller = vmc;
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
     * Handles the submission of the replenishment form to add money to the vending machine.
     * It updates the cash amounts in the vending machine based on the user input.
     * Closes the current stage after successful submission.
     *
     * @param actionEvent The ActionEvent triggered by the submission button.
     */
    @FXML
    public void handleSubmission(javafx.event.ActionEvent actionEvent) {
        this.stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        int q[] = new int[8];
        if(data.getRVMCurrent()) {
            try {
                q[0] = Integer.parseInt(PHP1.getText());
                q[1] = Integer.parseInt(PHP5.getText());
                q[2] = Integer.parseInt(PHP10.getText());
                q[3] = Integer.parseInt(PHP20.getText());
                q[4] = Integer.parseInt(PHP50.getText());
                q[5] = Integer.parseInt(PHP100.getText());
                q[6] = Integer.parseInt(PHP200.getText());
                q[7] = Integer.parseInt(PHP500.getText());
                int i = 0;
                while (i < 8) {
                    if (data.getCurrentVM().getCashCapacity() < (q[i] + data.getCurrentVM().getCashAmount().get(i).getQuantity())) {
                        throw new InvalidQuantityException();
                    }
                    i++;
                }
            } catch (NumberFormatException e) {
                System.out.println(e);
            } catch (InvalidQuantityException e) {
                showAlert(e.getMessage());
                return;
            }
            ArrayList<Cash> cash = new ArrayList<>(8);
            cash.add(new Cash(1, q[0]));
            cash.add(new Cash(5, q[1]));
            cash.add(new Cash(10, q[2]));
            cash.add(new Cash(20, q[3]));
            cash.add(new Cash(50, q[4]));
            cash.add(new Cash(100, q[5]));
            cash.add(new Cash(200, q[6]));
            cash.add(new Cash(500, q[7]));
            data.getCurrentVM().replenishMoney(cash);
            controller.updateDisplays();
        }
        else{
            try {
                q[0] = Integer.parseInt(PHP1.getText());
                q[1] = Integer.parseInt(PHP5.getText());
                q[2] = Integer.parseInt(PHP10.getText());
                q[3] = Integer.parseInt(PHP20.getText());
                q[4] = Integer.parseInt(PHP50.getText());
                q[5] = Integer.parseInt(PHP100.getText());
                q[6] = Integer.parseInt(PHP200.getText());
                q[7] = Integer.parseInt(PHP500.getText());
                for (int i = 0; i < 8; i++) {
                    if (data.getCurrentSVM().getCashCapacity() < (q[i] + data.getCurrentSVM().getCashAmount().get(i).getQuantity())) {
                        throw new InvalidQuantityException();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(e);
            } catch (InvalidQuantityException e) {
                showAlert(e.getMessage());
                return;
            }
            ArrayList<Cash> cash = new ArrayList<>(8);
            cash.add(new Cash(1, q[0]));
            cash.add(new Cash(5, q[1]));
            cash.add(new Cash(10, q[2]));
            cash.add(new Cash(20, q[3]));
            cash.add(new Cash(50, q[4]));
            cash.add(new Cash(100, q[5]));
            cash.add(new Cash(200, q[6]));
            cash.add(new Cash(500, q[7]));
            data.getCurrentSVM().replenishMoney(cash);
            svmcontroller.updateDisplays();
        }
        stage.close();
    }

    /**
     * Custom exception class to handle cases where the quantity of currency exceeds the maximum cash capacity.
     */
    public static class InvalidQuantityException extends Exception {
        public InvalidQuantityException(){
            super("Quantity exceeds maximum cash capacity!");
        }
    }
}