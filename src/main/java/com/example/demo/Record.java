package com.example.demo;

import javafx.beans.property.SimpleStringProperty;

import java.util.Arrays;

public class Record {
    private final SimpleStringProperty[] columns;

    public Record(int columnCount) {
        columns = new SimpleStringProperty[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columns[i] = new SimpleStringProperty("");
        }
    }

    public void setColumn(int index, String value) {
        columns[index].set(value);
    }

    public SimpleStringProperty getColumn(int index) {
        return columns[index];
    }

    public boolean matches(String query) {
        return Arrays.stream(columns)
                .anyMatch(column -> column.get().toLowerCase().contains(query.toLowerCase()));
    }
}
