package com.epam.engx.cleancode.finaltask.task1.enums;

public enum MiddleSeparators {
    MIDDLE_LEFT_CORNER("╠"),
    MIDDLE_MIDDLE_CORNER("╬"),
    MIDDLE_RIGHT_CORNER("╣");

    private String value;

    MiddleSeparators(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
