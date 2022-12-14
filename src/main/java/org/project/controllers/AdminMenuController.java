package org.project.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.project.TCP.Request;
import org.project.TCP.Response;
import org.project.clientMain.Client;
import org.project.clientMain.Main;

import org.project.entities.*;
import org.project.enums.RequestType;
import org.project.enums.ResponseStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.function.Predicate;


public class AdminMenuController {

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public AnchorPane innerPane;

    private final HelpfulFunctions helpfulFunctions = new HelpfulFunctions();

    //РАБОТА С ПОЛЬЗОВАТЕЛЯМИ
    //Список аккаунтов
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


    //Изменить роль пользователя
    @FXML
    private void onClickChangeRole(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Label label = new Label("Выберите пользователя для изменения роли: ");

        Button button = new Button();
        Label selected = new Label();

        TableView<User> table = onClickShowUsers(actionEvent);
        helpfulFunctions.addSubmitControls(innerPane, table, label, button, selected);

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
        helpfulFunctions.addSubmitControls(innerPane, table, label, button, selected);


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

        passwordLabel2.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        passwordLabel2.setLayoutX(600);
        passwordLabel2.setLayoutY(377.0);
        passwordLabel2.setPrefHeight(26.0);
        passwordLabel2.setPrefWidth(180.0);

        TextField textFieldSurname = new TextField();
        TextField textFieldName = new TextField();
        TextField textFieldLogin = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField passwordField2 = new PasswordField();

        passwordField.setLayoutX(778);
        passwordField.setLayoutY(324);
        passwordField.setPrefWidth(170);

        passwordField2.setLayoutX(778);
        passwordField2.setLayoutY(375);
        passwordField2.setPrefWidth(170);

        Button button = new Button("Подтвердить");

        helpfulFunctions.addObjectControlsStyles(mainLabel, surnameLabel, nameLabel, loginLabel, passwordLabel,
                textFieldSurname, textFieldName, textFieldLogin, button);

        innerPane.getChildren().addAll(mainLabel, surnameLabel, nameLabel, loginLabel, passwordLabel, passwordLabel2, textFieldSurname,
                textFieldLogin, textFieldName, passwordField, passwordField2, button);

        button.setOnAction(event -> {
            if(helpfulFunctions.checkUserInput(textFieldSurname, textFieldName, textFieldLogin, passwordField, passwordField2, innerPane)){
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
    private TableView<Person> onClickShowEmployees(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        innerPane.getChildren().clear();

        Request request = new Request();
        request.setRequestType(RequestType.SHOWPEOPLE);

        Client.sendMessage(request);

        Response response = Client.getMessage();

        if(response.getResponseStatus() == ResponseStatus.OK) {
            Type type = new TypeToken<ArrayList<Person>>(){}.getType();
            ArrayList<Person> allPeople = new Gson().fromJson(response.getResponseData(), type);


            ObservableList<Person> people = FXCollections.observableArrayList(allPeople);
            TableView<Person> table = new TableView<>(people);


            TableColumn<Person, String> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("personId"));

            TableColumn<Person, String> surnameColumn = new TableColumn<>("Фамилия");
            surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

            TableColumn<Person, String> nameColumn = new TableColumn<>("Имя");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Person, String> patronymicColumn = new TableColumn<>("Отчество");
            patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));

            TableColumn<Person, String> mailColumn = new TableColumn<>("Почта");
            mailColumn.setCellValueFactory(new PropertyValueFactory<>("workEmail"));


            table.getColumns().addAll(idColumn, surnameColumn, nameColumn, patronymicColumn, mailColumn);
            table.setLayoutX(300);
            table.setLayoutY(160);
            table.setPrefWidth(464);

            Label label = new Label("Список сотрудников: ");
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
    private void onClickEditEmployee(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Label label = new Label("Выберите сотрудника для изменений: ");

        Button button = new Button();
        Label selected = new Label();


        Label fieldLabel = new Label("Изменяемое поле: ");
        fieldLabel.setStyle("-fx-font-weight: 600; -fx-font-size: 14.0;");
        fieldLabel.setLayoutX(820);
        fieldLabel.setLayoutY(300);
        fieldLabel.setPrefHeight(26.0);
        fieldLabel.setPrefWidth(180.0);

        ObservableList<String> fields = FXCollections.observableArrayList("Фамилия", "Имя", "Отчество", "Почта");

        ChoiceBox<String> employeesChoiceBox = new ChoiceBox<>(fields);
        employeesChoiceBox.setValue("Фамилия");
        employeesChoiceBox.setLayoutX(980);
        employeesChoiceBox.setLayoutY(300);

        Label newValueLabel = new Label("Новое значение: ");
        newValueLabel.setStyle("-fx-font-weight: 600; -fx-font-size: 14.0;");
        newValueLabel.setLayoutX(820);
        newValueLabel.setLayoutY(360.0);
        newValueLabel.setPrefHeight(26.0);
        newValueLabel.setPrefWidth(180.0);

        TextField textFieldValue = new TextField();
        textFieldValue.setLayoutX(980);
        textFieldValue.setLayoutY(360);
        textFieldValue.setPrefWidth(170);

        TableView<Person> table = onClickShowEmployees(actionEvent);
        helpfulFunctions.addSubmitControls(innerPane, table, label, button, selected);
        button.setLayoutX(640);

        innerPane.getChildren().addAll(fieldLabel, employeesChoiceBox, newValueLabel, textFieldValue);

        TableView.TableViewSelectionModel<Person> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                int id = newValue.getPersonId();

                selected.setText("Выбранный сотрудник: " + id);


                button.setOnAction(event -> {
                    helpfulFunctions.submitEditEmployee(newValue, employeesChoiceBox.getValue(), textFieldValue.getText());

                    try {
                        onClickShowEmployees(actionEvent);
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                });
            }
        });
    }
    @FXML
    private void onClickAddEmployee(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        innerPane.getChildren().clear();

        Label mainLabel = new Label("Добавление сотрудника");

        Label surnameLabel = new Label("Фамилия: ");
        Label nameLabel = new Label("Имя: ");
        Label patronymicLabel = new Label("Отчество: ");
        Label mailLabel = new Label("Рабочая почта: ");

        TextField textFieldSurname = new TextField();
        TextField textFieldName = new TextField();
        TextField textFieldPatronymic = new TextField();
        TextField textFieldMail = new TextField();

        textFieldMail.setLayoutX(778);
        textFieldMail.setLayoutY(324);
        textFieldMail.setPrefWidth(170);

        Button button = new Button("Подтвердить");

        helpfulFunctions.addObjectControlsStyles(mainLabel, surnameLabel, nameLabel, patronymicLabel, mailLabel,
                textFieldSurname, textFieldName, textFieldPatronymic, button);

        patronymicLabel.setPrefWidth(150);
        mailLabel.setPrefWidth(150);

        innerPane.getChildren().addAll(mainLabel, surnameLabel, nameLabel, patronymicLabel, mailLabel,
                textFieldSurname, textFieldName, textFieldPatronymic, textFieldMail, button);

        button.setOnAction(event -> {
            if(helpfulFunctions.checkEmployeeInput(textFieldSurname, textFieldName, textFieldPatronymic, textFieldMail, innerPane)){
                try{
                    helpfulFunctions.submitAddEmployee(textFieldSurname, textFieldName, textFieldPatronymic, textFieldMail);

                    onClickShowEmployees(actionEvent);

                } catch (Exception e){
                    e.getStackTrace();
                }
            }
        });

    }
    @FXML
    private void onClickDeleteEmployee(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Label label = new Label("Выберите сотрудника для удаления: ");
        Button button = new Button();
        Label selected = new Label();

        TableView<Person> table = onClickShowEmployees(actionEvent);
        helpfulFunctions.addSubmitControls(innerPane, table, label, button, selected);


        TableView.TableViewSelectionModel<Person> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                int id = newValue.getPersonId();
                selected.setText("Выбранный сотрудник: " + id);

                button.setVisible(true);

                button.setOnAction(event -> {
                    try {
                        helpfulFunctions.submitDeleteEmployee(newValue);
                        onClickShowEmployees(actionEvent);

                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                });
            }
        });
    }

    //РАБОТА С КОМПАНИЯМИ
    @FXML
    TableView<Company> onClickShowCompanies(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        return helpfulFunctions.showCompanies(innerPane);
    }

    @FXML
    private void onClickAddCompany(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        innerPane.getChildren().clear();

        Label mainLabel = new Label("Добавление компании");

        Label nameLabel = new Label("Название: ");
        Label countryLabel = new Label("Страна: ");
        Label checkLabel = new Label("Расчетный счёт: ");
        Label mailLabel = new Label("Контактная почта: ");

        TextField textFieldName = new TextField();
        TextField textFieldCountry = new TextField();

        TextField textFieldCheck = new TextField();
        TextField textFieldMail = new TextField();

        textFieldMail.setLayoutX(778);
        textFieldMail.setLayoutY(324);
        textFieldMail.setPrefWidth(170);

        Button button = new Button("Подтвердить");

        helpfulFunctions.addObjectControlsStyles(mainLabel, nameLabel, countryLabel, checkLabel, mailLabel,
                textFieldCountry, textFieldName, textFieldCheck, button);

        checkLabel.setPrefWidth(300);
        mailLabel.setPrefWidth(300);

        innerPane.getChildren().addAll(mainLabel, nameLabel, countryLabel, checkLabel, mailLabel,
                textFieldName, textFieldCountry, textFieldCheck, textFieldMail, button);

        button.setOnAction(event -> {
            if(helpfulFunctions.checkCompanyInput(textFieldName, textFieldCountry, textFieldCheck, textFieldMail, innerPane)){
                try{
                    helpfulFunctions.submitAddCompany(textFieldName, textFieldCountry, textFieldCheck, textFieldMail);

                    onClickShowCompanies(actionEvent);

                } catch (Exception e){
                    e.getStackTrace();
                }
            }
        });
    }

    @FXML
    private void onClickDeleteCompany(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Label label = new Label("Выберите компанию для удаления: ");
        Button button = new Button();
        Label selected = new Label();

        TableView<Company> table = onClickShowCompanies(actionEvent);
        helpfulFunctions.addSubmitControls(innerPane, table, label, button, selected);


        TableView.TableViewSelectionModel<Company> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                int id = newValue.getCompanyId();
                selected.setText("Выбранная компания: " + id);

                button.setVisible(true);

                button.setOnAction(event -> {
                    try {
                        helpfulFunctions.submitDeleteCompany(newValue);
                        onClickShowCompanies(actionEvent);

                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                });
            }
        });
    }



    //РАБОТА С ТОВАРАМИ
    @FXML
    void onClickShowItems(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        helpfulFunctions.submitShowItems(1, innerPane);
    }

    @FXML
    void onClickSearchItem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        helpfulFunctions.searchItems(innerPane);
    }



    @FXML
    private void onClickAddItem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        innerPane.getChildren().clear();

        Label mainLabel = new Label("Добавление товара");

        Label nameLabel = new Label("Наименование: ");
        Label costLabel = new Label("Стоимость: ");

        TextField textFieldName = new TextField();
        TextField textFieldCost = new TextField();


        Button button = new Button("Подтвердить");

        helpfulFunctions.addObjectControlsStyles(mainLabel, nameLabel, costLabel, textFieldCost, textFieldName, button);


        innerPane.getChildren().addAll(mainLabel, nameLabel, costLabel, textFieldName, textFieldCost, button);

        button.setOnAction(event -> {
            if(helpfulFunctions.checkItemInput(textFieldName, textFieldCost, innerPane)){
                try{
                    helpfulFunctions.submitAddItem(textFieldName, textFieldCost);

                    onClickShowItems(actionEvent);

                } catch (Exception e){
                    e.getStackTrace();
                }
            }
        });
    }

    @FXML
    private void onClickDeleteItem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Label label = new Label("Выберите товар для удаления: ");
        Button button = new Button();
        Label selected = new Label();

        TableView<Item> table = helpfulFunctions.submitShowItems(1, innerPane);
        helpfulFunctions.addSubmitControls(innerPane, table, label, button, selected);


        TableView.TableViewSelectionModel<Item> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                int id = newValue.getItemId();
                selected.setText("Выбранный товар: " + id);

                button.setVisible(true);

                button.setOnAction(event -> {
                    try {
                        helpfulFunctions.submitDeleteItem(newValue);
                        onClickShowCompanies(actionEvent);

                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                });
            }
        });
    }


    //Статистика компании
    @FXML
    private void onClickCompanyReport(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        helpfulFunctions.createReport();
    }


    //Аккаунт
    @FXML
    private void onClickExit(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Main.setRoot("/main");
    }
}
