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
    private void onClickYourSuccessfulTrades(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Ваши успешные сделки месяца*");
    }

    @FXML
    private void onClickCompareYourResults(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Ваша статистика по месяцам*");
    }


    //Статистика компании
    @FXML
    private void onClickBestEmployee(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Сотрудник месяца*");
    }

    @FXML
    private void onClickCompanySuccessfulTrades(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Наиболее успешные сделки  для компании в этом месяце*");
    }

    @FXML
    private void onClickCompareCompanyResults(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Статистика компании по месяцам*");
    }


    //Сотрудники компании
    @FXML
    private void onClickShowEmployees(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Список сотрудников*");
    }

    @FXML
    private void onClickSearchEmployee(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Поиск сотрудника*");
    }


    //Личные аккаунты
    @FXML
    private void onClickExit(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Main.setRoot("/main");
    }
    @FXML
    private void onClickShowAccountData(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Данные текущего аккаунта*");
    }

    @FXML
    private void onClickChangeAccountData(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Изменить данные текущего аккаунта*");
    }

    @FXML
    private void onClickDeleteAccount(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Удалить текущий аккаунт*");
    }

    @FXML
    private void onClickShowAccounts(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Показать список личных аккаунтов*");
    }

}
