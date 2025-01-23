package com.example.demo;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Procedures {

    public static void updateEmployeePhone() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            // Диалог ввода ID и нового номера телефона
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Обновление телефона");
            dialog.setHeaderText("Введите ID сотрудника и новый номер телефона");

            // Ввод ID
            dialog.setContentText("Введите ID сотрудника:");
            String employeeId = dialog.showAndWait().orElse(null);

            // Ввод нового номера
            dialog.setContentText("Введите новый номер телефона:");
            String newPhoneNumber = dialog.showAndWait().orElse(null);

            if (employeeId != null && newPhoneNumber != null) {
                // Вызов процедуры
                String sql = "CALL update_employee_phone(?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, Long.parseLong(employeeId));
                statement.setString(2, newPhoneNumber);
                statement.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Телефон сотрудника успешно обновлен!");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sortEmployeesByName() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            // Диалог подтверждения
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Сортировка сотрудников по имени производится. Проверьте таблицу employee.");
            alert.show();

            // Пример SQL сортировки
            String sql = "SELECT * FROM employee ORDER BY name";
            connection.createStatement().execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
