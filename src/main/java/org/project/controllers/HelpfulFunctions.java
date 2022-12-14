package org.project.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.project.TCP.Request;
import org.project.TCP.Response;
import org.project.clientMain.Client;
import org.project.clientMain.Main;
import org.project.entities.Company;
import org.project.entities.Item;
import org.project.entities.Person;
import org.project.entities.User;
import org.project.enums.RequestType;
import org.project.enums.ResponseStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class HelpfulFunctions {

    void addSubmitControls(Pane innerPane, TableView table, Label label, Button button, Label selected){

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


    boolean checkUserInput(TextField textFieldSurname, TextField textFieldName, TextField textFieldLogin,
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


    void addObjectControlsStyles(Label mainLabel, Label firstLabel, Label secondLabel, Label thirdLabel, Label forthLabel, TextField firstTextField, TextField secondTextField, TextField thirdTextField,
                                        Button button){

        mainLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 16.0; -fx-font-style: italic;");
        mainLabel.setLayoutX(650);
        mainLabel.setLayoutY(100);
        mainLabel.setPrefHeight(38.0);
        mainLabel.setPrefWidth(220.0);

        firstLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        firstLabel.setLayoutX(600);
        firstLabel.setLayoutY(170.0);
        firstLabel.setPrefHeight(26.0);
        firstLabel.setPrefWidth(100.0);


        secondLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        secondLabel.setLayoutX(600);
        secondLabel.setLayoutY(225);
        secondLabel.setPrefHeight(26.0);
        secondLabel.setPrefWidth(69.0);

        thirdLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        thirdLabel.setLayoutX(600);
        thirdLabel.setLayoutY(275);
        thirdLabel.setPrefHeight(26.0);
        thirdLabel.setPrefWidth(69.0);


        forthLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        forthLabel.setLayoutX(600);
        forthLabel.setLayoutY(326);
        forthLabel.setPrefHeight(26.0);
        forthLabel.setPrefWidth(69.0);


        firstTextField.setLayoutX(778);
        firstTextField.setLayoutY(171);
        firstTextField.setPrefWidth(170);


        secondTextField.setLayoutX(778);
        secondTextField.setLayoutY(223);
        secondTextField.setPrefWidth(170);


        thirdTextField.setLayoutX(778);
        thirdTextField.setLayoutY(273);
        thirdTextField.setPrefWidth(170);


        button.setLayoutX(840);
        button.setLayoutY(440);
        button.setStyle("-fx-font-weight: 700; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #F4A460; -fx-border-color: #D2691E;");
        button.setPrefWidth(120.0);
        button.setPrefHeight(38.0);

    }


    void addObjectControlsStyles(Label mainLabel, Label firstLabel, Label secondLabel, TextField firstTextField, TextField secondTextField,
                                 Button button){

        mainLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 16.0; -fx-font-style: italic;");
        mainLabel.setLayoutX(650);
        mainLabel.setLayoutY(100);
        mainLabel.setPrefHeight(38.0);
        mainLabel.setPrefWidth(220.0);

        firstLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        firstLabel.setLayoutX(600);
        firstLabel.setLayoutY(270.0);
        firstLabel.setPrefHeight(26.0);
        firstLabel.setPrefWidth(150.0);


        secondLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        secondLabel.setLayoutX(600);
        secondLabel.setLayoutY(325);
        secondLabel.setPrefHeight(26.0);
        secondLabel.setPrefWidth(150.0);


        firstTextField.setLayoutX(778);
        firstTextField.setLayoutY(271);
        firstTextField.setPrefWidth(170);


        secondTextField.setLayoutX(778);
        secondTextField.setLayoutY(323);
        secondTextField.setPrefWidth(170);


        button.setLayoutX(835);
        button.setLayoutY(400);
        button.setStyle("-fx-font-weight: 700; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #F4A460; -fx-border-color: #D2691E;");
        button.setPrefWidth(120.0);
        button.setPrefHeight(38.0);

    }

    void addObjectControlsStyles(Label mainLabel, Label firstLabel, Label secondLabel,  DatePicker datePicker, Button firstBtn,
                                 Button secondBtn){
        mainLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 16.0; -fx-font-style: italic;");
        mainLabel.setLayoutX(650);
        mainLabel.setLayoutY(70);
        mainLabel.setPrefHeight(38.0);
        mainLabel.setPrefWidth(500);

        firstLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        firstLabel.setLayoutX(400);
        firstLabel.setLayoutY(150);
        firstLabel.setPrefHeight(26.0);
        firstLabel.setPrefWidth(300);


        secondLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        secondLabel.setLayoutX(400);
        secondLabel.setLayoutY(200);
        secondLabel.setPrefHeight(26.0);
        secondLabel.setPrefWidth(300);


        datePicker.setLayoutX(550);
        datePicker.setLayoutY(200);
        datePicker.setPrefWidth(170);

        firstBtn.setLayoutX(1150);
        firstBtn.setLayoutY(310);
        firstBtn.setStyle("-fx-font-weight: 700; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #F4A460; -fx-border-color: #D2691E;");
        firstBtn.setPrefHeight(38.0);
        firstBtn.setPrefWidth(150);

        secondBtn.setLayoutX(1150);
        secondBtn.setLayoutY(480);
        secondBtn.setStyle("-fx-font-weight: 700; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #F4A460; -fx-border-color: #D2691E;");
        secondBtn.setPrefWidth(220);
        secondBtn.setPrefHeight(38.0);
    }

    void searchItems(AnchorPane innerPane) throws IOException, ClassNotFoundException {
        innerPane.getChildren().clear();

        TextField textFieldSearch = new TextField();

        textFieldSearch.setLayoutX(300);
        textFieldSearch.setLayoutY(100);
        textFieldSearch.setPrefWidth(300);
        textFieldSearch.setText("Поиск...");


        Request request = new Request();
        request.setRequestType(RequestType.SHOWITEMS);

        Client.sendMessage(request);

        Response response = Client.getMessage();

        if(response.getResponseStatus() == ResponseStatus.OK) {
            Type type = new TypeToken<ArrayList<Item>>(){}.getType();
            ArrayList<Item> allItems = new Gson().fromJson(response.getResponseData(), type);

            FilteredList<Item> filteredData = new FilteredList<>(FXCollections.observableArrayList(allItems));
            TableView<Item> table = new TableView<>();
            table.setItems(filteredData);

            createItemsTable(table);


            textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(createPredicate(newValue));
            });


            innerPane.getChildren().addAll(textFieldSearch, table);

        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(response.getResponseMessage());
            alert.showAndWait();
        }
    }


    TableView<Company> showCompanies(AnchorPane innerPane) throws IOException, ClassNotFoundException {
        innerPane.getChildren().clear();

        Request request = new Request();
        request.setRequestType(RequestType.SHOWCOMPANIES);

        Client.sendMessage(request);

        String mainText = "Список компаний: ";

        return makeTableForCompanies(mainText, innerPane);
    }


    TableView<Company> makeTableForCompanies(String mainText, AnchorPane innerPane) throws IOException, ClassNotFoundException {

        Response response = Client.getMessage();

        if(response.getResponseStatus() == ResponseStatus.OK) {
            Type type = new TypeToken<List<Company>>(){}.getType();
            List<Company> allCompanies = new Gson().fromJson(response.getResponseData(), type);

            ObservableList<Company> companies = FXCollections.observableArrayList(allCompanies);
            TableView<Company> table = new TableView<>(companies);


            TableColumn<Company, String> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("companyId"));

            TableColumn<Company, String> nameColumn = new TableColumn<>("Название");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));

            TableColumn<Company, String> countryColumn = new TableColumn<>("Страна");
            countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

            TableColumn<Company, String> checkColumn = new TableColumn<>("Расчетный счёт");
            checkColumn.setCellValueFactory(new PropertyValueFactory<>("checkingAccount"));

            TableColumn<Company, String> mailColumn = new TableColumn<>("Почта");
            mailColumn.setCellValueFactory(new PropertyValueFactory<>("companyEmail"));


            table.getColumns().addAll(idColumn, nameColumn, countryColumn, checkColumn, mailColumn);
            table.setLayoutX(300);
            table.setLayoutY(160);
            table.setPrefWidth(527);

            Label label = new Label(mainText);
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
            alert.setHeaderText("Пользователь " + registerUser.getLogin()
                    + " успешно добавлен!");
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


    //СОТРУДНИКИ
    void submitEditEmployee(Person person, String field, String value){
        try {
            Person updatedEmployee = person;

            switch(field){
                case "Фамилия": {
                    updatedEmployee.setSurname(value);
                    break;
                }
                case "Имя": {
                    updatedEmployee.setName(value);
                    break;
                }
                case "Отчество": {
                    updatedEmployee.setPatronymic(value);
                    break;
                }
                case "Почта": {
                    updatedEmployee.setWorkEmail(value);
                    break;
                }
            }

            Request request = new Request();
            request.setRequestMessage(new Gson().toJson(updatedEmployee));
            request.setRequestType(RequestType.CHANGEPERSON);

            Client.sendMessage(request);

            Response response = Client.getMessage();
            System.out.println("Server: " + response);

            Alert alert;
            if (response.getResponseStatus().equals(ResponseStatus.OK)){
                alert = new Alert(Alert.AlertType.INFORMATION);
                updatedEmployee = new Gson().fromJson(response.getResponseData(), Person.class);
                alert.setHeaderText("Измененный сотрудник: " + updatedEmployee.getSurname() + " " + updatedEmployee.getName());
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

    void submitDeleteEmployee(Person person){
        try{
            Person deletedPerson = person;
            Request request = new Request();
            request.setRequestMessage(new Gson().toJson(deletedPerson));
            request.setRequestType(RequestType.DELETEPERSON);

            Client.sendMessage(request);

            Response response = Client.getMessage();
            System.out.println("Server: " + response);

            Alert alert;
            if (response.getResponseStatus().equals(ResponseStatus.OK)){
                alert = new Alert(Alert.AlertType.INFORMATION);
                deletedPerson = new Gson().fromJson(response.getResponseData(), Person.class);
                alert.setHeaderText("Сотрудник " + deletedPerson.getSurname() + " " + deletedPerson.getName() +
                        " успешно удален!");
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

    void submitAddEmployee(TextField textFieldSurname, TextField textFieldName, TextField textFieldPatronymic,
                           TextField textFieldMail) throws IOException, ClassNotFoundException{

        Person registerPerson = new Person();
        registerPerson.setName(textFieldName.getText());
        registerPerson.setSurname(textFieldSurname.getText());
        registerPerson.setPatronymic(textFieldPatronymic.getText());
        registerPerson.setWorkEmail(textFieldMail.getText());

        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(registerPerson));
        request.setRequestType(RequestType.ADDPERON);

        Client.sendMessage(request);

        Response response = Client.getMessage();
        System.out.println("Server: " + response);


        Alert alert;
        if(response.getResponseStatus() == ResponseStatus.OK) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            registerPerson = new Gson().fromJson(response.getResponseData(), Person.class);
            alert.setHeaderText("Сотрудник " + registerPerson.getSurname() + " " + registerPerson.getName() +
                    " успешно добавлен!");
            alert.setTitle(response.getResponseMessage());

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(response.getResponseMessage());
        }
        alert.showAndWait();

    }

    boolean checkEmployeeInput(TextField textFieldSurname, TextField textFieldName, TextField textFieldPatronymic,
                               TextField textFieldMail, AnchorPane innerPane){
        String errorMessage = "";
        if (textFieldSurname.getText() == null || textFieldSurname.getText().length() < 2){
            errorMessage += "Фамилия менее двух символов!\n";
        }
        if (textFieldName.getText() == null || textFieldName.getText().length() < 2){
            errorMessage += "Имя менее двух символов!\n";
        }
        if (textFieldPatronymic.getText() == null || textFieldPatronymic.getText().length() < 5){
            errorMessage += "Отчество менее пяти символов!\n";
        }
        if (textFieldMail.getText() == null || textFieldMail.getText().length() < 10){
            errorMessage += "Рабочая почта менее десяти символов!\n";
        }
        return AuthenticationController.check(errorMessage, innerPane);
    }



    //КОМПАНИИ
    boolean checkCompanyInput(TextField textFieldName, TextField textFieldCountry, TextField textFieldCheck,
                               TextField textFieldMail, AnchorPane innerPane){
        String errorMessage = "";
        if (textFieldName.getText() == null || textFieldName.getText().length() < 2){
            errorMessage += "Название компании менее двух символов!\n";
        }
        if (textFieldCountry.getText() == null || textFieldCountry.getText().length() < 2){
            errorMessage += "Название страны менее двух символов!\n";
        }
        if (textFieldCheck.getText() == null || textFieldCheck.getText().length() < 15){
            errorMessage += "Расчетный счет менее пятнадати символов!\n";
        }
        if (textFieldMail.getText() == null || textFieldMail.getText().length() < 8){
            errorMessage += "Почта менее восьми символов!\n";
        }
        return AuthenticationController.check(errorMessage, innerPane);
    }

    void submitAddCompany(TextField textFieldName, TextField textFieldCountry, TextField textFieldCheck,
                          TextField textFieldMail) throws IOException, ClassNotFoundException{

        Company addCompany = new Company();
        addCompany.setCompanyName(textFieldName.getText());
        addCompany.setCountry(textFieldCountry.getText());
        addCompany.setCheckingAccount(textFieldCheck.getText());
        addCompany.setCompanyEmail(textFieldMail.getText());

        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(addCompany));
        request.setRequestType(RequestType.ADDCOMPANY);

        Client.sendMessage(request);

        Response response = Client.getMessage();
        System.out.println("Server: " + response);


        Alert alert;
        if(response.getResponseStatus() == ResponseStatus.OK) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            addCompany = new Gson().fromJson(response.getResponseData(), Company.class);
            alert.setHeaderText("Компания " + addCompany.getCompanyName() + " " +
                    " успешно добавлена!");
            alert.setTitle(response.getResponseMessage());

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(response.getResponseMessage());
        }
        alert.showAndWait();
    }

    void submitDeleteCompany(Company company){
        try{
            Company delCompany = company;
            Request request = new Request();
            request.setRequestMessage(new Gson().toJson(delCompany));
            request.setRequestType(RequestType.DELETECOMPANY);

            Client.sendMessage(request);

            Response response = Client.getMessage();
            System.out.println("Server: " + response);

            Alert alert;
            //подтверждение
            if (response.getResponseStatus().equals(ResponseStatus.OK)){
                alert = new Alert(Alert.AlertType.INFORMATION);
                delCompany = new Gson().fromJson(response.getResponseData(), Company.class);
                alert.setHeaderText("Компания " + delCompany.getCompanyName() + " " +
                        " успешно удалена!");
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


    //ТОВАРЫ
    boolean checkItemInput(TextField textFieldName, TextField textFieldCost, AnchorPane innerPane){
        String errorMessage = "";
        boolean flag = true;
        String cost = textFieldCost.getText();
        if (textFieldName.getText() == null || textFieldName.getText().length() < 3){
            errorMessage += "Наименование товара менее трех символов!\n";
        }

        try{
            double k = Double.parseDouble(cost);
        } catch(Exception e){
            flag = false;
        }

        if (cost == null || cost.length() < 2 || !flag){
            errorMessage += "Некорректный ввод стоимости!\n";
        }

        return AuthenticationController.check(errorMessage, innerPane);
    }

    TableView<Item> submitShowItems(int itemsType, AnchorPane innerPane) throws IOException, ClassNotFoundException{
            innerPane.getChildren().clear();
            Request request = new Request();
            System.out.println(itemsType);
            if(itemsType == 1) request.setRequestType(RequestType.SHOWITEMS);

            else if(itemsType == 2) request.setRequestType(RequestType.SHOWEXPITEMS);

            else if(itemsType == 3) request.setRequestType(RequestType.SHOWIMPITEMS);

            Client.sendMessage(request);

            Response response = Client.getMessage();
             System.out.println(response.getResponseMessage());


        if(response.getResponseStatus() == ResponseStatus.OK) {
                Type type = new TypeToken<ArrayList<Item>>(){}.getType();
                ArrayList<Item> allItems = new Gson().fromJson(response.getResponseData(), type);

                ObservableList<Item> items = FXCollections.observableArrayList(allItems);
                TableView<Item> table = new TableView<>(items);


                createItemsTable(table);

                Label label = new Label("Список товаров: ");
                label.setLayoutX(300);
                label.setLayoutY(100);
                label.setStyle("-fx-font-size: 14; -fx-font-weight: 700; -fx-font-style: italic");
                if(itemsType == 1){
                    table.setPrefWidth(470);
                }

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


    void submitAddItem(TextField textFieldName, TextField textFieldCost) throws IOException, ClassNotFoundException{

        Item addItem = new Item();
        addItem.setItemName(textFieldName.getText());
        addItem.setItemCost(Double.parseDouble(textFieldCost.getText()));

        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(addItem));
        request.setRequestType(RequestType.ADDITEM);

        Client.sendMessage(request);

        Response response = Client.getMessage();
        System.out.println("Server: " + response);


        Alert alert;
        if(response.getResponseStatus() == ResponseStatus.OK) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            addItem = new Gson().fromJson(response.getResponseData(), Item.class);
            alert.setHeaderText("Товар " + addItem.getItemName() + " " +
                    " успешно добавлен!");
            alert.setTitle(response.getResponseMessage());

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(response.getResponseMessage());
        }
        alert.showAndWait();
    }

    void submitDeleteItem(Item item){
        try{
            Item delItem = item;
            Request request = new Request();
            request.setRequestMessage(new Gson().toJson(delItem));
            request.setRequestType(RequestType.DELETEITEM);

            Client.sendMessage(request);

            Response response = Client.getMessage();
            System.out.println("Server: " + response);

            Alert alert;
            //подтверждение
            if (response.getResponseStatus().equals(ResponseStatus.OK)){
                alert = new Alert(Alert.AlertType.INFORMATION);
                delItem = new Gson().fromJson(response.getResponseData(), Item.class);
                alert.setHeaderText("Товар " + delItem.getItemName() + " " +
                        " успешно удален!");
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


    void createReport() throws IOException, ClassNotFoundException{
        Request request = new Request();
        request.setRequestType(RequestType.CREATEREPORT);

        Client.sendMessage(request);

        Response response = Client.getMessage();
        System.out.println("Server: " + response);


        Alert alert;
        //подтверждение
        if (response.getResponseStatus().equals(ResponseStatus.OK)){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Отчет");
            alert.setHeaderText(response.getResponseMessage());

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(response.getResponseMessage());
        }
        alert.showAndWait();
    }


    void createItemsTable(TableView<Item> table){
        TableColumn<Item, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Item, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, Double> costColumn = new TableColumn<>("Стоимость");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("itemCost"));


        table.getColumns().addAll(idColumn, nameColumn, costColumn);
        table.setLayoutX(300);
        table.setLayoutY(160);
        table.setPrefWidth(280);
    }

    Predicate<Item> createPredicate(String searchText){
        return item -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return (item.getItemName().toLowerCase().contains(searchText.toLowerCase())) ||
                    (Double.toString(item.getItemCost()).toLowerCase().contains(searchText.toLowerCase()));
        };
    }

}
