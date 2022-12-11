package org.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import org.project.clientMain.Main;


import java.io.IOException;

public class SignUpController {
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField textFieldSurname;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldLogin;

    @FXML
    public PasswordField passwordField;

    @FXML
    public PasswordField passwordField2;

    @FXML
    public Button submitBtn;
    @FXML
    public Button backBtn;

    private HelpfulFunctions helpfulFunctions = new HelpfulFunctions();
    @FXML
    private void onBackBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException{
        Main.setRoot("/main");
    }
    @FXML
    private void onSubmitBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException{
        if(helpfulFunctions.isInputValid(textFieldSurname, textFieldName, textFieldLogin,
                passwordField, passwordField2, anchorPane)){
            helpfulFunctions.submitAddUser(1, textFieldSurname, textFieldName, textFieldLogin,
                    passwordField);
        }
    }
}
