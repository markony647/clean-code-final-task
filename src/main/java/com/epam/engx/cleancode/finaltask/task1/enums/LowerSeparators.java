package com.epam.engx.cleancode.finaltask.task1.enums;

public enum LowerSeparators {
    LOWER_LEFT_CORNER("╚"),
    LOWER_MIDDLE_CORNER("╩"),
    LOWER_RIGHT_CORNER("╝");

    private String value;

    LowerSeparators(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
