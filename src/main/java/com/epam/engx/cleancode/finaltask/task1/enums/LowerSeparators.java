package com.epam.engx.cleancode.finaltask.task1.enums;

public enum LowerSeparators {
    BOTTOM_LEFT_CORNER("╚"),
    BOTTOM_MIDDLE_CORNER("╩"),
    BOTTOM_RIGHT_CORNER("╝");

    private String value;

    LowerSeparators(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
