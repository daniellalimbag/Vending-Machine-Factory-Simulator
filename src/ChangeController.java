import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class is a controller for the Change View in the vending machine application.
 * It manages the display of the change returned to the user after a transaction.
 */
public class ChangeController implements Initializable {
    @FXML
    private TableView<Cash> cashTableView;
    @FXML
    private TableColumn<Cash, Integer> php;
    @FXML
    private TableColumn<Cash, Integer> quantity;
    private ObservableList<Cash> cashList = FXCollections.observableArrayList();
    private ArrayList<Cash> change;
    @FXML
    private Label total;
    DataSingleton data = DataSingleton.getInstance();

    /**
     * Sets the change (ArrayList of Cash) to be displayed in the TableView.
     *
     * @param c ArrayList of Cash representing the change returned to the user.
     */
    public void setChange(ArrayList<Cash> c) {
        this.change = c;
        updateCashList(); // Update the cashList with the new data
    }

    /**
     * Initializes the ChangeController and sets up the TableView and TableColumns.
     * This method is automatically called by JavaFX after loading the FXML file.
     *
     * @param url            The location used to resolve relative paths for the root object,
     *                       or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object,
     *                       or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        php.setCellValueFactory(new PropertyValueFactory<Cash, Integer>("DENOMINATION"));
        quantity.setCellValueFactory(new PropertyValueFactory<Cash, Integer>("Quantity"));
        cashTableView.setItems(cashList);
        updateTotal();
    }

    /**
     * Method to update the cashList (ObservableList) based on the change ArrayList.
     * Clears the cashList and adds the new change data to the list.
     * It then updates the total label when cashList is updated.
     */
    private void updateCashList() {
        cashList.clear();
        if (change != null) {
            cashList.addAll(change);
        }
        updateTotal(); // Update the total label when cashList is updated
    }

    /**
     * Method to update the total label based on the current cashList.
     * It calculates the total value of the change and sets it to the total label.
     */
    private void updateTotal() {
        if (change != null) {
            String totalValue = Float.toString(VendingMachine.calculateValue(change));
            total.setText(totalValue);
        }
    }
}
