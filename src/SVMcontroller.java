import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
/**
 * The SVMcontroller class is a controller for the Special Vending Machine (SVM) application.
 * It handles the user interface and interactions with the SVM, allowing users to select items,
 * insert money, and perform various transactions.
 */
public class SVMcontroller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private SpecialVendingMachine VM;
    private Slot[] slots;
    private Slot[] addOnSlots;
    @FXML
    private HBox slotContainer;
    @FXML
    private HBox addOnSlotContainer;
    @FXML
    private VBox productContainer;
    @FXML
    private Label keyDisplay;
    @FXML
    private Label priceDisplay;
    @FXML
    private GridPane keyPad;
    @FXML
    private Pane input;
    @FXML
    private Item itemDispensed;
    @FXML
    private Button dispensedItem;
    @FXML
    private ImageView customizedProduct;
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
    private GridPane gridPane;
    @FXML
    private AnchorPane gridPaneContainer;
    @FXML
    private TableView<Item> recipeView;
    @FXML
    private TableColumn<Item, String> itemName;
    @FXML
    private TableColumn<Item, Float> itemPrice;
    @FXML
    private TableColumn<Item, Float> itemCalories;
    private ObservableList<Item> recipeItemList = FXCollections.observableArrayList();
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
    private ArrayList<Item> itemList = new ArrayList<>();
    private ArrayList<Item> products = new ArrayList<>();
    DataSingleton data = DataSingleton.getInstance();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    /**
     * Initializes the SVMcontroller.
     * This method is automatically called by JavaFX after loading the FXML file.
     *
     * @param url            The URL used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VM = data.getCurrentSVM();
        VM.setController(this);
        itemList = VM.getOrder();
        slots = new Slot[data.getCurrentSVM().getItemSlots().length];
        for (int i = 0; i < VM.getItemSlots().length; i++) {
            slots[i] = VM.getItemSlots()[i];
        }
        data.getCurrentSVM().addOnSellableStatus();
        GridPane customGridPane = null;
        try {
            customGridPane = createCustomGridPane();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gridPaneContainer.getChildren().add(customGridPane);
        displayAddOns();
        displayProducts();

        recipeItemList.addAll(itemList);
        itemName.setCellValueFactory(new PropertyValueFactory<Item,String>("NAME"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<Item,Float>("price"));
        itemCalories.setCellValueFactory(new PropertyValueFactory<Item,Float>("calories"));
        recipeView.setItems(recipeItemList);

        cashCollectedList.addAll(data.getCurrentSVM().getCashAmount());
        phpCollected.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("DENOMINATION"));
        quantityCollected.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("Quantity"));
        moneyCollected.setItems(cashCollectedList);

        cashAvailableList.addAll(data.getCurrentSVM().getCashAmount());
        phpAvailable.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("DENOMINATION"));
        quantityAvailable.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("Quantity"));
        moneyAvailable.setItems(cashAvailableList);

        updateDisplays();
        updateCashCollected();
    }

    /**
     * Creates a custom GridPane for displaying item slots in the SVM interface.
     *
     * @return The created custom GridPane.
     * @throws IOException If an I/O error occurs while creating the custom GridPane.
     */
    private GridPane createCustomGridPane() throws IOException {
        gridPane = new GridPane();
        int numSlots = data.getCurrentSVM().getItemSlots().length;
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
                    slotController.setSlot(data.getCurrentSVM().getItemSlots()[ctr]);
                    data.getCurrentSVM().getItemSlots()[ctr].setController(slotController);
                    gridPane.add(hBox, col, row); // Swap columnIndex and rowIndex
                    ctr++;
                } else {
                    // Add empty panes or placeholders if there are no more slots
                    Pane emptyPane = new Pane();
                    gridPane.add(emptyPane, col, row);
                }
            }
        }

        // If needed, you can also change the StackPane's alignment to resize the GridPane within the StackPane
        StackPane.setAlignment(gridPane, Pos.CENTER); // Replace Pos.CENTER with the desired alignment
        gridPane.setStyle("-fx-grid-lines-visible: true;");
        return gridPane;
    }

    /**
     * Displays the add-ons available in the SVM interface.
     */
    public void displayAddOns() {
        addOnSlots = new Slot[data.getCurrentSVM().getAddOnSlots().length];
        for (int i = 0; i < data.getCurrentSVM().getAddOnSlots().length; i++) {
            addOnSlots[i] = data.getCurrentSVM().getAddOnSlots()[i];
        }
        try {
            addOnSlotContainer.getChildren().clear(); // Clear existing add-ons if any
            for (int i = 0; i < addOnSlots.length; i++) {
                Slot a = data.getCurrentSVM().getAddOnSlots()[i];
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SlotDisplay.fxml"));
                HBox hBox = loader.load();
                SlotController slotController = loader.getController();
                slotController.setSlot(a);
                addOnSlotContainer.getChildren().add(hBox); // Add the add-on to addOnSlotContainer
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the products available in the SVM interface.
     */
    public void displayProducts() {
        products = new ArrayList<>();
        for (int i = 0; i <data.getCurrentSVM().getProducts().size(); i++) {
            products.add(data.getCurrentSVM().getProducts().get(i));
        }
        try {
            productContainer.getChildren().clear();
            for (int i = 0; i < data.getCurrentSVM().getProducts().size(); i++) {
                Item a = data.getCurrentSVM().getProducts().get(i);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductDisplay.fxml"));
                VBox vBox = loader.load();
                ProductController productController = loader.getController();
                productController.setItem(a);
                productContainer.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the customized product image displayed in the SVM interface.
     * @param product The selected product for customization.
     */
    public void updateCustomProduct(Item product) {
        String itemName = product.getNAME().trim();
        String imageUrl = "/image/food/" + itemName + ".png";
        Image image;
        try {
            image = new Image(imageUrl);
        } catch (Exception e) {
            // If the URL is invalid or the image doesn't exist, set the default image (questionmark.png)
            image = new Image("/image/food/questionmark.png");
        }
        // Set the new image to the customizedProduct ImageView
        customizedProduct.setImage(image);
    }

    /**
     * Updates the list of cash collected and refreshes the TableView in the SVM interface.
     */
    public void updateCashCollected() {
        cashCollectedList.clear(); // Clear the list
        cashCollectedList.addAll(data.getCurrentSVM().getCashAmount()); // Add the updated data
        moneyCollected.refresh(); // Refresh the TableView
    }
    /**
     * Handles the event when the user presses a button representing money denomination.
     * @param event The ActionEvent triggered by pressing a money button.
     */
    @FXML
    public void moneyPressed(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int value = Integer.parseInt(clickedButton.getText());
        Cash c = new Cash(value, 1);
        data.getCurrentSVM().insertMoney(c);
        updateCash(value);
    }

    /**
     * Updates the cash display with the new input amount.
     * @param input The integer value of the cash input.
     */
    private void updateCash(int input) {
        int currentVal = Integer.parseInt(cashDisplay.getText());
        int newVal = currentVal + input;
        cashDisplay.setText(Integer.toString(newVal));
    }
    /**
     * Handles the event when the user presses a numeric button on the SVM interface.
     * @param event The ActionEvent triggered by pressing a numeric button.
     */
    @FXML
    public void onButtonPressed(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();
        updateDisplay(buttonText);
    }

    /**
     * Updates the display with the input string.
     * @param input The string input to be added to the display.
     */
    private void updateDisplay(String input) {
        String currentText = keyDisplay.getText();
        keyDisplay.setText(currentText + input);
    }
    /**
     * Handles the event when the user presses the backspace button to remove the last digit from the display.
     * @param event The ActionEvent triggered by pressing the backspace button.
     */
    @FXML
    public void pressedBackspace(ActionEvent event) {
        backSpace = (Button) event.getSource();
        String currentText = keyDisplay.getText();
        keyDisplay.setText(currentText.substring(0, currentText.length() - 1));
    }

    /**
     * Handles the event when the user presses the confirm button to process a transaction.
     * @param event The ActionEvent triggered by pressing the confirm button.
     * @throws IOException If an I/O error occurs while displaying the change or item dispensing UI.
     */
    @FXML
    public void pressedConfirm(ActionEvent event) throws IOException {
        float price = 0;
        float change = 0;
        Transaction t = null;
        ArrayList<Cash> temp = VM.prepareMoney();
        temp = data.getCurrentSVM().getCashInserted();
        ArrayList<Cash> Inserted = temp;
        ArrayList<Cash> Change = null;
        try {
            boolean inStock = true;
            for (int i = 0; i < data.getCurrentSVM().getOrder().size(); i++) {
                if (data.getCurrentSVM().findSlot(data.getCurrentSVM().getOrder().get(i)).isEmpty()) {
                    inStock = false;
                }
            }
            price = 0;
            if (data.getCurrentSVM().getOrder().size() > 1) {
                for (int i = 0; i < data.getCurrentSVM().getOrder().size(); i++) {
                    price += data.getCurrentSVM().getOrder().get(i).getPrice();
                }
            }
                if (data.getCurrentSVM().findDish() != null)
                    if (!inStock)
                        throw new EmptySlotException();
                    else if (price > VendingMachine.calculateValue(data.getCurrentSVM().getCashInserted()))
                        throw new InsufficientCashException();
                    else if (VendingMachine.calculateValue(data.getCurrentSVM().getCashAmount()) < change)
                        throw new InsufficientChangeException();
        }catch (EmptySlotException e) {
            showAlert(e.getMessage());
            return;
        } catch (InsufficientCashException e) {
            showAlert(e.getMessage());
            return;
        } catch (InsufficientChangeException e) {
            showAlert(e.getMessage());
            return;
        }
            //Decrement quantity of every ingredient
            for(int i = 0; i < data.getCurrentSVM().getOrder().size(); i++){
                Slot slot = data.getCurrentSVM().findSlot(data.getCurrentSVM().getOrder().get(i));
                slot.dispenseItem();
            }
            data.getCurrentSVM().getOrder().clear();

            //saving transaction and dispensing change when buying dish
            Item item = data.getCurrentSVM().getRecentProduct();

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            Change = VM.giveChange(price, data.getCurrentSVM().getCashInserted());
        t = new Transaction(item, formattedDateTime, VM.getRestockCount(), Inserted, Change);
        data.getCurrentSVM().addTransaction(t);
        data.getCurrentSVM().organizeMoney(Inserted);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeDisplay.fxml"));
            Parent root = loader.load();
            ChangeController changeController = loader.getController();
            changeController.setChange(Change);
            Stage stage = new Stage();
            stage.setTitle("Dispensing Change...");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            cashDisplay.setText("0000");
            data.getCurrentSVM().setCashInserted(VM.prepareMoney());
            data.getCurrentSVM().setRecentProduct(null);
        keyDisplay.setText("");
        updateDisplays();
    }

    /**
     * Handles the event when the user presses the enter button to select an item based on the slot number.
     * @param event The ActionEvent triggered by pressing the enter button.
     * @throws IOException If an I/O error occurs while displaying the change or item dispensing UI.
     */
    @FXML
    public void pressedEnter(ActionEvent event) throws IOException {
        float change = 0, price = 0;
            int slotNum = 0, index = 0;
            Item item = null;
            Transaction t = null;
            ArrayList<Cash> temp = VM.prepareMoney();
            temp = data.getCurrentSVM().getCashInserted();
            ArrayList<Cash> Inserted = temp;
            ArrayList<Cash> Change = null;
            try {
                enter = (Button) event.getSource();
                slotNum = Integer.parseInt(keyDisplay.getText());
                index = data.getCurrentSVM().findSlot(slotNum);
                item = data.getCurrentSVM().findItem(slotNum);
                if(item != null)
                    change = VendingMachine.calculateValue(data.getCurrentSVM().getCashAmount()) - item.getPrice();
                if (data.getCurrentSVM().findSlot(slotNum) == -1)
                        throw new InvalidSlotNumberException();
                    else if (data.getCurrentSVM().getItemSlots()[index].isEmpty())
                        throw new EmptySlotException();
                    else if (item.getPrice() > VendingMachine.calculateValue(data.getCurrentSVM().getCashInserted()) && data.getCurrentSVM().getOrder().size()==0)
                        throw new InsufficientCashException();
                    else if (VendingMachine.calculateValue(data.getCurrentSVM().getCashAmount()) < change)
                        throw new InsufficientChangeException();
                    else if (item.isaddOn() && data.getCurrentSVM().getOrder().size()==0)
                        throw new NotSellableException();
            } catch (InvalidSlotNumberException e) {
                showAlert(e.getMessage());
                return;
            } catch (EmptySlotException e) {
                showAlert(e.getMessage());
                return;
            } catch (InsufficientCashException e) {
                showAlert(e.getMessage());
                return;
            } catch (InsufficientChangeException e) {
                showAlert(e.getMessage());
                return;
            } catch (NotSellableException e) {
                showAlert(e.getMessage());
                return;
            }
            /*
             dispensedItem.setOpacity(1);
             dispensedItem.setDisable(false);**/
            if(!keyDisplay.getText().equalsIgnoreCase("")) {
                if (data.getCurrentSVM().getOrder().size() >= 1) {
                    data.getCurrentSVM().addToOrder(item);
                } else {
                    price = item.getPrice();
                    Change = VM.giveChange(price, data.getCurrentSVM().getCashInserted());
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedDateTime = now.format(formatter);
                    t = new Transaction(item, formattedDateTime, VM.getRestockCount(), Inserted, Change);
                    data.getCurrentSVM().addTransaction(t);
                    data.getCurrentSVM().organizeMoney(Inserted);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeDisplay.fxml"));
                    Parent root = loader.load();
                    ChangeController changeController = loader.getController();
                    changeController.setChange(Change);
                    Stage stage = new Stage();
                    stage.setTitle("Dispensing Change...");
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            keyDisplay.setText("");
            if(data.getCurrentSVM().getOrder().size() == 0) {
                cashDisplay.setText("0000");
                data.getCurrentSVM().setCashInserted(VM.prepareMoney());
                VM.getItemSlots()[index].dispenseItem();
                VM.getItemSlots()[index].getSlotController().updateSlotDetails();
                updateDisplays();
            }
        }


    /**
     * Handles the event when the user presses the "Dispense Item" button to view the dispensed item details.
     * @param event The ActionEvent triggered by pressing the "Dispense Item" button.
     * @throws IOException If an I/O error occurs while displaying the dispensed item UI.
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
     * Handles the event when the user presses the "End Transaction" button to end the current transaction.
     * @param event The ActionEvent triggered by pressing the "End Transaction" button.
     * @throws IOException If an I/O error occurs while displaying the change dispensing UI.
     */
    @FXML
    public void endTransaction(javafx.event.ActionEvent event) throws IOException {
        cashDisplay.setText("0000");
        if(VM.calculateValue(data.getCurrentSVM().getCashInserted()) > 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeDisplay.fxml"));
            Parent root = loader.load();
            ChangeController changeController = loader.getController();
            changeController.setChange(data.getCurrentSVM().getCashInserted());

            Stage stage = new Stage();
            stage.setTitle("Dispensing Money...");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            data.getCurrentSVM().setCashInserted(VM.prepareMoney());
        }
        data.getCurrentSVM().getOrder().clear();
        recipeItemList.clear();
        recipeView.refresh();
        customizedProduct.setImage(null);
        updateDisplays();
    }

    /**
     * Handles the event when the user presses the "Back" button to return to the main SVM interface.
     *
     * @param event The ActionEvent triggered by pressing the "Back" button.
     * @throws IOException If an I/O error occurs while loading the main SVM interface.
     */
    @FXML
    public void back(javafx.event.ActionEvent event)throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TestSVM.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the event when the user presses the "Change Item" button to change an item's details.
     * @param event The ActionEvent triggered by pressing the "Change Item" button.
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
     * Handles the event when the user presses the "Restock Item" button to restock an item.
     * @param event The ActionEvent triggered by pressing the "Restock Item" button.
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
    }

    /**
     * Displays an alert with the given message.
     * @param message The message to be displayed in the alert.
     */
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the event when the user sets a new price for an item.
     * @param event The ActionEvent triggered by pressing the "Set New Price" button.
     */
    @FXML
    public void setNewPrice(ActionEvent event) {
        int slotnum = 0;
        float price = 0;
        int index = 0;
        try {
            slotnum = Integer.parseInt(slotField.getText());
            price = Float.parseFloat(priceField.getText());
            index = data.getCurrentSVM().findSlot(slotnum);
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
     * Handles the event when the user wants to replenish the money in the SVM.
     * @param event The ActionEvent triggered by pressing the "Replenish Money" button.
     */
    @FXML
    public void replenishMoney(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReplenishMoney.fxml"));
            Parent root = loader.load();
            ReplenishMoneyController controller = loader.getController();
            controller.setSVMController(this);
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
        float val = VM.calculateValue(data.getCurrentSVM().getCashAmount());
        cashAvailable.setText(Float.toString(val));
        updateDisplays();
    }

    /**
     * Updates the displays showing the recipe items, cash collected, and available cash.
     */
    public void updateDisplays() {
        int price = 0;
        if (data.getCurrentSVM().getOrder().size() > 1) {
            for (int i = 0; i < data.getCurrentSVM().getOrder().size(); i++) {
                price += data.getCurrentSVM().getOrder().get(i).getPrice();
            }
        }
        if(price > 0)
            priceDisplay.setText(Integer.toString(price));
        else
            priceDisplay.setText("0000");

        recipeItemList.clear();
        recipeItemList.addAll(itemList);
        recipeView.refresh();
        lastRestock.setText(data.getLastSVMrestock());
        restockIndex.setText(Integer.toString(data.getCurrentSVM().getRestockCount()));

        // After updating the cash collected data
        cashCollectedList.clear();
        cashCollectedList.addAll(data.getCurrentSVM().getCashAmount());

        // After updating the available cash data
        cashAvailableList.clear();
        cashAvailableList.addAll(data.getCurrentSVM().getCashAmount());

        moneyCollected.refresh();
        moneyAvailable.refresh();

        restockIndex.setText(Integer.toString(data.getCurrentSVM().getRestockCount()));
        collectVal.setText(Float.toString(data.getCurrentSVM().calculateValue(data.getCurrentSVM().getCashAmount())));
        cashAvailable.setText(Float.toString(data.getCurrentSVM().calculateValue(data.getCurrentSVM().getCashAmount())));
    }

    /**
     * Handles the event when the user wants to view the transaction history.
     * @param event The ActionEvent triggered by pressing the "Show Transactions" button.
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
     * Handles the event when the user wants to collect the money accumulated in the SVM.
     *
     * @param event The ActionEvent triggered by pressing the "Collect Money" button.
     * @throws IOException If an I/O error occurs while displaying the change dispensing UI.
     */
    @FXML
    public void collectMoney(javafx.event.ActionEvent event) throws IOException {
        data.getCurrentSVM().resetCashCollected();
        updateDisplays();
    }
}