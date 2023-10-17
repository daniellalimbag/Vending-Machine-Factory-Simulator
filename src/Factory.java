import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Factory extends Application implements Initializable {
    DataSingleton data = DataSingleton.getInstance();
    public void initialize(){

    }
    public static void main(String args[]){
        launch();
    }
    @Override
    public void start(Stage stage){
        Slot slots[] = new Slot[12];
        int ctr  = 0;
        while(ctr < 12) {
            for (int i = 0; i < 4; i++) {
                int j = 0;
                while(ctr < 12 && j < 3) {
                    slots[ctr] = new Slot(((i + 1) * 10 + j), 15, 15);
                    j++;
                    ctr++;
                }
            }
        }
        // Can be sold separately
        Item corn = new Item("Corn", 50, 125);
        Item taffy = new Item("Taffy", 30, 350);
        Item coconut = new Item("Coconut", 83, 300);
        Item tomato = new Item("Tomato", 35, 33);
        Item birchnut = new Item("Birchnut", 75, 108);
        Item mushroom = new Item("Mushroom", 165, 40);
        Item durian = new Item("Durian", 60, 147);
        Item banana = new Item("Banana", 100, 300);
        Item cactus = new Item("Cactus", 47, 24);
        Item bread = new Item("Bread", 95, 265);
        Item berries = new Item("Berries", 210, 204);
        Item pomegranate = new Item("Pomegranate", 250, 234);

        VendingMachine rvm = new VendingMachine("Predefined", slots, 100);
        for(int i = 0; i < rvm.getCashAmount().size(); i++){
            rvm.getCashAmount().get(i).setQuantity(50);
        }
        data.setPredefinedRVM(rvm);

        // Add-ons
        Item milk = new Item("Milk", 12, 20);
        Item butter = new Item("Butter", 30, 117);
        Item fish = new Item("Fish", 116, 420);
        Item roe = new Item("Roe", 99, 673);
        Item meat = new Item("Meat", 300, 897);
        Item honey = new Item("Honey", 75, 82);
        Item dressing = new Item("Dressing", 29, 38);
        Item rice = new Item("Rice", 10, 20);
        Item flour = new Item("Flour", 4, 22);
        Item egg = new Item("Egg", 13, 249);
        Item watermelon = new Item("Watermelon", 360, 120);
        slots[0].setItem(milk);
        slots[1].setItem(taffy);
        slots[2].setItem(coconut);
        slots[3].setItem(tomato);
        slots[4].setItem(birchnut);
        slots[5].setItem(mushroom);
        slots[6].setItem(durian);
        slots[7].setItem(banana);
        slots[8].setItem(cactus);
        slots[9].setItem(bread);
        slots[10].setItem(berries);
        slots[11].setItem(pomegranate);
        // Recipe for dishes
        Item waffles = new Item("Waffles");
        waffles.addIngredient(milk);
        waffles.addIngredient(butter);
        waffles.addIngredient(egg);
        waffles.addIngredient(flour);

        Item fruitMedley = new Item("FruitMedley");
        fruitMedley.addIngredient(watermelon);
        fruitMedley.addIngredient(berries);
        fruitMedley.addIngredient(banana);

         Item flowerSalad = new Item("FlowerSalad");
         flowerSalad.addIngredient(cactus);
         flowerSalad.addIngredient(dressing);
         flowerSalad.addIngredient(tomato);

        Item maki = new Item("Maki");
        maki.addIngredient(fish);
        maki.addIngredient(roe);

        Item honeyNuggets = new Item("HoneyNuggets");
        honeyNuggets.addIngredient(meat);
        honeyNuggets.addIngredient(honey);
        honeyNuggets.addIngredient(butter);
        honeyNuggets.addIngredient(flour);
        Slot[] svmSlots = new Slot[11];
        ctr  = 0;
        int i = 10;
        while(ctr < 11) {
            svmSlots[ctr] = new Slot(i, 15, 15);
            i++;
            ctr++;
        }
        svmSlots[0].setItem(milk);
        svmSlots[1].setItem(meat);
        svmSlots[2].setItem(egg);
        svmSlots[3].setItem(roe);
        svmSlots[4].setItem(watermelon);
        svmSlots[5].setItem(mushroom);
        svmSlots[6].setItem(banana);
        svmSlots[7].setItem(berries);
        svmSlots[8].setItem(cactus);
        svmSlots[9].setItem(fish);
        svmSlots[10].setItem(tomato);
        Slot[] addOnSlots = new Slot[11];
        ctr  = 0;
        i = 50;
        while(ctr < 11) {
            addOnSlots[ctr] = new Slot(i, 15, 15);
            i++;
            ctr++;
        }
        corn.setaddOn(true);
        honey.setaddOn(true);
        dressing.setaddOn(true);
        rice.setaddOn(true);
        flour.setaddOn(true);
        pomegranate.setaddOn(true);
        taffy.setaddOn(true);
        coconut.setaddOn(true);
        birchnut.setaddOn(true);
        bread.setaddOn(true);
        butter.setaddOn(true);

        addOnSlots[0].setItem(corn);
        addOnSlots[1].setItem(honey);
        addOnSlots[2].setItem(dressing);
        addOnSlots[3].setItem(rice);
        addOnSlots[4].setItem(flour);
        addOnSlots[5].setItem(pomegranate);
        addOnSlots[6].setItem(taffy);
        addOnSlots[7].setItem(coconut);
        addOnSlots[8].setItem(birchnut);
        addOnSlots[9].setItem(bread);
        addOnSlots[10].setItem(butter);
        SpecialVendingMachine svm = new SpecialVendingMachine("Predefined", svmSlots, 100, addOnSlots, 5);
        for(i = 0; i < rvm.getCashAmount().size(); i++){
            svm.getCashAmount().get(i).setQuantity(50);
        }
        svm.addProduct(waffles);
        svm.addProduct(fruitMedley);
        svm.addProduct(flowerSalad);
        svm.addProduct(maki);
        svm.addProduct(honeyNuggets);
        svm.setAddOnSlots(addOnSlots);
        ctr  = 0;
        i = 70;
        while(ctr < svm.getProducts().size()) {
            svm.getProducts().get(ctr).setProductNum(i);
            ctr++;
            i++;
        }
        data.setPredefinedSVM(svm);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            StackPane layout = new StackPane();
            Scene scene = new Scene(root);
            stage.setTitle("Vending Machine Factory");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
