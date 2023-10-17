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
 * This class is a controller for the Create Menu View in the vending machine application.
 * It allows the user to choose between creating a regular vending machine or a special vending machine.
 */
public class CreateMenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    /**
     * Initializes the CreateMenuController.
     * This method is automatically called by JavaFX after loading the FXML file.
     */
    @FXML
    void initialize() {}

    /**
     * Loads the Create Regular Vending Machine View and displays it.
     * @param actionEvent The ActionEvent triggered by the "Create Regular Vending Machine" button.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    public void createRegular(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateRVM.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the Create Special Vending Machine View and displays it.
     * @param actionEvent The ActionEvent triggered by the "Create Special Vending Machine" button.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    public void createSpecial(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateSVM.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the Main Menu View and displays it.
     * @param event The ActionEvent triggered by the "Back" button.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    public void back(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
