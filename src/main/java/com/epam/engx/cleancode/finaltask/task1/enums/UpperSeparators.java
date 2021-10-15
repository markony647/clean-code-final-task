package com.epam.engx.cleancode.finaltask.task1.enums;

public enum UpperSeparators {
    UPPER_LEFT_CORNER("╔"),
    UPPER_MIDDLE_CORNER("╦"),
    UPPER_RIGHT_CORNER("╗");

    private String value;

    UpperSeparators(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
