package com.example.florence.user.service.repository.model;

import lombok.Getter;

@Getter
public enum ValidationMethodEnum {
    SAVE("SAVE"),

    CHANGE("CHANGE"),

    DELETE("DELETE"),

    FIND_BY_ID("FIND_BY_ID"),

    FIND_BY("FIND_BY");

    private final String value;

    ValidationMethodEnum(String value) {
        this.value = value;
    }
}
