import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

/**
 * TestSVMController is the controller class for the test menu of the Special Vending Machine (SVM) application.
 * It allows users to select a pre-defined SVM or a custom SVM for testing purposes.
 */
public class TestSVMController{
    private Parent root;
    private Scene scene;
    private Stage stage;
    private ArrayList<SplitPane> paneList;
    @FXML
    private Button delete;
    @FXML
    private Button test;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label VMname;
    @FXML
    private Pane gridPaneContainer;
    @FXML
    DataSingleton data = DataSingleton.getInstance();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    /**
     * Initializes the TestSVMController.
     * This method is automatically called by JavaFX after loading the FXML file.
     */
    @FXML
    public void initialize(){
        if(data.getCustomSVM()!=null)
            VMname.setText(data.getCustomSVM().getNAME());
    }

    /**
     * Handles button click events when the user navigates back to the main menu.
     * This method loads the main menu view
     * @param event The ActionEvent triggered by clicking the "Back" button.
     * @throws IOException If there is an error loading the main menu view.
     */
    @FXML
    public void back(javafx.event.ActionEvent event)throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles button click events when the user selects the "Pre-Defined Test" option.
     * This method loads the predefined SVM for testing purposes and displays its details.
     * @param actionEvent The ActionEvent triggered by clicking the "Pre-Defined Test" button.
     */
    @FXML
    public void handlePreDefTest(javafx.event.ActionEvent actionEvent) {
        test = (Button) actionEvent.getSource();
        data.setCurrentSVM(data.getPredefinedSVM());
        data.setRVMCurrent(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SVMdisplay.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles button click events when the user clicks the "Delete" button to delete the custom SVM.
     * This method deletes the current custom SVM from the data singleton.
     * @param event The ActionEvent triggered by clicking the "Delete" button.
     */
    @FXML
    private void handleDelete(ActionEvent event) {
        delete = (Button) event.getSource();
        data.deleteCurrentVM();
    }

    /**
     * Handles button click events when the user selects the "Custom SVM Test" option.
     * This method loads the custom SVM for testing purposes and displays its details.
     * @param actionEvent The ActionEvent triggered by clicking the "Custom SVM Test" button.
     */
    @FXML
    public void handleTest(javafx.event.ActionEvent actionEvent) {
        test = (Button) actionEvent.getSource();
        data.setCurrentSVM(data.getCustomSVM());
        data.setRVMCurrent(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SVMdisplay.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
