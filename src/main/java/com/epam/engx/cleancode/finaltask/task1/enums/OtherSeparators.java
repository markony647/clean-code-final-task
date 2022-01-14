package com.epam.engx.cleancode.finaltask.task1.enums;

public enum OtherSeparators {

    SIDE_BORDER("║"),
    LINE("═"),
    INDENT(" "),
    NEW_LINE("\n");

    private String value;

    OtherSeparators(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
