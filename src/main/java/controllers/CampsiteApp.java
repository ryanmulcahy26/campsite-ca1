package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.CampsiteMainSystem;

public class CampsiteApp extends Application{

    private final CampsiteMainSystem system = new CampsiteMainSystem();

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/main.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setSystem(system);

        stage.setTitle("Campsite Management System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}