package org.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.project.clientMain.Main;

import java.io.IOException;

public class MainController {

    @FXML
    private void onAuthBtnClick(ActionEvent actionEvent) throws IOException {
        Main.setRoot("/authentication");
    }

    @FXML
    private void onRegisterBtnClick(ActionEvent actionEvent) throws IOException {
        Main.setRoot("/signUp");
    }

}