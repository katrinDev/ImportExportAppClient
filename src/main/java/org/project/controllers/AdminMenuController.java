package org.project.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminMenuController {

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Pane innerPane;


    //Работа с пользователями
    @FXML
    private TableView<User> onClickShowUsers(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        innerPane.getChildren().clear();

        Request request = new Request();
        request.setRequestType(RequestType.SHOWUSERS);

        Client.sendMessage(request);

        Response response = Client.getMessage();

        if(response.getResponseStatus() == ResponseStatus.OK) {
            Type type = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> allUsers = new Gson().fromJson(response.getResponseData(), type);


            ObservableList<User> users = FXCollections.observableArrayList(allUsers);
            TableView<User> table = new TableView<>(users);


            TableColumn<User, String> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

            TableColumn<User, String> loginColumn = new TableColumn<>("Логин");
            loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));


            TableColumn<User, String> roleColumn = new TableColumn<>("Роль");

            roleColumn.setCellValueFactory(( TableColumn.CellDataFeatures<User, String> user ) ->
            {
                String roleName = user.getValue().getRole().getRoleName();
                return new ReadOnlyStringWrapper(roleName);
            });

            TableColumn<User, String> personColumn = new TableColumn<>("Владелец аккаунта");
            personColumn.setCellValueFactory(
                ( TableColumn.CellDataFeatures<User, String> user ) -> {
                    Person person = user.getValue().getPerson();
                    String personString = person.getSurname() + " " +  person.getName() + " " + person.getPatronymic();
                    return new ReadOnlyStringWrapper(personString);
                }
            );

            table.getColumns().addAll(idColumn, loginColumn, roleColumn, personColumn);
            table.setLayoutX(300);
            table.setLayoutY(160);
            table.setPrefWidth(418);

            Label label = new Label("Список аккаунтов: ");
            label.setLayoutX(300);
            label.setLayoutY(100);
            label.setStyle("-fx-font-size: 14; -fx-font-weight: 700; -fx-font-style: italic");


            innerPane.getChildren().addAll(label, table);
            return table;
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(response.getResponseMessage());
            alert.showAndWait();
            return null;
        }
    }
    @FXML
    private void onClickChangeRole(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Label label = new Label("Выберите пользователя для изменения роли: ");
        label.setLayoutX(300);
        label.setLayoutY(100);
        label.setStyle("-fx-font-size: 14; -fx-font-weight: 700; -fx-font-style: italic");
        TableView<User> table = onClickShowUsers(actionEvent);
        innerPane.getChildren().clear();


        table.setEditable(true);
        innerPane.getChildren().addAll(label, table);

        TableView.TableViewSelectionModel<User> selectionModel = table.getSelectionModel();
        Label selected = new Label();
        Button button = new Button();

        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                int id = newValue.getUserId();

                selected.setText("Selected account: " + id);
                selected.setLayoutX(300);
                selected.setLayoutY(580);
                selected.setStyle("-fx-font-size: 14; -fx-font-weight: 600;");

                button.setVisible(true);
                button.setText("Подтвердить");
                button.setLayoutX(600);
                button.setLayoutY(620);
                button.setStyle("-fx-font-weight: 700; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #F4A460; -fx-border-color: #D2691E;");
                button.setPrefWidth(120.0);
                button.setPrefHeight(38.0);
                button.setOnAction(event -> {
                    changeRoleButtonClick(id);
                    label.setText("");
                    button.setVisible(false);
                    try {
                        onClickShowUsers(actionEvent);
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                });
                innerPane.getChildren().addAll(selected, button);

            }
        });

    }

    private void changeRoleButtonClick(int id){
        try {
            User updatedUser = new User();
            updatedUser.setUserId(id);

            Request request = new Request();
            request.setRequestMessage(new Gson().toJson(updatedUser));
            request.setRequestType(RequestType.CHANGEROLE);

            Client.sendMessage(request);

            Response response = Client.getMessage();
            System.out.println("Server: " + response);

            if (response.getResponseStatus().equals(ResponseStatus.OK)){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                updatedUser = new Gson().fromJson(response.getResponseData(), User.class);
                alert.setHeaderText("Роль пользователя " + updatedUser.getLogin() + ": "
                        + updatedUser.getRole().getRoleName());
                alert.setTitle(response.getResponseMessage());
                alert.showAndWait();


            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(response.getResponseMessage());
                alert.showAndWait();
            }
        } catch(Exception e){
            System.out.println("\n" + e.getClass());
            e.getStackTrace();
        }
    }


    @FXML
    private void onClickDeleteUser(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
    }

    @FXML
    private void onClickAddUser(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Добавить пользователя*");
    }


    //Работа с сотрудниками
    @FXML
    private void onClickShowEmployees(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Показать список сотрудников*");
    }
    @FXML
    private void onClickEditEmployee(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Изменить данные сотрудника*");
    }
    @FXML
    private void onClickAddEmployee(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Добавить сотрудника*");
    }
    @FXML
    private void onClickDeleteEmployee(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Удалить сотрудника*");
    }


    //Работа с товарными группами
    @FXML
    private void onClickAddGroup(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Добавить группу товаров*");
    }

    @FXML
    private void onClickShowGroups(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Вывести список групп товаров*");
    }

    @FXML
    private void onClickDeleteGroup(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Удалить группу*");
    }

    @FXML
    private void onClickChangeGroupData(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Изменить данные группы*");
    }


    //Работа с номенклатурой товаров
    @FXML
    private void onClickShowItems(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Вывести списком номенклатуру компании*");
    }

    @FXML
    private void onClickChangeItem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Изменить данные продукта*");
    }

    @FXML
    private void onClickAddItem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Добавить продукт*");
    }

    @FXML
    private void onClickDeleteItem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Удалить продукт*");
    }


    //Статистика компании
    @FXML
    private void onClickCompanyReport(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Месячный отчет* ");
    }


    //Аккаунт
    @FXML
    private void onClickChangeAccountData(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        textArea.setText("*Изменить логин/пароль*");
    }

    @FXML
    private void onClickExit(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Main.setRoot("/main");
    }
}
