package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private static final Map<String, String> tableMap = new HashMap<>();

    static {
        // Сопоставление русских названий с английскими
        tableMap.put("Должности", "position");
        tableMap.put("Сотрудники", "employee");
        tableMap.put("Правила", "rules");
        tableMap.put("Возможные участники", "MayBeParticipant");
        tableMap.put("Запросы", "request");
        tableMap.put("Участники", "participant");
        tableMap.put("Прохождение испытаний", "passingChalenges");
        tableMap.put("Результаты", "results");
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-alignment: center; -fx-spacing: 15; -fx-padding: 20; -fx-background-color: #2a2a2a;");

        // Комбо-бокс для выбора таблицы
        ComboBox<String> tableSelector = new ComboBox<>();
        tableSelector.getItems().addAll(tableMap.keySet());
        tableSelector.setPromptText("Выберите таблицу");
        tableSelector.setStyle("-fx-background-color: pink; -fx-text-fill: black;");

        // Основные кнопки
        Button viewTableButton = new Button("Просмотреть таблицу");
        viewTableButton.setOnAction(e -> {
            String selectedTable = tableSelector.getValue();
            if (selectedTable != null) {
                String tableName = tableMap.get(selectedTable); // Получаем английское имя таблицы
                ViewTable.display(tableName);
            }
        });
        viewTableButton.setStyle("-fx-background-color: pink; -fx-text-fill: black;");

        Button addRecordButton = new Button("Добавить запись");
        addRecordButton.setOnAction(e -> {
            String selectedTable = tableSelector.getValue();
            if (selectedTable != null) {
                String tableName = tableMap.get(selectedTable);
                AddRecord.addRecord(tableName);
            }
        });
        addRecordButton.setStyle("-fx-background-color: pink; -fx-text-fill: black;");

        Button deleteRecordButton = new Button("Удалить запись");
        deleteRecordButton.setOnAction(e -> {
            String selectedTable = tableSelector.getValue();
            if (selectedTable != null) {
                String tableName = tableMap.get(selectedTable);
                DeleteRecord.deleteRecord(tableName);
            }
        });
        deleteRecordButton.setStyle("-fx-background-color: pink; -fx-text-fill: black;");

        // Дополнительные кнопки
        Button updatePhoneButton = new Button("Обновить телефон сотрудника");
        updatePhoneButton.setOnAction(e -> Procedures.updateEmployeePhone());
        updatePhoneButton.setStyle("-fx-background-color: pink; -fx-text-fill: black;");

        Button getRulesButton = new Button("Посмотреть правила, созданные сотрудником");
        getRulesButton.setOnAction(e -> Functions.getRulesByEmployee());
        getRulesButton.setStyle("-fx-background-color: pink; -fx-text-fill: black;");

        Button countRowsButton = new Button("Подсчитать количество строк в таблице");
        countRowsButton.setOnAction(e -> Functions.countRows());
        countRowsButton.setStyle("-fx-background-color: pink; -fx-text-fill: black;");

        Button sortEmployeesButton = new Button("Сортировать сотрудников по имени");
        sortEmployeesButton.setOnAction(e -> Procedures.sortEmployeesByName());
        sortEmployeesButton.setStyle("-fx-background-color: pink; -fx-text-fill: black;");

        // Добавление элементов в интерфейс
        root.getChildren().addAll(
                tableSelector,
                viewTableButton,
                addRecordButton,
                deleteRecordButton,
                updatePhoneButton,
                getRulesButton,
                countRowsButton,
                sortEmployeesButton
        );

        // Настройка сцены и отображение
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Киберспорт");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

