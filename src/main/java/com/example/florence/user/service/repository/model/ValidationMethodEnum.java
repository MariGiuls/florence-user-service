package com.example.florence.user.service.repository.model;

public enum ValidationMethodEnum {
    SAVE("SAVE"),

    CHANGE("CHANGE"),

    DELETE("DELETE"),

    FIND_BY_ID("FIND_BY_ID"),

    FIND_BY("FIND_BY");

    private String value;

    ValidationMethodEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
