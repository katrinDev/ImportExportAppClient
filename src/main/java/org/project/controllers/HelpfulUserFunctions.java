package org.project.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HelpfulUserFunctions {

    HelpfulFunctions helpfulFunctions = new HelpfulFunctions();

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

    void setAddTradeControls(int companyType, String mainText, DatePicker datePicker, ChoiceBox<String> companyChoiceBox, Label selected, Label itemAmountLabel, TextField itemAmountTextField, Label areaLabel, TextArea textArea,
                             Button addToListButton, Button submitButton,TableView<Item> table, AnchorPane innerPane) throws IOException, ClassNotFoundException{
        Label mainLabel = new Label(mainText);

        Label companyLabel = new Label("Компания-партнер: ");

        Label dateLabel = new Label("Дата поставки: ");


        Label choiceLabel = new Label("Выберите товар и укажите количество: ");
        choiceLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 15.0; -fx-font-style: italic;");
        choiceLabel.setLayoutX(400);
        choiceLabel.setLayoutY(250);
        choiceLabel.setPrefHeight(26.0);
        choiceLabel.setPrefWidth(600);

        helpfulFunctions.addObjectControlsStyles(mainLabel, companyLabel, dateLabel,
                datePicker, addToListButton, submitButton);


        table.setLayoutX(400);
        table.setLayoutY(300);

        selected.setStyle("-fx-font-weight: 600; -fx-font-size: 15.0; -fx-font-style: italic;");
        selected.setLayoutX(800);
        selected.setLayoutY(300);

        itemAmountLabel.setStyle("-fx-font-weight: 600; -fx-font-size: 15.0; -fx-font-style: italic;");
        itemAmountLabel.setLayoutX(800);
        itemAmountLabel.setLayoutY(330);
        itemAmountLabel.setPrefWidth(170);

        itemAmountTextField.setLayoutX(900);
        itemAmountTextField.setLayoutY(328);

        areaLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 14.0; -fx-font-style: italic;");
        areaLabel.setLayoutX(800);
        areaLabel.setLayoutY(420.0);
        areaLabel.setPrefHeight(26.0);
        areaLabel.setPrefWidth(180.0);

        textArea.setLayoutX(800);
        textArea.setLayoutY(473);
        textArea.setPrefWidth(310);

        innerPane.getChildren().clear();

        innerPane.getChildren().addAll(mainLabel, selected, table, companyLabel, dateLabel,
                datePicker, choiceLabel);

        setCompaniesChoiceBox(companyType, innerPane, companyChoiceBox);
    }

    void setCompaniesChoiceBox(int companyType, AnchorPane innerPane, ChoiceBox<String> companyChoiceBox) throws IOException, ClassNotFoundException{
        Request request = new Request();
        if(companyType == 1) request.setRequestType(RequestType.SHOWCOMPANIES);

        else if(companyType == 2) request.setRequestType(RequestType.SHOWEXPCOMPANIES);

        else request.setRequestType(RequestType.SHOWIMPCOMPANIES);

        Client.sendMessage(request);

        Response response = Client.getMessage();

        Type type = new TypeToken<ArrayList<Company>>(){}.getType();
        ArrayList<Company> allCompanies = new Gson().fromJson(response.getResponseData(), type);

        ArrayList<String> companiesNames = new ArrayList<>();
        for(Company i: allCompanies){
            companiesNames.add(i.getCompanyName());
        }

        ObservableList<String> fields = FXCollections.observableArrayList(companiesNames);


        //Выпадающий список компаний
        companyChoiceBox.setItems(fields);
        companyChoiceBox.setLayoutX(580);
        companyChoiceBox.setLayoutY(150);

        innerPane.getChildren().add(companyChoiceBox);
    }


    boolean checkIntField(AnchorPane innerPane, TextField itemAmountTextField){
        boolean flag = true;
        try{
            int check = Integer.parseInt(itemAmountTextField.getText());
            if(check < 1 || check > 50) flag = false;

        } catch(Exception e){
            flag = false;
        }

        if(!flag){
            String errorMessage = "Некорректный ввод количества!";
            AuthenticationController.check(errorMessage, innerPane);
        }

        return flag;
    }

    boolean checkTradeInput(ChoiceBox<String> companyChoiceBox, DatePicker datePicker, List<Order> orders, AnchorPane innerPane){
        String errorMessage = "";
        if (companyChoiceBox.getValue() == null){
            errorMessage += "Компания не указана!\n";
        }
        if (datePicker.getValue() == null){
            errorMessage += "Дата поставки не выбрана!\n";
        }
        if (orders.isEmpty()){
            errorMessage += "Товары не добавлены!\n";
        }

        return AuthenticationController.check(errorMessage, innerPane);
    }



    void submitAddTrade(int type, DatePicker datePicker, ChoiceBox<String> companyChoiceBox, List<Order> orders) throws IOException, ClassNotFoundException{
        TradeOperation addOperation = new TradeOperation();

        if(type == 1) addOperation.setOperationType("export");
        else addOperation.setOperationType("import");

        addOperation.setSupplyDate(datePicker.getValue().toString());

        addOperation.setUser(Client.getUser());

        Company company = new Company();
        company.setCompanyName(companyChoiceBox.getValue());

        addOperation.setCompany(company);

        addOperation.setOrders(orders);
        System.out.println(orders);


        System.out.println(addOperation);
        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(addOperation));
        request.setRequestType(RequestType.ADDTRADE);

//        if(type == 1){
//            request.setRequestType(RequestType.ADDEXPTRADE);
//        } else{
//            request.setRequestType(RequestType.ADDIMPTRADE);
//        }

//        Gson gson = new GsonBuilder().setDateFormat("MM-dd-yyyy").create();

        Client.sendMessage(request);//на сокет отправляем запрос


        Response response = Client.getMessage();
        System.out.println("Server: " + response);


        Alert alert;
        if(response.getResponseStatus() == ResponseStatus.OK) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            addOperation = new Gson().fromJson(response.getResponseData(), TradeOperation.class);
            alert.setHeaderText("Новая торговая операция c компанией " + addOperation.getCompany().getCompanyName()
                    + " успешно добавлена!");
            alert.setTitle(response.getResponseMessage());

            request.setRequestMessage(new Gson().toJson(Client.getUser()));
            request.setRequestType(RequestType.ADDTRADEUSER);

            Client.sendMessage(request);

            response = Client.getMessage();
            System.out.println("Server: " + response);
            System.out.println("------------------");


            request.setRequestMessage(new Gson().toJson(orders));
            request.setRequestType(RequestType.ADDTRADEORDERS);

            Client.sendMessage(request);

            response = Client.getMessage();
            System.out.println("Server: " + response);


        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(response.getResponseMessage());
        }
        alert.showAndWait();

    }



    TableView<TradeOperation> showAnyTrades(AnchorPane innerPane, Label label) throws IOException, ClassNotFoundException{
        Response response = Client.getMessage();

        if(response.getResponseStatus() == ResponseStatus.OK) {
            Type type = new TypeToken<ArrayList<TradeOperation>>(){}.getType();
            ArrayList<TradeOperation> expTrades = new Gson().fromJson(response.getResponseData(), type);


            ObservableList<TradeOperation> operations = FXCollections.observableArrayList(expTrades);
            TableView<TradeOperation> table = new TableView<>(operations);


            TableColumn<TradeOperation, String> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("operationId"));
            idColumn.setMinWidth(35);
            idColumn.setMaxWidth(40);

            TableColumn<TradeOperation, String> companyColumn = new TableColumn<>("Компания-партнер");
            companyColumn.setCellValueFactory(( TableColumn.CellDataFeatures<TradeOperation, String> operation ) ->
            {
                String companyName = operation.getValue().getCompany().getCompanyName();
                return new ReadOnlyStringWrapper(companyName);
            });

            TableColumn<TradeOperation, String> dateColumn = new TableColumn<>("Дата поставки");
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("supplyDate"));


            TableColumn<TradeOperation, Double> costColumn = new TableColumn<>("Стоимость операции");
            costColumn.setCellValueFactory(new PropertyValueFactory<>("fullCost"));

            costColumn.setMinWidth(80);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


            table.getColumns().addAll(idColumn, companyColumn, dateColumn, costColumn);
            table.setLayoutX(300);
            table.setLayoutY(160);
            table.setPrefWidth(600);

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


    void submitDeleteTrade(TradeOperation tradeOperation) throws IOException, ClassNotFoundException{
        TradeOperation delOperation = tradeOperation;

        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(delOperation));
        request.setRequestType(RequestType.DELETETRADE);

        Client.sendMessage(request);

        Response response = Client.getMessage();
        System.out.println("Server: " + response);

        Alert alert;
        if (response.getResponseStatus().equals(ResponseStatus.OK)){
            alert = new Alert(Alert.AlertType.INFORMATION);
            delOperation = new Gson().fromJson(response.getResponseData(), TradeOperation.class);
            alert.setHeaderText("Внешнеторговая операция с id " + delOperation.getOperationId() +
                     " успешно удалена!");
            alert.setTitle(response.getResponseMessage());

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(response.getResponseMessage());
        }
        alert.showAndWait();

    }


}
