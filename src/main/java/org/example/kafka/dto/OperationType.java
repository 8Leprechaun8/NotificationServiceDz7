package org.example.kafka.dto;

public enum OperationType {

    CREATE("Создание"),
    DELETE("Удаление");

    private String value;

    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
