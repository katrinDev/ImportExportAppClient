package org.project.controllers;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.project.TCP.Request;
import org.project.TCP.Response;
import org.project.clientMain.Client;
import org.project.clientMain.Main;
import org.project.entities.Person;
import org.project.entities.User;
import org.project.enums.RequestType;
import org.project.enums.ResponseStatus;

import java.io.IOException;

import static javafx.scene.paint.Color.WHITE;

public class HelpfulFunctions {

    TableView<User> addSubmitControls(Pane innerPane, TableView<User> table, Label label, Button button, Label selected, ActionEvent actionEvent){
        try{
            label.setLayoutX(300);
            label.setLayoutY(100);
            label.setStyle("-fx-font-size: 14; -fx-font-weight: 700; -fx-font-style: italic;");

            innerPane.getChildren().clear();

            table.setEditable(true);
            innerPane.getChildren().addAll(label, table);

            selected.setLayoutX(300);
            selected.setLayoutY(580);
            selected.setStyle("-fx-font-size: 14; -fx-font-weight: 600;");

            button.setText("Подтвердить");
            button.setLayoutX(600);
            button.setLayoutY(620);
            button.setStyle("-fx-font-weight: 700; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #F4A460; -fx-border-color: #D2691E;");
            button.setPrefWidth(120.0);
            button.setPrefHeight(38.0);
            innerPane.getChildren().addAll(selected, button);
            return table;

        } catch(Exception e){
            System.out.println("Unable to get users table");
            return null;
        }
    }

    //Подтвердить изменение роли
    void submitRoleChange(int id){
        try {
            User updatedUser = new User();
            updatedUser.setUserId(id);

            Request request = new Request();
            request.setRequestMessage(new Gson().toJson(updatedUser));
            request.setRequestType(RequestType.CHANGEROLE);

            Client.sendMessage(request);

            Response response = Client.getMessage();
            System.out.println("Server: " + response);

            Alert alert;
            if (response.getResponseStatus().equals(ResponseStatus.OK)){
                alert = new Alert(Alert.AlertType.INFORMATION);
                updatedUser = new Gson().fromJson(response.getResponseData(), User.class);
                alert.setHeaderText("Роль пользователя " + updatedUser.getLogin() + ": "
                        + updatedUser.getRole().getRoleName());
                alert.setTitle(response.getResponseMessage());

            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(response.getResponseMessage());
            }
            alert.showAndWait();
        } catch(Exception e){
            System.out.println("\n" + e.getClass());
            e.getStackTrace();
        }
    }


    //Подтвердить удаление
    void submitDeleteUser(int id) throws IOException, ClassNotFoundException{
        User delUser = new User();
        delUser.setUserId(id);

        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(delUser));
        request.setRequestType(RequestType.DELETEUSER);

        Client.sendMessage(request);

        Response response = Client.getMessage();
        System.out.println("Server: " + response);

        Alert alert;
        if (response.getResponseStatus().equals(ResponseStatus.OK)){
            alert = new Alert(Alert.AlertType.INFORMATION);
            delUser = new Gson().fromJson(response.getResponseData(), User.class);
            alert.setHeaderText("Пользователь " + delUser.getLogin() + ": "
                    + "успешно удален!");
            alert.setTitle(response.getResponseMessage());

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(response.getResponseMessage());
        }
        alert.showAndWait();

    }

    boolean isInputValid(TextField textFieldSurname, TextField textFieldName, TextField textFieldLogin,
                                 PasswordField passwordField, PasswordField passwordField2, AnchorPane innerPane){
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
        return AuthenticationController.check(errorMessage, innerPane);
    }


    void addUserControlsStyles(Label mainLabel, Label surnameLabel, Label nameLabel, Label loginLabel, Label passwordLabel, Label passwordLabel2, TextField textFieldSurname, TextField textFieldName, TextField textFieldLogin,
                                       PasswordField passwordField, PasswordField passwordField2, Button button){
        mainLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 16.0; -fx-font-style: italic;");
        mainLabel.setLayoutX(650);
        mainLabel.setLayoutY(100);
        mainLabel.setPrefHeight(38.0);
        mainLabel.setPrefWidth(220.0);

        surnameLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        surnameLabel.setLayoutX(600);
        surnameLabel.setLayoutY(170.0);
        surnameLabel.setPrefHeight(26.0);
        surnameLabel.setPrefWidth(100.0);


        nameLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        nameLabel.setLayoutX(600);
        nameLabel.setLayoutY(225);
        nameLabel.setPrefHeight(26.0);
        nameLabel.setPrefWidth(69.0);

        loginLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        loginLabel.setLayoutX(600);
        loginLabel.setLayoutY(275);
        loginLabel.setPrefHeight(26.0);
        loginLabel.setPrefWidth(69.0);


        passwordLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        passwordLabel.setLayoutX(600);
        passwordLabel.setLayoutY(326);
        passwordLabel.setPrefHeight(26.0);
        passwordLabel.setPrefWidth(69.0);


        passwordLabel2.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        passwordLabel2.setLayoutX(600);
        passwordLabel2.setLayoutY(377.0);
        passwordLabel2.setPrefHeight(26.0);
        passwordLabel2.setPrefWidth(180.0);


        textFieldSurname.setLayoutX(778);
        textFieldSurname.setLayoutY(171);
        textFieldSurname.setPrefWidth(170);


        textFieldName.setLayoutX(778);
        textFieldName.setLayoutY(223);
        textFieldName.setPrefWidth(170);


        textFieldLogin.setLayoutX(778);
        textFieldLogin.setLayoutY(273);
        textFieldLogin.setPrefWidth(170);

        passwordField.setLayoutX(778);
        passwordField.setLayoutY(324);
        passwordField.setPrefWidth(170);

        passwordField2.setLayoutX(778);
        passwordField2.setLayoutY(375);
        passwordField2.setPrefWidth(170);

        button.setLayoutX(860);
        button.setLayoutY(440);
        button.setStyle("-fx-font-weight: 700; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #F4A460; -fx-border-color: #D2691E;");
        button.setPrefWidth(120.0);
        button.setPrefHeight(38.0);

    }


    void submitAddUser(int funcNumber, TextField textFieldSurname, TextField textFieldName, TextField textFieldLogin,
                               PasswordField passwordField) throws IOException, ClassNotFoundException{
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


        Alert alert;
        if(response.getResponseStatus() == ResponseStatus.OK) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            registerUser = new Gson().fromJson(response.getResponseData(), User.class);
            alert.setHeaderText("Пользователь " + registerUser.getLogin() + ": "
                    + "успешно добавлен!");
            alert.setTitle(response.getResponseMessage());

            if(funcNumber == 1){
                Main.setRoot("/main");
            }

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(response.getResponseMessage());
        }
        alert.showAndWait();

    }


}
