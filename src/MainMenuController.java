import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class is a controller for the Main Menu View in the vending machine application.
 * It handles the actions and transitions to different views when buttons are clicked.
 */
public class MainMenuController {
    private Scene scene;
    private Parent root;
    @FXML
    private Button exitButton;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    private Stage stage;

    /**
     * Initializes the MainMenuController.
     * This method is automatically called by JavaFX after loading the FXML file.
     */
    @FXML
    void initialize() {
    }

    /**
     * Handles the action when the "Create" button is clicked.
     * It loads the Create Menu View and switches to that scene.
     * @param actionEvent The ActionEvent triggered by clicking the "Create" button.
     * @throws IOException If an error occurs while loading the Create Menu View.
     */
    public void create(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateMenu.fxml"));
        root = loader.load();
        CreateMenuController createMenuController = loader.getController();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the action when the "Exit" button is clicked.
     * It closes the current stage and exits the program.
     * @param actionEvent The ActionEvent triggered by clicking the "Exit" button.
     * @throws IOException If an error occurs while closing the stage.
     */
    public void exit(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        System.out.println("Exiting program...");
        stage.close();
    }

    /**
     * Handles the action when the "Test" button is clicked.
     * It loads the Test Menu View and switches to that scene.
     * @param actionEvent The ActionEvent triggered by clicking the "Test" button.
     * @throws IOException If an error occurs while loading the Test Menu View.
     */
    public void test(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TestMenu.fxml"));
        root = loader.load();
        TestMenuController testMenuController = loader.getController();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
