package org.project.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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

import static javafx.scene.paint.Color.WHITE;

public class AdminMenuController {

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public AnchorPane innerPane;

    private final HelpfulFunctions helpfulFunctions = new HelpfulFunctions();

    //РАБОТА С ПОЛЬЗОВАТЕЛЯМИ
    //Список аккаунтов
    @FXML
    TableView<User> onClickShowUsers(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
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


    //Изменить роль пользователя
    @FXML
    private void onClickChangeRole(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Label label = new Label("Выберите пользователя для изменения роли: ");

        Button button = new Button();
        Label selected = new Label();

        TableView<User> table = onClickShowUsers(actionEvent);
        helpfulFunctions.addSubmitControls(innerPane, table, label, button, selected, actionEvent);

        TableView.TableViewSelectionModel<User> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                int id = newValue.getUserId();

                selected.setText("Выбранный аккаунт: " + id);
                button.setText("Подтвердить");

                button.setOnAction(event -> {
                    helpfulFunctions.submitRoleChange(id);

                    try {
                        onClickShowUsers(actionEvent);
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                });
            }
        });

    }


    //Удалить пользователя
    @FXML
    private void onClickDeleteUser(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        Label label = new Label("Выберите пользователя для удаления: ");
        Button button = new Button();
        Label selected = new Label();

        TableView<User> table = onClickShowUsers(actionEvent);
        helpfulFunctions.addSubmitControls(innerPane, table, label, button, selected, actionEvent);


        TableView.TableViewSelectionModel<User> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                int id = newValue.getUserId();
                selected.setText("Выбранный аккаунт: " + id);

                button.setVisible(true);

                button.setOnAction(event -> {
                    try {
                        helpfulFunctions.submitDeleteUser(id);
                        onClickShowUsers(actionEvent);

                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                });
            }
        });
    }


    //Добавить пользователя
    @FXML
    private void onClickAddUser(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        innerPane.getChildren().clear();

        Label mainLabel = new Label("Добавление пользователя");

        Label surnameLabel = new Label("Фамилия: ");
        Label nameLabel = new Label("Имя: ");
        Label loginLabel = new Label("Логин: ");
        Label passwordLabel = new Label("Пароль: ");
        Label passwordLabel2 = new Label("Подтвердите пароль: ");

        TextField textFieldSurname = new TextField();
        TextField textFieldName = new TextField();
        TextField textFieldLogin = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField passwordField2 = new PasswordField();

        Button button = new Button("Подтвердить");

        helpfulFunctions.addUserControlsStyles(mainLabel, surnameLabel, nameLabel, loginLabel, passwordLabel, passwordLabel2,
                textFieldSurname, textFieldName, textFieldLogin, passwordField, passwordField2, button);

        innerPane.getChildren().addAll(mainLabel, surnameLabel, nameLabel, loginLabel, passwordLabel, passwordLabel2, textFieldSurname,
                textFieldLogin, textFieldName, passwordField, passwordField2, button);

        button.setOnAction(event -> {
            if(helpfulFunctions.isInputValid(textFieldSurname, textFieldName, textFieldLogin, passwordField, passwordField2, innerPane)){
                try{
                    helpfulFunctions.submitAddUser(2, textFieldSurname, textFieldName, textFieldLogin, passwordField);

                    onClickShowUsers(actionEvent);

                } catch (Exception e){
                    e.getStackTrace();
                }
            }
        });
    }


    //РАБОТА С СОТРУДНИКАМИ
    //Список сотрудников
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
