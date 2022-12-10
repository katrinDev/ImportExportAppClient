package org.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.project.clientMain.Main;

import java.io.IOException;

public class EmployeeMenuController {

    @FXML
    public TextArea textArea;

    //Работа с текущими экспортными сделками
    @FXML
    private void onClickShowExportTrades(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Информация об экспортных сделках*");
    }

    @FXML
    private void onClickAddExportTrade(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Добавление экспортной сделки*");
    }

    @FXML
    private void onClickChangeExportTrade(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Изменение данных экспортной сделки*");
    }

    @FXML
    private void onClickDeleteTrade(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Подтвердить отмену сделки*");
    }


    //Работа с текущими импортными сделками
    @FXML
    private void onClickShowImportTrades(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Информация об импортных сделках*");
    }

    @FXML
    private void onClickAddImportTrade(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Добавление импортной сделки*");
    }

    @FXML
    private void onClickChangeImportTrade(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Изменение данных импортной сделки*");
    }


    //Компании-партнеры
    @FXML
    private void onClickFullPartnersList(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Полный список партнеров*");
    }
    @FXML
    private void onClickYourPartners(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Партнеры, сделки с которыми Вы контролируете*");
    }

    //Номенклатура
    @FXML
    private void onClickShowItems(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Список товаров*");
    }
    @FXML
    private void onClickFilterItems(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Фильтрация товара*");
    }


    //Просмотр личной истории сделок
    @FXML
    private void onClickShowYourTradesHistory(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Показать историю сделок*");
    }
    @FXML
    private void onClickFilterTrades(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Фильтрация сделок*");
    }

    @FXML
    private void onClickSearchTrade(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Поиск сделки*");
    }


    //Личная статистика

    @FXML
    private void onClickProductivityChart(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*График продуктивности*");
    }


    //Статистика компании
    @FXML
    private void onClickCompanyReport(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Месячный отчет*");
    }

    //Аккаунт
    @FXML
    private void onClickChangeAccountData(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Изменить логин/пароль*");
    }

    @FXML
    private void onClickExit(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Main.setRoot("/main");
    }

}
