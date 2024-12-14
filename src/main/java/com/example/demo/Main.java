package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        ComboBox<String> tableSelector = new ComboBox<>();
        tableSelector.getItems().addAll("position", "employee", "rules", "MayBeParticipant", "request", "participant", "passingChalenges", "results");
        tableSelector.setPromptText("Выберите таблицу");

        Button viewTableButton = new Button("Просмотреть таблицу");
        viewTableButton.setOnAction(e -> {
            String tableName = tableSelector.getValue();
            if (tableName != null) {
                ViewTable.display(tableName);
            }
        });

        Button addRecordButton = new Button("Добавить запись");
        addRecordButton.setOnAction(e -> {
            String tableName = tableSelector.getValue();
            if (tableName != null) {
                AddRecord.addRecord(tableName);
            }
        });

        Button deleteRecordButton = new Button("Удалить запись");
        deleteRecordButton.setOnAction(e -> {
            String tableName = tableSelector.getValue();
            if (tableName != null) {
                DeleteRecord.deleteRecord(tableName);
            }
        });

        root.getChildren().addAll(tableSelector, viewTableButton, addRecordButton, deleteRecordButton);

        // Верстка
        VBox layout = new VBox(10, tableSelector, viewTableButton, addRecordButton, deleteRecordButton);
        layout.setStyle("-fx-alignment: center; -fx-spacing: 15; -fx-padding: 20; -fx-background-color: #2a2a2a;");
        tableSelector.setStyle("-fx-background-color: pink; -fx-text-fill: black;");
        viewTableButton.setStyle("-fx-background-color: pink; -fx-text-fill: black;");
        addRecordButton.setStyle("-fx-background-color: pink; -fx-text-fill: black;");
        deleteRecordButton.setStyle("-fx-background-color: pink; -fx-text-fill: black;");

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("КиберСпорт");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
