import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

/**
 * The VMcontroller class represents the controller for the Vending Machine (VM) application.
 * It handles user interactions with the VM, manages the display and logic, and communicates
 * with the underlying VendingMachine model to perform actions such as dispensing items and
 * handling transactions.
 *
 * This class implements the Initializable interface, which allows it to initialize the UI and
 * set up the initial state of the VM when the FXML file is loaded.
 */
public class VMcontroller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private VendingMachine VM;
    private Slot[] slots;
    @FXML
    private GridPane gridPane;
    @FXML
    private Pane gridPaneContainer;
    @FXML
    private Label keyDisplay;
    @FXML
    private GridPane keyPad;
    @FXML
    private Pane input;
    @FXML
    private Item itemDispensed;
    @FXML
    private Button dispensedItem;
    @FXML
    private Button backSpace;
    @FXML
    private Button enter;
    @FXML
    private TextField slotField;
    @FXML
    private TextField priceField;
    @FXML
    private TableView<Cash> moneyCollected;
    @FXML
    private TableColumn<Cash, Integer> phpCollected;
    @FXML
    private TableColumn<Cash, Integer> quantityCollected;
    private ObservableList<Cash> cashCollectedList = FXCollections.observableArrayList();
    @FXML
    private TableView<Cash> moneyAvailable;
    @FXML
    private TableColumn<Cash, Integer> phpAvailable;
    @FXML
    private TableColumn<Cash, Integer> quantityAvailable;
    private ObservableList<Cash> cashAvailableList = FXCollections.observableArrayList();
    @FXML
    private Label cashAvailable;
    @FXML
    private Label cashDisplay;
    @FXML
    private Label restockIndex;
    @FXML
    private Label lastRestock;
    @FXML
    private Label collectVal;
    @FXML
    private StackPane transactionHistoryPane; // The container for the TransactionHistory.fxml
    @FXML
    private Button showTransactionHistoryButton;
    private ArrayList<Button> money = new ArrayList<>();
    DataSingleton data = DataSingleton.getInstance();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    /**
     * Initializes the controller after its root element has been processed.
     * This method is automatically called by JavaFX during the initialization process.
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resource bundle for the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VM = data.getCurrentVM();
        slots = new Slot[VM.getItemSlots().length];
        for(int i = 0; i < VM.getItemSlots().length; i++){
            slots[i] = VM.getItemSlots()[i];
        }
        GridPane customGridPane = null;
        try {
            customGridPane = createCustomGridPane();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gridPaneContainer.getChildren().add(customGridPane);

        cashCollectedList.addAll(data.getCurrentVM().getCashAmount());
        phpCollected.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("DENOMINATION"));
        quantityCollected.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("Quantity"));
        moneyCollected.setItems(cashCollectedList);

        cashAvailableList.addAll(data.getCurrentVM().getCashAmount());
        phpAvailable.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("DENOMINATION"));
        quantityAvailable.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("Quantity"));
        moneyAvailable.setItems(cashAvailableList);

        updateDisplays();
    }

    /**
     * Creates a custom GridPane to display the vending machine item slots.
     * @return The created GridPane.
     * @throws IOException If an I/O error occurs while loading the SlotDisplay.fxml file.
     */
    private GridPane createCustomGridPane() throws IOException {
        gridPane = new GridPane();
        int numSlots = data.getCurrentVM().getItemSlots().length;
        float n = numSlots;
        if(n != 9) {
            while (n % 4 != 0) {
                n++;
            }
        }
        int nRows = (int)Math.ceil(Math.sqrt(n));
        int nCols = (int)Math.ceil(n/nRows);
        for (int col = 0; col < nCols; col++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            double colPercentWidth = 100.0 / nCols;
            columnConstraints.setPercentWidth(colPercentWidth);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
        for (int row = 0; row < nRows; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            double rowPercentHeight = 100.0 / nRows;
            rowConstraints.setPercentHeight(rowPercentHeight);
            gridPane.getRowConstraints().add(rowConstraints);
        }
        int ctr = 0;
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                if (ctr < VM.getItemSlots().length) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("SlotDisplay.fxml"));
                    HBox hBox = fxmlLoader.load();
                    SlotController slotController = fxmlLoader.getController();
                    slotController.setSlot(data.getCurrentVM().getItemSlots()[ctr]);
                    data.getCurrentVM().getItemSlots()[ctr].setController(slotController);
                    gridPane.add(hBox, col, row); // Swap columnIndex and rowIndex
                    ctr++;
                } else {
                    // Add empty panes or placeholders if there are no more slots
                    Pane emptyPane = new Pane();
                    gridPane.add(emptyPane, col, row);
                }
            }
        }
        // Set the preferred width and height of the GridPane dynamically
        double newPrefWidth = 465;
        double newPrefHeight = 494;
        gridPane.setPrefWidth(newPrefWidth);
        gridPane.setPrefHeight(newPrefHeight);
        gridPane.setMaxWidth(newPrefWidth);
        gridPane.setMaxHeight(newPrefHeight);

        StackPane.setAlignment(gridPane, Pos.CENTER); // Replace Pos.CENTER with the desired alignment
        gridPane.setStyle("-fx-grid-lines-visible: true;");
        return gridPane;
    }

    /**
     * Updates the TableView displaying the cash collected by the vending machine.
     */
    public void updateCashCollected() {
        cashCollectedList.clear(); // Clear the list
        cashCollectedList.addAll(data.getCurrentVM().getCashAmount()); // Add the updated data
        moneyCollected.refresh(); // Refresh the TableView
    }

    /**
     * Event handler for when a money button is pressed.
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void moneyPressed(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int value = Integer.parseInt(clickedButton.getText());
        Cash c = new Cash(value, 1);
        data.getCurrentVM().insertMoney(c);
        updateCash(value);
    }

    /**
     * Updates the cash display with the given input amount.
     * @param input The amount of cash to add to the current display value.
     */
    private void updateCash(int input) {
        int currentVal = Integer.parseInt(cashDisplay.getText());
        int newVal = currentVal + input;
        cashDisplay.setText(Integer.toString(newVal));
    }

    /**
     * Event handler for when any non-money button is pressed.
     * Updates the key display by appending the pressed button's text to the current display.
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void onButtonPressed(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();
        updateDisplay(buttonText);
    }

    /**
     * Updates the key display by appending the given input to the current text.
     * @param input The input to be appended to the current text in the key display.
     */
    private void updateDisplay(String input) {
        String currentText = keyDisplay.getText();
        keyDisplay.setText(currentText + input);
    }

    /**
     * Event handler for the "Backspace" button.
     * Removes the last character from the key display.
     * @param event The ActionEvent triggered by the "Backspace" button press.
     */
    @FXML
    public void pressedBackspace(ActionEvent event) {
        backSpace = (Button) event.getSource();
        String currentText = keyDisplay.getText();
        keyDisplay.setText(currentText.substring(0, currentText.length() - 1));
    }

    /**
     * Event handler for the "Enter" button.
     * Performs the vending machine transaction based on the entered slot number and dispenses the item if successful.
     * @param event The ActionEvent triggered by the "Enter" button press.
     * @throws IOException If an I/O error occurs while loading the ChangeDisplay.fxml file.
     */
    @FXML
    public void pressedEnter(ActionEvent event) throws IOException {
        float change = 0, price = 0;
        int slotNum = 0, index = 0;
        Item item = null;
        Transaction t = null;
        ArrayList<Cash> temp = VM.prepareMoney();
        temp = data.getCurrentVM().getCashInserted();
        ArrayList<Cash> Inserted = temp;
        ArrayList<Cash> Change = null;
        System.out.println("inserted: " + VM.calculateValue(Inserted));
        try {
            enter = (Button) event.getSource();
            slotNum = Integer.parseInt(keyDisplay.getText());
            index = data.getCurrentVM().findSlot(slotNum);
            if(index != -1){
            change = VM.calculateValue(data.getCurrentVM().getCashInserted()) - data.getCurrentVM().getItemSlots()[index].getItem().getPrice();
            price = data.getCurrentVM().getItemSlots()[index].getItem().getPrice();
            Change = VM.giveChange(price, data.getCurrentVM().getCashInserted());
            item = data.getCurrentVM().getItemSlots()[index].getItem();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            t = new Transaction(item, formattedDateTime, VM.getRestockCount(), Inserted, Change);
            }
            if (data.getCurrentVM().findSlot(slotNum) == -1)
                throw new InvalidSlotNumberException();
            else if (data.getCurrentVM().getItemSlots()[index].isEmpty())
                throw new EmptySlotException();
            else if (price > VM.calculateValue(data.getCurrentVM().getCashInserted()))
                throw new InsufficientCashException();
             else if (VM.calculateValue(data.getCurrentVM().getCashAmount()) < change)
             throw new InsufficientChangeException();
        } catch (InvalidSlotNumberException e) {
            showAlert(e.getMessage());
            return;
        } catch (EmptySlotException e) {
         showAlert(e.getMessage());
            return;
        } catch (InsufficientCashException e) {
         showAlert(e.getMessage());
            return;
        }catch (InsufficientChangeException e) {
         showAlert(e.getMessage());
            return;
         }
            this.itemDispensed = item;
            dispensedItem.setOpacity(1);
            dispensedItem.setDisable(false);
            data.getCurrentVM().addTransaction(t);
            data.getCurrentVM().organizeMoney(Inserted);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeDisplay.fxml"));
            Parent root = loader.load();
            ChangeController changeController = loader.getController();
            changeController.setChange(Change);
            Stage stage = new Stage();
            stage.setTitle("Dispensing Change...");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            keyDisplay.setText("");
            cashDisplay.setText("0000");
            data.getCurrentVM().setCashInserted(VM.prepareMoney());
        VM.getItemSlots()[index].dispenseItem();
        VM.getItemSlots()[index].getSlotController().updateSlotDetails();
        updateDisplays();
    }

    /**
     * Event handler for the "Get Item" button.
     * Opens the DispenseItem.fxml window and displays the dispensed item details.
     * @param event The ActionEvent triggered by the "Get Item" button press.
     * @throws IOException If an I/O error occurs while loading the DispenseItem.fxml file.
     */
    @FXML
    public void getItem(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DispenseItem.fxml"));
        Parent root = loader.load();
        DispenseItemController dispenseItemController = loader.getController();
        dispenseItemController.setItem(this.itemDispensed);
        Stage stage = new Stage();
        stage.setTitle("Dispensing Item...");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        dispenseItemController.updateDisplay(); // Call updateDisplay to update the UI
        dispensedItem.setOpacity(0);
        dispensedItem.setDisable(true);
    }

    /**
     * Event handler for the "End Transaction" button.
     * Resets the cash display and shows the change display with the remaining cash.
     * @param event The ActionEvent triggered by the "End Transaction" button press.
     * @throws IOException If an I/O error occurs while loading the ChangeDisplay.fxml file.
     */
    @FXML
    public void endTransaction(javafx.event.ActionEvent event) throws IOException {
        cashDisplay.setText("0000");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeDisplay.fxml"));
        Parent root = loader.load();
        ChangeController changeController = loader.getController();
        changeController.setChange(data.getCurrentVM().getCashInserted());

        Stage stage = new Stage();
        stage.setTitle("Dispensing Money...");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        data.getCurrentVM().setCashInserted(VM.prepareMoney());
    }

    /**
     * Event handler for the "Back" button.
     * Returns to the TestMenu.fxml view.
     * @param event The ActionEvent triggered by the "Back" button press.
     * @throws IOException If an I/O error occurs while loading the TestMenu.fxml file.
     */
    @FXML
    public void back(javafx.event.ActionEvent event)throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TestMenu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Event handler for the "Change Item" button.
     * Opens the ChangeItem.fxml window for changing item details.
     * @param event The ActionEvent triggered by the "Change Item" button press.
     */
    @FXML
    public void changeItem(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeItem.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Change Item");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for the "Restock Item" button.
     * Opens the RestockItem.fxml window for restocking the vending machine items.
     * @param event The ActionEvent triggered by the "Restock Item" button press.
     */
    @FXML
    public void restockItem(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RestockItem.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Restock Item");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        lastRestock.setText(formattedDateTime);
    }

    /**
     * Displays an alert with the given error message.
     * @param message The error message to be displayed in the alert.
     */
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Event handler for the "Set New Price" button.
     * Updates the price of an item in the vending machine based on the entered slot number and new price.
     * @param event The ActionEvent triggered by the "Set New Price" button press.
     */
    @FXML
    public void setNewPrice(ActionEvent event) {
        int slotnum = 0;
        float price = 0;
        int index = 0;
        try {
            slotnum = Integer.parseInt(slotField.getText());
            price = Float.parseFloat(priceField.getText());
            index = data.getCurrentVM().findSlot(slotnum);
            if (index == -1) {
                showAlert("Invalid slot number.");
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        } finally {
            // Update the price only if the slot number is valid
            VM.getItemSlots()[index].getItem().setPrice(price);
            VM.getItemSlots()[index].getSlotController().updateSlotDetails();
            priceField.setText("");
            slotField.setText("");
        }
    }

    /**
     * Event handler for the "Replenish Money" button.
     * Opens the ReplenishMoney.fxml window for replenishing the vending machine's cash.
     * @param event The ActionEvent triggered by the "Replenish Money" button press.
     */
    @FXML
    public void replenishMoney(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReplenishMoney.fxml"));
            Parent root = loader.load();
            ReplenishMoneyController controller = loader.getController();
            controller.setVMController(this);
            Stage stage = new Stage();
            stage.setTitle("Replenish Money");
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        float val = VM.calculateValue(data.getCurrentVM().getCashAmount());
        cashAvailable.setText(Float.toString(val));
        updateDisplays();
    }

    /**
     * Updates the displays showing the cash collected and available cash in the vending machine.
     */
    public void updateDisplays() {
        lastRestock.setText(data.getLastRVMrestock());
        restockIndex.setText(Integer.toString(data.getCurrentVM().getRestockCount()));

        // After updating the cash collected data
        cashCollectedList.clear();
        cashCollectedList.addAll(data.getCurrentVM().getCashAmount());

        // After updating the available cash data
        cashAvailableList.clear();
        cashAvailableList.addAll(data.getCurrentVM().getCashAmount());

        moneyCollected.refresh();
        moneyAvailable.refresh();

        restockIndex.setText(Integer.toString(data.getCurrentVM().getRestockCount()));
        collectVal.setText(Float.toString(data.getCurrentVM().calculateValue(data.getCurrentVM().getCashAmount())));
        cashAvailable.setText(Float.toString(data.getCurrentVM().calculateValue(data.getCurrentVM().getCashAmount())));
    }

    /**
     * Event handler for the "Show Transactions" button.
     * Opens the TransactionHistory.fxml window to display the vending machine's transaction history.
     * @param event The ActionEvent triggered by the "Show Transactions" button press.
     */
    @FXML
    public void showTransactions(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionHistory.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Transactions");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for the "Collect Money" button.
     * Resets the cash collected and updates the displays.
     * @param event The ActionEvent triggered by the "Collect Money" button press.
     * @throws IOException If an I/O error occurs while updating the displays.
     */
    @FXML
    public void collectMoney(javafx.event.ActionEvent event) throws IOException {
        data.getCurrentVM().resetCashCollected();
        updateDisplays();
    }
}