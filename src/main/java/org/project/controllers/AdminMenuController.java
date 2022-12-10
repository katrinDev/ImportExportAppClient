package org.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import org.project.clientMain.Main;

import java.io.IOException;

public class AdminMenuController {

    @FXML
    private TextArea textArea;

    @FXML
    public AnchorPane anchorPane;


    //Работа с пользователями
    @FXML
    private void onClickShowUsers(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Список всех пользователей*");
    }
    @FXML
    private void onClickChangeRole(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Изменить роли*");
    }
    @FXML
    private void onClickChangeUserData(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Изменить данные пользователя*");
    }

    @FXML
    private void onClickDeleteUser(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Удалить пользователя*");
    }

    @FXML
    private void onClickAddUser(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Добавить пользователя*");
    }


    //Работа с сотрудниками
    @FXML
    private void onClickShowEmployees(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Показать список сотрудников*");
    }
    @FXML
    private void onClickEditEmployee(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Изменить данные сотрудника*");
    }
    @FXML
    private void onClickAddEmployee(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Добавить сотрудника*");
    }
    @FXML
    private void onClickDeleteEmployee(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Удалить сотрудника*");
    }


    //Работа с товарными группами
    @FXML
    private void onClickAddGroup(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Добавить группу товаров*");
    }

    @FXML
    private void onClickShowGroups(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Вывести список групп товаров*");
    }

    @FXML
    private void onClickDeleteGroup(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Удалить группу*");
    }

    @FXML
    private void onClickChangeGroupData(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Изменить данные группы*");
    }


    //Работа с номенклатурой товаров
    @FXML
    private void onClickShowItems(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Вывести списком номенклатуру компании*");
    }

    @FXML
    private void onClickChangeItem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Изменить данные продукта*");
    }

    @FXML
    private void onClickAddItem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Добавить продукт*");
    }

    @FXML
    private void onClickDeleteItem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        textArea.setText("*Удалить продукт*");
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
