import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * TransactionController is the controller class for the transaction details view in the Special Vending Machine (SVM) application.
 * It displays the details of a transaction, including the item purchased, cash inserted, and change given.
 */
public class TransactionController implements Initializable {
    private Transaction transaction;
    @FXML
    private TableView<Cash> moneyInsertedView;
    @FXML
    private TableView<Cash> changeView;
    @FXML
    private TableColumn<Cash, Integer> pesosInserted;
    @FXML
    private TableColumn<Cash, Integer> quantityInserted;
    @FXML
    private TableColumn<Cash, Integer> pesosChange;
    @FXML
    private TableColumn<Cash, Integer> quantityChange;

    @FXML
    private Label itemName;
    @FXML
    private Label insertedVal;
    @FXML
    private Label changeVal;
    @FXML
    private Label transactionNum;
    @FXML
    private Label date;
    DataSingleton data = DataSingleton.getInstance();
    private ArrayList<Cash> cashInserted;
    private ArrayList<Cash> changeArr;
    private ObservableList<Cash> cashObservableList = FXCollections.observableArrayList();
    private ObservableList<Cash> changeObservableList = FXCollections.observableArrayList();

    /**
     * Sets the transaction details to be displayed in the view.
     * @param t The Transaction object containing the transaction details.
     * @param index The index of the transaction in the transaction history.
     */
    public void setTransaction(Transaction t, int index){
        if(data.getRVMCurrent()) {
            this.transaction = t;
            this.changeArr = transaction.getChange();
            this.cashInserted = transaction.getCashInserted();
            this.transactionNum.setText("#" + index);
            cashObservableList.clear();
            cashObservableList.addAll(cashInserted);
            changeObservableList.clear();
            changeObservableList.addAll(changeArr);
            itemName.setText(transaction.getItem().getNAME());
            insertedVal.setText(Float.toString(data.getCurrentVM().calculateValue(cashInserted)));
            changeVal.setText(Float.toString(data.getCurrentVM().calculateValue(changeArr)));
            date.setText(transaction.getDate());
            updateLists();
        }
        else {
            this.transaction = t;
            this.changeArr = transaction.getChange();
            this.cashInserted = transaction.getCashInserted();
            this.transactionNum.setText("#" + index);
            cashObservableList.clear();
            cashObservableList.addAll(cashInserted);
            changeObservableList.clear();
            changeObservableList.addAll(changeArr);
            itemName.setText(transaction.getItem().getNAME());
            insertedVal.setText(Float.toString(data.getCurrentSVM().calculateValue(cashInserted)));
            changeVal.setText(Float.toString(data.getCurrentSVM().calculateValue(changeArr)));
            date.setText(transaction.getDate());
            updateLists();
        }
    }

    /**
     * Initializes the TransactionController.
     * This method is automatically called by JavaFX after loading the FXML file.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pesosInserted.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("DENOMINATION"));
        quantityInserted.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("Quantity"));
        pesosChange.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("DENOMINATION"));
        quantityChange.setCellValueFactory(new PropertyValueFactory<Cash,Integer>("Quantity"));
        moneyInsertedView.setItems(cashObservableList);
        changeView.setItems(changeObservableList);

        // Initialize the lists to empty ArrayLists
        cashInserted = new ArrayList<>();
        changeArr = new ArrayList<>();
        updateLists();
    }


    /**
     * Updates the displayed cash inserted and change given lists with the latest data.
     * This method is called whenever there are changes to the cashInserted or changeArr fields.
     */
    private void updateLists() {
        cashObservableList.clear();
        if (cashInserted != null) {
            cashObservableList.addAll(cashInserted);
        }
        changeObservableList.clear();
        if (changeArr != null) {
            changeObservableList.addAll(changeArr);
        }
        updateTotal(); // Update the total label when cashList is updated
    }

    /**
     * Updates the total values displayed for cash inserted and change given.
     * This method calculates the total values and updates the corresponding labels.
     * It is called whenever there are changes to the cashInserted or changeArr fields.
     */
    private void updateTotal(){
        if(data.getRVMCurrent()) {
            if (cashInserted != null) {
                String totalValue = Float.toString(data.getCurrentVM().calculateValue(cashInserted));
                insertedVal.setText(totalValue);
            }
            if (changeArr != null) {
                String totalValue = Float.toString(data.getCurrentVM().calculateValue(changeArr));
                changeVal.setText(totalValue);
            }
        }
        else{
            if (cashInserted != null) {
                String totalValue = Float.toString(data.getCurrentSVM().calculateValue(cashInserted));
                insertedVal.setText(totalValue);
            }
            if (changeArr != null) {
                String totalValue = Float.toString(data.getCurrentSVM().calculateValue(changeArr));
                changeVal.setText(totalValue);
            }
        }
    }
}
