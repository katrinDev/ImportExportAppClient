package org.project.controllers;


import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.project.TCP.Request;
import org.project.TCP.Response;
import org.project.clientMain.Main;
import org.project.entities.Role;
import org.project.entities.User;
import org.project.enums.RequestType;
import org.project.enums.ResponseStatus;
import org.project.clientMain.Client;

import java.io.IOException;

public class AuthenticationController {
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField textFieldLogin;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Button submitBtn;
    @FXML
    public Button backBtn;

    @FXML
    private void onBackBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Main.setRoot("/main");
    }

    @FXML
    private void onSubmitBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if(isInputValid()){
            User requestUser = new User();

            requestUser.setLogin(textFieldLogin.getText());
            requestUser.setPassword(passwordField.getText());

            Request request = new Request();
            request.setRequestMessage(new Gson().toJson(requestUser));
            request.setRequestType(RequestType.LOGIN);

            Client.sendMessage(request);//на сокет отправляем запрос
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ожидание ответа");
            alert.setHeaderText("Пожалуйста, подождите");
            alert.setContentText("Идет проверка данных");
            alert.showAndWait();

            Response response = Client.getMessage();
            alert.hide();

            if(response.getResponseStatus() == ResponseStatus.OK) {
                 System.out.println(response);
                 Role role = new Gson().fromJson(response.getResponseData(), Role.class);
                 System.out.println(role);
                 alert.close();
                 if(role.getRoleId() == 1){
                     Main.setRoot("/adminMenu");
                 }
                 else{
                     Main.setRoot("/employeeMenu");
                 }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка доступа");
                alert.setHeaderText("Неверный логин или пароль!");
                alert.showAndWait();
            }
        }
    }

    private boolean isInputValid(){
        String errorMessage = "";
         if (textFieldLogin.getText() == null || textFieldLogin.getText().length() < 5){
             errorMessage += "Логин менее пяти символов!\n";
         }
         if (passwordField.getText() == null || passwordField.getText().length() < 5){
            errorMessage += "Пароль менее пяти символов!\n";
         }
        return check(errorMessage, anchorPane);
    }

    static boolean check(String errorMessage, AnchorPane anchorPane) {
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(anchorPane.getScene().getWindow());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Пожалуйста, исправьте некорректный ввод");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
