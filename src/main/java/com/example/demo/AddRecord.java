package com.example.demo;

import javafx.scene.control.TextInputDialog;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddRecord {
    public static void addRecord(String tableName) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Введите данные для таблицы " + tableName + " через запятую");

        dialog.showAndWait().ifPresent(input -> {
            try (Connection connection = DatabaseUtil.getConnection()) {
                // Получаем данные, разделенные запятыми
                String[] data = input.split(",");

                // Формируем SQL-запрос
                String sql = generateInsertQuery(tableName, data.length);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                // Подготовка данных для вставки
                for (int i = 0; i < data.length; i++) {
                    String value = data[i].trim();

                    // Приведение типа для числовых значений
                    if (isNumeric(value)) {
                        preparedStatement.setLong(i + 1, Long.parseLong(value)); // Если число, преобразуем в BIGINT
                    } else {
                        preparedStatement.setString(i + 1, value); // Иначе используем как строку
                    }
                }

                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static String generateInsertQuery(String tableName, int columnsCount) {
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < columnsCount; i++) {
            placeholders.append("?");
            if (i < columnsCount - 1) placeholders.append(", ");
        }
        return "INSERT INTO " + tableName + " VALUES (" + placeholders + ")";
    }

    // Проверяем, является ли строка числом
    private static boolean isNumeric(String str) {
        try {
            Long.parseLong(str); // Пробуем преобразовать строку в Long
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

