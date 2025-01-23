package com.example.demo;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Functions {

    public static void getRulesByEmployee() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            // Ввод ID сотрудника
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Получить правила");
            dialog.setHeaderText("Введите ID сотрудника:");
            String employeeId = dialog.showAndWait().orElse(null);

            if (employeeId != null) {
                // Вызов функции
                String sql = "SELECT * FROM get_rules_by_employee(?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, Long.parseLong(employeeId));

                ResultSet resultSet = statement.executeQuery();

                // Вывод результата
                StringBuilder result = new StringBuilder("Правила:\n");
                while (resultSet.next()) {
                    result.append("ID: ").append(resultSet.getLong("rule_id"))
                            .append(", Название: ").append(resultSet.getString("rule_name"))
                            .append(", Содержание: ").append(resultSet.getString("rule_content"))
                            .append(", Дата создания: ").append(resultSet.getDate("rule_create_date"))
                            .append("\n");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(result.toString());
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void countRows() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            // Ввод имени таблицы
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Подсчет строк");
            dialog.setHeaderText("Введите название таблицы:");
            String tableName = dialog.showAndWait().orElse(null);

            if (tableName != null) {
                // Выполнение подсчета строк
                String sql = "SELECT COUNT(*) AS row_count FROM " + tableName;
                ResultSet resultSet = connection.createStatement().executeQuery(sql);

                if (resultSet.next()) {
                    long rowCount = resultSet.getLong("row_count");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Количество строк в таблице " + tableName + ": " + rowCount);
                    alert.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
