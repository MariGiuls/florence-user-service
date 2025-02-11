package com.example.florence.user.service.service.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ValidationMethodEnum {
    SAVE("save"),

    CHANGE("change"),

    DELETE("delete"),

    FIND_BY_ID("findById"),

    FIND_BY("findBy");

    private final String value;

    ValidationMethodEnum(String value) {
        this.value = value;
    }

    public static ValidationMethodEnum fromValue(String value) {
        return Arrays.stream(values())
                .filter(e -> e.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid value: " + value));
    }
}
