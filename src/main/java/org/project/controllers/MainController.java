package org.project.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.project.clientMain.Main;

import java.io.IOException;

public class MainController {
    @FXML
    private Pane pane;

    @FXML
    private void onAuthBtnClick(ActionEvent actionEvent) throws IOException {
        Main.setRoot("/authentication");
//        Stage stage = (Stage) pane.getScene().getWindow();
//        stage.close();
//
//        Scene scene = new Scene(Main.loadFXML("/authentication"));
//        stage = new Stage();
//        stage.setTitle("Магазин электроники и бытовой техники");
//        stage.setScene(scene);
//        stage.show();
    }

    @FXML
    private void onRegisterBtnClick(ActionEvent actionEvent) throws IOException {
        Main.setRoot("/signUp");
    }

    @FXML
    private void onQuitBtnClick(ActionEvent actionEvent) throws IOException {
        Platform.exit();
    }
}