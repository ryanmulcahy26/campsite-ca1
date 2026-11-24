package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Campsite;
import services.CampsiteMainSystem;

public class MainController{

    private CampsiteMainSystem system;

    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField contactField;
    @FXML private ComboBox<Integer> starsBox;
    @FXML private CheckBox restaurant;
    @FXML private CheckBox pool;
    @FXML private Label statusLabel;

    public void setSystem(CampsiteMainSystem system){
        this.system = system;
    }

    @FXML
    private void initialize(){
        starsBox.getItems().add(1);
        starsBox.getItems().add(2);
        starsBox.getItems().add(3);
        starsBox.getItems().add(4);
        starsBox.getItems().add(5);
        starsBox.setValue(3);
    }

    @FXML
    private void onAddCampsite(){
        Campsite campsite = system.addCampsite(nameField.getText(),
                addressField.getText(), starsBox.getValue(), contactField.getText());
        if(campsite == null){
            statusLabel.setText("Could not add, name is null or already used.");
            return;
        }
        if(restaurant.isSelected()){
            campsite.addFacility("Restaurant");
        }
        if(pool.isSelected()){
            campsite.addFacility("Swimming pool");
        }
        statusLabel.setText("Added campsite: " + campsite.getName());
        nameField.clear();
        addressField.clear();
        contactField.clear();
        restaurant.setSelected(false);
        pool.setSelected(false);
    }
}