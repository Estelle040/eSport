package com.example.demo;

import javafx.scene.control.TextInputDialog;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteRecord {
    public static void deleteRecord(String tableName) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Введите ID для удаления записи из таблицы " + tableName);

        dialog.showAndWait().ifPresent(input -> {
            try (Connection connection = DatabaseUtil.getConnection()) {
                String sql = "DELETE FROM " + tableName + " WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, Long.parseLong(input.trim()));
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
