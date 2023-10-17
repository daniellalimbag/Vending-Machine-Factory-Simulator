import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * This class is a controller for the Create Special Vending Machine View in the vending machine application.
 * It allows the user to create a new special vending machine with custom settings.
 */
public class CreateSVMController {
    @FXML
    private javafx.scene.control.TextField nameField;
    @FXML
    private Slider slider;
    @FXML
    private Slider slider1;
    @FXML
    private Slider slider2;
    @FXML
    private Slider slider3;
    @FXML
    private Slider slider4;
    private int slotCap;
    private int cashCap;
    private int addOnCap;
    private int productCap;
    @FXML
    private int numSlots;
    @FXML
    private Label label;
    @FXML
    private Button save;
    private Stage stage;
    private Scene scene;
    private Parent root;
    DataSingleton data = DataSingleton.getInstance();

    /**
     * Creates a new special vending machine based on the user's input settings.
     * It sets the name, number of slots, slot capacity, cash capacity, add-on slot capacity, and product slot capacity
     * for the new vending machine. The new vending machine is stored in the DataSingleton for future use.
     * @param actionEvent The ActionEvent triggered by the "Create Special Vending Machine" button.
     */
    public void createSVM(javafx.event.ActionEvent actionEvent) {
        String name = new String();
        try {
            name = nameField.getText();
            numSlots = (int) slider.getValue();
            slotCap = (int) slider1.getValue();
            cashCap = (int) slider2.getValue();
            addOnCap = (int) slider3.getValue();
            productCap = (int) slider4.getValue();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Slot[] slots = new Slot[numSlots];
            float n = numSlots;
            while (n % 4 != 0) {
                n++;
            }
            int nRows = (int) Math.ceil(Math.sqrt(n));
            int nCols = (int) Math.ceil(n / nRows);
            int ctr = 0;
            while (ctr < numSlots) {
                for (int i = 0; i < nRows; i++) {
                    int j = 0;
                    while (ctr < numSlots && j < nCols) {
                        slots[ctr] = new Slot(((i + 1) * 10 + j), slotCap);
                        j++;
                        ctr++;
                    }
                }
            }
            Slot[] addOns = new Slot[addOnCap];
            int i = 20;
            ctr = 0;
            while (ctr < addOnCap) {
                addOns[ctr] = new Slot(i, addOnCap);
                i++;
                ctr++;
            }
            SpecialVendingMachine SVM = new SpecialVendingMachine(name, slots, cashCap, addOns, productCap);
            data.setCustomSVM(SVM);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Loads the Create Menu View and displays it.
     * @param event The ActionEvent triggered by the "Back" button.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    public void back(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateMenu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
