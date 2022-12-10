package org.project.clientMain;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application{
    static Scene scene;

    @Override
    public void start(Stage stage){

        Client.createInstance();
        try{
            scene = new Scene(loadFXML("/main"));
            stage.setTitle("Магазин электроники и бытовой техники");
            stage.setScene(scene);
            stage.show();

        } catch(IOException e){
            System.out.println("\nБыло вызвано IOException");
        }
    }

    @Override
    public void stop(){
        Client.closeEverything();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource( fxml + ".fxml"));
        return fxmlLoader.load();
    }
}

