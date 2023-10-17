import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * TransactionHistoryController is the controller class for the transaction history view in the Special Vending Machine (SVM) application.
 * It displays a list of transaction items, each showing the details of a specific transaction.
 */
public class TransactionHistoryController implements Initializable {
    @FXML
    private HBox transactionContainer;
    private ArrayList<Transaction> transactions;
    DataSingleton data = DataSingleton.getInstance();

    /**
     * Initializes the TransactionHistoryController.
     * This method is automatically called by JavaFX after loading the FXML file.
     * Loads the transaction items into the HBox to display the transaction history.
     * Each transaction item is loaded from "TransactionItem.fxml" using FXMLLoader.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object
     */
    // Method to initialize the transactions in the ScrollPane
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Clear the HBox to ensure no duplicate entries
            transactionContainer.getChildren().clear();
            // Load "TransactionItem.fxml" for each transaction and add it to the HBox
            if (data.getRVMCurrent()) {
                for (int i = 0; i < data.getCurrentVM().getTransactions().size(); i++) {
                    Transaction t = data.getCurrentVM().getTransactions().get(i);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionDisplay.fxml"));
                    HBox transactionItem = loader.load();
                    TransactionController transactionController = loader.getController();
                    transactionController.setTransaction(t, i + 1);
                    transactionContainer.getChildren().add(transactionItem);
                }
            } else {
                for (int i = 0; i < data.getCurrentSVM().getTransactions().size(); i++) {
                    Transaction t = data.getCurrentSVM().getTransactions().get(i);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionDisplay.fxml"));
                    HBox transactionItem = loader.load();
                    TransactionController transactionController = loader.getController();
                    transactionController.setTransaction(t, i + 1);
                    transactionContainer.getChildren().add(transactionItem);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
