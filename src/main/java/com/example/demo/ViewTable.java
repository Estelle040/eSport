package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewTable {
    public static void display(String tableName) {
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        TableView<Record> tableView = new TableView<>();
        ObservableList<Record> data = FXCollections.observableArrayList();

        // Поле для ввода текста поиска
        TextField searchField = new TextField();
        searchField.setPromptText("Введите текст для поиска...");

        // Кнопка для активации поиска
        Button searchButton = new Button("Поиск");

        // Кнопка будет запускать поиск при нажатии
        searchButton.setOnAction(event -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                tableView.setItems(FXCollections.observableArrayList(
                        data.filtered(record -> record.matches(searchText))
                ));
            } else {
                // Если строка поиска пустая, показываем все данные
                tableView.setItems(data);
            }
        });

        try (Connection connection = DatabaseUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);

            int columnCount = resultSet.getMetaData().getColumnCount();

            // Создание колонок на основе метаданных
            for (int i = 1; i <= columnCount; i++) {
                TableColumn<Record, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i));
                final int columnIndex = i - 1;
                column.setCellValueFactory(dataValue -> dataValue.getValue().getColumn(columnIndex));
                column.setSortable(true);
                tableView.getColumns().add(column);
            }

            // Заполнение данных в таблицу
            while (resultSet.next()) {
                Record record = new Record(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    record.setColumn(i - 1, resultSet.getString(i));
                }
                data.add(record);
            }

            tableView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Создание панели для поиска
        HBox searchBox = new HBox(10, new Label("Поиск:"), searchField, searchButton);
        searchBox.setPadding(new Insets(10));

        // Размещение элементов на сцене
        root.setTop(searchBox);
        root.setCenter(tableView);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Таблица: " + tableName);
        stage.show();
    }
}
