package org.project.controllers;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.project.TCP.Request;
import org.project.clientMain.Client;
import org.project.clientMain.Main;
import org.project.entities.*;
import org.project.enums.RequestType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeMenuController {

    @FXML
    public AnchorPane innerPane;

    private final HelpfulFunctions helpfulFunctions = new HelpfulFunctions();
    private final HelpfulUserFunctions helpfulUserFunctions = new HelpfulUserFunctions();


    //Работа с текущими экспортными сделками
    @FXML
    TableView<TradeOperation> onClickShowExportTrades(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        innerPane.getChildren().clear();

        Request request = new Request();
        request.setRequestType(RequestType.SHOWMYEXPTRADES);
        request.setRequestMessage(new Gson().toJson(Client.getUser()));

        Client.sendMessage(request);
        Label label = new Label("Список Ваших экспортных операций: ");

        return helpfulUserFunctions.showAnyTrades(innerPane, label);
    }


    @FXML
    private void onClickAddExportTrade(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String mainText = "Добавление экспортной сделки: ";
        addAnyTrade(mainText, innerPane, 1, actionEvent);
    }


    @FXML
    private void onClickDeleteExpTrade(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Label label = new Label("Выберите экспортную операцию для удаления: ");
        Button button = new Button();
        Label selected = new Label();

        TableView<TradeOperation> table = onClickShowExportTrades(actionEvent);
        helpfulFunctions.addSubmitControls(innerPane, table, label, button, selected);


        TableView.TableViewSelectionModel<TradeOperation> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                int id = newValue.getOperationId();
                selected.setText("Выбранный экспорт: " + id);

                button.setVisible(true);

                button.setOnAction(event -> {
                    try {
                        helpfulUserFunctions.submitDeleteTrade(newValue);
                        onClickShowExportTrades(actionEvent);

                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                });
            }
        });
    }

    @FXML
    private void onClickDeleteImpTrade(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Label label = new Label("Выберите импортную операцию для удаления: ");
        Button button = new Button();
        Label selected = new Label();

        TableView<TradeOperation> table = onClickShowImportTrades(actionEvent);
        helpfulFunctions.addSubmitControls(innerPane, table, label, button, selected);

        TableView.TableViewSelectionModel<TradeOperation> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                int id = newValue.getOperationId();
                selected.setText("Выбранный импорт: " + id);

                button.setVisible(true);

                button.setOnAction(event -> {
                    try {
                        helpfulUserFunctions.submitDeleteTrade(newValue);
                        onClickShowImportTrades(actionEvent);

                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                });
            }
        });
    }


    //Работа с текущими импортными сделками
    @FXML
    TableView<TradeOperation> onClickShowImportTrades(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        innerPane.getChildren().clear();

        Request request = new Request();
        request.setRequestType(RequestType.SHOWMYIMPTRADES);
        request.setRequestMessage(new Gson().toJson(Client.getUser()));

        Client.sendMessage(request);

        Label label = new Label("Список Ваших импортных операций: ");

        return helpfulUserFunctions.showAnyTrades(innerPane, label);
    }

    @FXML
    private void onClickAddImportTrade(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String mainText = "Добавление импортной сделки: ";
        addAnyTrade(mainText, innerPane, 2, actionEvent);
    }


    //Компании-партнеры
    @FXML
    TableView<Company> onClickFullPartnersList(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        return helpfulFunctions.showCompanies(innerPane);
    }
    @FXML
    private void onClickYourPartners(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        innerPane.getChildren().clear();

        Request request = new Request();
        request.setRequestType(RequestType.SHOWMYPARTNERS);
        request.setRequestMessage(new Gson().toJson(Client.getUser()));

        Client.sendMessage(request);

        String mainText = "Список партнеров контролируемых Вами сделок: ";
        helpfulFunctions.makeTableForCompanies(mainText, innerPane);
    }

    //Номенклатура
    @FXML
    private void onClickShowItems(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        helpfulFunctions.submitShowItems(innerPane);
    }
    @FXML
    private void onClickFilterItems(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        helpfulFunctions.searchItems(innerPane);
    }

//    //Личная статистика
//    @FXML
//    private void onClickProductivityChart(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//    }


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


    void addAnyTrade(String mainText, AnchorPane innerPane, int type, ActionEvent actionEvent) throws IOException, ClassNotFoundException{
        TableView<Item> table = helpfulFunctions.submitShowItems(innerPane);

        ChoiceBox<String> companyChoiceBox = new ChoiceBox<>();

        DatePicker datePicker = new DatePicker();

        Label selected = new Label();

        Label itemAmountLabel = new Label("Количество: ");

        TextField itemAmountTextField = new TextField();

        Label areaLabel = new Label("Полный заказ: ");

        TextArea textArea = new TextArea();

        Button addToListButton = new Button("Добавить в заказ");

        Button submitButton = new Button("Завершить оформление");


        helpfulUserFunctions.setAddTradeControls(mainText, datePicker, companyChoiceBox, selected, itemAmountLabel, itemAmountTextField, areaLabel, textArea, addToListButton, submitButton, table,  innerPane);

        List<Order> orders = new ArrayList<>();

        innerPane.getChildren().addAll( itemAmountLabel, itemAmountTextField,
                areaLabel, textArea, addToListButton, submitButton);

        TableView.TableViewSelectionModel<Item> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                String itemName = newValue.getItemName();

                selected.setText("Выбранный товар:  " + itemName);


                addToListButton.setOnAction(event -> {
                    if(helpfulUserFunctions.checkIntField(innerPane, itemAmountTextField)){
                        orders.add(new Order(newValue, Integer.parseInt(itemAmountTextField.getText())));
                    }

                    textArea.clear();
                    for(Order i : orders){
                        textArea.appendText("\nТовар: " + i.getItem().getItemName() + "     Количество: " + i.getItemAmount());
                    }

                });

                submitButton.setOnAction(event -> {
                    if(helpfulUserFunctions.checkTradeInput(companyChoiceBox, datePicker, orders, innerPane)) {
                        try {
                            helpfulUserFunctions.submitAddTrade(type, datePicker, companyChoiceBox, orders);
                            if(type == 1){
                                onClickShowExportTrades(actionEvent);
                            } else {
                                onClickShowImportTrades(actionEvent);
                            }

                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                });

            }
        });
    }

}
