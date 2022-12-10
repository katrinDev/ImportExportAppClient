package org.project.controllers;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.project.TCP.Request;
import org.project.TCP.Response;
import org.project.clientMain.Client;
import org.project.clientMain.Main;
import org.project.entities.Person;
import org.project.entities.User;
import org.project.enums.RequestType;
import org.project.enums.ResponseStatus;

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

    @FXML
    private void onBackBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException{
        Main.setRoot("/main");
    }
    @FXML
    private void onSubmitBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException{
        if(isInputValid()){
            Person registerPerson = new Person();
            registerPerson.setName(textFieldName.getText());
            registerPerson.setSurname(textFieldSurname.getText());

            User registerUser = new User();
            registerUser.setLogin(textFieldLogin.getText());
            registerUser.setPassword(passwordField.getText());
            registerUser.setPerson(registerPerson);

            Request request = new Request();
            request.setRequestMessage(new Gson().toJson(registerUser));
            request.setRequestType(RequestType.SIGNUP);

            Client.sendMessage(request);//на сокет отправляем запрос

            Response response = Client.getMessage();
            System.out.println("Server: " + response);

            if(response.getResponseStatus() == ResponseStatus.OK) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Пользователь добавлен");
                alert.setHeaderText(response.getResponseMessage());
                alert.showAndWait();

                Main.setRoot("/main");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(response.getResponseMessage());
                alert.showAndWait();
            }
        }
    }

    private boolean isInputValid(){
        String errorMessage = "";
        if (textFieldSurname.getText() == null || textFieldSurname.getText().length() < 2){
            errorMessage += "Фамилия менее двух символов!\n";
        }
        if (textFieldName.getText() == null || textFieldName.getText().length() < 2){
            errorMessage += "Имя менее двух символов!\n";
        }
        if (textFieldLogin.getText() == null || textFieldLogin.getText().length() < 5){
            errorMessage += "Логин менее пяти символов!\n";
        }
        if (passwordField.getText() == null || passwordField.getText().length() < 5){
            errorMessage += "Пароль менее пяти символов!\n";
        }
        if (!passwordField.getText().equals(passwordField2.getText())){
            errorMessage += "Пароли не совпадают!\n";
        }
        return AuthenticationController.check(errorMessage, anchorPane);
    }
}
