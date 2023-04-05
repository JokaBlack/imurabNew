package com.attractorschool.imurab.util.enums;

public enum FieldStatus {
    CREATED("Создан"),
    CONFIRMED("Под");
    private final String status;

    FieldStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
