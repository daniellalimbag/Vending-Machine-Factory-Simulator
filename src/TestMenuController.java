import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * TestMenuController is the controller class for the test menu of the Special Vending Machine (SVM) application.
 * It manages the user interface and navigation between different test scenarios.
 */
public class TestMenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    /**
     * Initializes the TestMenuController.
     * This method is automatically called by JavaFX after loading the FXML file.
     */
    @FXML
    void initialize() {
    }

    /**
     * Handles button click events when the user selects the "Test RVM" option.
     * This method loads the test scenario for the Regular Vending Machine (RVM).
     * @param actionEvent The ActionEvent triggered by clicking the "Test RVM" button.
     * @throws IOException If there is an error loading the TestRVM view.
     */
    public void testRVM(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TestRVM.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles button click events when the user selects the "Test SVM" option.
     * This method loads the test scenario for the Special Vending Machine (SVM).
     * @param actionEvent The ActionEvent triggered by clicking the "Test SVM" button.
     * @throws IOException If there is an error loading the TestSVM view.
     */
    public void testSVM(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TestSVM.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles button click events when the user navigates back to the main menu.
     * This method loads the main menu view.
     * @param event The ActionEvent triggered by clicking the "Back" button.
     * @throws IOException If there is an error loading the main menu view.
     */
    public void back(javafx.event.ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
